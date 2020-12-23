package com.sanitas.clinicapp.mr;

import java.util.Date;

public class Report {

    private final Date date;
    private final Date lastEdit;

    private final int sealCode;

    public Report(Date date, Date lastEdit, int sealCode) {
        this.date = date;
        this.lastEdit = lastEdit;
        this.sealCode = sealCode;
    }

    public Date getDate() {
        return date;
    }

    public Date getLastEdit() {
        return lastEdit;
    }

    public int getSealCode() {
        return sealCode;
    }

}
