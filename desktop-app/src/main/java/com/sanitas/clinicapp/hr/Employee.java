package com.sanitas.clinicapp.hr;

import java.sql.Date;

public class Employee {
    private String cnp;
    private String firstname;
    private String lastname;
    private String address;
    private String phoneNum;
    private String email;
    private String iban;
    private int contractNum;
    private Date employmentDate;
    private String position;
    private double salary;
    private int workedHrs;

    public Employee(String cnp, String lastname, String firstname, String address, String phoneNum, String email, String iban, int contractNum, Date employmentDate, String position, double salary, int workedHrs)
    {
        this.cnp=cnp;
        this.lastname=lastname;
        this.firstname=firstname;
        this.address=address;
        this.phoneNum=phoneNum;
        this.email=email;
        this.iban=iban;
        this.contractNum=contractNum;
        this.employmentDate=employmentDate;
        this.position=position;
        this.salary=salary;
        this.workedHrs=workedHrs;
    }

    public Employee(String cnp, String lastname, String firstname,String position) {
        this.cnp=cnp;
        this.lastname=lastname;
        this.firstname=firstname;
        this.position=position;
    }

    public Employee() {

    }


    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", position='" + position + '\'' +
                ", cnp='" + cnp + '\'' +
                '}';
    }
}
