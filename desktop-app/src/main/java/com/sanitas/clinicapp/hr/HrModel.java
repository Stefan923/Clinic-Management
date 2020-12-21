package com.sanitas.clinicapp.hr;

import com.mysql.cj.xdevapi.PreparableStatement;
import com.sanitas.clinicapp.ClinicApplication;
import com.sanitas.clinicapp.Database;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HrModel {
    private Database database;
    private boolean data=false;

    public HrModel() {
        database = ClinicApplication.getDatabase();
    }

    public void openView(){ new openViewMVC();}

    public void setData(boolean data) {
        this.data = data;
    }

    public boolean isData() {
        return data;
    }
}
