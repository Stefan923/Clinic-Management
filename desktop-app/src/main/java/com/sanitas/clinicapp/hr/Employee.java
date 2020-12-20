package com.sanitas.clinicapp.hr;

public class Employee {
    private String firstname;
    private String lastname;
    private String position;
    private String cnp;

    Employee(String lastname, String firstname, String position, String cnp)
    {
        this.lastname=lastname;
        this.firstname=firstname;
        this.position=position;
        this.cnp=cnp;
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
