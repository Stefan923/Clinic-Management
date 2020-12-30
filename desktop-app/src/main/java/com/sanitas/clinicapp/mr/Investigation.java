package com.sanitas.clinicapp.mr;

import java.util.Date;

public class Investigation {

    private final int idService;
    private String serviceName;
    private final String remarks;
    private final Date date;

    public Investigation(int idService, String serviceName, String remarks, Date date) {
        this(idService, remarks, date);

        this.serviceName = serviceName;
    }

    public Investigation(int idService, String remarks, Date date) {
        this.idService = idService;
        this.remarks = remarks;
        this.date = date;
    }

    public int getIdService() {
        return idService;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getRemarks() {
        return remarks;
    }

    public Date getDate() {
        return date;
    }

}
