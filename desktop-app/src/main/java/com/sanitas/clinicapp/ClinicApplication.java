package com.sanitas.clinicapp;

import com.sanitas.clinicapp.hr.Employee;
import com.sanitas.clinicapp.login.LoginMVC;

import java.util.List;

public class ClinicApplication {

    private static Database database;

    private static Account user;

    public static void main(String[] args) {
        database = new Database("localhost", 3306, "polyclinics", "root", "DButcnMySQL");

        new LoginMVC();
    }

    public static Database getDatabase() {
        return database;
    }

    public static Account getUser() {
        return user;
    }

    public static void disconnectUser() {
        user = null;
    }

    public static String getCnp() { return user.getCnp(); }

    public static void setUser(Employee employee, String cnp, int idMedicalUnit, List<String> permissions) {
        user = new Account(employee, cnp, idMedicalUnit, permissions);
    }

    public static class Account {

        private final int idMedicalUnit;

        private final Employee employee;
        private final String cnp;
        private final List<String> permissions;

        public Account(Employee employee, String cnp, int idMedicalUnit, List<String> permissions) {
            this.employee = employee;
            this.cnp = cnp;
            this.idMedicalUnit = idMedicalUnit;
            this.permissions = permissions;
        }

        public Employee getEmployee() {
            return employee;
        }

        public String getCnp() {
            return cnp;
        }

        public int getIdMedicalUnit() {
            return idMedicalUnit;
        }

        public boolean hasPermission(String string) {
            return permissions.contains(string) || permissions.contains("*");
        }

        public boolean isSuperAdmin() {
            return permissions.contains("+");
        }

    }

}
