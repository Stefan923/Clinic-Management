-- -----------------------------------------------------
-- Schema `polyclinics`
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `polyclinics`;
USE `polyclinics`;

-- -----------------------------------------------------
-- Table `polyclinics`.`employees`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`employees` (
  `cnp` VARCHAR(13) NOT NULL,
  `lastName` VARCHAR(25) NULL DEFAULT NULL,
  `firstName` VARCHAR(50) NULL DEFAULT NULL,
  `address` VARCHAR(100) NULL DEFAULT NULL,
  `phoneNum` VARCHAR(10) NULL DEFAULT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `iban` VARCHAR(45) NULL DEFAULT NULL,
  `contractNum` INT NULL DEFAULT NULL,
  `employmentDate` DATE NULL DEFAULT NULL,
  `position` VARCHAR(25) NULL DEFAULT NULL,
  `salary` DECIMAL(10,2) NULL DEFAULT NULL,
  `workedHrs` INT NULL DEFAULT NULL,
  PRIMARY KEY (`cnp`));

-- -----------------------------------------------------
-- Table `polyclinics`.`doctors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`doctors` (
  `cnpEmployee` VARCHAR(13) NOT NULL,
  `sealCode` VARCHAR(5) NULL DEFAULT NULL,
  `commission` DECIMAL(3,2) NULL,
  `scientificTitle` VARCHAR(45) NULL DEFAULT NULL,
  `didacticTitle` VARCHAR(20) NULL,
  PRIMARY KEY (`cnpEmployee`),
  UNIQUE INDEX `sealCode_UNIQUE` (`sealCode` ASC) VISIBLE,
  CONSTRAINT `fk_doctors_cnpEmployee`
    FOREIGN KEY (`cnpEmployee`)
    REFERENCES `polyclinics`.`employees` (`cnp`));

-- -----------------------------------------------------
-- Table `polyclinics`.`specialities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`specialities` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));

-- -----------------------------------------------------
-- Table `polyclinics`.`doctor_specialities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`doctor_specialities` (
  `cnpDoctor` VARCHAR(13) NOT NULL,
  `idSpeciality` INT NOT NULL,
  `rank` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`cnpDoctor`, `idSpeciality`),
  INDEX `idx_doctor_specialities_idSpeciality` (`idSpeciality` ASC) INVISIBLE,
  CONSTRAINT `fk_doctor_specialities_cnpDoctor`
    FOREIGN KEY (`cnpDoctor`)
    REFERENCES `polyclinics`.`doctors` (`cnpEmployee`),
  CONSTRAINT `fk_doctor_specialities_idSpeciality`
    FOREIGN KEY (`idSpeciality`)
    REFERENCES `polyclinics`.`specialities` (`id`));

-- -----------------------------------------------------
-- Table `polyclinics`.`medical_units`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`medical_units` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NULL DEFAULT NULL,
  `address` VARCHAR(100) NULL DEFAULT NULL,
  `iban` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`));

-- -----------------------------------------------------
-- Table `polyclinics`.`cabinets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`cabinets` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idMedicalUnit` INT NOT NULL,
  `name` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_cabinets_idMedicalUnit` (`idMedicalUnit` ASC) INVISIBLE,
  CONSTRAINT `fk_cabinets_idMedicalUnit`
    FOREIGN KEY (`idMedicalUnit`)
    REFERENCES `polyclinics`.`medical_units` (`id`));

-- -----------------------------------------------------
-- Table `polyclinics`.`equipments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`equipments` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));

-- -----------------------------------------------------
-- Table `polyclinics`.`cabinet_equipments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`cabinet_equipments` (
  `idCabinet` INT NOT NULL,
  `idEquipment` INT NOT NULL,
  PRIMARY KEY (`idCabinet`, `idEquipment`),
  INDEX `fk_cabinet_equipments_idEquipment_idx` (`idEquipment` ASC) VISIBLE,
  CONSTRAINT `fk_cabinet_equipments_idCabinet`
    FOREIGN KEY (`idCabinet`)
    REFERENCES `polyclinics`.`cabinets` (`id`),
  CONSTRAINT `fk_cabinet_equipments_idEquipment`
    FOREIGN KEY (`idEquipment`)
    REFERENCES `polyclinics`.`equipments` (`id`));

-- -----------------------------------------------------
-- Table `polyclinics`.`accreditations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`accreditations` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`));

-- -----------------------------------------------------
-- Table `polyclinics`.`doctor_accreditations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`doctor_accreditations` (
  `cnpDoctor` VARCHAR(13) NOT NULL,
  `idAccreditation` INT NOT NULL,
  PRIMARY KEY (`cnpDoctor`, `idAccreditation`),
  CONSTRAINT `fk_doctor_accreditations_idAccreditation`
    FOREIGN KEY (`idAccreditation`)
    REFERENCES `polyclinics`.`accreditations` (`id`),
  CONSTRAINT `fk_doctor_accreditations_idDoctor`
    FOREIGN KEY (`cnpDoctor`)
    REFERENCES `polyclinics`.`doctors` (`cnpEmployee`));

-- -----------------------------------------------------
-- Table `polyclinics`.`nurse`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`nurse` (
  `cnpEmployee` VARCHAR(13) NOT NULL,
  `type` VARCHAR(20) NULL DEFAULT NULL,
  `rank` VARCHAR(20) NULL,
  PRIMARY KEY (`cnpEmployee`),
  CONSTRAINT `fk_nurse_cnpEmployee`
    FOREIGN KEY (`cnpEmployee`)
    REFERENCES `polyclinics`.`employees` (`cnp`));

-- -----------------------------------------------------
-- Table `polyclinics`.`holidays`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`holidays` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `cnpEmployee` VARCHAR(13) NOT NULL,
  `startDate` DATE NOT NULL,
  `endDate` DATE NOT NULL,
  INDEX `fk_holidays_cnp_idx` (`cnpEmployee` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_holidays_cnpEmployee`
    FOREIGN KEY (`cnpEmployee`)
    REFERENCES `polyclinics`.`employees` (`cnp`));

-- -----------------------------------------------------
-- Table `polyclinics`.`medical_services`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`medical_services` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `cnpDoctor` VARCHAR(13) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `idSpeciality` INT NULL DEFAULT NULL,
  `idAccreditation` INT NULL DEFAULT NULL,
  `idEquipment` INT NULL DEFAULT NULL,
  `price` DECIMAL(8,2) NOT NULL DEFAULT 0,
  `duration` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_medical_services_cnpDoctor_idx` (`cnpDoctor` ASC) VISIBLE,
  INDEX `fk_medical_services_idEquipment_idx` (`idEquipment` ASC) VISIBLE,
  INDEX `fk_medical_services_idAccreditation_idx` (`idAccreditation` ASC) VISIBLE,
  INDEX `fk_medical_services_idSpeciality_idx` (`idSpeciality` ASC) VISIBLE,
  CONSTRAINT `fk_medical_services_cnpDoctor`
    FOREIGN KEY (`cnpDoctor`)
    REFERENCES `polyclinics`.`doctors` (`cnpEmployee`),
  CONSTRAINT `fk_medical_services_idEquipment`
    FOREIGN KEY (`idEquipment`)
    REFERENCES `polyclinics`.`equipments` (`id`),
  CONSTRAINT `fk_medical_services_idAccreditation`
    FOREIGN KEY (`idAccreditation`)
    REFERENCES `polyclinics`.`accreditations` (`id`),
  CONSTRAINT `fk_medical_services_idSpeciality`
    FOREIGN KEY (`idSpeciality`)
    REFERENCES `polyclinics`.`specialities` (`id`));

-- -----------------------------------------------------
-- Table `polyclinics`.`patients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`patients` (
  `cnp` VARCHAR(13) NOT NULL,
  `lastName` VARCHAR(25) NULL DEFAULT NULL,
  `firstName` VARCHAR(50) NULL DEFAULT NULL,
  `iban` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`cnp`));

-- -----------------------------------------------------
-- Table `polyclinics`.`reports`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`reports` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `cnpPatient` VARCHAR(13) NOT NULL,
  `diagnostic` VARCHAR(255) NULL DEFAULT '',
  `recommendation` VARCHAR(255) NULL DEFAULT '',
  `date` TIMESTAMP NOT NULL,
  `lastEdit` TIMESTAMP NULL DEFAULT NULL,
  `sealCode` VARCHAR(5) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_reports_idx` (`cnpPatient` ASC) VISIBLE,
  INDEX `fk_reports_sealCode_idx` (`sealCode` ASC) VISIBLE,
  CONSTRAINT `fk_reports_cnpPatient`
    FOREIGN KEY (`cnpPatient`)
    REFERENCES `polyclinics`.`patients` (`cnp`),
  CONSTRAINT `fk_reports_sealCode`
    FOREIGN KEY (`sealCode`)
    REFERENCES `polyclinics`.`doctors` (`sealCode`));
    
-- -----------------------------------------------------
-- Table `polyclinics`.`report_investigations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`report_investigations` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idReport` INT NOT NULL,
  `idService` INT NOT NULL,
  `remarks` VARCHAR(255) NULL DEFAULT '',
  `date` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_patitent_history_idService_idx` (`idService` ASC) VISIBLE,
  INDEX `fk_report_investigations_idReport_idx` (`idReport` ASC) VISIBLE,
  CONSTRAINT `fk_patitent_history_idService`
    FOREIGN KEY (`idService`)
    REFERENCES `polyclinics`.`medical_services` (`id`),
  CONSTRAINT `fk_report_investigations_idReport`
    FOREIGN KEY (`idReport`)
    REFERENCES `polyclinics`.`reports` (`id`));

-- -----------------------------------------------------
-- Table `polyclinics`.`transactions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`transactions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(10) NOT NULL,
  `date` TIMESTAMP NULL DEFAULT NULL,
  `amount` DECIMAL(10,2) NOT NULL,
  `sender` VARCHAR(45) NOT NULL,
  `receiver` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));

-- -----------------------------------------------------
-- Table `polyclinics`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`roles` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`));

-- -----------------------------------------------------
-- Table `polyclinics`.`accounts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`accounts` (
  `cnpEmployee` VARCHAR(13) NOT NULL,
  `username` VARCHAR(20) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `idRole` INT NOT NULL DEFAULT 1,
  INDEX `fk_accounts_cnpEmployee_idx` (`cnpEmployee` ASC) VISIBLE,
  INDEX `fk_accounts_idRole_idx` (`idRole` ASC) VISIBLE,
  PRIMARY KEY (`cnpEmployee`),
  CONSTRAINT `fk_accounts_cnpEmployee`
    FOREIGN KEY (`cnpEmployee`)
    REFERENCES `polyclinics`.`employees` (`cnp`),
  CONSTRAINT `fk_accounts_idRole`
    FOREIGN KEY (`idRole`)
    REFERENCES `polyclinics`.`roles` (`id`));

-- -----------------------------------------------------
-- Table `polyclinics`.`role_permissions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`role_permissions` (
  `idRole` INT NOT NULL,
  `permission` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idRole`, `permission`),
  CONSTRAINT `fk_role_permissions_idRole`
    FOREIGN KEY (`idRole`)
    REFERENCES `polyclinics`.`roles` (`id`));

-- -----------------------------------------------------
-- Table `polyclinics`.`employee_schedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`employee_schedule` (
  `cnpEmployee` VARCHAR(13) NOT NULL,
  `idMedicalUnit` INT NOT NULL,
  `dayOfWeek` VARCHAR(10) NOT NULL,
  `startHour` TIME NOT NULL,
  `endHour` TIME NOT NULL,
  INDEX `fk_employee_schedule_idMedicalUnit_idx` (`idMedicalUnit` ASC) VISIBLE,
  CONSTRAINT `fk_employee_schedule_cnpEmployee`
    FOREIGN KEY (`cnpEmployee`)
    REFERENCES `polyclinics`.`employees` (`cnp`),
  CONSTRAINT `fk_employee_schedule_idMedicalUnit`
    FOREIGN KEY (`idMedicalUnit`)
    REFERENCES `polyclinics`.`medical_units` (`id`));

-- -----------------------------------------------------
-- Table `polyclinics`.`medical_unit_schedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`medical_unit_schedule` (
  `idMedicalUnit` INT NOT NULL,
  `dayOfWeek` VARCHAR(10) NOT NULL,
  `startHour` TIME NOT NULL,
  `endHour` TIME NOT NULL,
  PRIMARY KEY (`idMedicalUnit`, `dayOfWeek`),
  CONSTRAINT `fk_medical_unit_schedule_idMedicalUnit`
    FOREIGN KEY (`idMedicalUnit`)
    REFERENCES `polyclinics`.`medical_units` (`id`));

-- -----------------------------------------------------
-- Table `polyclinics`.`appointments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`appointments` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `cnpPatient` VARCHAR(13) NOT NULL,
  `cnpDoctor` VARCHAR(13) NOT NULL,
  `idCabinet` INT NOT NULL,
  `idSpeciality` INT NOT NULL,
  `date` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_appointments_idCabinet_idx` (`idCabinet` ASC) VISIBLE,
  INDEX `fk_appointments_idPatient_idx` (`cnpPatient` ASC) VISIBLE,
  INDEX `fk_appointments_cnpDoctor_idx` (`cnpDoctor` ASC) VISIBLE,
  INDEX `fk_appointments_idSpeciality_idx` (`idSpeciality` ASC) VISIBLE,
  CONSTRAINT `fk_appointments_cnpDoctor`
    FOREIGN KEY (`cnpDoctor`)
    REFERENCES `polyclinics`.`doctors` (`cnpEmployee`),
  CONSTRAINT `fk_appointments_idCabinet`
    FOREIGN KEY (`idCabinet`)
    REFERENCES `polyclinics`.`cabinets` (`id`),
  CONSTRAINT `fk_appointments_idPatient`
    FOREIGN KEY (`cnpPatient`)
    REFERENCES `polyclinics`.`patients` (`cnp`),
  CONSTRAINT `fk_appointments_idSpeciality`
    FOREIGN KEY (`idSpeciality`)
    REFERENCES `polyclinics`.`specialities` (`id`));
    
-- -----------------------------------------------------
-- Table `polyclinics`.`appointment_services`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`appointment_services` (
  `idMedicalService` INT NOT NULL,
  `idAppointment` INT NOT NULL,
  PRIMARY KEY (`idMedicalService`, `idAppointment`),
  INDEX `fk_appointment_services_idAppointment_idx` (`idAppointment` ASC) VISIBLE,
  CONSTRAINT `fk_appointment_services_idMedicalService`
    FOREIGN KEY (`idMedicalService`)
    REFERENCES `polyclinics`.`medical_services` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_appointment_services_idAppointment`
    FOREIGN KEY (`idAppointment`)
    REFERENCES `polyclinics`.`appointments` (`id`));

-- -----------------------------------------------------
-- Table `polyclinics`.`analyse`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `polyclinics`.`analyse` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `minimum` DECIMAL(5,2) NULL,
  `maximum` DECIMAL(5,2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`));
  
-- -----------------------------------------------------
-- Table `polyclinics`.`patient_analyses`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `polyclinics`.`patient_analyses` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `cnpPatient` VARCHAR(13) NOT NULL,
  `idAnalyse` INT NULL,
  `value` DECIMAL(5,2) NULL DEFAULT NULL,
  `isPositive` TINYINT NULL DEFAULT NULL,
  `date` TIMESTAMP NOT NULL,
  INDEX `fk_patient_analyses_cnpPatient_idx` (`cnpPatient` ASC) VISIBLE,
  INDEX `fk_patient_analyses_idAnalyse_idx` (`idAnalyse` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_patient_analyses_cnpPatient`
    FOREIGN KEY (`cnpPatient`)
    REFERENCES `polyclinics`.`patients` (`cnp`),
  CONSTRAINT `fk_patient_analyses_idAnalyse`
    FOREIGN KEY (`idAnalyse`)
    REFERENCES `polyclinics`.`analyse` (`id`));
