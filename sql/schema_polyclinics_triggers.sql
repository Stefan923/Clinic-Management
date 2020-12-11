DROP TRIGGER IF EXISTS ON_EMPLOYEE_SCHEDULE_DELETE;
DELIMITER //
CREATE TRIGGER ON_EMPLOYEE_SCHEDULE_DELETE BEFORE DELETE ON `employee_schedule` FOR EACH ROW
BEGIN
	IF ((SELECT E.`position` FROM `employees` E WHERE E.`cnp`=OLD.`cnpEmployee`) LIKE 'Medic') THEN
		DELETE FROM `appointments` A WHERE A.`cnpDoctor`=OLD.`cnpEmployee` AND DAYNAME(A.`date`)=OLD.`dayOfWeek` AND TIME(A.`date`)>=OLD.`startHour` AND TIME(A.`date`)<=OLD.`endHour`;
	END IF;
END;
// DELIMITER ;