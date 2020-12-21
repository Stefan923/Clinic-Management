package com.sanitas.clinicapp.hr.addemployee;

import com.sanitas.clinicapp.ClinicApplication;
import com.sanitas.clinicapp.Database;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

public class AddModel {
    private Database database;

    public AddModel() {
        database = ClinicApplication.getDatabase();
    }

    public boolean insertEmployee(String cnp, String lastname, String firstname, String adress,String phone,
                               String iban, int contract,String date,String position,double salary, int hours)

    {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL INSERT_EMPLOYEE((?, ?, ?,?,?,?,?,?,?,?,?,?)");
            callableStatement.setString(1, cnp);
            callableStatement.setString(2, lastname);
            callableStatement.setString(3, firstname);
            callableStatement.setString(4, adress);
            callableStatement.setString(5, phone);
            callableStatement.setString(6, iban);
            callableStatement.setInt(7, contract);
            callableStatement.setString(8, date);
            callableStatement.setString(9, position);
            callableStatement.setDouble(10, salary);
            callableStatement.setInt(11, hours);
            callableStatement.registerOutParameter(12, Types.INTEGER);
            callableStatement.executeQuery();

            boolean result = callableStatement.getBoolean(12);
            callableStatement.close();

            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
