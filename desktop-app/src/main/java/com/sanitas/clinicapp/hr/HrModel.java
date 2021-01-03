package com.sanitas.clinicapp.hr;

import com.sanitas.clinicapp.ClinicApplication;
import com.sanitas.clinicapp.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HrModel {
    private Database database;
    private boolean data=false;

    public HrModel() {
        database = ClinicApplication.getDatabase();
    }

    public Employee getEmployee(String cnp) {
        Employee employee = null;

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT lastName,firstName,position FROM `employees` WHERE `cnp` = ?;");
            preparedStatement.setString(1, cnp);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                employee = new Employee(cnp,
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return employee;
    }
    public boolean deleteEmployee(String cnp) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL DELETE_EMPLOYEE(?, ?);");
            callableStatement.setString(1, cnp);
            callableStatement.registerOutParameter(2, Types.BOOLEAN);
            callableStatement.execute();

            return callableStatement.getBoolean(2);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean updateEmployee(String cnp, String lastname, String firstname,String position) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL UPDATE_EMPLOYEE(?, ?, ?, ?,?);");
            callableStatement.setString(1, cnp);
            callableStatement.setString(2, lastname);
            callableStatement.setString(3, firstname);
            callableStatement.setString(4, position);
            callableStatement.registerOutParameter(5, Types.BOOLEAN);
            callableStatement.execute();

            return callableStatement.getBoolean(5);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public List<Employee> getAllData(String lastname, String firstname, String position) {
        boolean first = true;
        int and = 0;
        List<Employee> employeeList = new ArrayList<Employee>();
        try {
            StringBuilder stringBuilder = new StringBuilder("SELECT cnp, lastName, firstName, position FROM employees ");
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

    public boolean insertEmployee(String cnp, String lastname, String firstname, String adress,String phone, String email,
                                  String iban, int contract,String date,String position,double salary, int hours) {

        try {
            CallableStatement callableStatement = database.callableStatement("CALL INSERT_EMPLOYEE(?, ?, ?,?,?,?,?,?,?,?,?,?,?)");
            callableStatement.setString(1, cnp);
            callableStatement.setString(2, lastname);
            callableStatement.setString(3, firstname);
            callableStatement.setString(4, adress);
            callableStatement.setString(5, phone);
            callableStatement.setString(6, email);
            callableStatement.setString(7, iban);
            callableStatement.setInt(8, contract);
            callableStatement.setString(9, date);
            callableStatement.setString(10, position);
            callableStatement.setDouble(11, salary);
            callableStatement.setInt(12, hours);
            callableStatement.registerOutParameter(13, Types.INTEGER);
            callableStatement.executeQuery();

            boolean result = callableStatement.getBoolean(13);
            callableStatement.close();

            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }



    public void setData(boolean data) {
        this.data = data;
    }

    public boolean isData() {
        return data;
    }
}
