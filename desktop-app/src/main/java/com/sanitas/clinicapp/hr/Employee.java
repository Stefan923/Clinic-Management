package com.sanitas.clinicapp.hr;

public class Employee {
    private String firstname;
    private String lastname;
    private String position;
    private String cnp;

    Employee(String cnp, String lastname, String firstname, String position)
    {
        this.cnp=cnp;
        this.lastname=lastname;
        this.firstname=firstname;
        this.position=position;
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
