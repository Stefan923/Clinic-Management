package com.sanitas.clinicapp.login;

import com.sanitas.clinicapp.ClinicApplication;
import com.sanitas.clinicapp.Database;
import com.sanitas.clinicapp.homepage.HomePageMVC;
import com.sanitas.clinicapp.hr.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public void loadUserData(String username) {
        String cnp = "";
        List<String> permissions = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT `cnp` FROM `view_accounts` WHERE `username` LIKE ?;");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                cnp = resultSet.getString(1);
            }

            PreparedStatement preparedStatement1 = database.preparedStatement("SELECT `permission` FROM `view_permissions` WHERE `cnp` = ?;");
            preparedStatement1.setString(1, cnp);
            ResultSet resultSet1 = preparedStatement1.executeQuery();

            while (resultSet.next()) {
                permissions.add(resultSet.getString(1));
            }

            ClinicApplication.setUser(null, cnp, permissions);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void openHomePage() {
        new HomePageMVC();
    }

}
