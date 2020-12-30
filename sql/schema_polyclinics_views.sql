DROP VIEW IF EXISTS `polyclinics`.`view_employees`;
CREATE VIEW `polyclinics`.`view_employees` AS
	SELECT * FROM `employees`;
    
DROP VIEW IF EXISTS `polyclinics`.`view_accounts`;
CREATE VIEW `polyclinics`.`view_accounts` AS
	SELECT `username`, `cnpEmployee` as `cnp` FROM `accounts`;

DROP VIEW IF EXISTS `polyclinics`.`view_services`;
CREATE VIEW `polyclinics`.`view_services` AS
	SELECT MS.`id`, MS.`cnpDoctor`, MS.`name` AS `ms_name`, MS.`price`, MS.`duration`, EMP.`lastName`, EMP.`firstName`, S.`name` AS `s_name`, IFNULL(A.`name`, '') AS `a_name`, IFNULL(E.`name`, '') AS `e_name`
    FROM `medical_services` MS
		INNER JOIN `employees` EMP ON EMP.`cnp` = MS.`cnpDoctor`
        INNER JOIN `specialities` S ON S.`id` = MS.`idSpeciality`
        LEFT OUTER JOIN `accreditations` A ON A.`id` = MS.`idAccreditation`
        LEFT OUTER JOIN `equipments` E ON E.`id` = MS.`idEquipment`;

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

DROP VIEW IF EXISTS `polyclinics`.`view_accreditations_by_doctor`;
CREATE VIEW `polyclinics`.`view_accreditations_by_doctor` AS
	SELECT D.`cnpEmployee` as `cnp`, A.`id`, A.`name`
    FROM `accreditations` A, `doctor_accreditations` DA, `doctors` D
	WHERE DA.`cnpDoctor` = D.`cnpEmployee` AND A.`id` = DA.`idAccreditation`;

DROP VIEW IF EXISTS `polyclinics`.`view_equipments`;
CREATE VIEW `polyclinics`.`view_equipments` AS
	SELECT `id`, `name`
    FROM `equipments`;
    
DROP VIEW IF EXISTS `polyclinics`.`view_medical_units`;
CREATE VIEW `polyclinics`.`view_medical_units` AS
	SELECT `name`, `iban`
    FROM `medical_units`;

SELECT * FROM `view_medical_units`;

