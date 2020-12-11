DROP PROCEDURE IF EXISTS CHECK_CREDITENTIALS;
DELIMITER //
CREATE PROCEDURE CHECK_CREDITENTIALS(IN `_username` VARCHAR(20), IN `_password` VARCHAR(45), OUT `validation` INT)
BEGIN
	IF ((SELECT COUNT(*) FROM `accounts` A WHERE A.`username`=`_username` AND A.`password`=`_password`) = 1) THEN
		SET `validation` = 1;
	ELSE
		SET `validation` = 0;
	END IF;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS INSERT_EMPLOYEE_SCHEDULE;
DELIMITER //
CREATE PROCEDURE INSERT_EMPLOYEE_SCHEDULE(IN `_cnpEmployee` VARCHAR(13), IN `_idMedicalUnit` INT, IN `_dayOfWeek` VARCHAR(10), IN `_startHour` TIME, IN `_endHour` TIME, OUT `validation` INT)
BEGIN
	IF (`_startHour` < `_endHour`
		AND (SELECT COUNT(*) FROM `medical_unit_schedule` MS WHERE MS.`idMedicalUnit`=`_idMedicalUnit` AND MS.`dayOfWeek`=`_dayOfWeek` AND MS.`startHour`<=`_startHour` AND MS.`endHour`>=`_endHour`) > 0
		AND (SELECT COUNT(*) FROM `employee_schedule` ES WHERE ES.`cnpEmployee`=`_cnpEmployee` AND ES.`dayOfWeek`=`_dayOfWeek` AND ((ES.`startHour`>=`_startHour` AND ES.`startHour`<`_endHour` ) OR (ES.`endHour`>`_startHour` AND ES.`endHour`<=`_endHour`) OR (ES.`startHour`<`_startHour` AND ES.`endHour`>`_endHour`))) = 0) THEN
		SET `validation` = 1;
		INSERT INTO `employee_schedule` (`cnpEmployee`, `idMedicalUnit`, `dayOfWeek`, `startHour`, `endHour`) VALUES (`_cnpEmployee`, `_idMedicalUnit` , `_dayOfWeek`, `_startHour` , `_endHour`);
	ELSE
		SET `validation` = 0;
	END IF;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS DELETE_EMPLOYEE_SCHEDULE;
DELIMITER //
CREATE PROCEDURE DELETE_EMPLOYEE_SCHEDULE(IN `_cnpEmployee` VARCHAR(13), IN `_idMedicalUnit` INT, IN `_dayOfWeek` VARCHAR(10), IN `_startHour` TIME, IN `_endHour` TIME, IN `force` INT, OUT `validation` INT)
BEGIN
	IF ((SELECT COUNT(*) FROM `employee_schedule` WHERE `cnpEmployee`=`_cnpEmployee` AND `idMedicalUnit`=`_idMedicalUnit` AND `dayOfWeek` LIKE `_dayOfWeek` AND `startHour`>=`_startHour` AND `endHour`<=`_endHour`) >= 1) THEN
		IF ((SELECT `position` FROM `employees` WHERE `cnp`=`_cnpEmployee`) NOT LIKE 'Medic'
			OR `force`='1'
			OR(SELECT COUNT(*) FROM `appointments` WHERE `cnpDoctor`=`_cnpEmployee` AND DAYNAME(`date`) LIKE `_dayOfWeek` AND TIME(`date`)>=`_startHour` AND TIME(`date`)<=`_endHour`) = 0) THEN
            
			DELETE FROM `employee_schedule` WHERE `cnpEmployee`=`_cnpEmployee` AND `idMedicalUnit`=`_idMedicalUnit` AND `dayOfWeek` LIKE `_dayOfWeek` AND `startHour`>=`_startHour` AND `endHour`<=`_endHour`;
			SET `validation` = 1; -- s-a executat
		ELSE
			SET `validation` = 0; -- sunt programari care trebuiesc sterse
		END IF;
	ELSE
		SET `validation` = 2; -- nu exista intrare in orar pentru datele furnizate
    END IF;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS UPDATE_EMPLOYEE_SCHEDULE;
DELIMITER //
CREATE PROCEDURE UPDATE_EMPLOYEE_SCHEDULE(IN `_cnpEmployee` VARCHAR(13), IN `_idMedicalUnit` INT, IN `_dayOfWeek` VARCHAR(10), IN `_startHour` TIME, IN `_endHour` TIME, IN `new_startHour` TIME, IN `new_endHour` TIME, IN `force` INT, OUT `validation` INT)
BEGIN
	IF ((SELECT COUNT(*) FROM `employee_schedule` WHERE `cnpEmployee`=`_cnpEmployee` AND `idMedicalUnit`=`_idMedicalUnit` AND `dayOfWeek` LIKE `_dayOfWeek` AND `startHour`=`_startHour` AND `endHour`=`_endHour`) = 1) THEN
		IF ((SELECT `position` FROM `employees` WHERE `cnp`=`_cnpEmployee`) NOT LIKE 'Medic'
			OR `force`='1'
			OR(SELECT COUNT(*) FROM `appointments` WHERE `cnpDoctor`=`_cnpEmployee` AND DAYNAME(`date`) LIKE `_dayOfWeek` AND TIME(`date`)>=`_startHour` AND TIME(`date`)<=`_endHour`) = 0) THEN
            
			UPDATE `employee_schedule` SET `startHour`=`new_startHour`, `endHour`=`new_endHour` WHERE `cnpEmployee`=`_cnpEmployee` AND `idMedicalUnit`=`_idMedicalUnit` AND `dayOfWeek` LIKE `_dayOfWeek` AND `startHour`=`_startHour` AND `endHour`=`_endHour`;
			SET `validation` = 1; -- s-a executat
		ELSE
			SET `validation` = 0; -- sunt programari care trebuiesc sterse
		END IF;
	ELSE
		SET `validation` = 2; -- nu exista intrare in orar pentru datele furnizate
    END IF;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS INSERT_HOLIDAY;
DELIMITER //
CREATE PROCEDURE INSERT_HOLIDAY(IN `_cnpEmployee` VARCHAR(13), IN `_startDate` DATE, IN `_endDate` DATE, OUT `validation` INT)
BEGIN
	IF ((SELECT COUNT(*) FROM `holidays` WHERE `cnpEmployee`=`_cnpEmployee` AND ((`startDate`>=`_startDate` AND `startDate`<=`_endDate`) OR (`endDate`>=`_startDate` AND `endDate`<=`_endDate`) OR (`startDate`<`_startDate` AND `endDate`>`_endDate`))) = 0) THEN
		INSERT INTO `holidays` (`cnpEmployee`, `startDate`, `endDate`) VALUES (`_cnpEmployee`, `_startDate`, `_endDate`);
		SET `validation` = 1;
	ELSE
		SET `validation` = 0;
	END IF;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS INSERT_EMPLOYEE;
DELIMITER //
CREATE PROCEDURE INSERT_EMPLOYEE(IN `_cnp` VARCHAR(13), IN `_lastName` VARCHAR(25), IN `_firstName` VARCHAR(50), IN `_address` VARCHAR(100), IN `_phoneNum` VARCHAR(10), IN `_email` VARCHAR(45), IN `_iban` VARCHAR(45), IN `_contractNum` INT, IN `_employmentDate` DATE, IN `_position` VARCHAR(25), IN `_salary` DECIMAL(10,2), IN `_workedHrs` INT, OUT `validation` INT)
BEGIN
	IF ((SELECT COUNT(*) FROM `employees` WHERE `cnp`=`_cnp`) = 0) THEN
		INSERT INTO `employees` (`cnp`, `lastName`, `firstName`, `address`, `phoneNum`, `email`, `iban`, `contractNum`, `employmentDate`, `position`, `salary`, `workedHrs`) VALUES (`_cnp`, `_lastName`, `_firstName`, `_address`, `_phoneNum`, `_email`, `_iban`, `_contractNum`, `_employmentDate`, `_position`, `_salary`, `_workedHrs`);
		SET `validation` = 1;
	ELSE
		SET `validation` = 0;
	END IF;
END;
// DELIMITER ;


-- teste
DROP PROCEDURE IF EXISTS TEST;
DELIMITER //
CREATE PROCEDURE TEST()
BEGIN
	SET @output = -1;
	-- CALL CHECK_CREDITENTIALS('admin1', 'adminpass', @output);
	-- CALL INSERT_EMPLOYEE_SCHEDULE('2700927417309', '3', 'Monday', '08:00:00', '12:00:00', @output);
	-- CALL INSERT_EMPLOYEE_SCHEDULE('2700927417309', '3', 'Monday', '07:59:00', '09:00:00', @output);
	-- CALL INSERT_EMPLOYEE_SCHEDULE('2700927417309', '3', 'Monday', '11:00:00', '11:00:01', @output);
	-- CALL DELETE_EMPLOYEE_SCHEDULE('2700927417309', '3', 'Monday', '12:00:00', '18:00:00', 1, @output);
    -- CALL UPDATE_EMPLOYEE_SCHEDULE('2700927417309', '3', 'Monday', '13:00:00', '16:00:00', '14:55:00', '16:00:00', 1, @output);
    -- CALL INSERT_HOLIDAY('2700927417309', '2020-12-13', '2020-12-15', @output);
    CALL INSERT_EMPLOYEE('2700735934101', 'Spatariu', 'Diana', 'Cluj-Napoca, str. Nicolae Iorga nr. 6', '0783527882', 'spatariu.diana@gmail.com', 'RO64RZBR3277465196914272', '30', '2017-02-20', 'Receptioner', '3470', '120', @output);
    SELECT @output;
END;
// DELIMITER ;

CALL TEST();

-- INGORA
-- (TIME_TO_SEC(TIME(`date`)) + `duration` * 60)>=TIME_TO_SEC(`_startHour`) AND TIME(`date`)<=`_endHour`)

-- SELECT * FROM `employees` WHERE `lastName`='?' AND `firstName`='?' AND `position`='?';
-- SELECT * FROM `employee_schedule` WHERE `cnpEmployee`='?';
-- SELECT * FROM `holidays` WHERE `cnpEmployee`='?';