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
IF (`new_startHour` < `new_endHour`
		AND (SELECT COUNT(*) FROM `medical_unit_schedule` MS WHERE MS.`idMedicalUnit`=`_idMedicalUnit` AND MS.`dayOfWeek`=`_dayOfWeek` AND MS.`startHour`<=`new_startHour` AND MS.`endHour`>=`new_endHour`) > 0
		AND (SELECT COUNT(*) FROM `employee_schedule` ES WHERE ES.`cnpEmployee`=`_cnpEmployee` AND ES.`dayOfWeek`=`_dayOfWeek` AND ES.`startHour`<>`_startHour` AND ES.`endHour`<>`_endHour` AND ((ES.`startHour`>=`new_startHour` AND ES.`startHour`<`new_endHour` ) OR (ES.`endHour`>`new_startHour` AND ES.`endHour`<=`new_endHour`) OR (ES.`startHour`<`new_startHour` AND ES.`endHour`>`new_endHour`))) = 0) THEN
		IF ((SELECT `position` FROM `employees` WHERE `cnp`=`_cnpEmployee`) NOT LIKE 'Medic'
			OR `force`='1'
			OR (SELECT COUNT(*) FROM `appointments` WHERE `cnpDoctor`=`_cnpEmployee` AND DAYNAME(`date`) LIKE `_dayOfWeek` AND TIME(`date`)>=`_startHour` AND TIME(`date`)<=`_endHour`) = 0) THEN
            
			UPDATE `employee_schedule` SET `startHour`=`new_startHour`, `endHour`=`new_endHour` WHERE `cnpEmployee`=`_cnpEmployee` AND `idMedicalUnit`=`_idMedicalUnit` AND `dayOfWeek` LIKE `_dayOfWeek` AND `startHour`=`_startHour` AND `endHour`=`_endHour`;
			SET `validation` = 1; -- s-a executat
		ELSE
			SET `validation` = 0; -- sunt programari care trebuiesc sterse
		END IF;
        ELSE
			SET `validation` = 0;
    END IF;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS INSERT_HOLIDAY;
DELIMITER //
CREATE PROCEDURE INSERT_HOLIDAY(IN `_cnpEmployee` VARCHAR(13), IN `_startDate` DATE, IN `_endDate` DATE, IN `force` INT, OUT `validation` INT)
BEGIN
	IF ((SELECT COUNT(*) FROM `holidays` WHERE `cnpEmployee`=`_cnpEmployee` AND ((`startDate`>=`_startDate` AND `startDate`<=`_endDate`) OR (`endDate`>=`_startDate` AND `endDate`<=`_endDate`) OR (`startDate`<`_startDate` AND `endDate`>`_endDate`))) = 0) THEN
		IF ((SELECT `position` FROM `employees` WHERE `cnp`=`_cnpEmployee`) NOT LIKE 'Medic'
			OR `force`='1'
            OR (SELECT COUNT(*) FROM `appointments` WHERE `cnpDoctor`=`_cnpEmployee` AND DATE(`date`) >= `_startDate` AND DATE(`date`) <= `_endDate`) = 0) THEN
        
			INSERT INTO `holidays` (`cnpEmployee`, `startDate`, `endDate`) VALUES (`_cnpEmployee`, `_startDate`, `_endDate`);
			SET `validation` = 1; -- s-a executat
		ELSE
			SET `validation` = 0; -- sunt programari care trebuiesc sterse
		END IF;
	ELSE
		SET `validation` = 0; -- nu exista intrare in orar pentru datele furnizate
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

DROP PROCEDURE IF EXISTS INSERT_SPECIALITY;
DELIMITER //
CREATE PROCEDURE INSERT_SPECIALITY(IN `_cnpDoctor` VARCHAR(13), IN `_idSpeciality` INT, IN `_rank` varchar(20),OUT `validation` INT)
BEGIN
	IF ((SELECT COUNT(*) from `doctors` where `cnpEmployee` =`_cnpDoctor`) > 0) THEN
		INSERT INTO `doctor_specialities` (`cnpDoctor` ,  `idSpeciality`,`rank`) VALUES (`_cnpDoctor` ,  `_idSpeciality`,`_rank`);
		SET `validation` = 1;
	ELSE
		SET `validation` = 0;
	END IF;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS DELETE_SPECIALITY;
DELIMITER //
CREATE PROCEDURE DELETE_SPECIALITY(IN `_cnpDoctor` VARCHAR(13), IN `_idSpeciality` INT,OUT `validation` INT)
BEGIN
	IF ((SELECT COUNT(*) from `doctor_specialities` WHERE `cnpDoctor`=`_cnpDoctor` and `idSpeciality`=`_idSpeciality`) > 0) THEN
		DELETE FROM `doctor_specialities` WHERE `cnpDoctor`=`_cnpDoctor` and `idSpeciality`=`_idSpeciality`;
		SET `validation` = 1;
	ELSE
		SET `validation` = 0;
	END IF;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS INSERT_ACCREDITATION;
DELIMITER //
CREATE PROCEDURE INSERT_ACCREDITATION(IN `_cnpDoctor` VARCHAR(13), IN `_idAccreditation` INT,OUT `validation` INT)
BEGIN
	IF ((SELECT COUNT(*) from `doctors` where `cnpEmployee` =`_cnpDoctor`) > 0) THEN
		INSERT INTO `doctor_accreditations` (`cnpDoctor` ,  `idAccreditation`) VALUES (`_cnpDoctor` ,  `_idAccreditation`);
		SET `validation` = 1;
	ELSE
		SET `validation` = 0;
	END IF;
END;
// DELIMITER ;





DROP PROCEDURE IF EXISTS DELETE_ACCREDITATION;
DELIMITER //
CREATE PROCEDURE DELETE_ACCREDITATION(IN `_cnpDoctor` VARCHAR(13), IN `_idAccreditation` INT,OUT `validation` INT)
BEGIN
	IF ((SELECT COUNT(*) from `doctor_accreditations` WHERE `cnpDoctor`=`_cnpDoctor` and `idAccreditation`=`_idAccreditation`) > 0) THEN
		DELETE FROM `doctor_accreditations` WHERE `cnpDoctor`=`_cnpDoctor` and `idAccreditation`=`_idAccreditation`;
		SET `validation` = 1;
	ELSE
		SET `validation` = 0;
	END IF;
END;
// DELIMITER ;


DROP PROCEDURE IF EXISTS INSERT_DOCTOR;
DELIMITER //
CREATE PROCEDURE INSERT_DOCTOR(IN `_cnpEmployee` VARCHAR(13), IN `_sealcode` VARCHAR(25), IN `_commission` decimal(3,2), IN `_scientificTitle` VARCHAR(100), IN `_didacticTitle` VARCHAR(10),OUT `validation` INT)
BEGIN
		SET `validation` = 0;
		if (select count(*) from `doctors` where `cnpEmployee` =`_cnpEmployee`)>0 then
			update `doctors` set `didacticTitle`=`_didacticTitle` , `scientificTitle`=`_scientificTitle`, `commission`=`_commission`, `sealcode`=`_sealcode` where `cnpEmployee`=`_cnpEmployee`;
            SET `validation` = 1;
		else
			if( select count(*) from `employees` where `cnp`=`_cnpEmployee`)>0 then
			insert into `doctors` (`cnpEmployee`, `sealcode`, `commission`,`scientificTitle`,`didacticTitle`) value (`_cnpEmployee`, `_sealcode`, `_commission`,`_scientificTitle`,`_didacticTitle`);
            SET `validation` = 1;
            end if;
		end if;

END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS DELETE_EMPLOYEE;
DELIMITER //
CREATE PROCEDURE DELETE_EMPLOYEE(IN `_cnp` VARCHAR(13), OUT `validation` INT)
BEGIN
	IF ((SELECT COUNT(*) FROM `employees` WHERE `cnp`=`_cnp`) = 1) THEN
		DELETE FROM `employees` WHERE `cnp`=`_cnp`;
		SET `validation` = 1;
	ELSE
		SET `validation` = 0;
	END IF;
END;
// DELIMITER ;


DROP PROCEDURE IF EXISTS UPDATE_EMPLOYEE;
DELIMITER //
CREATE PROCEDURE UPDATE_EMPLOYEE(IN `_cnp` VARCHAR(13), IN `_lastName` VARCHAR(25), IN `_firstName` VARCHAR(50), IN `_address` VARCHAR(100), IN `_phoneNum` VARCHAR(10), IN `_email` VARCHAR(45),OUT `validation` INT)
BEGIN
	IF ((SELECT COUNT(*) FROM `employees` WHERE `cnp`=`_cnp`) = 1) THEN
		SET @sqlAction = CONCAT("UPDATE employees SET lastName = '", `_lastName`, "', firstName = '", `_firstName`, "' , address='",`_address`,"' , phoneNum='",`_phoneNum`,"' , email='",`_email`,"' WHERE cnp='", `_cnp`, "';");
		PREPARE statement FROM @sqlAction;
		EXECUTE statement;
		SET `validation` = 1;
	ELSE
		SET `validation` = 0;
	END IF;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS GET_TOTAL_PROFIT;
DELIMITER //
CREATE PROCEDURE GET_TOTAL_PROFIT(IN `startDate` DATE, IN `endDate` DATE, OUT `profit` DECIMAL(10,2))
BEGIN
	SET @income = 0, @outcome = 0;
	SELECT IFNULL(SUM(`amount`), 0) INTO @income FROM `transactions` WHERE DATE(`date`) >= `startDate` AND DATE(`date`) <= `endDate` AND `type` = 'income';
	SELECT IFNULL(SUM(`amount`), 0) INTO @outcome FROM `transactions` WHERE DATE(`date`) >= `startDate` AND DATE(`date`) <= `endDate` AND `type` = 'outcome';
    SET `profit` = @income - @outcome;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS GET_MEDICAL_UNIT_PROFIT;
DELIMITER //
CREATE PROCEDURE GET_MEDICAL_UNIT_PROFIT(IN `_iban` VARCHAR(50), IN `startDate` DATE, IN `endDate` DATE, OUT `profit` DECIMAL(10,2))
BEGIN
	SET @income = 0, @outcome = 0;
    SET @iban = (SELECT `iban` FROM `medical_units` WHERE `iban` = `_iban` LIMIT 1);
    
    IF (@iban IS NOT NULL) THEN
		SELECT IFNULL(SUM(`amount`), 0) INTO @income FROM `transactions` WHERE `receiver` = @iban AND DATE(`date`) >= `startDate` AND DATE(`date`) <= `endDate` AND `type` = 'income';
		SELECT IFNULL(SUM(`amount`), 0) INTO @outcome FROM `transactions` WHERE `sender` = @iban AND DATE(`date`) >= `startDate` AND DATE(`date`) <= `endDate` AND `type` = 'outcome';
    END IF;
    
    SET `profit` = @income - @outcome;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS GET_PROFIT_BY_SPECIALITY;
DELIMITER //
CREATE PROCEDURE GET_PROFIT_BY_SPECIALITY(IN `_id` INT, IN `startDate` DATE, IN `endDate` DATE, OUT `profit` DECIMAL(10,2))
BEGIN
	SELECT IFNULL(SUM(M.`price` * A.`count`), 0) INTO `profit`
		FROM (SELECT `idMedicalService`, COUNT(*) AS `count`
			FROM `appointment_services` A, `appointments` AP
            WHERE AP.`idSpeciality` = `_id` AND AP.`id` = A.`idAppointment` AND AP.`hasReceipt` = 1 AND DATE(AP.`date`) >= `startDate` AND DATE(AP.`date`) <= `endDate` GROUP BY A.`idMedicalService`) A, `medical_services` M
		WHERE M.`id` = A.`idMedicalService`;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS GET_DOCTOR_PROFIT_TOTAL;
DELIMITER //
CREATE PROCEDURE GET_DOCTOR_PROFIT_TOTAL(IN `_cnp` VARCHAR(13), IN `startDate` DATE, IN `endDate` DATE, OUT `result` DECIMAL(10,2))
BEGIN
	SELECT IFNULL(SUM(M.`price` * A.`count`), 0) INTO `result`
		FROM (SELECT `idMedicalService`, COUNT(*) AS `count` 
			FROM `appointment_services` A, `appointments` AP
            WHERE AP.`cnpDoctor` = `_cnp` AND AP.`id` = A.`idAppointment` AND AP.`hasReceipt` = 1 AND DATE(AP.`date`) >= `startDate` AND DATE(AP.`date`) <= `endDate` GROUP BY A.`idMedicalService`) A, `medical_services` M
		WHERE M.`id` = A.`idMedicalService`;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS GET_HOLIDAY_HRS;
DELIMITER //
CREATE PROCEDURE GET_HOLIDAY_HRS(IN `_cnp` VARCHAR(13), IN `_startDate` DATE, IN `_endDate` DATE, OUT `result` INT)
BEGIN
	SET @tempHrs = 0, @holidayHrs = 0, @date1 = `_startDate`;
    
    WHILE (@date1 <= `_endDate`) DO
		IF ((SELECT COUNT(*) FROM `holidays` H 
				WHERE H.`cnpEmployee` = `_cnp` AND @date1 >= H.`startDate` AND @date1 <= H.`endDate`) > 0) THEN
				
			SELECT IFNULL(SUM(HOUR(TIMEDIFF(ES.`startHour`, ES.`endHour`))), 0) INTO @tempHrs
				FROM `employee_schedule` ES
				WHERE ES.`cnpEmployee` = `_cnp` AND DAYNAME(@date1) = ES.`dayOfWeek`;
			
            SET @holidayHrs = @holidayHrs + @tempHrs;
		END IF;
        
		SET @date1 = DATE_ADD(@date1, INTERVAL 1 DAY);
	END WHILE;
    
    SET `result` = @holidayHrs;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS GET_EMPLOYEE_SALARY;
DELIMITER //
CREATE PROCEDURE GET_EMPLOYEE_SALARY(IN `_cnp` VARCHAR(13), IN `startDate` DATE, IN `endDate` DATE, OUT `result` DECIMAL(10,2))
BEGIN
	SET @workedHrs = 0, @holidayHrs = 0, @date1 = `startDate`;
	/*SELECT IFNULL(SUM(HOUR(TIMEDIFF(`startHour`, `endHour`))), 0) INTO @workedHrs
		FROM `employee_schedule` ES
		WHERE ES.`cnpEmployee` = `_cnp`;*/
    
    WHILE (@date1 <= `endDate`) DO
		SET @tempHrs = 0;
		SELECT IFNULL(SUM(HOUR(TIMEDIFF(ES.`startHour`, ES.`endHour`))), 0) INTO @tempHrs
			FROM `employee_schedule` ES
			WHERE ES.`cnpEmployee` = `_cnp` AND DAYNAME(@date1) = ES.`dayOfWeek`;
			
		SET @workedHrs = @workedHrs + @tempHrs;
        
		SET @date1 = DATE_ADD(@date1, INTERVAL 1 DAY);
	END WHILE;
	
	CALL GET_HOLIDAY_HRS(`_cnp`, `startDate`, `endDate`, @holidayHrs);
	
	SELECT 	IFNULL((((@workedHrs - @holidayHrs) * E.`salary`) / E.`workedHrs`), 0) INTO `result`
		FROM `employees` E
		WHERE E.`cnp` = `_cnp`;
END;
// DELIMITER ;

-- dupa programari, nu dupa orar
DROP PROCEDURE IF EXISTS GET_DOCTOR_SALARY;
DELIMITER //
CREATE PROCEDURE GET_DOCTOR_SALARY(IN `_cnp` VARCHAR(13), IN `startDate` DATE, IN `endDate` DATE, OUT `result` DECIMAL(10,2))
BEGIN
	SET @workedHrs = 0, @commission = 0;
	SELECT IFNULL(SUM(M.`duration` * A.`count`) / 60, 0) INTO @workedHrs
		FROM (SELECT `idMedicalService`, COUNT(*) AS `count` 
			FROM `appointment_services` A, `appointments` AP
            WHERE AP.`cnpDoctor` = `_cnp` AND AP.`id` = A.`idAppointment` AND AP.`hasReceipt` = 1 AND DATE(AP.`date`) >= `startDate` AND DATE(AP.`date`) <= `endDate` GROUP BY A.`idMedicalService`) A, `medical_services` M
		WHERE M.`id` = A.`idMedicalService`;
	
    CALL GET_DOCTOR_PROFIT_TOTAL(`_cnp`, `startDate`, `endDate`, @commission);
	
	SELECT (((@workedHrs * E.`salary`) / E.`workedHrs`) + (@commission * D.`commission`)) INTO `result`
		FROM `employees` E, `doctors` D
		WHERE E.`cnp` = `_cnp` AND D.`cnpEmployee` = `_cnp`;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS GET_PROFIT_BY_DOCTOR;
DELIMITER //
CREATE PROCEDURE GET_PROFIT_BY_DOCTOR(IN `_cnp` VARCHAR(13), IN `startDate` DATE, IN `endDate` DATE, OUT `result` DECIMAL(10,2))
BEGIN
	SET @total = 0, @salary = 0;
    CALL GET_DOCTOR_PROFIT_TOTAL(`_cnp`, `startDate`, `endDate`, @total);
    CALL GET_DOCTOR_SALARY(`_cnp`, `startDate`, `endDate`, @salary);
    SET `result` = @total - @salary;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS INSERT_PATIENT;
DELIMITER //
CREATE PROCEDURE INSERT_PATIENT(IN `_cnp` VARCHAR(13), IN `_lastName` VARCHAR(25), IN `_firstName` VARCHAR(50), IN `_iban` VARCHAR(45), OUT `validation` INT)
BEGIN
	IF ((SELECT COUNT(*) FROM `patients` WHERE `cnp` = `_cnp`) = 0) THEN
		INSERT INTO `patients` (`cnp`, `lastName`, `firstName`, `iban`) VALUE (`_cnp`, `_lastName` , `_firstName`, `_iban`);
		SET `validation` = 1;
    ELSE
		SET `validation` = 0;
    END IF;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS SAVE_PATIENT;
DELIMITER //
CREATE PROCEDURE SAVE_PATIENT(IN `_cnp` VARCHAR(13), IN `_lastName` VARCHAR(25), IN `_firstName` VARCHAR(50), IN `_iban` VARCHAR(45), OUT `validation` INT)
BEGIN
	IF ((SELECT COUNT(*) FROM `patients` WHERE `cnp`=`_cnp`) = 1) THEN
		SET @sqlAction = CONCAT("UPDATE patients SET lastName = '", `_lastName`, "', firstName = '", `_firstName`, "', iban = '", `_iban`, "' WHERE cnp='", `_cnp`, "';");
		PREPARE statement FROM @sqlAction;
		EXECUTE statement;
		SET `validation` = 1;
	ELSE
		SET `validation` = 0;
	END IF;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS DELETE_PATIENT;
DELIMITER //
CREATE PROCEDURE DELETE_PATIENT(IN `_cnp` VARCHAR(13), OUT `validation` INT)
BEGIN
	IF ((SELECT COUNT(*) FROM `patients` WHERE `cnp` = `_cnp`) = 1) THEN
		DELETE FROM `patients` WHERE `cnp` = `_cnp`;
		SET `validation` = 1;
    ELSE
		SET `validation` = 0;
    END IF;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS GET_RECEIPT;
DELIMITER //
CREATE PROCEDURE GET_RECEIPT(IN `_id` INT, OUT `_name` VARCHAR(50), OUT `_patientName` VARCHAR(50), OUT `_address` VARCHAR(100), OUT `_date` TIMESTAMP, OUT `_services` VARCHAR(255), OUT `_price` DECIMAL(10,2))
BEGIN
	SET @unitIBAN = null, @patientIBAN = null;
	SELECT M.`name`, M.`iban`, M.`address`, A.`date` INTO `_name`, @unitIBAN, `_address`, `_date`
		FROM `medical_units` M, `appointments` A, `cabinets` C
		WHERE A.`idCabinet` = C.`id` AND M.`id` = C.`idMedicalUnit` LIMIT 1;
	
    SELECT GROUP_CONCAT(CONCAT(MS.`name`, ' ', MS.`price`)), SUM(MS.`price`) INTO `_services`, `_price`
		FROM `appointments` A, `appointment_services` S, `medical_services` MS
        WHERE A.`id` = `_id` AND S.`idAppointment` = A.`id` AND S.`idMedicalService` = MS.`id`;

	SELECT CONCAT(P.`lastName`, ' ', P.`firstName`), P.`iban` INTO `_patientName`, @patientIBAN
		FROM `appointments` A, `patients` P
        WHERE P.`cnp` = A.`cnpPatient` AND A.`id` = `_id`;
	
    IF ((SELECT COUNT(*) FROM `appointments` WHERE `id` = `_id` AND `hasReceipt` = '0') > 0) THEN
		UPDATE `appointments` SET `hasReceipt` = 1 WHERE `id` = `_id`;
        INSERT INTO `transactions` (`type`, `date`, `amount`, `sender`, `receiver`) VALUE ('income', NOW(), `_price`, @patientIBAN, @unitIBAN);
	END IF;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS INSERT_REPORT;
DELIMITER //
CREATE PROCEDURE INSERT_REPORT(IN `_cnp` VARCHAR(13), OUT `validation` INT)
BEGIN
	IF ((SELECT COUNT(*) FROM `patients` WHERE `cnp` = `_cnp`) = 1) THEN
        
        INSERT INTO `reports` (`cnpPatient`, `date`, `lastEdit`) VALUE (`_cnp`, NOW(), NOW());
		SET `validation` = 1;
    ELSE
		SET `validation` = 0;
    END IF;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS UPDATE_REPORT;
DELIMITER //
CREATE PROCEDURE UPDATE_REPORT(IN `_id` VARCHAR(13), IN `_diagnostic` VARCHAR(255), IN `_recommendation` VARCHAR(255), OUT `validation` INT)
BEGIN
	IF ((SELECT COUNT(*) FROM `reports` WHERE `id` = `_id` AND `sealCode` IS NULL) = 1) THEN
        
        UPDATE `reports` SET `diagnostic` = `_diagnostic`, `recommendation` = `_recommendation`, `lastEdit` = NOW() WHERE `id` = `_id`;
		SET `validation` = 1;
    ELSE
		SET `validation` = 0;
    END IF;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS CONFIRM_REPORT;
DELIMITER //
CREATE PROCEDURE CONFIRM_REPORT(IN `_id` VARCHAR(13), IN `_sealCode` VARCHAR(5), OUT `validation` INT)
BEGIN
	IF ((SELECT COUNT(*) FROM `doctors` WHERE `sealCode` = `_sealCode`) = 1
		AND (SELECT COUNT(*) FROM `reports` WHERE `id` = `_id` AND `sealCode` IS NULL) = 1) THEN
        
        UPDATE `reports` SET `sealCode` = `_sealCode` WHERE `id` = `_id`;
		SET `validation` = 1;
    ELSE
		SET `validation` = 0;
    END IF;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS INSERT_REPORT_INVESTIGATION;
DELIMITER //
CREATE PROCEDURE INSERT_REPORT_INVESTIGATION(IN `_idReport` INT, IN `_idService` INT, IN `_remarks` VARCHAR(255), OUT `validation` INT)
BEGIN
	IF ((SELECT COUNT(*) FROM `reports` WHERE `id` = `_idReport`) = 1
		AND (SELECT COUNT(*) FROM `medical_services` WHERE `id` = `_idService`) = 1) THEN
        
        INSERT INTO `report_investigations` (`idReport`, `idService`, `remarks`, `date`) VALUE (`_idReport`, `_idService`, `_remarks`, NOW());
        UPDATE `reports` SET `date` = NOW() WHERE `id` = `_idReport`;
		SET `validation` = 1;
    ELSE
		SET `validation` = 0;
    END IF;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS INSERT_ANALYSE;
DELIMITER //
CREATE PROCEDURE INSERT_ANALYSE(IN `_cnp` VARCHAR(13), IN `_idAnalyse` INT, IN `_value` DECIMAL(5, 2), OUT `validation` INT)
BEGIN
	IF ((SELECT COUNT(*) FROM `patients` WHERE `cnp` = `_cnp`) = 1
		AND (SELECT COUNT(*) FROM `analyse` WHERE `id` = `_idAnalyse`) = 1) THEN
        
        SET @isPositive = (SELECT COUNT(*) FROM `analyse` A WHERE `id` = `_idAnalyse` AND (`minimum` > `_value` OR `maximum` < `_value`));
        INSERT INTO `patient_analyses` (`cnpPatient`, `idAnalyse`, `value`, `isPositive`, `date`) VALUE (`_cnp`, `_idAnalyse`, `_value`, @isPositive, NOW());
		SET `validation` = 1;
    ELSE
		SET `validation` = 0;
    END IF;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS INSERT_MEDICAL_SERVICE;
DELIMITER //
CREATE PROCEDURE INSERT_MEDICAL_SERVICE(IN `_cnp` VARCHAR(13), IN `_name` VARCHAR(45), IN `_idSpeciality` INT, IN `_idAccreditation` INT, IN `_idEquipment` INT, IN `_price` DECIMAL(8, 2), IN `_duration` INT, OUT `validation` INT)
BEGIN
	IF ((SELECT COUNT(*) FROM `doctors` WHERE `cnpEmployee` = `_cnp`) = 1
		AND (SELECT COUNT(*) FROM `doctor_specialities` WHERE `cnpDoctor` = `_cnp` AND `idSpeciality` = `_idSpeciality`) > 0
        AND ((SELECT COUNT(*) FROM `doctor_accreditations` WHERE `cnpDoctor` = `_cnp` AND `idAccreditation` = `_idAccreditation`) > 0 OR `_idAccreditation` IS NULL)
        AND ((SELECT COUNT(*) FROM `equipments` WHERE `id` = `_idEquipment`) > 0 OR `_idEquipment` IS NULL)) THEN
        
        INSERT INTO `medical_services` (`cnpDoctor`, `name`, `idSpeciality`, `idAccreditation`, `idEquipment`, `price`, `duration`) VALUE (`_cnp`, `_name`, `_idSpeciality`, `_idAccreditation`, `_idEquipment`, `_price`, `_duration`);
		SET `validation` = 1;
    ELSE
		SET `validation` = 0;
    END IF;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS SAVE_MEDICAL_SERVICE;
DELIMITER //
CREATE PROCEDURE SAVE_MEDICAL_SERVICE(IN `_id` INT, IN `_cnp` VARCHAR(13), IN `_name` VARCHAR(45), IN `_idSpeciality` INT, IN `_idAccreditation` INT, IN `_idEquipment` INT, IN `_price` DECIMAL(8, 2), IN `_duration` INT, OUT `validation` INT)
BEGIN
	IF ((SELECT COUNT(*) FROM `medical_services` WHERE `id` = `_id`) = 1
		AND (SELECT COUNT(*) FROM `doctor_specialities` WHERE `cnpDoctor` = `_cnp` AND `idSpeciality` = `_idSpeciality`) > 0
        AND ((SELECT COUNT(*) FROM `doctor_accreditations` WHERE `cnpDoctor` = `_cnp` AND `idAccreditation` = `_idAccreditation`) > 0 OR `_idAccreditation` IS NULL)
        AND ((SELECT COUNT(*) FROM `equipments` WHERE `id` = `_idEquipment`) > 0 OR `_idEquipment` IS NULL)) THEN
        
        UPDATE `medical_services` SET `name` = `_name`, `idSpeciality` = `_idSpeciality`, `idAccreditation` = `_idAccreditation`, `idEquipment` = `_idEquipment`, `price` = `_price`, `duration` = `_duration` WHERE `id` = `_id`;
		SET `validation` = 1;
    ELSE
		SET `validation` = 0;
    END IF;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS DELETE_MEDICAL_SERVICE;
DELIMITER //
CREATE PROCEDURE DELETE_MEDICAL_SERVICE(IN `_id` INT, OUT `validation` INT)
BEGIN
	IF ((SELECT COUNT(*) FROM `medical_services` WHERE `id` = `_id`) > 0) THEN
        
        DELETE FROM `medical_services` WHERE `id` = `_id`;
		SET `validation` = 1;
    ELSE
		SET `validation` = 0;
    END IF;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS INSERT_APPOINTMENT;
DELIMITER //
CREATE PROCEDURE INSERT_APPOINTMENT(IN `_cnpPatient` VARCHAR(13), IN `_cnpDoctor` VARCHAR(13), IN `_idCabinet` INT, IN `_idSpeciality` INT, IN `_date` TIMESTAMP, OUT `result` INT)
BEGIN
	IF ((SELECT COUNT(*) FROM `doctors` WHERE `cnpEmployee` = `_cnpDoctor`) = 1
		AND (SELECT COUNT(*) FROM `patients` WHERE `cnp` = `_cnpPatient`) = 1
		AND (SELECT COUNT(*) FROM `cabinets` WHERE `id` = `_idCabinet`) = 1
		AND (SELECT COUNT(*) FROM `specialities` WHERE `id` = `_idSpeciality`) = 1) THEN
        
        INSERT INTO `appointments` (`cnpPatient`, `cnpDoctor`, `idCabinet`, `idSpeciality`, `date`) VALUE (`_cnpPatient`, `_cnpDoctor`, `_idCabinet`, `_idSpeciality`, `_date`);
		SET `result` = (SELECT `id` FROM `appointments` ORDER BY `id` DESC LIMIT 1);
    ELSE
		SET `result` = 0;
    END IF;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS INSERT_APPOINTMENT_SERVICE;
DELIMITER //
CREATE PROCEDURE INSERT_APPOINTMENT_SERVICE(IN `_idMedicalService` INT, IN `_idAppointment` INT, OUT `validation` INT)
BEGIN
	IF ((SELECT COUNT(*) FROM `medical_services` WHERE `id` = `_idMedicalService`) = 1
		AND (SELECT COUNT(*) FROM `appointments` WHERE `id` = `_idAppointment`) = 1) THEN
        
        INSERT INTO `appointment_services` (`idMedicalService`, `idAppointment`) VALUE (`_idMedicalService`, `_idAppointment`);
		SET `validation` = 1;
    ELSE
		SET `validation` = 0;
    END IF;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS DELETE_APPOINTMENT;
DELIMITER //
CREATE PROCEDURE DELETE_APPOINTMENT(IN `_id` INT, OUT `validation` INT)
BEGIN
	IF ((SELECT COUNT(*) FROM `appointments` WHERE `id` = `_id`) > 0) THEN
        
        DELETE FROM `appointments` WHERE `id` = `_id`;
		SET `validation` = 1;
    ELSE
		SET `validation` = 0;
    END IF;
END;
// DELIMITER ;

-- Teste pentru proceduri
DROP PROCEDURE IF EXISTS TEST;
DELIMITER //
CREATE PROCEDURE TEST()
BEGIN
	SET @output = -1, @output_name = '', @output_address = '', @output_date = '', @output_services = '', @output_price = 0, @output_name2 = '';
	-- CALL CHECK_CREDITENTIALS('admin1', 'adminpass', @output);
	-- CALL INSERT_EMPLOYEE_SCHEDULE('2700927417309', '3', 'Monday', '08:00:00', '12:00:00', @output);
	-- CALL INSERT_EMPLOYEE_SCHEDULE('2700927417309', '3', 'Monday', '07:59:00', '09:00:00', @output);
	-- CALL INSERT_EMPLOYEE_SCHEDULE('2700927417309', '3', 'Monday', '11:00:00', '11:00:01', @output);
	 -- CALL DELETE_EMPLOYEE_SCHEDULE('2700927417309', '3', 'Monday', '13:00:00', '16:00:00', 1, @output);
     -- CALL UPDATE_EMPLOYEE_SCHEDULE('2700927417309', '2', 'Monday', '13:00:00', '15:00:00', '14:55:00', '16:00:00', 1, @output);
    -- CALL INSERT_HOLIDAY('2700927417309', '2020-12-13', '2020-12-15', 1, @output);
    -- CALL INSERT_EMPLOYEE('2700735934101', 'Spatariu', 'Diana', 'Cluj-Napoca, str. Nicolae Iorga nr. 6', '0783527882', 'spatariu.diana@gmail.com', 'RO64RZBR3277465196914272', '30', '2017-02-20', 'Receptioner', '3470', '120', @output);
    -- CALL DELETE_EMPLOYEE('2700735934101', @output);
    -- CALL UPDATE_EMPLOYEE('2700735934101', 'contractNum', '31', @output);
    -- CALL GET_TOTAL_PROFIT('2019-01-01', '2019-12-31', @output);
    -- CALL GET_MEDICAL_UNIT_PROFIT('RO29RZBR5523951481289988', '2019-01-01', '2019-12-31', @output);
    -- CALL GET_TOTAL_PROFIT('2019-01-01', '2019-12-31', @output);
    -- CALL GET_MEDICAL_UNIT_PROFIT('2', '2019-01-01', '2019-12-31', @output);
    -- CALL GET_PROFIT_BY_SPECIALITY('1', '2020-01-01', '2020-12-15', @output);
    -- CALL GET_DOCTOR_PROFIT_TOTAL('2700927417309', '2020-12-01', '2020-12-31', @output);
    -- CALL GET_DOCTOR_SALARY('2700927417309', '2020-12-01', '2020-12-31', @output);
	-- CALL GET_PROFIT_BY_DOCTOR('2700927417309', '2020-12-01', '2020-12-31', @output);
	-- CALL GET_EMPLOYEE_SALARY('2700927417309', '2020-01-01', '2020-01-31', @output);
    -- CALL INSERT_PATIENT('1871054098525', 'Ianc', 'Daniel', 'RO12RZBR6975321332427243', @output);
    -- CALL DELETE_PATIENT('1871054098525', @output);
    -- CALL GET_RECEIPT('2', @output_name, @output_name2,  @output_address, @output_date, @output_services, @output_price);
    -- SELECT @output_name, @output_name2, @output_address, @output_date, @output_services, @output_price;
    
	-- CALL INSERT_PATIENT_HISTORY('1520619148967', '1', 'are toate bolile', NULL, @output);
    -- CALL GET_EMPLOYEE_SALARY('2700927417309', '2020-12-01', '2020-12-31', @output);
    -- CALL INSERT_ANALYSE('2910815468725', '3', '70', @output);
    -- CALL INSERT_MEDICAL_SERVICE('2700927417309', 'Consultatie cardiologie', '1', NULL, NULL, '300', '30', @output);
    -- CALL DELETE_MEDICAL_SERVICE('7', @output);
    -- call INSERT_DOCTOR('2811204117404','50012',0.30,null,'lector',@output);
    SELECT @output;
END;
// DELIMITER ;

-- CALL TEST();
