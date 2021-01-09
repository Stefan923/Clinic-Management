package com.sanitas.clinicapp.profile;

import com.sanitas.clinicapp.ClinicApplication;
import com.sanitas.clinicapp.Database;

public class ProfileModel {
    private Database database;
    private boolean data=false;

    public ProfileModel() {
        database = ClinicApplication.getDatabase();
    }

    public void openView(){ new OpenProfileMVC();}

    public void setData(boolean data) {
        this.data = data;
    }

    public boolean isData() {
        return data;
    }
}
