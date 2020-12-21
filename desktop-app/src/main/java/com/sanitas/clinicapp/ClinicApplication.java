package com.sanitas.clinicapp;

import com.sanitas.clinicapp.hr.Employee;
import com.sanitas.clinicapp.hr.HrMVC;
import com.sanitas.clinicapp.hr.HrModel;
import com.sanitas.clinicapp.hr.addemployee.AddMVC;
import com.sanitas.clinicapp.hr.addemployee.AddView;
import com.sanitas.clinicapp.hr.openViewMVC;
import com.sanitas.clinicapp.login.LoginMVC;

import java.util.ArrayList;
import java.util.List;

public class ClinicApplication {

    private static Database database;

    public static void main(String[] args) {
        database = new Database("localhost", 3306, "polyclinics", "root", "workhardgetgood");
        //new LoginMVC();
        //new HrMVC();
        //new openViewMVC();
        new AddMVC();

    }

    public static Database getDatabase() {
        return database;
    }

}
