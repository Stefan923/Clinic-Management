package com.sanitas.clinicapp.hr;

import java.util.Date;

public class Employee {
    private String firstname;
    private String lastname;
    private String position;
    private String cnp;
    private String adress;
    private String phone;
    private String email;
    private String iban;
    private int contract;
    private Date date;
    private double salary;
    private int hours;

    public Employee(String cnp, String lastname, String firstname, String adress, String phone, String email,
             String iban, int contract, Date date, String position, double salary, int hours)
    {
        this.cnp=cnp;
        this.lastname=lastname;
        this.firstname=firstname;
        this.position=position;
        this.adress=adress;
        this.phone=phone;
        this.email=email;
        this.iban=iban;
        this.contract=contract;
        this.date=date;
        this.salary=salary;
        this.hours=hours;
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

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPosition() {
        return position;
    }

    public String getCnp() {
        return cnp;
    }

    public double getSalary() {
        return salary;
    }

    public int getContract() {
        return contract;
    }

    public int getHours() {
        return hours;
    }

    public String getAdress() {
        return adress;
    }

    public Date getDate() {
        return date;
    }

    public String getEmail() {
        return email;
    }

    public String getIban() {
        return iban;
    }

    public String getPhone() {
        return phone;
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
