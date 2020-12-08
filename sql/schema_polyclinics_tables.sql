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
  `rank` VARCHAR(20) NULL DEFAULT NULL,
  `sealCode` VARCHAR(20) NULL DEFAULT NULL,
  `scientificTitle` VARCHAR(20) NULL DEFAULT NULL,
  `didacticTitle` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`cnpEmployee`),
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
  PRIMARY KEY (`cnpDoctor`, `idSpeciality`),
  INDEX `idx_doctor_specialities_idSpeciality` (`idSpeciality` ASC) INVISIBLE,
  CONSTRAINT `fk_doctor_specialities_cnpDoctor`
    FOREIGN KEY (`cnpDoctor`)
    REFERENCES `polyclinics`.`doctors` (`cnpEmployee`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_doctor_specialities_idSpeciality`
    FOREIGN KEY (`idSpeciality`)
    REFERENCES `polyclinics`.`specialities` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table `polyclinics`.`medical_units`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`medical_units` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NULL DEFAULT NULL,
  `address` VARCHAR(100) NULL DEFAULT NULL,
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
    REFERENCES `polyclinics`.`cabinets` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cabinet_equipments_idEquipment`
    FOREIGN KEY (`idEquipment`)
    REFERENCES `polyclinics`.`equipments` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table `polyclinics`.`accreditations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`accreditations` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `polyclinics`.`doctor_accreditations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`doctor_accreditations` (
  `cnpDoctor` VARCHAR(13) NOT NULL,
  `idAccreditation` INT NOT NULL,
  PRIMARY KEY (`cnpDoctor`, `idAccreditation`),
  CONSTRAINT `fk_doctor_accreditations_idAccreditation`
    FOREIGN KEY (`idAccreditation`)
    REFERENCES `polyclinics`.`accreditations` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_doctor_accreditations_idDoctor`
    FOREIGN KEY (`cnpDoctor`)
    REFERENCES `polyclinics`.`doctors` (`cnpEmployee`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table `polyclinics`.`medical_unit_employees`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`medical_unit_employees` (
  `idMedicalUnit` INT NOT NULL,
  `cnpEmployee` VARCHAR(13) NOT NULL,
  PRIMARY KEY (`idMedicalUnit`, `cnpEmployee`),
  INDEX `fk_medical_unit_employees_cnpEmployee_idx` (`cnpEmployee` ASC) VISIBLE,
  CONSTRAINT `fk_medical_unit_employees_idMedicalUnit`
    FOREIGN KEY (`idMedicalUnit`)
    REFERENCES `polyclinics`.`medical_units` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_medical_unit_employees_cnpEmployee`
    FOREIGN KEY (`cnpEmployee`)
    REFERENCES `polyclinics`.`employees` (`cnp`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

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
  `cnp` VARCHAR(13) NOT NULL,
  `startDate` DATE NOT NULL,
  `endDate` DATE NOT NULL,
  INDEX `fk_holidays_cnp_idx` (`cnp` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_holidays_cnpEmployee`
    FOREIGN KEY (`cnp`)
    REFERENCES `polyclinics`.`employees` (`cnp`));

-- -----------------------------------------------------
-- Table `polyclinics`.`medical_services`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`medical_services` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idSpeciality` INT NOT NULL,
  `idMedicalUnit` INT NOT NULL,
  `hasAccreditation` TINYINT NULL DEFAULT 0,
  `price` DECIMAL(5,2) NULL DEFAULT NULL,
  `duration` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_medical_services_idMedicalUnti_idx` (`idMedicalUnit` ASC) VISIBLE,
  CONSTRAINT `fk_medical_services_idMedicalUnti`
    FOREIGN KEY (`idMedicalUnit`)
    REFERENCES `polyclinics`.`medical_units` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table `polyclinics`.`patients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`patients` (
  `cnp` VARCHAR(13) NOT NULL,
  `lastName` VARCHAR(25) NULL DEFAULT NULL,
  `firstName` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`cnp`));

-- -----------------------------------------------------
-- Table `polyclinics`.`patient_history`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`patient_history` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `cnpPatient` VARCHAR(13) NOT NULL,
  `idService` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_patitent_history_idService_idx` (`idService` ASC) VISIBLE,
  CONSTRAINT `fk_patitent_history_idService`
    FOREIGN KEY (`idService`)
    REFERENCES `polyclinics`.`medical_services` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_patient_history_cnpPatient`
    FOREIGN KEY (`cnpPatient`)
    REFERENCES `polyclinics`.`patients` (`cnp`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table `polyclinics`.`transactions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`transactions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(10) NOT NULL,
  `date` DATE NULL,
  `amount` DECIMAL(10,2) NOT NULL,
  `sender` VARCHAR(50) NOT NULL,
  `receiver` VARCHAR(50) NOT NULL,
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
  `cnpEmployee` VARCHAR(20) NOT NULL,
  `username` VARCHAR(20) NOT NULL,
  `idRole` INT NOT NULL DEFAULT 0,
  `password` VARCHAR(45) NOT NULL,
  INDEX `fk_accounts_cnpEmployee_idx` (`cnpEmployee` ASC) VISIBLE,
  INDEX `fk_accounts_idRole_idx` (`idRole` ASC) VISIBLE,
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
    REFERENCES `polyclinics`.`roles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table `polyclinics`.`employee_schedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`employee_schedule` (
  `cnpEmployee` VARCHAR(13) NOT NULL,
  `idMedicalUnit` INT NOT NULL,
  `dayOfWeek` VARCHAR(10) NOT NULL,
  `startHour` VARCHAR(5) NOT NULL,
  `endHour` VARCHAR(5) NOT NULL,
  PRIMARY KEY (`cnpEmployee`),
  INDEX `fk_employee_schedule_idMedicalUnit_idx` (`idMedicalUnit` ASC) VISIBLE,
  CONSTRAINT `fk_employee_schedule_cnpEmployee`
    FOREIGN KEY (`cnpEmployee`)
    REFERENCES `polyclinics`.`employees` (`cnp`),
  CONSTRAINT `fk_employee_schedule_idMedicalUnit`
    FOREIGN KEY (`idMedicalUnit`)
    REFERENCES `polyclinics`.`medical_units` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table `polyclinics`.`medical_unit_schedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polyclinics`.`medical_unit_schedule` (
  `idMedicalUnit` INT NOT NULL,
  `dayOfWeek` VARCHAR(10) NOT NULL,
  `startHour` VARCHAR(5) NOT NULL,
  `endHour` VARCHAR(5) NOT NULL,
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
  `data_programare` DATE NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_appointments_idCabinet_idx` (`idCabinet` ASC) VISIBLE,
  INDEX `fk_appointments_idPatient_idx` (`cnpPatient` ASC) VISIBLE,
  INDEX `fk_appointments_cnpDoctor_idx` (`cnpDoctor` ASC) VISIBLE,
  CONSTRAINT `fk_appointments_idCabinet`
    FOREIGN KEY (`idCabinet`)
    REFERENCES `polyclinics`.`cabinets` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_appointments_idPatient`
    FOREIGN KEY (`cnpPatient`)
    REFERENCES `polyclinics`.`patients` (`cnp`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_appointments_cnpDoctor`
    FOREIGN KEY (`cnpDoctor`)
    REFERENCES `polyclinics`.`doctors` (`cnpEmployee`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table `polyclinics`.`analyse`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `polyclinics`.`analyse` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `minimum` INT NULL,
  `maximum` INT NULL DEFAULT NULL,
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
  INDEX `fk_patient_analyses_cnpPatient_idx` (`cnpPatient` ASC) VISIBLE,
  INDEX `fk_patient_analyses_idAnalyse_idx` (`idAnalyse` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_patient_analyses_cnpPatient`
    FOREIGN KEY (`cnpPatient`)
    REFERENCES `polyclinics`.`patients` (`cnp`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_patient_analyses_idAnalyse`
    FOREIGN KEY (`idAnalyse`)
    REFERENCES `polyclinics`.`analyse` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
