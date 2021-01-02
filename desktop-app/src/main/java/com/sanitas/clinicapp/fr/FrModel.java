package com.sanitas.clinicapp.fr;

import com.sanitas.clinicapp.ClinicApplication;
import com.sanitas.clinicapp.Database;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FrModel {
    private final Database database;

    public FrModel() {
        database = ClinicApplication.getDatabase();
    }

    public double getMedicalUnitProfit(int id, Date startDate, Date endDate) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL GET_MEDICAL_UNIT_PROFIT(?, ?, ?, ?);");
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

    public double getProfitByDoctor(String cnp, Date startDate, Date endDate) {
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

    public double getProfitBySpeciality(int id, Date startDate, Date endDate) {
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

    public double getTotalProfit(Date startDate, Date endDate) {
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

    public double getEmployeeSalary(String cnp, Date startDate, Date endDate) {
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

    public double getDoctorSalary(String cnp, Date startDate, Date endDate) {
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

    public double getDoctorProfitTotal(String cnp, Date startDate, Date endDate) {
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

}
