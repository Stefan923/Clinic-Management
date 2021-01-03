package com.sanitas.clinicapp.fr.panels;

import com.sanitas.clinicapp.ui.DateLabelFormatter;
import com.sanitas.clinicapp.ui.StyledJButton;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Properties;

public class PanelEmployeeSalary extends JPanel {
    private JTextField tfCNP = new JTextField(20);
    private JTextField tfSalary = new JTextField(10);
    private JButton btnShowSalary = new StyledJButton("Afisare").getButton();

    private UtilDateModel utilDateModelMin = new UtilDateModel();
    private UtilDateModel utilDateModelMax = new UtilDateModel();

    public PanelEmployeeSalary(){
        setLayout(new BorderLayout());

        tfSalary.setEditable(false);

        JPanel detailsPanel = new JPanel(new GridLayout(5, 1));
        detailsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel employeesCNP = new JPanel(new FlowLayout());
        employeesCNP.setBorder(new EmptyBorder(0, 0, 10, 0));
        employeesCNP.add(new JLabel("CNP Angajat:"));
        employeesCNP.add(tfCNP);

        Properties properties = new Properties();
        properties.put("text.today","Today");
        properties.put("text.month","Month");
        properties.put("text.year","Year");

        JDatePanelImpl jDatePanelMin = new JDatePanelImpl(utilDateModelMin, properties);
        JDatePickerImpl jDatePickerMin = new JDatePickerImpl(jDatePanelMin, new DateLabelFormatter());

        JDatePanelImpl jDatePanelMax = new JDatePanelImpl(utilDateModelMax, properties);
        JDatePickerImpl jDatePickerMax = new JDatePickerImpl(jDatePanelMax, new DateLabelFormatter());

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Inceput:"));
        searchPanel.add(jDatePickerMin);
        searchPanel.add(new JLabel("Sfarsit:"));
        searchPanel.add(jDatePickerMax);

        JPanel employeeSalaryPanel = new JPanel(new FlowLayout());
        employeeSalaryPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        employeeSalaryPanel.add(new JLabel("Salariu:"));
        employeeSalaryPanel.add(tfSalary);

        detailsPanel.add(employeesCNP);
        detailsPanel.add(searchPanel);
        detailsPanel.add(employeeSalaryPanel);

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.add(btnShowSalary);
        btnPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        add(detailsPanel, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.SOUTH);
    }

    public JTextField getTfCNP() {
        return tfCNP;
    }

    public JTextField getTfSalary() {
        return tfSalary;
    }

    public UtilDateModel getUtilDateModelMin() {
        return utilDateModelMin;
    }

    public UtilDateModel getUtilDateModelMax() {
        return utilDateModelMax;
    }

    public void addShowSalaryButtonListener(ActionListener actionListener) {
        btnShowSalary.addActionListener(actionListener);
    }
}
