package com.sanitas.clinicapp.profile.panels;

import com.sanitas.clinicapp.hr.Employee;
import com.sanitas.clinicapp.hr.panels.Nurse;
import com.sanitas.clinicapp.profile.ProfileModel;
import com.sanitas.clinicapp.struct.Doctor;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class PanelEmployeeProfile extends JPanel{
    private JTextField tfCnp = new JTextField(15);
    private JTextField tfLastname = new JTextField(15);
    private JTextField tfFirstname = new JTextField(15);
    private JTextField tfPosition= new JTextField(15);
    private JTextField tfAdress= new JTextField(18);
    private JTextField tfEmail= new JTextField(18);
    private JTextField tfContract= new JTextField(15);
    private JTextField tfPhone= new JTextField(15);
    private JTextField tfIban= new JTextField(18);
    private JTextField tfDate= new JTextField(15);
    private JTextField tfSalary= new JTextField(15);
    private JTextField tfHours= new JTextField(15);
    private JTextField tftype= new JTextField(15);
    private JTextField tfrank= new JTextField(15);

    private JTextField tfsealCode=new JTextField(15);
    private JTextField tfcommission=new JTextField(15);
    private JTextField tfscientificTitle=new JTextField(15);
    private JTextField tfdidacticTitle=new JTextField(15);


    public PanelEmployeeProfile(Employee employee) {
        setLayout(new BorderLayout());

        tfCnp.setEditable(false);
        tfAdress.setEditable(false);
        tfContract.setEditable(false);
        tfDate.setEditable(false);
        tfEmail.setEditable(false);
        tfHours.setEditable(false);
        tfIban.setEditable(false);
        tfPhone.setEditable(false);
        tfSalary.setEditable(false);

        tfCnp.setText(employee.getCnp());
        tfLastname.setText(employee.getLastname());
        tfFirstname.setText(employee.getFirstname());
        tfPosition.setText(employee.getPosition());
        tfAdress.setText(employee.getAdress());
        tfContract.setText(String.valueOf(employee.getContract()));
        tfDate.setText(String.valueOf(employee.getDate()));
        tfEmail.setText(employee.getEmail());
        tfHours.setText(String.valueOf(employee.getHours()));
        tfIban.setText(employee.getIban());
        tfPhone.setText(employee.getPhone());
        tfSalary.setText(String.valueOf(employee.getSalary()));


        JPanel cnpPanel = new JPanel(new FlowLayout());
        cnpPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        cnpPanel.add(new JLabel("CNP:"));
        cnpPanel.add(tfCnp);

        JPanel lastnamePanel = new JPanel(new FlowLayout());
        lastnamePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        lastnamePanel.add(new JLabel("Nume:"));
        lastnamePanel.add(tfLastname);

        JPanel firstnamePanel = new JPanel(new FlowLayout());
        firstnamePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        firstnamePanel.add(new JLabel("Prenume:"));
        firstnamePanel.add(tfFirstname);

        JPanel positionPanel = new JPanel(new FlowLayout());
        positionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        positionPanel.add(new JLabel("Functie:"));
        positionPanel.add(tfPosition);

        JPanel adressPanel = new JPanel(new FlowLayout());
        adressPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        adressPanel.add(new JLabel("Adresa:"));
        adressPanel.add(tfAdress);

        JPanel salaryPanel = new JPanel(new FlowLayout());
        salaryPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        salaryPanel.add(new JLabel("Salar:"));
        salaryPanel.add(tfSalary);

        JPanel ibanPanel = new JPanel(new FlowLayout());
        ibanPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        ibanPanel.add(new JLabel("Iban:"));
        ibanPanel.add(tfIban);

        JPanel hoursPanel = new JPanel(new FlowLayout());
        hoursPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        hoursPanel.add(new JLabel("Ore negociate:"));
        hoursPanel.add(tfHours);

        JPanel emailPanel = new JPanel(new FlowLayout());
        emailPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        emailPanel.add(new JLabel("E-mail:"));
        emailPanel.add(tfEmail);

        JPanel contractPanel = new JPanel(new FlowLayout());
        contractPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        contractPanel.add(new JLabel("Nr. contract:"));
        contractPanel.add(tfContract);

        JPanel phonePanel = new JPanel(new FlowLayout());
        phonePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        phonePanel.add(new JLabel("Telefon:"));
        phonePanel.add(tfPhone);

        JPanel datePanel = new JPanel(new FlowLayout());
        datePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        datePanel.add(new JLabel("Data angajare:"));
        datePanel.add(tfDate);

        JPanel ntypePanel = new JPanel(new FlowLayout());
        ntypePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        ntypePanel.add(new JLabel("Tip asistenta:"));
        ntypePanel.add(tftype);

        JPanel nrankPanel = new JPanel(new FlowLayout());
        nrankPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        nrankPanel.add(new JLabel("Rank asistenta:"));
        nrankPanel.add(tfrank);

        JPanel nurse = new JPanel(new GridLayout(1,2));
        nurse.add(nrankPanel);
        nurse.add(ntypePanel);

        JPanel sealPanel = new JPanel(new FlowLayout());
        sealPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        sealPanel.add(new JLabel("Parafa:"));
        sealPanel.add(tfsealCode);

        JPanel commisionPanel = new JPanel(new FlowLayout());
        commisionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        commisionPanel.add(new JLabel("Comision:"));
        commisionPanel.add(tfcommission);

        JPanel scientificPanel = new JPanel(new FlowLayout());
        scientificPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        scientificPanel.add(new JLabel("Titlu stiintific:"));
        scientificPanel.add(tfscientificTitle);

        JPanel didacticPanel = new JPanel(new FlowLayout());
        didacticPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        didacticPanel.add(new JLabel("Titlu didactic:"));
        didacticPanel.add(tfdidacticTitle);

        JPanel doctor = new JPanel(new GridLayout(2,2));
        doctor.add(sealPanel);
        doctor.add(commisionPanel);
        doctor.add(scientificPanel);
        doctor.add(didacticPanel);


        JPanel reportDataPanel = new JPanel();
        reportDataPanel.setLayout(new BorderLayout());
        if(tfPosition.getText().equals("Asistent Medical") && employee instanceof Nurse){
            tftype.setEditable(false);
            tfrank.setEditable(false);
            tftype.setText(((Nurse)employee).getType());
            tfrank.setText(((Nurse)employee).getRank());
            reportDataPanel.add(nurse,BorderLayout.SOUTH);
        }

        if(tfPosition.getText().equals("Medic") && employee instanceof Doctor){
            tfsealCode.setText(((Doctor) employee).getSealCode());
            tfcommission.setText(String.valueOf(((Doctor) employee).getCommission()));
            tfscientificTitle.setText(((Doctor) employee).getScientificTitle());
            tfdidacticTitle.setText(((Doctor) employee).getDidacticTitle());
            reportDataPanel.add(doctor,BorderLayout.SOUTH);
        }


        setVisible(false);
    }

    public JTextField getTfCnp() {
        return tfCnp;
    }

    public JTextField getTfLastname() {
        return tfLastname;
    }

    public JTextField getTfFirstname() {
        return tfFirstname;
    }

    public JTextField getTfPosition() {
        return tfPosition;
    }

    public JTextField getTfsealCode() {
        return tfsealCode;
    }

    public JTextField getTfscientificTitle() {
        return tfscientificTitle;
    }

    public JTextField getTfdidacticTitle() {
        return tfdidacticTitle;
    }

    public JTextField getTfcommission() {
        return tfcommission;
    }

}



