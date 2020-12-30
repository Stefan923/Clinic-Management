package com.sanitas.clinicapp.mr.panels;

import com.sanitas.clinicapp.mr.Investigation;
import com.sanitas.clinicapp.mr.MedicalService;
import com.sanitas.clinicapp.mr.Report;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

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

    private JButton btnAddInvestigation = new StyledJButton("Adauga o investigatie").getButton();
    private JButton btnViewInvestigation = new StyledJButton("Afiseaza investigatia").getButton();
    private JButton btnSave = new StyledJButton("Salveaza").getButton();
    private JButton btnConfirm = new StyledJButton("Parafeaza").getButton();
    private JButton btnCancel = new StyledJButton("Anuleaza").getButton();

    private Report report;

    JTable investigationsTable = new JTable();

    public PanelViewReport(PanelShowReports previousPanel) {
        super(new BorderLayout());

        this.previousPanel = previousPanel;

        tfPatientLastName.setEditable(false);
        tfPatientFirstName.setEditable(false);
        tfSealCode.setEditable(false);
        tfDoctorFullName.setEditable(false);
        tfDate.setEditable(false);
        tfLastEdit.setEditable(false);

        JPanel patientLastNamePanel = new JPanel(new FlowLayout());
        patientLastNamePanel.setBorder(new EmptyBorder(0, 10, 10, 10));
        patientLastNamePanel.add(new JLabel("Nume:"));
        patientLastNamePanel.add(tfPatientLastName);

        JPanel patientFirstNamePanel = new JPanel(new FlowLayout());
        patientFirstNamePanel.setBorder(new EmptyBorder(0, 10, 10, 10));
        patientFirstNamePanel.add(new JLabel("Prenume:"));
        patientFirstNamePanel.add(tfPatientFirstName);

        JPanel sealCodePanel = new JPanel(new FlowLayout());
        sealCodePanel.setBorder(new EmptyBorder(0, 10, 10, 10));
        sealCodePanel.add(new JLabel("Parafa:"));
        sealCodePanel.add(tfSealCode);

        JPanel doctorFullNamePanel = new JPanel(new FlowLayout());
        doctorFullNamePanel.setBorder(new EmptyBorder(0, 10, 10, 10));
        doctorFullNamePanel.add(new JLabel("Doctor:"));
        doctorFullNamePanel.add(tfDoctorFullName);

        JPanel datePanel = new JPanel(new FlowLayout());
        datePanel.setBorder(new EmptyBorder(0, 10, 10, 10));
        datePanel.add(new JLabel("Data crearii:"));
        datePanel.add(tfDate);

        JPanel lastEditPanel = new JPanel(new FlowLayout());
        lastEditPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
        lastEditPanel.add(new JLabel("Ultima modificare:"));
        lastEditPanel.add(tfLastEdit);

        JPanel diagnosticPanel = new JPanel(new FlowLayout());
        diagnosticPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
        diagnosticPanel.add(new JLabel("Diagnostic:"));
        diagnosticPanel.add(tfDiagnostic);

        JPanel recommendationPanel = new JPanel(new FlowLayout());
        recommendationPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
        recommendationPanel.add(new JLabel("Recomandari:"));
        recommendationPanel.add(tfRecommendation);

        JPanel firstLinePanel = new JPanel(new GridLayout(1, 2));
        firstLinePanel.setBorder(new EmptyBorder(20, 0, 0, 0));
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

        JPanel sixthLinePanel = new JPanel(new FlowLayout());
        JScrollPane tableScrollPane = new JScrollPane(investigationsTable);
        tableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tableScrollPane.setPreferredSize(new Dimension(500, 60));
        sixthLinePanel.add(tableScrollPane);
        sixthLinePanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        JPanel seventhLinePanel = new JPanel(new FlowLayout());
        seventhLinePanel.add(btnAddInvestigation);
        seventhLinePanel.add(btnViewInvestigation);

        JPanel reportDataPanel = new JPanel();
        reportDataPanel.setLayout(new BoxLayout(reportDataPanel, BoxLayout.Y_AXIS));
        reportDataPanel.add(firstLinePanel);
        reportDataPanel.add(secondLinePanel);
        reportDataPanel.add(thirdLinePanel);
        reportDataPanel.add(fourthLinePanel);
        reportDataPanel.add(fifthLinePanel);
        reportDataPanel.add(sixthLinePanel);
        reportDataPanel.add(seventhLinePanel);

        JScrollPane dataScrollPane = new JScrollPane(reportDataPanel);
        dataScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        dataScrollPane.setPreferredSize(new Dimension(600, 325));
        dataScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(btnSave);
        buttonsPanel.add(btnConfirm);
        buttonsPanel.add(btnCancel);
        buttonsPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        add(dataScrollPane, BorderLayout.NORTH);
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

    public void updateTable() {
        List<Investigation> investigations = report.getInvestigations();
        String[] columns = { "Serviciu medical", "Doctor", "Data" };

        Object[][] servicesData = new Object[investigations.size()][columns.length];
        for (int i = 0; i < investigations.size(); ++i) {
            Investigation investigation = investigations.get(i);

            servicesData[i][0] = investigation.getServiceName();
            servicesData[i][1] = investigation.getDoctorFullName();
            servicesData[i][2] = investigation.getDate();
        }

        investigationsTable.setModel(new DefaultTableModel(servicesData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
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

    public void addAddInvestigationButton(ActionListener actionListener) {
        btnAddInvestigation.addActionListener(actionListener);
    }

    public void addViewInvestigationButton(ActionListener actionListener) {
        btnViewInvestigation.addActionListener(actionListener);
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
