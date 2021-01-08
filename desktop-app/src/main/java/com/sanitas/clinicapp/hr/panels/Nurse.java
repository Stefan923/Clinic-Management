package com.sanitas.clinicapp.hr.panels;

import com.sanitas.clinicapp.hr.Employee;

public class Nurse extends Employee {
    private Employee employee;
    private String type;
    private String rank;

    public Nurse(Employee employee,String type,String rank){
        super(employee.getCnp(), employee.getLastname(), employee.getFirstname(), employee.getAdress(), employee.getPhone(), employee.getEmail(),
                employee.getIban(), employee.getContract(), employee.getDate(), employee.getPosition(), employee.getSalary(), employee.getHours());
        this.employee=employee;
        this.type=type;
        this.rank=rank;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
