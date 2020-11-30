DROP DATABASE Policlinici;
CREATE DATABASE IF NOT EXISTS Policlinici;
USE Policlinici;

CREATE TABLE IF NOT EXISTS specialitate(
	id_specialitate VARCHAR(13) PRIMARY KEY,
	denumire_specialitate VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS acreditari_speciale(
	id_acreditare VARCHAR(13) PRIMARY KEY,
	denumire_acreditare VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS servicii_medicale(
	id_servicii VARCHAR(13) PRIMARY KEY,
    specialitate VARCHAR(50),
    pret FLOAT(5, 2),
    durata INT,
    necesitate_competenta VARCHAR(2)
);

CREATE TABLE IF NOT EXISTS programari(
	id_programare VARCHAR(13) PRIMARY KEY,
    data_programare DATE
);

CREATE TABLE IF NOT EXISTS fisa_medicala(
	id_fisa_medicala VARCHAR(13) PRIMARY KEY,
    CONSTRAINT fk_serviciu FOREIGN KEY (id_fisa_medicala)
        REFERENCES servicii_medicale(id_servicii)
);

CREATE TABLE IF NOT EXISTS pacienti(
	CNP_pacient VARCHAR(13) PRIMARY KEY,
    CONSTRAINT fk_fisa_medicala FOREIGN KEY (CNP_pacient)
        REFERENCES fisa_medicala(id_fisa_medicala),
	CONSTRAINT fk_programari_pacient FOREIGN KEY (CNP_pacient)
        REFERENCES programari(id_programare)
);

CREATE TABLE IF NOT EXISTS istoric_tranzactii(
	id_tranzactie INT AUTO_INCREMENT PRIMARY KEY,
    tipul VARCHAR(10),
    data_tranzactie DATE,
    suma FLOAT(10, 2),
    platitor VARCHAR(50),
    beneficiar VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS echipament_medical(
	id_echipament VARCHAR(13) PRIMARY KEY,
    tip_echipament VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS permisii_roluri(
	id_permisie VARCHAR(13) PRIMARY KEY,
	denumire_permisie VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS unitati_medicale(
	id_unitati_medicale VARCHAR(13) PRIMARY KEY,
    denumire VARCHAR(50),
    adresa VARCHAR(100),
    CONSTRAINT fk_specialitati FOREIGN KEY (id_unitati_medicale)
        REFERENCES servicii_medicale (id_servicii)
);

CREATE TABLE IF NOT EXISTS angajati(
	CNP_angajat VARCHAR(13) PRIMARY KEY,
    nume VARCHAR(25),
    prenume VARCHAR(50),
    adresa VARCHAR(100),
    nr_telefpn VARCHAR(10),
    email VARCHAR(25),
    cont_IBAN VARCHAR(25),
    nr_contract VARCHAR(25),
    data_angajarii DATE,
    functia VARCHAR(25),
    salariu FLOAT(10, 2),
    nr_ore_lucrate INT,
    CONSTRAINT fk_policlinica FOREIGN KEY (CNP_angajat)
        REFERENCES unitati_medicale (id_unitati_medicale)
);

CREATE TABLE IF NOT EXISTS roluri(
	id_rol VARCHAR(13) PRIMARY KEY,
    CONSTRAINT fk_roluri FOREIGN KEY (id_rol)
        REFERENCES angajati (CNP_angajat),
	denumire_rol VARCHAR(20),
    CONSTRAINT fk_permisie FOREIGN KEY (id_rol)
        REFERENCES permisii_roluri(id_permisie)
);

CREATE TABLE IF NOT EXISTS conturi_aplicatie(
	username VARCHAR(13) PRIMARY KEY,
    parola VARCHAR(13),
    CONSTRAINT fk_rol FOREIGN KEY (username)
        REFERENCES roluri(id_rol)
);

CREATE TABLE IF NOT EXISTS concedii(
	id_concediu VARCHAR(13) PRIMARY KEY,
    data_start DATE,
    data_final DATE,
    CONSTRAINT fk_concediu FOREIGN KEY (id_concediu)
        REFERENCES angajati(CNP_angajat)
);

CREATE TABLE IF NOT EXISTS program_de_lucru_angajat(
	id_program VARCHAR(13) PRIMARY KEY,
    CONSTRAINT fk_program_lucru FOREIGN KEY (id_program)
        REFERENCES angajati (CNP_angajat),
	ziua_saptamanii VARCHAR(10),
    ora_start VARCHAR(5),
    ora_final VARCHAR(5)
);

CREATE TABLE IF NOT EXISTS asistenti(
	CNP_asistent VARCHAR(13) PRIMARY KEY,
    CONSTRAINT fk_asistent FOREIGN KEY (CNP_asistent)
        REFERENCES angajati (CNP_angajat),
	tipul VARCHAR(20),
    gradul VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS medici(
	CNP_medic VARCHAR(13) PRIMARY KEY,
    CONSTRAINT fk_medic FOREIGN KEY (CNP_medic)
        REFERENCES angajati (CNP_angajat),
	CONSTRAINT fk_specialitate FOREIGN KEY (CNP_medic)
        REFERENCES specialitate(id_specialitate),
    gradul VARCHAR(20),
    cod_parafa VARCHAR(20),
    titlu_stiintific VARCHAR(20),
    post_didactic VARCHAR(20),
    CONSTRAINT fk_acreditare FOREIGN KEY (CNP_medic)
        REFERENCES acreditari_speciale(id_acreditare),
	CONSTRAINT fk_programari FOREIGN KEY (CNP_medic)
        REFERENCES programari(id_programare)
);

CREATE TABLE IF NOT EXISTS program_functionare_policlinica(
	id_program VARCHAR(13) PRIMARY KEY,
    CONSTRAINT fk_program FOREIGN KEY (id_program)
        REFERENCES unitati_medicale (id_unitati_medicale),
	ziua_saptamanii VARCHAR(10),
    ora_deschidere VARCHAR(5),
    ora_inchidere VARCHAR(5)
);

CREATE TABLE IF NOT EXISTS cabinet(
	id_cabinet VARCHAR(13) PRIMARY KEY,
	denumire_cabinet VARCHAR(50),
    CONSTRAINT fk_id FOREIGN KEY (id_cabinet)
        REFERENCES unitati_medicale (id_unitati_medicale),
	CONSTRAINT fk_programari_cabinet FOREIGN KEY (id_cabinet)
        REFERENCES programari(id_programare),
	CONSTRAINT fk_echipament FOREIGN KEY (id_cabinet)
        REFERENCES echipament_medical(id_echipament)
);
