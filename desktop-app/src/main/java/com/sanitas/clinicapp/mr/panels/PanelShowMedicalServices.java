package com.sanitas.clinicapp.mr.panels;

import com.sanitas.clinicapp.mr.MedicalService;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class PanelShowMedicalServices extends JPanel {

    private JButton btnAdd = new StyledJButton("Adauga un serviciu").getButton();
    private JButton btnView = new StyledJButton("Afisare").getButton();
    private JButton btnDelete = new StyledJButton("Sterge").getButton();

    private JTable medicalServicesTable = new JTable();

    private List<MedicalService> medicalServices;

    public PanelShowMedicalServices() {
        setLayout(new BorderLayout());

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(btnAdd);
        buttonsPanel.add(btnView);
        buttonsPanel.add(btnDelete);

        medicalServicesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        medicalServicesTable.setFillsViewportHeight(true);
        JScrollPane jScrollPane = new JScrollPane(medicalServicesTable);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setPreferredSize(new Dimension(540, 240));

        JPanel tablePanel = new JPanel(new FlowLayout());
        tablePanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        tablePanel.add(jScrollPane);

        add(tablePanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    public void updateTable(List<MedicalService> medicalServices) {
        this.medicalServices = medicalServices;
        String[] columns = { "Nume", "Specializare", "Durata", "Pret" };

        Object[][] servicesData = new Object[medicalServices.size()][columns.length];
        for (int i = 0; i < medicalServices.size(); ++i) {
            MedicalService medicalService = medicalServices.get(i);

            servicesData[i][0] = medicalService.getName();
            servicesData[i][1] = medicalService.getSpeciality();
            servicesData[i][2] = medicalService.getDuration();
            servicesData[i][3] = medicalService.getPrice();
        }

        medicalServicesTable.setModel(new DefaultTableModel(servicesData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    public JTable getServicesTable() {
        return medicalServicesTable;
    }

    public List<MedicalService> getMedicalServices() {
        return medicalServices;
    }

    public void addBtnAddListener(ActionListener actionListener) {
        btnAdd.addActionListener(actionListener);
    }

    public void addBtnViewListener(ActionListener actionListener) {
        btnView.addActionListener(actionListener);
    }

    public void addBtnDeleteListener(ActionListener actionListener) {
        btnDelete.addActionListener(actionListener);
    }

}
