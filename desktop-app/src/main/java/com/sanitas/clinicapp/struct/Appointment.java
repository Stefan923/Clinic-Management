package com.sanitas.clinicapp.struct;

import java.util.Date;
import java.util.List;

public class Appointment implements Comparable<Appointment> {

    private int id;
    private String cnpPatient;
    private String cnpDoctor;
    private int idCabinet;
    private int idSpeciality;
    private Date date;

    private String patientName;
    private String doctorName;
    private String cabinetName;
    private String specialityName;

    private String duration;

    private List<MedicalService> services;

    public Appointment(int id, String patientName, String doctorName, String cabinetName, String specialityName, int duration, Date date) {
        this.id = id;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.cabinetName = cabinetName;
        this.specialityName = specialityName;
        this.duration = convertDuration(duration);
        this.date = date;
    }

    public Appointment(String cnpPatient, String cnpDoctor, int idCabinet, int idSpeciality, Date date) {
        this.cnpPatient = cnpPatient;
        this.cnpDoctor = cnpDoctor;
        this.idCabinet = idCabinet;
        this.idSpeciality = idSpeciality;
        this.date = date;
    }

    private String convertDuration(int minutes) {
        int hours = minutes / 60;
        minutes %= 60;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(hours > 0 ? (hours < 10 ? "0" + hours : String.valueOf(hours)) : "00")
                .append(":");
        stringBuilder
                .append(minutes > 0 ? (minutes < 10 ? "0" + minutes : String.valueOf(minutes)) : "00")
                .append(":00");

        return stringBuilder.toString();
    }

    public int getId() {
        return id;
    }

    public String getCnpPatient() {
        return cnpPatient;
    }

    public String getCnpDoctor() {
        return cnpDoctor;
    }

    public int getIdCabinet() {
        return idCabinet;
    }

    public int getIdSpeciality() {
        return idSpeciality;
    }

    public Date getDate() {
        return date;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getCabinetName() {
        return cabinetName;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public String getDuration() {
        return duration;
    }

    public List<MedicalService> getServices() {
        return services;
    }

    public void setServices(List<MedicalService> services) {
        this.services = services;
    }

    @Override
    public int compareTo(Appointment o) {
        return Integer.compare(0, date.compareTo(o.date));
    }

}
