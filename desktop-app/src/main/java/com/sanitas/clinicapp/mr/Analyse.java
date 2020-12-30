package com.sanitas.clinicapp.mr;

import java.util.Date;

public class Analyse {

    private int id;
    private String name;
    private float value;
    private boolean isPositive;

    private Date date;

    private int idAnalyse;
    private float minimum;
    private float maximum;

    public Analyse(int id, String name, float value, boolean isPositive, int idAnalyse, float minimum, float maximum, Date date) {
        this(idAnalyse, value, isPositive);
        this.id = id;
        this.name = name;
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public Analyse(int idAnalyse, float value, boolean isPositive) {
        this.idAnalyse = idAnalyse;
        this.value = value;
        this.isPositive = isPositive;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getValue() {
        return value;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public Date getDate() {
        return date;
    }

    public int getIdAnalyse() {
        return idAnalyse;
    }

    public float getMinimum() {
        return minimum;
    }

    public float getMaximum() {
        return maximum;
    }

}
