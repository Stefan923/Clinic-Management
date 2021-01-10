package com.sanitas.clinicapp.hr;

import com.mysql.cj.xdevapi.PreparableStatement;
import com.sanitas.clinicapp.ClinicApplication;
import com.sanitas.clinicapp.Database;
import com.sanitas.clinicapp.hr.panels.Holiday;
import com.sanitas.clinicapp.hr.panels.Nurse;
import com.sanitas.clinicapp.struct.Doctor;


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

    public boolean insertDoctor(String cnp, String sealCode, float commission, String scientificTitle, String didacticTitle) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL INSERT_DOCTOR(?,?,?,?,?,?);");
            callableStatement.setString(1, cnp);
            callableStatement.setString(2, sealCode);
            callableStatement.setFloat(3, commission);
            callableStatement.setString(4, scientificTitle);
            callableStatement.setString(5, didacticTitle);
            callableStatement.registerOutParameter(6, Types.BOOLEAN);
            callableStatement.execute();

            return callableStatement.getBoolean(6);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }


    public List<Schedule> viewSchedule(String cnp) {
        List<Schedule> schedule = new ArrayList<Schedule>();

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT * FROM `view_employee_schedule` WHERE `cnpEmployee`=? ;");
            preparedStatement.setString(1, cnp);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                schedule.add( new Schedule(cnp,
                        resultSet.getInt(1), resultSet.getString(3),
                        resultSet.getTime(4), resultSet.getTime(5)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return schedule;
    }

    public List<Holiday> viewHoliday(String cnp) {
        List<Holiday> holidays = new ArrayList<Holiday>();

        try {
            PreparedStatement preparedStatement = database.preparedStatement("SELECT * FROM `view_holidays` WHERE `cnpEmployee`=? ;");
            preparedStatement.setString(1, cnp);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                holidays.add( new Holiday(cnp,
                        resultSet.getDate(2), resultSet.getDate(3)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return holidays;
    }

    public boolean insertHoliday(String cnp,Date start,Date end,int force) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL INSERT_HOLIDAY(?, ?,?,?,?);");
            callableStatement.setString(1, cnp);
            callableStatement.setDate(2, start);
            callableStatement.setDate(3, end);
            callableStatement.setInt(4, force);
            callableStatement.registerOutParameter(5, Types.BOOLEAN);
            callableStatement.execute();

            return callableStatement.getBoolean(5);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
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

    public boolean deleteEmployeeSchedule(String cnpEmployee,int idMedicalUnit, String dayOfWeek, Time startHour,Time endHour,int force) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL DELETE_EMPLOYEE_SCHEDULE(?, ?, ?, ?, ?, ?, ?);");
            callableStatement.setString(1, cnpEmployee);
            callableStatement.setInt(2, idMedicalUnit);
            callableStatement.setString(3, dayOfWeek);
            callableStatement.setTime(4, startHour);
            callableStatement.setTime(5, endHour);
            callableStatement.setInt(6, force);
            callableStatement.registerOutParameter(7, Types.BOOLEAN);
            callableStatement.execute();

            return callableStatement.getBoolean(7);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean insertEmployeeSchedule(String cnpEmployee,int idMedicalUnit, String dayOfWeek, Time startHour,Time endHour) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL INSERT_EMPLOYEE_SCHEDULE(?, ?, ?, ?, ?, ?);");
            callableStatement.setString(1, cnpEmployee);
            callableStatement.setInt(2, idMedicalUnit);
            callableStatement.setString(3, dayOfWeek);
            callableStatement.setTime(4, startHour);
            callableStatement.setTime(5, endHour);
            callableStatement.registerOutParameter(6, Types.BOOLEAN);
            callableStatement.execute();

            return callableStatement.getBoolean(6);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean updateEmployeeSchedule(String cnpEmployee,int idMedicalUnit, String dayOfWeek, Time startHour,Time endHour,Time newstartHour,Time newendHour,int force) {
        try {
            CallableStatement callableStatement = database.callableStatement("CALL UPDATE_EMPLOYEE_SCHEDULE(?, ?, ?, ?, ?, ?, ?,?,?);");
            callableStatement.setString(1, cnpEmployee);
            callableStatement.setInt(2, idMedicalUnit);
            callableStatement.setString(3, dayOfWeek);
            callableStatement.setTime(4, startHour);
            callableStatement.setTime(5, endHour);
            callableStatement.setTime(6, newstartHour);
            callableStatement.setTime(7, newendHour);
            callableStatement.setInt(8, force);
            callableStatement.registerOutParameter(9, Types.BOOLEAN);
            callableStatement.execute();

            return callableStatement.getBoolean(9);
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
                employeeList.add(new Employee(
                        resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
                        resultSet.getString(7), resultSet.getInt(8), resultSet.getDate(9),
                        resultSet.getString(10), resultSet.getDouble(11), resultSet.getInt(12)));
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
