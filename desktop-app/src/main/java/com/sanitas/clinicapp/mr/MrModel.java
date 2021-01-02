package com.sanitas.clinicapp.mr;

import com.sanitas.clinicapp.ClinicApplication;
import com.sanitas.clinicapp.Database;
import com.sanitas.clinicapp.struct.*;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class MrModel {

    private final Database database;

    public MrModel() {
        database = ClinicApplication.getDatabase();
    }

    public boolean addPatient(String cnp, String lastname, String firstname, String iban) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL INSERT_PATIENT(?, ?, ?, ?, ?);");
            callableStatement.setString(1, cnp);
            callableStatement.setString(2, lastname);
            callableStatement.setString(3, firstname);
            callableStatement.setString(4, iban);
            callableStatement.registerOutParameter(5, Types.BOOLEAN);
            callableStatement.execute();

            return callableStatement.getBoolean(5);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public List<Patient> getPatients(String lastname, String firstname) {
        List<Patient> patients = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT `cnp`, `lastname`, `firstname` FROM `view_patients` WHERE `lastname` LIKE CONCAT('%', ?, '%') AND  `firstname` LIKE CONCAT('%', ?, '%');");
            preparedStatement.setString(1, lastname);
            preparedStatement.setString(2, firstname);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                patients.add(new Patient(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        patients.sort(Patient::compareTo);
        return patients;
    }

    public Map<String, String> getPatients() {
        Map<String, String> patients = new HashMap<>();
        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT `cnp`, CONCAT(`lastname`, ' ', `firstname`) AS `name` FROM `view_patients` ORDER BY `name` ASC;");
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                patients.put(resultSet.getString(1),
                        resultSet.getString(2));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return patients;
    }

    public Patient getPatient(String cnp) {
        Patient patient = null;

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT `lastname`, `firstname`, `iban` FROM `view_patients` WHERE `cnp` = ?;");
            preparedStatement.setString(1, cnp);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                patient = new Patient(cnp,
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return patient;
    }

    public boolean updatePatient(String cnp, String lastname, String firstname, String iban) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL SAVE_PATIENT(?, ?, ?, ?, ?);");
            callableStatement.setString(1, cnp);
            callableStatement.setString(2, lastname);
            callableStatement.setString(3, firstname);
            callableStatement.setString(4, iban);
            callableStatement.registerOutParameter(5, Types.BOOLEAN);
            callableStatement.execute();

            return callableStatement.getBoolean(5);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean deletePatient(String cnp) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL DELETE_PATIENT(?, ?);");
            callableStatement.setString(1, cnp);
            callableStatement.registerOutParameter(2, Types.BOOLEAN);
            callableStatement.execute();

            return callableStatement.getBoolean(2);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public List<Report> getReports(String cnp, java.util.Date startDate, java.util.Date endDate) {
        List<Report> reports = new ArrayList<>();
        int index = 2;

        StringBuilder query = new StringBuilder("SELECT * FROM `view_reports` WHERE `cnpPatient` = ?");
        if (startDate != null) {
            query.append(" AND `date` >= ?");
        }
        if (endDate != null) {
            query.append(" AND `date` <= ?");
        }
        query.append(";");

        try {
            PreparedStatement preparedStatement = database.preparedStatement(query.toString());
            preparedStatement.setString(1, cnp);
            if (startDate != null) {
                preparedStatement.setTimestamp(index++, new Timestamp(startDate.getTime()));
            }
            if (endDate != null) {
                preparedStatement.setTimestamp(index, new Timestamp(endDate.getTime()));
            }
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                reports.add(new Report(resultSet.getInt(1),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9),
                        resultSet.getTimestamp(10),
                        resultSet.getTimestamp(11)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        reports.sort(Report::compareTo);

        return reports;
    }

    public Report getReport(int id) {
        Report report = null;

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT * FROM `view_reports` WHERE `id` = ?;");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                report = new Report(resultSet.getInt(1),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9),
                        resultSet.getTimestamp(10),
                        resultSet.getTimestamp(11));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return report;
    }

    public boolean addReport(String cnp) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL INSERT_REPORT(?, ?);");
            callableStatement.setString(1, cnp);
            callableStatement.registerOutParameter(2, Types.BOOLEAN);
            callableStatement.execute();

            return callableStatement.getBoolean(2);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean saveReport(Report report) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL UPDATE_REPORT(?, ?, ?, ?);");
            callableStatement.setInt(1, report.getId());
            callableStatement.setString(2, report.getDiagnostic());
            callableStatement.setString(3, report.getRecommendation());
            callableStatement.registerOutParameter(4, Types.BOOLEAN);
            callableStatement.execute();

            return callableStatement.getBoolean(4);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean confirmReport(Report report, String sealCode) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL CONFIRM_REPORT(?, ?, ?);");
            callableStatement.setInt(1, report.getId());
            callableStatement.setString(2, sealCode);
            callableStatement.registerOutParameter(3, Types.BOOLEAN);
            callableStatement.execute();

            return callableStatement.getBoolean(3);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public List<MedicalService> getMedicalServices(String cnp) {
        List<MedicalService> medicalServices = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = database
                    .preparedStatement("SELECT * FROM `view_services`" +
                            (cnp.equals("") ? ";" : " WHERE `cnpDoctor` = ?;"));
            if (!cnp.equals("")) {
                preparedStatement.setString(1, cnp);
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                medicalServices.add(new MedicalService(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getInt(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9),
                        resultSet.getString(10)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicalServices;
    }

    public List<MedicalService> getMedicalServices(String cnpDoctor, int idSpeciality, int idCabinet) {
        List<MedicalService> medicalServices = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT `id`, `serviceName`, `duration`, `price` FROM `view_services_by_cabinet` WHERE `cnpDoctor` = ? AND `idSpeciality` = ? AND (`idCabinet` = ? OR `idCabinet` IS NULL);");
            preparedStatement.setString(1, cnpDoctor);
            preparedStatement.setInt(2, idSpeciality);
            preparedStatement.setInt(3, idCabinet);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                medicalServices.add(new MedicalService(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getDouble(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicalServices;
    }

    public List<MedicalService> getMedicalServices(int idAppointment) {
        List<MedicalService> medicalServices = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT `idMedicalService`, `name`, `duration`, `price` FROM `view_appointment_services` WHERE `idAppointment` = ?;");
            preparedStatement.setInt(1, idAppointment);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                medicalServices.add(new MedicalService(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getDouble(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicalServices;
    }

    public boolean addMedicalService(MedicalService medicalService) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL INSERT_MEDICAL_SERVICE(?, ?, ?, ?, ?, ?, ?, ?);");
            callableStatement.setString(1, medicalService.getCnpDoctor());
            callableStatement.setString(2, medicalService.getName());
            callableStatement.setInt(3, medicalService.getIdSpeciality());
            if (medicalService.getIdAccreditation() == null) {
                callableStatement.setNull(4, Types.INTEGER);
            } else {
                callableStatement.setInt(4, medicalService.getIdAccreditation());
            }
            if (medicalService.getIdEquipment() == null) {
                callableStatement.setNull(5, Types.INTEGER);
            } else {
                callableStatement.setInt(5, medicalService.getIdEquipment());
            }
            callableStatement.setDouble(6, medicalService.getPrice());
            callableStatement.setInt(7, medicalService.getDuration());
            callableStatement.registerOutParameter(8, Types.BOOLEAN);
            callableStatement.execute();

            return callableStatement.getBoolean(8);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean saveMedicalService(MedicalService medicalService) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL SAVE_MEDICAL_SERVICE(?, ?, ?, ?, ?, ?, ?, ?, ?);");
            callableStatement.setInt(1, medicalService.getId());
            callableStatement.setString(2, medicalService.getCnpDoctor());
            callableStatement.setString(3, medicalService.getName());
            callableStatement.setInt(4, medicalService.getIdSpeciality());
            if (medicalService.getIdAccreditation() == null) {
                callableStatement.setNull(5, Types.INTEGER);
            } else {
                callableStatement.setInt(5, medicalService.getIdAccreditation());
            }
            if (medicalService.getIdEquipment() == null) {
                callableStatement.setNull(6, Types.INTEGER);
            } else {
                callableStatement.setInt(6, medicalService.getIdEquipment());
            }
            callableStatement.setDouble(7, medicalService.getPrice());
            callableStatement.setInt(8, medicalService.getDuration());
            callableStatement.registerOutParameter(9, Types.BOOLEAN);
            callableStatement.execute();

            return callableStatement.getBoolean(9);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean deleteMedicalService(int id) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL DELETE_MEDICAL_SERVICE(?, ?);");
            callableStatement.setInt(1, id);
            callableStatement.registerOutParameter(2, Types.BOOLEAN);
            callableStatement.execute();

            return callableStatement.getBoolean(2);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public Map<Integer, String> getSpecialities(String cnp) {
        Map<Integer, String> specialities = new HashMap<>();

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT `id`, `name` FROM `view_specialities_by_doctor` WHERE `cnp` = ?;");
            preparedStatement.setString(1, cnp);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                specialities.put(resultSet.getInt(1),
                        resultSet.getString(2));
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return specialities;
    }

    public Map<Integer, String> getSpecialities() {
        Map<Integer, String> specialities = new HashMap<>();

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT `id`, `name` FROM `view_specialities`;");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                specialities.put(resultSet.getInt(1),
                        resultSet.getString(2));
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return specialities;
    }

    public Map<Integer, String> getAccreditations(String cnp) {
        Map<Integer, String> accreditations = new HashMap<>();

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT `id`, `name` FROM `view_accreditations_by_doctor` WHERE `cnp` = ?;");
            preparedStatement.setString(1, cnp);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                accreditations.put(resultSet.getInt(1),
                        resultSet.getString(2));
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return accreditations;
    }

    public Map<Integer, String> getEquipments(String cnp) {
        Map<Integer, String> equipments = new HashMap<>();

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT `id`, `name` FROM `view_equipments`;");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                equipments.put(resultSet.getInt(1),
                        resultSet.getString(2));
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return equipments;
    }

    public Map<Integer, String> getCabintes(int idMedicalUnit) {
        Map<Integer, String> cabinets = new HashMap<>();

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT `id`, `name` FROM `view_cabinets` WHERE `idMedicalUnit` = ? ORDER BY `name` ASC;");
            preparedStatement.setInt(1, idMedicalUnit);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                cabinets.put(resultSet.getInt(1),
                        resultSet.getString(2));
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return cabinets;
    }

    public Map<String, String> getDoctors(int idSpeciality, int idMedicalUnit) {
        Map<String, String> doctors = new HashMap<>();
        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT VS.`cnp`, VS.`name` FROM `view_doctors_by_speciality` VS, `view_doctors_by_medical_unit` VM WHERE VS.`id` = ? AND VM.`cnpEmployee` = VS.`cnp` AND VM.`idMedicalUnit` = ? ORDER BY `name` ASC;");
            preparedStatement.setInt(1, idSpeciality);
            preparedStatement.setInt(2, idMedicalUnit);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                doctors.put(resultSet.getString(1),
                        resultSet.getString(2));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return doctors;
    }

    public Doctor getDoctor(String cnp) {
        Doctor doctor = null;

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT * FROM `view_doctors` WHERE `cnpEmployee` = ?;");
            preparedStatement.setString(1, cnp);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                doctor = new Doctor(cnp,
                        resultSet.getString(2),
                        resultSet.getFloat(3),
                        resultSet.getString(4),
                        resultSet.getString(5));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return doctor;
    }

    public List<Investigation> getInvestigations(int idReport) {
        List<Investigation> investigations = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT `idInvestigation`, `idService`, `serviceName`, CONCAT(`d_lastName`, ' ', `d_firstName`) AS `doctorName`, `remarks`, `date` FROM `view_investigations` WHERE `idReport` = ?;");
            preparedStatement.setInt(1, idReport);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                investigations.add(new Investigation(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getTimestamp(6)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return investigations;
    }

    public boolean addInvestigation(Investigation investigation, int idReport) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL INSERT_REPORT_INVESTIGATION(?, ?, ?, ?)");
            callableStatement.setInt(1, idReport);
            callableStatement.setInt(2, investigation.getIdService());
            callableStatement.setString(3, investigation.getRemarks());
            callableStatement.registerOutParameter(4, Types.BOOLEAN);
            callableStatement.execute();

            return callableStatement.getBoolean(4);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public List<Analyse> getPatientAnalyses(String cnp, Date startDate, Date endDate) {
        List<Analyse> analyses = new ArrayList<>();
        int index = 2;

        StringBuilder query = new StringBuilder("SELECT * FROM `view_patient_analyses` WHERE `cnpPatient` = ?");
        if (startDate != null) {
            query.append(" AND `date` >= ?");
        }
        if (endDate != null) {
            query.append(" AND `date` <= ?");
        }
        query.append(";");

        try {
            PreparedStatement preparedStatement = database.preparedStatement(query.toString());
            preparedStatement.setString(1, cnp);
            if (startDate != null) {
                preparedStatement.setTimestamp(index++, new Timestamp(startDate.getTime()));
            }
            if (endDate != null) {
                preparedStatement.setTimestamp(index, new Timestamp(endDate.getTime()));
            }
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                analyses.add(new Analyse(
                        resultSet.getString(2),
                        resultSet.getFloat(3),
                        resultSet.getBoolean(4),
                        resultSet.getInt(5),
                        resultSet.getFloat(6),
                        resultSet.getFloat(7),
                        resultSet.getTimestamp(8)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        analyses.sort(Analyse::compareTo);

        return analyses;
    }

    public List<Analyse> getAnalyses() {
        List<Analyse> analyses = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT * FROM `view_analyses`;");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                analyses.add(new Analyse(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getFloat(3),
                        resultSet.getFloat(4)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return analyses;
    }

    public boolean addAnalyse(Analyse analyse, String cnpPacient) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL INSERT_ANALYSE(?, ?, ?, ?)");
            callableStatement.setString(1, cnpPacient);
            callableStatement.setInt(2, analyse.getIdAnalyse());
            callableStatement.setFloat(3, analyse.getValue());
            callableStatement.registerOutParameter(4, Types.BOOLEAN);
            callableStatement.execute();

            return callableStatement.getBoolean(4);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public List<Appointment> getAppointments() {
        List<Appointment> appointments = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT `id`, `patientName`, `doctorName`, `cabinetName`, `duration`, `specialityName`, `date` FROM `view_appointments`;");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                appointments.add(new Appointment(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getInt(6),
                        resultSet.getTimestamp(7)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        appointments.sort(Appointment::compareTo);

        return appointments;
    }

    public List<Appointment> getAppointments(String cnpDoctor) {
        List<Appointment> appointments = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT `id`, `patientName`, `doctorName`, `cabinetName`, `specialityName`, `duration`, `date` FROM `view_appointments` WHERE `cnpDoctor` = ?;");
            preparedStatement.setString(1, cnpDoctor);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                appointments.add(new Appointment(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getInt(6),
                        resultSet.getTimestamp(7)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        appointments.sort(Appointment::compareTo);

        return appointments;
    }

    public boolean checkAppointmentByDoctor(String cnpDoctor, Date startDate, Date endDate) {
        boolean result = false;

        Timestamp start = new Timestamp(startDate.getTime());
        Timestamp end = new Timestamp(endDate.getTime());

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT COUNT(*) FROM `view_appointments` WHERE `cnpDoctor` = ? AND ((`date` < ? AND `endDate` > ?) OR (`date` < ? AND `endDate` > ?) OR (`date` >= ? AND `endDate` <= ?));");
            preparedStatement.setString(1, cnpDoctor);
            preparedStatement.setTimestamp(2, start);
            preparedStatement.setTimestamp(3, start);
            preparedStatement.setTimestamp(4, end);
            preparedStatement.setTimestamp(5, end);
            preparedStatement.setTimestamp(6, start);
            preparedStatement.setTimestamp(7, end);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) == 0) {
                result = true;
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public boolean checkAppointmentByDoctorSchedule(String cnpDoctor, int idMedicalUnit, Date startDate, Date endDate) {
        boolean result = false;

        Timestamp start = new Timestamp(startDate.getTime());
        Timestamp end = new Timestamp(endDate.getTime());

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT COUNT(*) FROM `view_employee_schedule` WHERE `cnpEmployee` = ? AND `idMedicalUnit` = ? AND `dayOfWeek` LIKE DAYNAME(?) AND `startHour` <= TIME(?) AND `endHour` >= TIME(?);");
            preparedStatement.setString(1, cnpDoctor);
            preparedStatement.setInt(2, idMedicalUnit);
            preparedStatement.setTimestamp(3, start);
            preparedStatement.setTimestamp(4, start);
            preparedStatement.setTimestamp(5, end);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getInt(1) > 0;
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public boolean checkAppointmentByDoctorHolidays(String cnpDoctor, Date startDate) {
        boolean result = false;

        Timestamp start = new Timestamp(startDate.getTime());

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT COUNT(*) FROM `view_holidays` WHERE `cnpEmployee` = ? AND `startDate` <= DATE(?) AND `endDate` >= DATE(?);");
            preparedStatement.setString(1, cnpDoctor);
            preparedStatement.setTimestamp(2, start);
            preparedStatement.setTimestamp(3, start);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getInt(1) == 0;
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public boolean checkAppointmentByPacient(String cnpPacient, Date startDate, Date endDate) {
        boolean result = false;

        Timestamp start = new Timestamp(startDate.getTime());
        Timestamp end = new Timestamp(endDate.getTime());

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT COUNT(*) FROM `view_appointments` WHERE `cnpPatient` = ? AND ((`date` < ? AND `endDate` > ?) OR (`date` < ? AND `endDate` > ?) OR (`date` >= ? AND `endDate` <= ?));");
            preparedStatement.setString(1, cnpPacient);
            preparedStatement.setTimestamp(2, start);
            preparedStatement.setTimestamp(3, start);
            preparedStatement.setTimestamp(4, end);
            preparedStatement.setTimestamp(5, end);
            preparedStatement.setTimestamp(6, start);
            preparedStatement.setTimestamp(7, end);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getInt(1) == 0;
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public boolean checkAppointmentBySpeciality(int idSpeciality, Date startDate, Date endDate) {
        boolean result = false;

        Timestamp start = new Timestamp(startDate.getTime());
        Timestamp end = new Timestamp(endDate.getTime());

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT COUNT(*) FROM `view_appointments` WHERE `idSpeciality` = ? AND ((`date` < ? AND `endDate` > ?) OR (`date` < ? AND `endDate` > ?) OR (`date` >= ? AND `endDate` <= ?));");
            preparedStatement.setInt(1, idSpeciality);
            preparedStatement.setTimestamp(2, start);
            preparedStatement.setTimestamp(3, start);
            preparedStatement.setTimestamp(4, end);
            preparedStatement.setTimestamp(5, end);
            preparedStatement.setTimestamp(6, start);
            preparedStatement.setTimestamp(7, end);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) == 0) {
                result = true;
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public boolean checkAppointmentByCabinet(int idCabinet, Date startDate, Date endDate) {
        boolean result = false;

        Timestamp start = new Timestamp(startDate.getTime());
        Timestamp end = new Timestamp(endDate.getTime());

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT COUNT(*) FROM `view_appointments` WHERE `idCabinet` = ? AND ((`date` < ? AND `endDate` > ?) OR (`date` < ? AND `endDate` > ?) OR (`date` >= ? AND `endDate` <= ?));");
            preparedStatement.setInt(1, idCabinet);
            preparedStatement.setTimestamp(2, start);
            preparedStatement.setTimestamp(3, start);
            preparedStatement.setTimestamp(4, end);
            preparedStatement.setTimestamp(5, end);
            preparedStatement.setTimestamp(6, start);
            preparedStatement.setTimestamp(7, end);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) == 0) {
                result = true;
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public Appointment getAppointment(int idAppointment) {
        Appointment appointment = null;

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT `patientName`, `doctorName`, `cabinetName`, `specialityName`, `duration`, `date` FROM `view_appointments` WHERE `id` = ?;");
            preparedStatement.setInt(1, idAppointment);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                appointment = new Appointment(
                        idAppointment,
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5),
                        resultSet.getTimestamp(6));
                appointment.setServices(getMedicalServices(idAppointment));
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return appointment;
    }

    public int addAppointment(Appointment appointment) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL INSERT_APPOINTMENT(?, ?, ?, ?, ?, ?)");
            callableStatement.setString(1, appointment.getCnpPatient());
            callableStatement.setString(2, appointment.getCnpDoctor());
            callableStatement.setInt(3, appointment.getIdCabinet());
            callableStatement.setInt(4, appointment.getIdSpeciality());
            callableStatement.setTimestamp(5, new Timestamp(appointment.getDate().getTime()));
            callableStatement.registerOutParameter(6, Types.INTEGER);
            callableStatement.execute();

            return callableStatement.getInt(6);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return 0;
    }

    public boolean addAppointmentServices(int idAppointment, List<MedicalService> services) {
        final boolean[] result = {true};

        services.forEach(medicalService -> {
            try {
                CallableStatement callableStatement = database.callableStatement("CALL INSERT_APPOINTMENT_SERVICE(?, ?, ?)");
                callableStatement.setInt(1, medicalService.getId());
                callableStatement.setInt(2, idAppointment);
                callableStatement.registerOutParameter(3, Types.BOOLEAN);
                callableStatement.execute();

                result[0] = result[0] && callableStatement.getBoolean(3);
            } catch (SQLException ex) {
                result[0] = false;
                ex.printStackTrace();
            }
        });

        return result[0];
    }

}

