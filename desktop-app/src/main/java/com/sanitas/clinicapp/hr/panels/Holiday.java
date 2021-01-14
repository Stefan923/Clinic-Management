package com.sanitas.clinicapp.hr.panels;

import java.util.Date;

public class Holiday {
    private String cnpEmployee;
    private Date startDate;
    private Date endDate;

    public Holiday(String cnpEmployee,Date startDate, Date endDate)
    {
        this.cnpEmployee=cnpEmployee;
        this.startDate=startDate;
        this.endDate=endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCnpEmployee() {
        return cnpEmployee;
    }
}
