package com.sanitas.clinicapp.struct;

public class MedicalService {

    private int id;
    private String name;

    private String cnpDoctor;
    private String lastname;
    private String firstname;

    private String speciality;
    private String accreditation;
    private String equipment;

    private int idSpeciality;
    private Integer idAccreditation;
    private Integer idEquipment;

    private double price;
    private int duration;

    public MedicalService(int id, String cnpDoctor, String name, double price, int duration, String lastname, String firstname, String speciality, String accreditation, String equipment) {
        this.id = id;
        this.cnpDoctor = cnpDoctor;
        this.name = name;
        this.lastname = lastname;
        this.firstname = firstname;
        this.speciality = speciality;
        this.accreditation = accreditation;
        this.equipment = equipment;
        this.price = price;
        this.duration = duration;
    }

    public MedicalService(String cnpDoctor, String name, int idSpeciality, Integer idAccreditation, Integer idEquipment, double price, int duration) {
        this.cnpDoctor = cnpDoctor;
        this.name = name;
        this.idSpeciality = idSpeciality;
        this.idAccreditation = idAccreditation;
        this.idEquipment = idEquipment;
        this.price = price;
        this.duration = duration;
    }

    public MedicalService(int id, String name, int duration, double price) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.price = price;
    }

    public String getFormattedDuration() {
        int minutes = duration;
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

    public void setId(int id) {
        this.id = id;
    }

    public String getCnpDoctor() {
        return cnpDoctor;
    }

    public String getName() {
        return name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getAccreditation() {
        return accreditation;
    }

    public String getEquipment() {
        return equipment;
    }

    public double getPrice() {
        return price;
    }

    public int getDuration() {
        return duration;
    }

    public int getIdSpeciality() {
        return idSpeciality;
    }

    public Integer getIdAccreditation() {
        return idAccreditation;
    }

    public Integer getIdEquipment() {
        return idEquipment;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }
}
