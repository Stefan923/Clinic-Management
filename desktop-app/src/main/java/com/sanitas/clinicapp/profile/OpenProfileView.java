package com.sanitas.clinicapp.profile;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class OpenProfileView  extends JPanel{

    private JTextField TxtCnp= new JTextField(15);
    private JTextField TxtFirst = new JTextField(15);
    private JTextField TxtLast = new JTextField(15);
    private JTextField TxtAddress = new JTextField(30);
    private JTextField TxtPhone = new JTextField(10);
    private JTextField TxtEmail = new JTextField(20);
    private JTextField TxtIban = new JTextField(25);
    private JTextField TxtContract =new JTextField(3);
    private JTextField TxtEmploymentDate =new JTextField(10);
    private JTextField TxtPosition = new JTextField(15);
    private JTextField TxtSalary = new JTextField(7);
    private JTextField TxtWorkedHrs = new JTextField(4);

    private OpenProfileModel model;

    OpenProfileView(OpenProfileModel model) {
        this.model = model;

        JPanel cnp = new JPanel(new FlowLayout());
        cnp.add(new JLabel("CNP: "));
        cnp.add(TxtCnp);

        JPanel lastname = new JPanel(new FlowLayout());
        lastname.add(new JLabel("Last Name: "));
        lastname.add(TxtLast);

        JPanel firstname = new JPanel(new FlowLayout());
        firstname.add(new JLabel("First Name: "));
        firstname.add(TxtFirst);

        JPanel address = new JPanel(new FlowLayout());
        address.add(new JLabel("Address: "));
        address.add(TxtAddress);

        JPanel phoneNum = new JPanel(new FlowLayout());
        phoneNum.add(new JLabel("Phone Number: "));
        phoneNum.add(TxtPhone);

        JPanel email = new JPanel(new FlowLayout());
        email.add(new JLabel("Email: "));
        email.add(TxtEmail);

        JPanel iban = new JPanel(new FlowLayout());
        iban.add(new JLabel("IBAN: "));
        iban.add(TxtIban);

        JPanel contract = new JPanel(new FlowLayout());
        contract.add(new JLabel("Contract Number: "));
        contract.add(TxtContract);

        JPanel employmentDate = new JPanel(new FlowLayout());
        employmentDate.add(new JLabel("Employment Date: "));
        employmentDate.add(TxtEmploymentDate);

        JPanel position = new JPanel(new FlowLayout());
        position.add(new JLabel("Position: "));
        position.add(TxtPosition);

        JPanel salary = new JPanel(new FlowLayout());
        salary.add(new JLabel("Salary: "));
        salary.add(TxtSalary);

        JPanel workedHrs = new JPanel(new FlowLayout());
        workedHrs.add(new JLabel("Last Name: "));
        workedHrs.add(TxtWorkedHrs);

        JPanel searchData = new JPanel(new BorderLayout());
        searchData.add(cnp, BorderLayout.NORTH);
        searchData.add(lastname, BorderLayout.CENTER);
        searchData.add(firstname, BorderLayout.SOUTH);

        searchData.setBorder(new EmptyBorder(0, 0, 20, 0));


    }


    public JTextField getTxtCnp() {
        return TxtCnp;
    }

    public JTextField getTxtFirst() {
        return TxtFirst;
    }

    public JTextField getTxtLast() {
        return TxtLast;
    }

    public JTextField getTxtAddress() {
        return TxtAddress;
    }

    public JTextField getTxtPhone() {
        return TxtPhone;
    }

    public JTextField getTxtEmail() {
        return TxtEmail;
    }

    public JTextField getTxtIban() {
        return TxtIban;
    }

    public JTextField getTxtContract() {
        return TxtContract;
    }

    public JTextField getTxtEmploymentDate() {
        return TxtEmploymentDate;
    }

    public JTextField getTxtPosition() {
        return TxtPosition;
    }

    public JTextField getTxtSalary() {
        return TxtSalary;
    }

    public JTextField getTxtWorkedHrs() {
        return TxtWorkedHrs;
    }
}
