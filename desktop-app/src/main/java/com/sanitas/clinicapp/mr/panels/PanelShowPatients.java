package com.sanitas.clinicapp.mr.panels;

import com.sanitas.clinicapp.mr.MrModel;
import com.sanitas.clinicapp.mr.Patient;
import com.sanitas.clinicapp.ui.Colors;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class PanelShowPatients extends JPanel {

    private JTextField tfLastname = new JTextField(15);
    private JTextField tfFirstname = new JTextField(15);

    private JButton btnSearch = new StyledJButton("Cauta").getButton();
    private JButton btnModify = new StyledJButton("Editare").getButton();
    private JButton btnDelete = new StyledJButton("Sterge").getButton();

    private JTable patientTable;

    public PanelShowPatients(MrModel model) {
        super(new BorderLayout());

        btnSearch.setBackground(Colors.MAIN_COLOR.getColor());

        patientTable = initializeTable(model);

        JScrollPane jScrollPane = new JScrollPane(patientTable);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setPreferredSize(new Dimension(500, 200));
        JPanel tablePanel = new JPanel(new FlowLayout());
        tablePanel.add(jScrollPane);

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Nume:"));
        searchPanel.add(tfLastname);
        searchPanel.add(new JLabel("Prenume:"));
        searchPanel.add(tfFirstname);
        searchPanel.add(btnSearch);

        JPanel editPanel = new JPanel(new FlowLayout());
        editPanel.add(btnModify);
        editPanel.add(btnDelete);

        this.add(searchPanel, BorderLayout.NORTH);
        this.add(tablePanel, BorderLayout.CENTER);
        this.add(editPanel, BorderLayout.SOUTH);

        this.setVisible(true);
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

    public void addModifyButtonListener(ActionListener actionListener) {
        btnModify.addActionListener(actionListener);
    }

    public void addDeleteButtonListener(ActionListener actionListener) {
        btnDelete.addActionListener(actionListener);
    }

}
