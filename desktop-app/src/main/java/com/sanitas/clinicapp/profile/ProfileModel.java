package com.sanitas.clinicapp.profile;

import com.sanitas.clinicapp.ClinicApplication;
import com.sanitas.clinicapp.Database;
import com.sanitas.clinicapp.hr.Employee;
import com.sanitas.clinicapp.hr.panels.Nurse;
import com.sanitas.clinicapp.struct.Doctor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ProfileModel {
    private Database database;
    private boolean data=false;

    public ProfileModel() {
        database = ClinicApplication.getDatabase();
    }

    public Employee getEmployee(String cnp) {
        Employee employee = null;

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT * FROM `view_employees` WHERE `cnp`=? ;");
            preparedStatement.setString(1, cnp);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                employee = new Employee(cnp,
                        resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
                        resultSet.getString(7), resultSet.getInt(8), resultSet.getDate(9),
                        resultSet.getString(10), resultSet.getDouble(11), resultSet.getInt(12));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return employee;
    }

    public Nurse getNurse(Employee employee) {
        Nurse nurse = null;

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT * FROM `view_nurses` WHERE `cnpEmployee`=? ;");
            preparedStatement.setString(1, employee.getCnp());
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                nurse = new Nurse(employee,
                        resultSet.getString(2), resultSet.getString(3));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return nurse;
    }
    public Doctor getDoctor(Employee employee) {
        Doctor doctor = null;

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT * FROM `view_doctors` WHERE `cnpEmployee` = ?;");
            preparedStatement.setString(1, employee.getCnp());
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                doctor = new Doctor(employee,
                        resultSet.getString(2),
                        resultSet.getFloat(3),
                        resultSet.getString(4),
                        resultSet.getString(5));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return doctor;
    }

    public Employee getEmployee(String lastname, String firstname, String position)
    {
        boolean first = true;
        int and = 0;
       Employee employee= new Employee();
        try {
            StringBuilder stringBuilder = new StringBuilder("SELECT * FROM view_employees ");
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
                employee = new Employee(
                        resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
                        resultSet.getString(7), resultSet.getInt(8), resultSet.getDate(9),
                        resultSet.getString(10), resultSet.getDouble(11), resultSet.getInt(12));
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return employee;
    }

    public void setData(boolean data) {
        this.data = data;
    }

    public boolean isData() {
        return data;
    }

}
