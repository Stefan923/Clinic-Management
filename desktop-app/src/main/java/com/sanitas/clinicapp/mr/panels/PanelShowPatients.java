package com.sanitas.clinicapp.mr.panels;

import com.sanitas.clinicapp.ClinicApplication;
import com.sanitas.clinicapp.mr.MrModel;
import com.sanitas.clinicapp.struct.Patient;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class PanelShowPatients extends JPanel {

    private JTextField tfLastname = new JTextField(15);
    private JTextField tfFirstname = new JTextField(15);

    private JButton btnSearch = new StyledJButton("Cauta").getButton();
    private JButton btnAddPatient = new StyledJButton("Adauga un pacient").getButton();
    private JButton btnViewPatient = new StyledJButton("Afiseaza").getButton();
    private JButton btnModify = new StyledJButton("Editeaza").getButton();
    private JButton btnDelete = new StyledJButton("Sterge").getButton();

    private JTable patientTable;

    public PanelShowPatients(MrModel model, ClinicApplication.Account account) {
        super(new BorderLayout());

        patientTable = initializeTable(model);

        JScrollPane jScrollPane = new JScrollPane(patientTable);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setPreferredSize(new Dimension(500, 240));
        JPanel tablePanel = new JPanel(new FlowLayout());
        tablePanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        tablePanel.add(jScrollPane);

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Nume:"));
        searchPanel.add(tfLastname);
        searchPanel.add(new JLabel("Prenume:"));
        searchPanel.add(tfFirstname);
        searchPanel.add(btnSearch);
        searchPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        if (account.hasPermission("mr.patients.write")) {
            buttonsPanel.add(btnAddPatient);
        }
        buttonsPanel.add(btnViewPatient);
        if (account.hasPermission("mr.patients.write")) {
            buttonsPanel.add(btnModify);
            buttonsPanel.add(btnDelete);
        }
        buttonsPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        add(searchPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        setVisible(false);
    }

    private JTable initializeTable(MrModel model) {
        String[] columns = { "Nume", "Prenume", "CNP" };
        List<Patient> patients = model.getPatients("", "");

        Object[][] patientsData = new Object[patients.size()][columns.length];
        for (int i = 0; i < patients.size(); ++i) {
            Patient patient = patients.get(i);

            patientsData[i][0] = patient.getLastname();
            patientsData[i][1] = patient.getFirstname();
            patientsData[i][2] = patient.getCnp();
        }

        JTable table = new JTable();
        table.setModel(new DefaultTableModel(patientsData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);

        return table;
    }

    public void updateTable(List<Patient> patients) {
        String[] columns = { "Nume", "Prenume", "CNP" };

        Object[][] patientsData = new Object[patients.size()][columns.length];
        for (int i = 0; i < patients.size(); ++i) {
            Patient patient = patients.get(i);

            patientsData[i][0] = patient.getLastname();
            patientsData[i][1] = patient.getFirstname();
            patientsData[i][2] = patient.getCnp();
        }

        patientTable.setModel(new DefaultTableModel(patientsData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    public JTextField getTfLastname() {
        return tfLastname;
    }

    public JTextField getTfFirstname() {
        return tfFirstname;
    }

    public JTable getJTable() {
        return patientTable;
    }

    public void addSearchButtonListener(ActionListener actionListener) {
        btnSearch.addActionListener(actionListener);
    }

    public void addAddPatientButtonListener(ActionListener actionListener) {
        btnAddPatient.addActionListener(actionListener);
    }

    public void addViewPatientButtonListener(ActionListener actionListener) {
        btnViewPatient.addActionListener(actionListener);
    }

    public void addModifyButtonListener(ActionListener actionListener) {
        btnModify.addActionListener(actionListener);
    }

    public void addDeleteButtonListener(ActionListener actionListener) {
        btnDelete.addActionListener(actionListener);
    }

}
