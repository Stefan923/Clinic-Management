package com.sanitas.clinicapp;

import com.sanitas.clinicapp.login.LoginMVC;

public class ClinicApplication {

    private static Database database;

    public static void main(String[] args) {
        database = new Database("localhost", 3306, "polyclinics", "username", "password");

        new LoginMVC();
    }

    public static Database getDatabase() {
        return database;
    }

}
