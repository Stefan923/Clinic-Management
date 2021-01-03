package com.sanitas.clinicapp.fr;

import com.sanitas.clinicapp.ClinicApplication;
import com.sanitas.clinicapp.Database;
import com.sanitas.clinicapp.struct.Transaction;

import java.sql.*;
import java.util.*;

public class FrModel {
    private final Database database;

    public FrModel() {
        database = ClinicApplication.getDatabase();
    }

    public double getMedicalUnitProfit(String iban, java.util.Date startDate, java.util.Date endDate) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL GET_MEDICAL_UNIT_PROFIT(?, ?, ?, ?);");
            callableStatement.setString(1, iban);
            callableStatement.setDate(2, new java.sql.Date(startDate.getTime()));
            callableStatement.setDate(3, new java.sql.Date(endDate.getTime()));
            callableStatement.registerOutParameter(4, Types.DOUBLE);
            callableStatement.execute();

            return callableStatement.getDouble(4);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0.0;
    }

    public double getProfitByDoctor(String cnp, java.util.Date startDate, java.util.Date endDate) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL GET_PROFIT_BY_DOCTOR(?, ?, ?, ?);");
            callableStatement.setString(1, cnp);
            callableStatement.setDate(2, new java.sql.Date(startDate.getTime()));
            callableStatement.setDate(3, new java.sql.Date(endDate.getTime()));
            callableStatement.registerOutParameter(4, Types.DOUBLE);
            callableStatement.execute();

            return callableStatement.getDouble(4);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0.0;
    }

    public double getProfitBySpeciality(int id, java.util.Date startDate, java.util.Date endDate) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL GET_PROFIT_BY_SPECIALITY(?, ?, ?, ?);");
            callableStatement.setInt(1, id);
            callableStatement.setDate(2, new java.sql.Date(startDate.getTime()));
            callableStatement.setDate(3, new java.sql.Date(endDate.getTime()));
            callableStatement.registerOutParameter(4, Types.DOUBLE);
            callableStatement.execute();

            return callableStatement.getDouble(4);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0.0;

    }

    public double getTotalProfit(java.util.Date startDate, java.util.Date endDate) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL GET_TOTAL_PROFIT(?, ?, ?);");
            callableStatement.setDate(1, new java.sql.Date(startDate.getTime()));
            callableStatement.setDate(2, new java.sql.Date(endDate.getTime()));
            callableStatement.registerOutParameter(3, Types.DOUBLE);
            callableStatement.execute();

            return callableStatement.getDouble(3);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0.0;

    }

    public double getEmployeeSalary(String cnp, java.util.Date startDate, java.util.Date endDate) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL GET_EMPLOYEE_SALARY(?, ?, ?, ?);");
            callableStatement.setString(1, cnp);
            callableStatement.setDate(2, new java.sql.Date(startDate.getTime()));
            callableStatement.setDate(3, new java.sql.Date(endDate.getTime()));
            callableStatement.registerOutParameter(4, Types.DOUBLE);
            callableStatement.execute();

            return callableStatement.getDouble(4);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0.0;
    }

    public double getDoctorSalary(String cnp, java.util.Date startDate, java.util.Date endDate) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL GET_DOCTOR_SALARY(?, ?, ?, ?);");
            callableStatement.setString(1, cnp);
            callableStatement.setDate(2, new java.sql.Date(startDate.getTime()));
            callableStatement.setDate(3, new java.sql.Date(endDate.getTime()));
            callableStatement.registerOutParameter(4, Types.DOUBLE);
            callableStatement.execute();

            return callableStatement.getDouble(4);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0.0;
    }

    public double getDoctorProfitTotal(String cnp, java.util.Date startDate, java.util.Date endDate) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL GET_DOCTOR_PROFIT_TOTAL(?, ?, ?, ?);");
            callableStatement.setString(1, cnp);
            callableStatement.setDate(2, new java.sql.Date(startDate.getTime()));
            callableStatement.setDate(3, new java.sql.Date(endDate.getTime()));
            callableStatement.registerOutParameter(4, Types.DOUBLE);
            callableStatement.execute();

            return callableStatement.getDouble(4);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0.0;
    }

    public HashMap<String, String> getMedicalUnits() {
        HashMap<String, String> medicalUnits = new HashMap<>();
        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT * FROM `view_medical_units`;");
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                medicalUnits.put(result.getString(1),
                        result.getString(2));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return medicalUnits;
    }

    public List<Transaction> getTransactions(java.util.Date startDate, java.util.Date endDate) {
        List<Transaction> transactions = new ArrayList<>();
        int index = 1;

        StringBuilder query = new StringBuilder("SELECT * FROM `view_transactions`");
        if (startDate != null) {
            query.append(" WHERE `date` >= ?");
            index++;
        }
        if(endDate != null) {
            query.append(index == 1 ? " WHERE" : "").append(" AND `date` <= ?");
        }

        query.append(" ORDER BY `date` DESC;");
        index = 1;

        try {
            PreparedStatement preparedStatement = database.preparedStatement(query.toString());
            if (startDate != null) {
                preparedStatement.setTimestamp(index++, new Timestamp(startDate.getTime()));
            }
            if (endDate != null) {
                preparedStatement.setTimestamp(index, new Timestamp(endDate.getTime()));
            }
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                transactions.add(new Transaction(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDate(3),
                        resultSet.getFloat(4),
                        resultSet.getString(5),
                        resultSet.getString(6)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        transactions.sort(Transaction::compareTo);

        return transactions;
    }

    public Map<Integer, String> getSpecialities() {
        HashMap<Integer, String> specialities = new HashMap<>();
        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT * FROM `view_specialities`;");
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                specialities.put(result.getInt(1),
                        result.getString(2));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return specialities;
    }
}
