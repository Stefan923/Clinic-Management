DROP VIEW IF EXISTS `polyclinics`.`view_employees`;
CREATE VIEW `polyclinics`.`view_employees` AS
	SELECT * FROM `employees`;
    
DROP VIEW IF EXISTS `polyclinics`.`view_accounts`;
CREATE VIEW `polyclinics`.`view_accounts` AS
	SELECT `username`, `cnpEmployee` as `cnp` FROM `accounts`;

DROP VIEW IF EXISTS `polyclinics`.`view_employee_schedule`;
CREATE VIEW `polyclinics`.`view_employee_schedule` AS
	SELECT `idMedicalUnit`, `cnpEmployee`, `dayOfWeek`, `startHour`, `endHour` FROM `employee_schedule`;

DROP VIEW IF EXISTS `polyclinics`.`view_holidays`;
CREATE VIEW `polyclinics`.`view_holidays` AS
	SELECT `cnpEmployee`, `startDate`, `endDate` FROM `holidays`;

DROP VIEW IF EXISTS `polyclinics`.`view_nurses`;
CREATE VIEW `polyclinics`.`view_nurses` AS
	SELECT * FROM `nurse`;


DROP VIEW IF EXISTS `polyclinics`.`view_services`;
CREATE VIEW `polyclinics`.`view_services` AS
	SELECT MS.`id`, MS.`cnpDoctor`, MS.`name` AS `ms_name`, MS.`price`, MS.`duration`, EMP.`lastName`, EMP.`firstName`, S.`name` AS `s_name`, IFNULL(A.`name`, '') AS `a_name`, IFNULL(E.`name`, '') AS `e_name`
    FROM `medical_services` MS
		INNER JOIN `employees` EMP ON EMP.`cnp` = MS.`cnpDoctor`
        INNER JOIN `specialities` S ON S.`id` = MS.`idSpeciality`
        LEFT OUTER JOIN `accreditations` A ON A.`id` = MS.`idAccreditation`
        LEFT OUTER JOIN `equipments` E ON E.`id` = MS.`idEquipment`;

DROP VIEW IF EXISTS `polyclinics`.`view_services_by_cabinet`;
CREATE VIEW `polyclinics`.`view_services_by_cabinet` AS
	SELECT MS.`id`, CE.`idCabinet`, MS.`cnpDoctor`, MS.`idSpeciality`, MS.`name` AS `serviceName`, MS.`price`, MS.`duration`
    FROM `medical_services` MS
		INNER JOIN `employees` EMP ON EMP.`cnp` = MS.`cnpDoctor`
        LEFT JOIN `cabinet_equipments` CE ON CE.`idEquipment` = MS.`idEquipment`
        LEFT JOIN `cabinets` C ON C.`id` = CE.`idCabinet`;

DROP VIEW IF EXISTS `polyclinics`.`view_permissions`;
CREATE VIEW `polyclinics`.`view_permissions` AS
	SELECT E.`cnp`, RP.`permission`
    FROM `role_permissions` RP, `roles` R, `employees` E, `accounts` A
	WHERE R.`id` = RP.`idRole` AND R.`id` = A.`idRole` AND E.`cnp` = A.`cnpEmployee`;

DROP VIEW IF EXISTS `polyclinics`.`view_specialities_by_doctor`;
CREATE VIEW `polyclinics`.`view_specialities_by_doctor` AS
	SELECT D.`cnpEmployee` as `cnp`, S.`id`, S.`name`
    FROM `specialities` S, `doctor_specialities` DS, `doctors` D
	WHERE DS.`cnpDoctor` = D.`cnpEmployee` AND S.`id` = DS.`idSpeciality`;

DROP VIEW IF EXISTS `polyclinics`.`view_specialities`;
CREATE VIEW `polyclinics`.`view_specialities` AS
	SELECT * FROM `specialities`;

DROP VIEW IF EXISTS `polyclinics`.`view_accreditations_by_doctor`;
CREATE VIEW `polyclinics`.`view_accreditations_by_doctor` AS
	SELECT D.`cnpEmployee` as `cnp`, A.`id`, A.`name`
    FROM `accreditations` A, `doctor_accreditations` DA, `doctors` D
	WHERE DA.`cnpDoctor` = D.`cnpEmployee` AND A.`id` = DA.`idAccreditation`;

DROP VIEW IF EXISTS `polyclinics`.`view_equipments`;
CREATE VIEW `polyclinics`.`view_equipments` AS
	SELECT `id`, `name`
    FROM `equipments`;

DROP VIEW IF EXISTS `polyclinics`.`view_patients`;
CREATE VIEW `polyclinics`.`view_patients` AS
	SELECT * FROM `patients`;

DROP VIEW IF EXISTS `polyclinics`.`view_reports`;
CREATE VIEW `polyclinics`.`view_reports` AS
	SELECT R.`id`, P.`cnp` AS `cnpPatient`, P.`lastName` AS `p_lastname`, P.`firstName` AS `p_firstname`, R.`sealCode`, E.`lastName` AS `e_lastname`, E.`firstName` AS `e_firstname`, R.`diagnostic`, R.`recommendation`, R.`date`, R.`lastEdit`
    FROM `reports` R
		INNER JOIN `patients` P ON P.`cnp` = R.`cnpPatient`
        LEFT OUTER JOIN `doctors` D ON D.`sealCode` = R.`sealCode`
        LEFT OUTER JOIN `employees` E ON E.`cnp` = D.`cnpEmployee`;

DROP VIEW IF EXISTS `polyclinics`.`view_investigations`;
CREATE VIEW `polyclinics`.`view_investigations` AS
	SELECT RI.`id` AS `idInvestigation`, RI.`idReport`, RI.`idService`, MS.`name` AS `serviceName`, RI.`remarks`, RI.`date`, E.`lastName` AS `d_lastName`, E.`firstName` AS `d_firstName`
    FROM `report_investigations` RI
		INNER JOIN `medical_services` MS ON MS.`id` = RI.`idService`
		INNER JOIN `employees` E ON E.`cnp` = MS.`cnpDoctor`;

DROP VIEW IF EXISTS `polyclinics`.`view_analyses`;
CREATE VIEW `polyclinics`.`view_analyses` AS
    SELECT * FROM `analyse`;

DROP VIEW IF EXISTS `polyclinics`.`view_patient_analyses`;
CREATE VIEW `view_patient_analyses` AS
    SELECT PA.`cnpPatient`, A.`name`, PA.`value`, PA.`isPositive`, PA.`idAnalyse`, A.`minimum`, A.`maximum`, PA.`date`
    FROM `patient_analyses` PA
        INNER JOIN `analyse` A ON A.`id` = PA.`idAnalyse`;

DROP VIEW IF EXISTS `polyclinics`.`view_doctors`;
CREATE VIEW `polyclinics`.`view_doctors` AS
	SELECT * FROM `doctors`;

DROP VIEW IF EXISTS `polyclinics`.`view_doctors_by_medical_unit`;
CREATE VIEW `polyclinics`.`view_doctors_by_medical_unit` AS
	SELECT D.`cnpEmployee`, ES.`idMedicalUnit` FROM `doctors` D
		INNER JOIN `employee_schedule` ES ON ES.`cnpEmployee` = D.`cnpEmployee`
        GROUP BY D.`cnpEmployee`, ES.`idMedicalUnit`;

DROP VIEW IF EXISTS `polyclinics`.`view_doctors_by_speciality`;
CREATE VIEW `polyclinics`.`view_doctors_by_speciality` AS
	SELECT S.`id`, E.`cnp`, CONCAT(E.`lastName`, ' ', E.`firstName`) AS `name`
	FROM `doctors` D
		INNER JOIN `employees` E ON E.`cnp` = D.`cnpEmployee`
        INNER JOIN `doctor_specialities` DS ON DS.`cnpDoctor` = D.`cnpEmployee`
        INNER JOIN `specialities` S ON S.`id` = DS.`idSpeciality`;

DROP VIEW IF EXISTS `polyclinics`.`view_appointments`;
CREATE VIEW `view_appointments` AS
    SELECT A.`id`, A.`cnpPatient`, A.`cnpDoctor`, A.`date`, CONCAT(P.`lastName`, ' ', P.`firstName`) AS `patientName`, CONCAT(E.`lastName`, ' ', E.`firstName`) AS `doctorName`, A.`idCabinet`, C.`name` AS `cabinetName`, S.`name` AS `specialityName`, SUM(MS.`duration`) AS `duration`, TIMESTAMPADD(MINUTE, SUM(MS.`duration`), A.`date`) AS `endDate`
    FROM `appointments` A
        INNER JOIN `patients` P ON P.`cnp` = A.`cnpPatient`
        INNER JOIN `employees` E ON E.`cnp` = A.`cnpDoctor`
        INNER JOIN `cabinets` C ON C.`id` = A.`idCabinet`
        INNER JOIN `specialities` S ON S.`id` = A.`idSpeciality`
        LEFT OUTER JOIN `appointment_services` APS ON APS.`idAppointment` = A.`id`
        LEFT OUTER JOIN `medical_services` MS ON MS.`id` = APS.`idMedicalService`
        GROUP BY A.`id`;

DROP VIEW IF EXISTS `polyclinics`.`view_transactions`;
CREATE VIEW `polyclinics`.`view_transactions` AS
	SELECT * FROM `transactions`;

SELECT * FROM `view_transactions`;
DROP VIEW IF EXISTS `polyclinics`.`view_appointment_services`;
CREATE VIEW `view_appointment_services` AS
    SELECT APS.`idAppointment`, APS.`idMedicalService`, MS.`name`, MS.`duration`, MS.`price`
    FROM `appointments` A
        INNER JOIN `appointment_services` APS ON APS.`idAppointment` = A.`id`
        INNER JOIN `medical_services` MS ON MS.`id` = APS.`idMedicalService`;

DROP VIEW IF EXISTS `polyclinics`.`view_medical_units`;
CREATE VIEW `polyclinics`.`view_medical_units` AS
	SELECT * FROM `medical_units`;

DROP VIEW IF EXISTS `polyclinics`.`view_cabinets`;
CREATE VIEW `polyclinics`.`view_cabinets` AS
	SELECT * FROM `cabinets`;
    
DROP VIEW IF EXISTS `polyclinics`.`view_doctor_cnp_name`;
CREATE VIEW `polyclinics`.`view_doctor_cnp_name` AS
	SELECT `cnp`, CONCAT(`lastName`, " ", `firstName`) 
    FROM `employees`, `doctors`
    WHERE `employees`.`cnp` = `doctors`.`cnpEmployee`;
    
SELECT * FROM `polyclinics`.`view_doctor_cnp_name`;

SELECT * FROM `view_services_by_cabinet`;

SELECT * FROM `view_medical_units`;