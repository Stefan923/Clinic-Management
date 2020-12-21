package com.sanitas.clinicapp.hr;

import com.sanitas.clinicapp.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class openViewModel {

    private Database database;
    public List<Employee> getAllData(String lastname, String firstname, String position) {
        boolean first = true;
        int and = 0;
        List<Employee> employeeList = new ArrayList<Employee>();
        try {
            StringBuilder stringBuilder = new StringBuilder("SELECT  lastName, firstName, position,cnp FROM employees ");
            if ((!lastname.equals("") || !firstname.equals("") || !position.equals("")) && first) {
                stringBuilder.append(" WHERE ");
                first = false;
            }
            if (!lastname.equals("")) {
                if (and != 0 && and < 3)
                    stringBuilder.append(" AND ");
                and++;
                stringBuilder.append(" lastName LIKE '");
                stringBuilder.append(lastname);
                stringBuilder.append("' ");
            }

            if (!firstname.equals("")) {
                if (and != 0 && and < 3)
                    stringBuilder.append(" AND ");
                and++;
                stringBuilder.append(" firstName LIKE '");
                stringBuilder.append(firstname);
                stringBuilder.append("' ");
            }

            if (!position.equals("")) {
                if (and != 0 && and < 3)
                    stringBuilder.append(" AND ");
                and++;
                stringBuilder.append(" position LIKE '");
                stringBuilder.append(position);
                stringBuilder.append("' ");
            }

            PreparedStatement preparedStatement = database.preparedStatement(stringBuilder.toString());
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                employeeList.add(new Employee(resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4)));
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return employeeList;
    }


}
