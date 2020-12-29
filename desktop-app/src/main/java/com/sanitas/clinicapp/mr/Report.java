package com.sanitas.clinicapp.mr;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Report {

    private int id;

    private String patientLastName;
    private String patientFirstName;

    private String sealCode;
    private String doctorLastName;
    private String doctorFirstName;

    private String diagnostic;
    private String recommendation;

    private Date date;
    private Date lastEdit;

    public Report(int id, String patientLastName, String patientFirstName, String sealCode, String doctorLastName, String doctorFirstName, String diagnostic, String recommendation, Date date, Date lastEdit) {
        this.id = id;
        this.patientLastName = patientLastName;
        this.patientFirstName = patientFirstName;
        this.sealCode = sealCode;
        this.doctorLastName = doctorLastName;
        this.doctorFirstName = doctorFirstName;
        this.diagnostic = diagnostic;
        this.recommendation = recommendation;
        this.date = date;
        this.lastEdit = lastEdit;
    }

    public int getId() {
        return id;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public String getSealCode() {
        return sealCode;
    }

    public String getDoctorLastName() {
        return doctorLastName;
    }

    public String getDoctorFirstName() {
        return doctorFirstName;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public Date getDate() {
        return date;
    }

    public Date getLastEdit() {
        return lastEdit;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

}
