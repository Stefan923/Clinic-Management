package com.sanitas.clinicapp.mr.panels;

import com.sanitas.clinicapp.struct.Patient;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelEditPatient extends JPanel {

    private JTextField tfCnp = new JTextField(15);
    private JTextField tfLastname = new JTextField(15);
    private JTextField tfFirstname = new JTextField(15);
    private JTextField tfIban = new JTextField(15);

    private JButton btnSave = new StyledJButton("Salveaza").getButton();
    private JButton btnCancel = new StyledJButton("Anuleaza").getButton();

    public PanelEditPatient(Patient patient) {
        setLayout(new BorderLayout());

        tfCnp.setEditable(false);
        tfCnp.setText(patient.getCnp());
        tfLastname.setText(patient.getLastname());
        tfFirstname.setText(patient.getFirstname());
        tfIban.setText(patient.getIban());

        JPanel cnpPanel = new JPanel(new FlowLayout());
        cnpPanel.setBorder(new EmptyBorder(20, 10, 10, 10));
        cnpPanel.add(new JLabel("CNP:"));
        cnpPanel.add(tfCnp);

        JPanel lastnamePanel = new JPanel(new FlowLayout());
        lastnamePanel.setBorder(new EmptyBorder(20, 10, 10, 10));
        lastnamePanel.add(new JLabel("Nume:"));
        lastnamePanel.add(tfLastname);

        JPanel firstnamePanel = new JPanel(new FlowLayout());
        firstnamePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        firstnamePanel.add(new JLabel("Prenume:"));
        firstnamePanel.add(tfFirstname);

        JPanel ibanPanel = new JPanel(new FlowLayout());
        ibanPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        ibanPanel.add(new JLabel("IBAN:"));
        ibanPanel.add(tfIban);

        JPanel patientData = new JPanel(new GridLayout(2, 2));
        patientData.add(cnpPanel);
        patientData.add(lastnamePanel);
        patientData.add(firstnamePanel);
        patientData.add(ibanPanel);

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(btnSave);
        buttonsPanel.add(btnCancel);
        buttonsPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        add(patientData, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);

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

    public JTextField getTfIban() {
        return tfIban;
    }

    public void addSaveButtonListener(ActionListener actionListener) {
        btnSave.addActionListener(actionListener);
    }

    public void addCancelButtonListener(ActionListener actionListener) {
        btnCancel.addActionListener(actionListener);
    }

}
