package com.sanitas.clinicapp.fr.panels;

import com.sanitas.clinicapp.ui.DateLabelFormatter;
import com.sanitas.clinicapp.ui.StyledJButton;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Properties;

public class PanelDoctorSalary extends JPanel {
    private JTextField tfCNP = new JTextField(20);
    private JTextField tfSalary = new JTextField(10);
    private JButton btnShowSalary = new StyledJButton("Afisare salariu").getButton();

    public PanelDoctorSalary(){
        setLayout(new BorderLayout());

        tfSalary.setEditable(false);

        JPanel detailsPanel = new JPanel(new GridLayout(5, 1));
        detailsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel doctorsCNP = new JPanel(new FlowLayout());
        doctorsCNP.setBorder(new EmptyBorder(0, 0, 10, 0));
        doctorsCNP.add(new JLabel("CNP Medic:"));
        doctorsCNP.add(tfCNP);

        Properties properties = new Properties();
        properties.put("text.today","Today");
        properties.put("text.month","Month");
        properties.put("text.year","Year");

        UtilDateModel utilDateModelMin = new UtilDateModel();
        JDatePanelImpl jDatePanelMin = new JDatePanelImpl(utilDateModelMin, properties);
        JDatePickerImpl jDatePickerMin = new JDatePickerImpl(jDatePanelMin, new DateLabelFormatter());

        UtilDateModel utilDateModelMax = new UtilDateModel();
        JDatePanelImpl jDatePanelMax = new JDatePanelImpl(utilDateModelMax, properties);
        JDatePickerImpl jDatePickerMax = new JDatePickerImpl(jDatePanelMax, new DateLabelFormatter());

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Inceput:"));
        searchPanel.add(jDatePickerMin);
        searchPanel.add(new JLabel("Sfarsit:"));
        searchPanel.add(jDatePickerMax);
        searchPanel.add(btnShowSalary);

        JPanel doctorSalaryPanel = new JPanel(new FlowLayout());
        doctorSalaryPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        doctorSalaryPanel.add(new JLabel("Salariu:"));
        doctorSalaryPanel.add(tfSalary);

        detailsPanel.add(doctorsCNP);
        detailsPanel.add(searchPanel);
        detailsPanel.add(doctorSalaryPanel);

        add(detailsPanel, BorderLayout.NORTH);
    }

    public JTextField getTfCNP() {
        return tfCNP;
    }

    public void updateCNP(String cnp) {
        tfCNP.setText(cnp);
    }
}
