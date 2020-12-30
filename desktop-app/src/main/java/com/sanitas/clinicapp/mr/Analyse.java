package com.sanitas.clinicapp.mr;

import java.util.Date;

public class Analyse implements Comparable<Analyse> {

    private String name;
    private float value;
    private boolean isPositive;

    private Date date;

    private int idAnalyse;
    private float minimum;
    private float maximum;

    public Analyse(String name, float value, boolean isPositive, int idAnalyse, float minimum, float maximum, Date date) {
        this(idAnalyse, value, isPositive);
        this.name = name;
        this.minimum = minimum;
        this.maximum = maximum;
        this.date = date;
    }

    public Analyse(int idAnalyse, float value, boolean isPositive) {
        this.idAnalyse = idAnalyse;
        this.value = value;
        this.isPositive = isPositive;
    }

    public Analyse(int idAnalyse, String name, float minimum, float maximum) {
        this.idAnalyse = idAnalyse;
        this.name = name;
        this.minimum = minimum;
        this.maximum = maximum;
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

    @Override
    public int compareTo(Analyse o) {
        return Integer.compare(0, date.compareTo(o.date));
    }

}
