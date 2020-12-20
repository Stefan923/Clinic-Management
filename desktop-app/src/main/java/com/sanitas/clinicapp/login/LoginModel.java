package com.sanitas.clinicapp.login;

import com.sanitas.clinicapp.ClinicApplication;
import com.sanitas.clinicapp.Database;
import com.sanitas.clinicapp.homepage.HomePageMVC;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

public class LoginModel {

    private Database database;

    public LoginModel() {
        database = ClinicApplication.getDatabase();
    }

    public boolean checkCreditentials(String username, String password) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL CHECK_CREDITENTIALS(?, ?, ?)");
            callableStatement.setString(1, username);
            callableStatement.setString(2, password);
            callableStatement.registerOutParameter(3, Types.INTEGER);
            callableStatement.executeQuery();

            boolean result = callableStatement.getBoolean(3);
            callableStatement.close();

            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void openHomePage() {
        new HomePageMVC();
    }

}
