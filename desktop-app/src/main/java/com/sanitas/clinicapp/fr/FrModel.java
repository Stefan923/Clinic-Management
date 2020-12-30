package com.sanitas.clinicapp.fr;

import com.sanitas.clinicapp.ClinicApplication;
import com.sanitas.clinicapp.Database;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

public class FrModel {
    private final Database database;

    public FrModel() {
        database = ClinicApplication.getDatabase();
    }

    public double getMedicalUnitProfit(int id, Date startDate, Date endDate)
    {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL GET_MEDICAL_UNIT_PROFIT(?, ?, ?, ?);");
            callableStatement.setInt(1, id);
            callableStatement.setDate(2, (java.sql.Date) startDate);
            callableStatement.setDate(3, (java.sql.Date) endDate);
            callableStatement.registerOutParameter(4, Types.DOUBLE);
            callableStatement.execute();

            return callableStatement.getDouble(4);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0.0;
    }
    public double getProfitByDoctor(String cnp, Date startDate, Date endDate)
    {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL GET_PROFIT_BY_DOCTOR(?, ?, ?, ?);");
            callableStatement.setString(1, cnp);
            callableStatement.setDate(2, (java.sql.Date) startDate);
            callableStatement.setDate(3, (java.sql.Date) endDate);
            callableStatement.registerOutParameter(4, Types.DOUBLE);
            callableStatement.execute();

            return callableStatement.getDouble(4);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0.0;
    }
    public double getProfitBySpeciality(String cnp, Date startDate, Date endDate)
    {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL GET_PROFIT_BY_SPECIALITY(?, ?, ?, ?);");
            callableStatement.setString(1, cnp);
            callableStatement.setDate(2, (java.sql.Date) startDate);
            callableStatement.setDate(3, (java.sql.Date) endDate);
            callableStatement.registerOutParameter(4, Types.DOUBLE);
            callableStatement.execute();

            return callableStatement.getDouble(4);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0.0;

    }
    public double getTotalProfit(Date startDate, Date endDate)
    {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL GET_TOTAL_PROFIT(?, ?, ?);");
            callableStatement.setDate(1, (java.sql.Date) startDate);
            callableStatement.setDate(2, (java.sql.Date) endDate);
            callableStatement.registerOutParameter(3, Types.DOUBLE);
            callableStatement.execute();

            return callableStatement.getDouble(3);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0.0;

    }
    public double getEmployeeSalary(String cnp, Date startDate, Date endDate)
    {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL GET_EMPLOYEE_SALARY(?, ?, ?, ?);");
            callableStatement.setString(1, cnp);
            callableStatement.setDate(2, (java.sql.Date) startDate);
            callableStatement.setDate(3, (java.sql.Date) endDate);
            callableStatement.registerOutParameter(4, Types.DOUBLE);
            callableStatement.execute();

            return callableStatement.getDouble(4);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0.0;
    }
    public double getDoctorSalary(String cnp, Date startDate, Date endDate)
    {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL GET_DOCTOR_SALARY(?, ?, ?, ?);");
            callableStatement.setString(1, cnp);
            callableStatement.setDate(2, (java.sql.Date) startDate);
            callableStatement.setDate(3, (java.sql.Date) endDate);
            callableStatement.registerOutParameter(4, Types.DOUBLE);
            callableStatement.execute();

            return callableStatement.getDouble(4);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0.0;
    }
    public double getDoctorProfitTotal(String cnp, Date startDate, Date endDate)
    {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL GET_DOCTOR_PROFIT_TOTAL(?, ?, ?, ?);");
            callableStatement.setString(1, cnp);
            callableStatement.setDate(2, (java.sql.Date) startDate);
            callableStatement.setDate(3, (java.sql.Date) endDate);
            callableStatement.registerOutParameter(4, Types.DOUBLE);
            callableStatement.execute();

            return callableStatement.getDouble(4);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0.0;
    }


}
