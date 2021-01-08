package com.sanitas.clinicapp.hr;

import java.sql.Time;

public class Schedule {
    private String cnpEmployee;
    private int idMedicalUnit;
    private String dayOfWeek;
    private Time startHour;
    private Time endHour;

    Schedule(String cnpEmployee,int idMedicalUnit, String dayOfWeek, Time startHour,Time endHour)
    {
        this.cnpEmployee=cnpEmployee;
        this.idMedicalUnit=idMedicalUnit;
        this.dayOfWeek=dayOfWeek;
        this.startHour=startHour;
        this.endHour=endHour;
    }


    public String getCnpEmployee() {
        return cnpEmployee;
    }

    public void setCnpEmployee(String cnpEmployee) {
        this.cnpEmployee = cnpEmployee;
    }

    public int getIdMedicalUnit() {
        return idMedicalUnit;
    }

    public void setIdMedicalUnit(int idMedicalUnit) {
        this.idMedicalUnit = idMedicalUnit;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Time getStartHour() {
        return startHour;
    }

    public void setStartHour(Time startHour) {
        this.startHour = startHour;
    }

    public Time getEndHour() {
        return endHour;
    }

    public void setEndHour(Time endHour) {
        this.endHour = endHour;
    }

}
