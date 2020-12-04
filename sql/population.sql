USE `polyclinics`;

INSERT INTO `medical_units`(name, address) 
VALUES
('Sanitas Cluj-Napoca', 'str. Observatorului, nr. 314'),
('Sanitas Arad', 'str. Aurel Vlaicu, nr. 91'),
('Sanitas Iasi', 'str. Mihai Eminescu, nr. 1'),
('Sanitas Craiova', 'str. Calea Bucuresti, nr. 1'),
('Sanitas Costanta', 'str. Vaselor, nr. 24');

INSERT INTO `medical_unit_schedule`(idMedicalUnit, dayOfWeek, startHour, endHour) 
VALUES
('1', 'Luni', '08:00', '20:00'),
('1', 'Marti', '08:00', '20:00'),
('1', 'Miercuri', '08:00', '20:00'),
('1', 'Joi', '08:00', '20:00'),
('1', 'Vineri', '08:00', '18:00'),
('1', 'Sambata', '08:00', '16:00'),

('2', 'Luni', '08:30', '20:00'),
('2', 'Marti', '08:30', '20:00'),
('2', 'Miercuri', '08:30', '20:00'),
('2', 'Joi', '08:30', '20:00'),
('2', 'Vineri', '08:30', '18:00'),
('2', 'Sambata', '08:30', '16:00'),
('2', 'Duminica', '08:30', '12:00'),

('3', 'Luni', '07:30', '19:00'),
('3', 'Marti', '07:30', '19:00'),
('3', 'Miercuri', '07:30', '19:00'),
('3', 'Joi', '07:30', '19:00'),
('3', 'Vineri', '07:30', '17:00'),
('3', 'Sambata', '07:30', '12:00'),

('4', 'Luni', '07:30', '20:00'),
('4', 'Marti', '07:30', '20:00'),
('4', 'Miercuri', '07:30', '20:00'),
('4', 'Joi', '07:30', '20:00'),
('4', 'Vineri', '07:30', '18:00'),
('4', 'Sambata', '07:30', '15:00'),

('5', 'Luni', '07:45', '20:00'),
('5', 'Marti', '07:45', '20:00'),
('5', 'Miercuri', '07:45', '20:00'),
('5', 'Joi', '07:45', '20:00'),
('5', 'Vineri', '07:45', '18:00'),
('5', 'Sambata', '07:45', '15:00'),
('5', 'Duminica', '08:00', '12:00');

INSERT INTO `cabinets`(idMedicalUnit, name) 
VALUES
('1', 'Cardiologie'),
('1', 'Dermatologie'),
('1', 'Chirurgie plastica'),
('1', 'Endocrinologie'),
('1', 'Stomatologie'),
('1', 'ORL'),
('1', 'Ortopedie'),
('1', 'Endoscopie'),
('1', 'Radiologie'),
('1', 'RMN'),
('1', 'Sala de operatie 1'),
('1', 'Sala de operatie 2'),

('2', 'Cardiologie'),
('2', 'Dermatologie'),
('2', 'Stomatologie'),
('2', 'Endocrinologie'),
('2', 'Radiologie'),
('2', 'Endoscopie'),

('3', 'Cardiologie'),
('3', 'Dermatologie'),
('3', 'Chirurgie plastica'),
('3', 'Endocrinologie'),
('3', 'Stomatologie'),
('3', 'ORL'),
('3', 'Ortopedie'),
('3', 'Endoscopie'),
('2', 'Radiologie'),
('3', 'RMN'),
('3', 'Sala de operatie'),

('4', 'Cardiologie'),
('4', 'Dermatologie'),
('4', 'Endocrinologie'),
('4', 'Stomatologie'),
('4', 'ORL'),
('4', 'Ortopedie'),
('4', 'Endoscopie'),
('4', 'Radiologie'),

('5', 'Cardiologie'),
('5', 'Dermatologie'),
('5', 'Endocrinologie'),
('5', 'Stomatologie'),
('5', 'ORL'),
('5', 'Ortopedie'),
('5', 'Endoscopie'),
('5', 'Radiologie'),
('5', 'Sala de operatie');

INSERT INTO `equipments`(name) 
VALUES
('Computer tomograf'),
('RMN'),
('EKG'),
('Ecocardiograf'),
('Aparat Holter'),
('Dermatoscop Digital'),
('Lampa UV'),
('Ecograf endocrinologie'),
('Scaun stomatologic'),
('Unitate de diagnosticare ORL'),
('Camera endoscopica ORL'),
('Ecograf'), 
('Sistem chirurgical'),
('Laparoscop'),
('Instalatie radiologica');

-- DELETE FROM `equipments`;

INSERT INTO `cabinet_equipments`(idCabinet, idEquipment) 
VALUES
('1', '3'),
('1', '4'),
('1', '5'),
('2', '6'),
('2', '7'),
('3', '13'),
('4', '8'),
('5', '9'),
('6', '10'),
('6', '11'),
('8', '12'),
('9', '15'),
('10', '1'),
('10', '2'),
('11', '13'),
('11', '14'),
('12', '13'),
('12', '14'),

('13', '3'),
('13', '4'),
('13', '5'),
('14', '6'),
('14', '7'),
('15', '9'),
('16', '8'),
('17', '15'),
('18', '12'),

('19', '3'),
('19', '4'),
('19', '5'),
('20', '6'),
('20', '7'),
('21', '13'),
('22', '8'),
('23', '9'),
('24', '10'),
('24', '11'),
('26', '12'),
('27', '15'),
('28', '1'),
('28', '2'),
('29', '13'),
('29', '14'),

('30', '3'),
('30', '4'),
('30', '5'),
('31', '6'),
('31', '7'),
('32', '8'),
('33', '9'),
('34', '10'),
('34', '11'),
('36', '12'),
('37', '15'),

('38', '3'),
('38', '4'),
('38', '5'),
('39', '6'),
('39', '7'),
('40', '8'),
('41', '9'),
('42', '10'),
('42', '11'),
('44', '12'),
('45', '15'),
('46', '13'),
('46', '14');

INSERT INTO `accreditations`(name) 
VALUES
('Ecografie'),
('Chirurgie'),
('Radiologie'),
('Explorare computer tomograf'),
('Imagistica prin rezonanta magnetica');

INSERT INTO `specialities`(name) 
VALUES
('Cardiologie'),
('Dermatologie si venerologie'),
('Chirurgie'),
('Chirurgie plastica'),
('Radiologie si imagistica'),
('Stomatologie'),
('ORL'),
('Ortopedie'),
('Pediatrie'),
('Oftalmologie'),
('Endocrinologie');

INSERT INTO `roles`(name) 
VALUES
('default'),
('receptioner'),
('resurse_umane'),
('asistent_medical'),
('medic'),
('contabil'),
('administrator'),
('super_administrator');

INSERT INTO `role_permissions`(idRole, permission) 
VALUES
('2', 'hr.read'),
('2', 'fc.read'),
('2', 'oa.appointment.read'),
('2', 'oa.appointment.write'),
('2', 'oa.patients_register.read'),
('2', 'oa.patients_register.write'),
('2', 'oa.receipt.write'),
('3', 'hr.read'),
('3', 'hr.read.other'),
('3', 'hr.write.other'),
('3', 'fc.read'),
('4', 'hr.read'),
('4', 'fc.read'),
('4', 'oa.medical_checkup.write'),
('4', 'oa.medical_checkup.read'),
('5', 'hr.read'),
('5', 'fc.read'),
('5', 'oa.history.write'),
('5', 'oa.history.read'),
('5', 'oa.medical_raport.write'),
('5', 'oa.medical_raport.read'),
('6', 'hr.read'),
('6', 'fc.read'),
('6', 'fc.write'),
('6', 'fc.read.other'),
('6', 'fc.write.other'),
('7', '*'),
('8', '*'),
('8', '+');

INSERT INTO `patients`(cnp, lastName, firstName) 
VALUES
('2910815468725', 'Alexa', 'Magda'),
('2750329315938', 'Ambrus', 'Erika'),
('1801119378613', 'Aproslavesei', 'Ion Dumitru'),
('1201204085247', 'Iliescu', 'Ion'),
('2380127165640', 'Georgescu', 'Carmen'),
('2460512257129', 'Marin', 'Florina'),
('1471020095263', 'Gras', 'Dumitru'),
('1520619148967', 'Tomoiaga', 'Vali'),
('2920430215231', 'Manea', 'Ana Georgiana'),
('1960408068054', 'Vijelie', 'Ioan Andrei'),
('1971005087985', 'Haralamb-Vantu', 'Codrin Mihai'),
('2961028088875', 'Schnitzel', 'Ana Maria'),
('5000604169904', 'Popescu', 'Daniel'),
('6000930329136', 'Stoica', 'Dana Andreea'),
('6000822065974', 'Halas', 'Elisabeta'),
('2991208015482', 'Boldea', 'Crina'),
('2971003128722', 'Fekete', 'Orsoyla');


