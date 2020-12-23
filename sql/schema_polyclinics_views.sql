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

SELECT * FROM polyclinics.view_accounts;