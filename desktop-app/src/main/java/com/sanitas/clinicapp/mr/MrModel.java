package com.sanitas.clinicapp.mr;

import com.sanitas.clinicapp.ClinicApplication;
import com.sanitas.clinicapp.Database;

import java.sql.*;
import java.sql.Date;
import java.util.*;

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
        List<Patient> patients = new ArrayList<Patient>();
        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT `cnp`, `lastname`, `firstname` FROM `patients` WHERE `lastname` LIKE CONCAT('%', ?, '%') AND  `firstname` LIKE CONCAT('%', ?, '%');");
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

    public Patient getPatient(String cnp) {
        Patient patient = null;

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT `lastname`, `firstname`, `iban` FROM `patients` WHERE `cnp` = ?;");
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

}
