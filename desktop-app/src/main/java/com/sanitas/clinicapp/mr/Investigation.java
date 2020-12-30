package com.sanitas.clinicapp.mr;

import java.util.Date;

public class Investigation {

    private int id;

    private final int idService;
    private String serviceName;
    private String doctorFullName;
    private final String remarks;
    private Date date;

    public Investigation(int id, int idService, String serviceName, String doctorFullName, String remarks, Date date) {
        this(idService, remarks);

        this.id = id;
        this.serviceName = serviceName;
        this.doctorFullName = doctorFullName;
        this.date = date;
    }

    public Investigation(int idService, String remarks) {
        this.idService = idService;
        this.remarks = remarks;
    }

    public int getId() {
        return id;
    }

    public int getIdService() {
        return idService;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getDoctorFullName() {
        return doctorFullName;
    }

    public String getRemarks() {
        return remarks;
    }

    public Date getDate() {
        return date;
    }

}
