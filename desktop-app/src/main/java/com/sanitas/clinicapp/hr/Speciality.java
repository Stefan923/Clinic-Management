package com.sanitas.clinicapp.hr;

import java.util.Map;

public class Speciality {

    private int id;
    private String speciality;
    private String rank;

    public Speciality(int id,String speciality,String rank)
    {
        this.id=id;
        this.speciality=speciality;
        this.rank=rank;
    }

    public int getId() {
        return id;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getRank() {
        return rank;
    }
}
