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