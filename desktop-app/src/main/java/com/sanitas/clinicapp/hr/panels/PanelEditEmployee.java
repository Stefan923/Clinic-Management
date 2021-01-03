package com.sanitas.clinicapp.hr.panels;

import com.sanitas.clinicapp.hr.Employee;
import com.sanitas.clinicapp.mr.Patient;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelEditEmployee extends JPanel {
    private JTextField tfCnp = new JTextField(15);
    private JTextField tfLastname = new JTextField(15);
    private JTextField tfFirstname = new JTextField(15);
    private JTextField tfPosition= new JTextField(15);

    private JButton btnSave = new StyledJButton("Salveaza").getButton();
    private JButton btnCancel = new StyledJButton("Anuleaza").getButton();

    public PanelEditEmployee(Employee employee) {
        setLayout(new BorderLayout());

        tfCnp.setEditable(false);
        tfCnp.setText(employee.getCnp());
        tfLastname.setText(employee.getLastname());
        tfFirstname.setText(employee.getFirstname());
        tfPosition.setText(employee.getPosition());

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

        JPanel ibanPanel = new JPanel(new FlowLayout());
        ibanPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        ibanPanel.add(new JLabel("Functie:"));
        ibanPanel.add(tfPosition);

        JPanel patientData = new JPanel(new GridLayout(2, 2));
        patientData.add(cnpPanel);
        patientData.add(lastnamePanel);
        patientData.add(firstnamePanel);
        patientData.add(ibanPanel);

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(btnSave);
        buttonsPanel.add(btnCancel);

        add(patientData, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.CENTER);

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

    public void addSaveButtonListener(ActionListener actionListener) {
        btnSave.addActionListener(actionListener);
    }

    public void addCancelButtonListener(ActionListener actionListener) {
        btnCancel.addActionListener(actionListener);
    }

    public void reset() {
        tfCnp.setText("");
        tfLastname.setText("");
        tfFirstname.setText("");
        tfPosition.setText("");
    }

}
