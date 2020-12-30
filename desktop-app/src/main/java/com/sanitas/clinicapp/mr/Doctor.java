package com.sanitas.clinicapp.mr;

public class Doctor {

    private String cnp;
    private String sealCode;

    private float commission;

    private String scientificTitle;
    private String didacticTitle;

    public Doctor(String cnp, String sealCode, float commission, String scientificTitle, String didacticTitle) {
        this.cnp = cnp;
        this.sealCode = sealCode;
        this.commission = commission;
        this.scientificTitle = scientificTitle;
        this.didacticTitle = didacticTitle;
    }

    public String getCnp() {
        return cnp;
    }

    public String getSealCode() {
        return sealCode;
    }

    public float getCommission() {
        return commission;
    }

    public String getScientificTitle() {
        return scientificTitle;
    }

    public String getDidacticTitle() {
        return didacticTitle;
    }

}
