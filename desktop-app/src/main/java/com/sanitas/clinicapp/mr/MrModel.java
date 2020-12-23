package com.sanitas.clinicapp.mr;

import com.sanitas.clinicapp.ClinicApplication;
import com.sanitas.clinicapp.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public List<Report> getReports(String cnp, Date startDate, Date endDate) {
        List<Report> reports = new ArrayList<>();
        int index = 2;

        StringBuilder query = new StringBuilder("SELECT `date`, `lastEdit`, `sealCode` FROM `reports` WHERE `cnpPatient` = ?");
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
                preparedStatement.setDate(++index, (java.sql.Date) startDate);
            }
            if (endDate != null) {
                preparedStatement.setDate(index, (java.sql.Date) endDate);
            }
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                reports.add(new Report(resultSet.getDate(1),
                        resultSet.getDate(2),
                        resultSet.getInt(3)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return reports;
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

}
