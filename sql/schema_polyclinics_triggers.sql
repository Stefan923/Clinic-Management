DROP TRIGGER IF EXISTS ON_EMPLOYEE_SCHEDULE_DELETE;
DELIMITER //
CREATE TRIGGER ON_EMPLOYEE_SCHEDULE_DELETE BEFORE DELETE ON `employee_schedule` FOR EACH ROW
BEGIN
	IF ((SELECT E.`position` FROM `employees` E WHERE E.`cnp`=OLD.`cnpEmployee`) LIKE 'Medic') THEN
		DELETE FROM `appointments` A WHERE A.`cnpDoctor`=OLD.`cnpEmployee` AND DAYNAME(A.`date`)=OLD.`dayOfWeek` AND TIME(A.`date`)>=OLD.`startHour` AND TIME(A.`date`)<=OLD.`endHour`;
	END IF;
END;
// DELIMITER ;

DROP TRIGGER IF EXISTS ON_EMPLOYEE_SCHEDULE_UPDATE;
DELIMITER //
CREATE TRIGGER ON_EMPLOYEE_SCHEDULE_UPDATE BEFORE UPDATE ON `employee_schedule` FOR EACH ROW
BEGIN
	IF ((SELECT E.`position` FROM `employees` E WHERE E.`cnp`=OLD.`cnpEmployee`) LIKE 'Medic') THEN
		DELETE FROM `appointments` A WHERE A.`cnpDoctor`=OLD.`cnpEmployee` AND DAYNAME(A.`date`)=OLD.`dayOfWeek` AND TIME(A.`date`)>=OLD.`startHour` AND TIME(A.`date`)<=OLD.`endHour` AND (TIME(A.`date`)<NEW.`startHour` OR TIME(A.`date`)>NEW.`endHour`);
	END IF;
END;
// DELIMITER ;



DROP TRIGGER IF EXISTS ON_HOLIDAY_INSERT;
DELIMITER //
CREATE TRIGGER ON_HOLIDAY_INSERT AFTER INSERT ON `holidays` FOR EACH ROW
BEGIN
	IF ((SELECT E.`position` FROM `employees` E WHERE E.`cnp`=NEW.`cnpEmployee`) LIKE 'Medic') THEN
		DELETE FROM `appointments` A WHERE A.`cnpDoctor`=NEW.`cnpEmployee` AND DATE(A.`date`)>=NEW.`startDate` AND DATE(A.`date`)<=NEW.`endDate`;
	END IF;
END;
// DELIMITER ;

DROP TRIGGER IF EXISTS ON_EMPLOYEE_INSERT;
DELIMITER //
CREATE TRIGGER ON_EMPLOYEE_INSERT AFTER INSERT ON `employees` FOR EACH ROW
BEGIN
	SET @count = 0, @idRole = 1;
    SET @lastName = SUBSTRING_INDEX(SUBSTRING_INDEX(LOWER(NEW.`lastName`), ' ', 1), '-', 1);
    SET @firstName = LOWER(SUBSTRING(NEW.`firstName`, 1, 1));
	SET @username = CONCAT(@firstName, '.', @lastName);
    WHILE ((SELECT COUNT(*) FROM `accounts` WHERE `username`=@username) = 1) DO
		SET @count = @count + 1;
		SET @username = CONCAT(@firstName, '.', @lastName, @count);
	END WHILE;
    
    IF (NEW.`position` LIKE 'Receptioner') THEN
		SET @idRole = 2;
    ELSEIF (NEW.`position` LIKE 'HR') THEN
		SET @idRole = 3;
    ELSEIF (NEW.`position` LIKE 'Asistent Medical') THEN
		SET @idRole = 4;
    ELSEIF (NEW.`position` LIKE 'Medic') THEN
		SET @idRole = 5;
    ELSEIF (NEW.`position` LIKE 'Contabil') THEN
		SET @idRole = 6;
	END IF;
    
	INSERT INTO `accounts` (`cnpEmployee`, `username`, `password`, `idRole`) VALUES (NEW.`cnp`, @username, CONCAT(NEW.`cnp`, @firstName),  @idRole);
END;
// DELIMITER ;

DROP TRIGGER IF EXISTS ON_EMPLOYEE_DELETE;
DELIMITER //
CREATE TRIGGER ON_EMPLOYEE_DELETE BEFORE DELETE ON `employees` FOR EACH ROW
BEGIN
	DELETE FROM `accounts` WHERE `cnpEmployee` = OLD.`cnp`;
	DELETE FROM `appointments` WHERE `cnpDoctor` = OLD.`cnp`;
	DELETE FROM `holidays` WHERE `cnpEmployee` = OLD.`cnp`;
	DELETE FROM `employee_schedule` WHERE `cnpEmployee` = OLD.`cnp`;
	DELETE FROM `nurse` WHERE `cnpEmployee` = OLD.`cnp`;
	DELETE FROM `medical_services` WHERE `cnpDoctor` = OLD.`cnp`;
	DELETE FROM `doctor_accreditations` WHERE `cnpDoctor` = OLD.`cnp`;
	DELETE FROM `doctor_specialities` WHERE `cnpDoctor` = OLD.`cnp`;
	DELETE FROM `doctors` WHERE `cnpEmployee` = OLD.`cnp`;
END;
// DELIMITER ;

DROP TRIGGER IF EXISTS ON_PATIENT_DELETE;
DELIMITER //
CREATE TRIGGER ON_PATIENT_DELETE BEFORE DELETE ON `patients` FOR EACH ROW
BEGIN
	DELETE FROM `appointments` WHERE `cnpPatient` = OLD.`cnp`;
	DELETE FROM `reports` WHERE `cnpPatient` = OLD.`cnp`;
	DELETE FROM `patient_analyses` WHERE `cnpPatient` = OLD.`cnp`;
END;
// DELIMITER ;

DROP TRIGGER IF EXISTS ON_REPORT_DELETE;
DELIMITER //
CREATE TRIGGER ON_REPORT_DELETE BEFORE DELETE ON `reports` FOR EACH ROW
BEGIN
	DELETE FROM `report_investigations` WHERE `idReport` = OLD.`id`;
END;
// DELIMITER ;

DROP TRIGGER IF EXISTS ON_APPOINTMENT_DELETE;
DELIMITER //
CREATE TRIGGER ON_APPOINTMENT_DELETE BEFORE DELETE ON `appointments` FOR EACH ROW
BEGIN
	DELETE FROM `appointment_services` WHERE `idAppointment` = OLD.`id`;
END;
// DELIMITER ;

DROP TRIGGER IF EXISTS ON_MEDICAL_SERVICE_DELETE;
DELIMITER //
CREATE TRIGGER ON_MEDICAL_SERVICE_DELETE BEFORE DELETE ON `medical_services` FOR EACH ROW
BEGIN
	DELETE FROM `appointment_services` WHERE `idMedicalService` = OLD.`id`;
END;
// DELIMITER ;

DROP TRIGGER IF EXISTS ON_ADD_POSITION;
DELIMITER //
CREATE TRIGGER ON_ADD_POSITION AFTER INSERT ON `employees` FOR EACH ROW
BEGIN
	IF (((SELECT E.`position` FROM `employees` E WHERE E.`cnp`=NEW.`cnp`) LIKE 'Medic') AND (select COUNT(*) from doctors where cnpEmployee=NEW.`cnp`)=0) THEN
		INSERT INTO `doctors` (`cnpEmployee`) VALUES (NEW.`cnp`);
	END IF;
    IF (((SELECT E.`position` FROM `employees` E WHERE E.`cnp`=NEW.`cnp`) LIKE 'Asistent Medical') AND (select COUNT(*) from nurse where cnpEmployee=NEW.`cnp`)=0) THEN
		INSERT INTO `nurse` (`cnpEmployee`) VALUES (NEW.`cnp`);
	END IF;
END;
// DELIMITER ;