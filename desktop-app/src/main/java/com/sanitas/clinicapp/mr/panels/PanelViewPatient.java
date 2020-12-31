package com.sanitas.clinicapp.mr.panels;

import com.sanitas.clinicapp.mr.Patient;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelViewPatient extends JPanel {

    private final Patient patient;

    private final JButton btnShowHistory = new StyledJButton("Afiseaza raport").getButton();
    private final JButton btnShowAnalyses = new StyledJButton("Afiseaza analize").getButton();
    private final JButton btnCancel = new StyledJButton("Anuleaza").getButton();

    private final JPanel previousPanel;

    public PanelViewPatient(JPanel previousPanel, Patient patient) {
        this.previousPanel = previousPanel;
        this.patient = patient;

        setLayout(new BorderLayout());

        JTextField tfCnp = new JTextField(15);
        tfCnp.setText(patient.getCnp());
        tfCnp.setEditable(false);

        JTextField tfLastname = new JTextField(15);
        tfLastname.setText(patient.getLastname());
        tfLastname.setEditable(false);

        JTextField tfFirstname = new JTextField(15);
        tfFirstname.setText(patient.getFirstname());
        tfFirstname.setEditable(false);

        JTextField tfIban = new JTextField(17);
        tfIban.setText(patient.getIban());
        tfIban.setEditable(false);

        JPanel cnpPanel = new JPanel(new FlowLayout());
        cnpPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        cnpPanel.add(new JLabel("CNP:"));
        cnpPanel.add(tfCnp);

        JPanel lastnamePanel = new JPanel(new FlowLayout());
        lastnamePanel.setBorder(new EmptyBorder(20, 10, 10, 10));
        lastnamePanel.add(new JLabel("Nume:"));
        lastnamePanel.add(tfLastname);

        JPanel firstnamePanel = new JPanel(new FlowLayout());
        firstnamePanel.setBorder(new EmptyBorder(20, 10, 10, 10));
        firstnamePanel.add(new JLabel("Prenume:"));
        firstnamePanel.add(tfFirstname);

        JPanel ibanPanel = new JPanel(new FlowLayout());
        ibanPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        ibanPanel.add(new JLabel("IBAN:"));
        ibanPanel.add(tfIban);

        JPanel patientData = new JPanel(new GridLayout(2, 2));
        patientData.add(lastnamePanel);
        patientData.add(firstnamePanel);
        patientData.add(cnpPanel);
        patientData.add(ibanPanel);

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(btnShowHistory);
        buttonsPanel.add(btnShowAnalyses);
        buttonsPanel.add(btnCancel);
        buttonsPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        add(patientData, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);

        setVisible(false);
    }

    public JPanel getPreviousPanel() {
        return previousPanel;
    }

    public void addShowHistoryButtonListener(ActionListener actionListener) {
        btnShowHistory.addActionListener(actionListener);
    }

    public void addShowAnalysesButtonListener(ActionListener actionListener) {
        btnShowAnalyses.addActionListener(actionListener);
    }

    public void addCancelButtonListener(ActionListener actionListener) {
        btnCancel.addActionListener(actionListener);
    }

    public Patient getPatient() {
        return patient;
    }
}
