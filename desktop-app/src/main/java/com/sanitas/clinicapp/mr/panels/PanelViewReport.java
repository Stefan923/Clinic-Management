package com.sanitas.clinicapp.mr.panels;

import com.sanitas.clinicapp.mr.Report;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class PanelViewReport extends JPanel {

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private PanelShowReports previousPanel;

    private JTextField tfPatientLastName = new JTextField(15);
    private JTextField tfPatientFirstName = new JTextField(15);
    private JTextField tfSealCode = new JTextField(5);
    private JTextField tfDoctorFullName = new JTextField(16);
    private JTextField tfDate = new JTextField(12);
    private JTextField tfLastEdit = new JTextField(12);
    private JTextArea tfDiagnostic = new JTextArea(2, 46);
    private JTextArea tfRecommendation = new JTextArea(2, 45);

    private JButton btnSave = new StyledJButton("Salveaza").getButton();
    private JButton btnConfirm = new StyledJButton("Parafeaza").getButton();
    private JButton btnCancel = new StyledJButton("Anuleaza").getButton();

    private Report report;

    public PanelViewReport(PanelShowReports previousPanel) {
        this.previousPanel = previousPanel;

        tfPatientLastName.setEditable(false);
        tfPatientFirstName.setEditable(false);
        tfSealCode.setEditable(false);
        tfDoctorFullName.setEditable(false);
        tfDate.setEditable(false);
        tfLastEdit.setEditable(false);

        setLayout(new BorderLayout());

        JPanel patientLastNamePanel = new JPanel(new FlowLayout());
        patientLastNamePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        patientLastNamePanel.add(new JLabel("Nume:"));
        patientLastNamePanel.add(tfPatientLastName);

        JPanel patientFirstNamePanel = new JPanel(new FlowLayout());
        patientFirstNamePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        patientFirstNamePanel.add(new JLabel("Prenume:"));
        patientFirstNamePanel.add(tfPatientFirstName);

        JPanel sealCodePanel = new JPanel(new FlowLayout());
        sealCodePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        sealCodePanel.add(new JLabel("Parafa:"));
        sealCodePanel.add(tfSealCode);

        JPanel doctorFullNamePanel = new JPanel(new FlowLayout());
        doctorFullNamePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        doctorFullNamePanel.add(new JLabel("Doctor:"));
        doctorFullNamePanel.add(tfDoctorFullName);

        JPanel datePanel = new JPanel(new FlowLayout());
        datePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        datePanel.add(new JLabel("Data crearii:"));
        datePanel.add(tfDate);

        JPanel lastEditPanel = new JPanel(new FlowLayout());
        lastEditPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        lastEditPanel.add(new JLabel("Ultima modificare:"));
        lastEditPanel.add(tfLastEdit);

        JPanel diagnosticPanel = new JPanel(new FlowLayout());
        diagnosticPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        diagnosticPanel.add(new JLabel("Diagnostic:"));
        diagnosticPanel.add(tfDiagnostic);

        JPanel recommendationPanel = new JPanel(new FlowLayout());
        recommendationPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        recommendationPanel.add(new JLabel("Recomandari:"));
        recommendationPanel.add(tfRecommendation);

        JPanel firstLinePanel = new JPanel(new GridLayout(1, 2));
        firstLinePanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        firstLinePanel.add(patientLastNamePanel);
        firstLinePanel.add(patientFirstNamePanel);

        JPanel secondLinePanel = new JPanel(new GridLayout(1, 3));
        secondLinePanel.add(sealCodePanel);
        secondLinePanel.add(doctorFullNamePanel);

        JPanel thirdLinePanel = new JPanel(new GridLayout(1, 2));
        thirdLinePanel.add(datePanel);
        thirdLinePanel.add(lastEditPanel);

        JPanel fourthLinePanel = new JPanel(new GridLayout(1, 1));
        fourthLinePanel.add(diagnosticPanel);

        JPanel fifthLinePanel = new JPanel(new GridLayout(1, 1));
        fifthLinePanel.add(recommendationPanel);

        JPanel reportData = new JPanel(new GridLayout(5, 1));
        reportData.add(firstLinePanel);
        reportData.add(secondLinePanel);
        reportData.add(thirdLinePanel);
        reportData.add(fourthLinePanel);
        reportData.add(fifthLinePanel);

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(btnSave);
        buttonsPanel.add(btnConfirm);
        buttonsPanel.add(btnCancel);

        add(reportData, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);

        setVisible(false);
    }

    public void loadReportData(Report report) {
        this.report = report;

        String sealCode = report.getSealCode();
        String doctorName = sealCode == null ? "" : (report.getDoctorLastName() + " " + report.getDoctorFirstName());

        tfPatientLastName.setText(report.getPatientLastName());
        tfPatientFirstName.setText(report.getPatientFirstName());
        tfSealCode.setText(sealCode == null ? "" : sealCode);
        tfDoctorFullName.setText(doctorName);
        tfDate.setText(DATE_FORMAT.format(report.getDate()));
        tfLastEdit.setText(DATE_FORMAT.format(report.getLastEdit()));
        tfDiagnostic.setText(report.getDiagnostic());
        tfRecommendation.setText(report.getRecommendation());

        if (report.getSealCode() != null) {
            tfDiagnostic.setEditable(false);
            tfRecommendation.setEditable(false);
        }
    }

    public PanelShowReports getPreviousPanel() {
        return previousPanel;
    }

    public JTextArea getTfDiagnostic() {
        return tfDiagnostic;
    }

    public JTextArea getTfRecommendation() {
        return tfRecommendation;
    }

    public Report getReport() {
        return report;
    }

    public void addSaveButtonListener(ActionListener actionListener) {
        btnSave.addActionListener(actionListener);
    }

    public void addConfirmButtonListener(ActionListener actionListener) {
        btnConfirm.addActionListener(actionListener);
    }

    public void addCancelButtonListener(ActionListener actionListener) {
        btnCancel.addActionListener(actionListener);
    }

}
