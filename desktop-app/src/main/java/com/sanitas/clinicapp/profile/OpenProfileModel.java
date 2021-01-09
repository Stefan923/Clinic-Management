package com.sanitas.clinicapp.profile;

import com.sanitas.clinicapp.Database;
import com.sanitas.clinicapp.hr.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpenProfileModel {

    private Database database;

    public Employee getEmployee(String cnp, String lastname, String firstname, String address, String phoneNum, String email, String iban, String contractNum, String employmentDate, String position, String salary, String workedHrs) {
        Employee employee = null;

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT * FROM employees");
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                employee = new Employee(resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
                        resultSet.getString(7), resultSet.getInt(8), resultSet.getDate(9),
                        resultSet.getString(10), resultSet.getDouble(11), resultSet.getInt(13));
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return employee;
    }

}
