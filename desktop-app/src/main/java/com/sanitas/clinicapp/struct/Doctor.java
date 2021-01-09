package com.sanitas.clinicapp.struct;

import com.sanitas.clinicapp.hr.Employee;

public class Doctor extends Employee {

    private String cnp;
    private String sealCode;

    private String name;

    private float commission;

    private String scientificTitle;
    private String didacticTitle;

    public Doctor(String cnp, String sealCode, String name, float commission, String scientificTitle, String didacticTitle) {
        super(null,null,null,null,null,null,null,0,null,null,0,0);
        this.cnp = cnp;
        this.sealCode = sealCode;
        this.name = name;
        this.commission = commission;
        this.scientificTitle = scientificTitle;
        this.didacticTitle = didacticTitle;
    }

    public Doctor(String cnp, String sealCode, float commission, String scientificTitle, String didacticTitle) {
        super(null,null,null,null,null,null,null,0,null,null,0,0);
        this.cnp = cnp;
        this.sealCode = sealCode;
        this.commission = commission;
        this.scientificTitle = scientificTitle;
        this.didacticTitle = didacticTitle;
    }

    public Doctor(Employee employee, String sealCode, float commission, String scientificTitle, String didacticTitle) {
        super(employee.getCnp(), employee.getLastname(), employee.getFirstname(), employee.getAdress(), employee.getPhone(), employee.getEmail(),
                employee.getIban(), employee.getContract(), employee.getDate(), employee.getPosition(), employee.getSalary(), employee.getHours());
        this.cnp=employee.getCnp();
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

    public String getName() {
        return name;
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
