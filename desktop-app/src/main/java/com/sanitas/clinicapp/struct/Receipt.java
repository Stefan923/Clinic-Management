package com.sanitas.clinicapp.struct;

import java.util.Date;

public class Receipt {
    private int id;
    private String medicalUnitName;
    private String patientName;
    private String adress;
    private Date date;
    private String services;
    private float price;

    public Receipt(int id, String medicalUnitName, String patientName, String adress, Date date, String services, float price) {
        this.id = id;
        this.medicalUnitName = medicalUnitName;
        this.patientName = patientName;
        this.adress = adress;
        this.date = date;
        this.services = services;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getMedicalUnitName() {
        return medicalUnitName;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getAdress() {
        return adress;
    }

    public Date getDate() {
        return date;
    }

    public String getServices() {
        return services;
    }

    public float getPrice() {
        return price;
    }
}
