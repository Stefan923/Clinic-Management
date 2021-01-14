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

public class PanelDoctorProfitTotal extends JPanel {
    private JTextField tfCNP = new JTextField(20);
    private JTextField tfProfit = new JTextField(10);
    private JButton btnShowProfit = new StyledJButton("Afisare").getButton();

    private UtilDateModel utilDateModelMin = new UtilDateModel();
    private UtilDateModel utilDateModelMax = new UtilDateModel();

    public PanelDoctorProfitTotal(){
        setLayout(new BorderLayout());

        tfProfit.setEditable(false);

        JPanel detailsPanel = new JPanel(new GridLayout(3, 1));
        detailsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel doctorsCNP = new JPanel(new FlowLayout());
        doctorsCNP.setBorder(new EmptyBorder(0, 0, 10, 0));
        doctorsCNP.add(new JLabel("CNP Medic:"));
        doctorsCNP.add(tfCNP);

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

        JPanel doctorProfitTotalPanel = new JPanel(new FlowLayout());
        doctorProfitTotalPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        doctorProfitTotalPanel.add(new JLabel("Profit:"));
        doctorProfitTotalPanel.add(tfProfit);

        detailsPanel.add(doctorsCNP);
        detailsPanel.add(searchPanel);
        detailsPanel.add(doctorProfitTotalPanel);

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.add(btnShowProfit);
        btnPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        add(detailsPanel, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.SOUTH);
    }

    public JTextField getTfCNP() {
        return tfCNP;
    }

    public UtilDateModel getUtilDateModelMin() {
        return utilDateModelMin;
    }

    public UtilDateModel getUtilDateModelMax() {
        return utilDateModelMax;
    }

    public JTextField getTfProfit() {
        return tfProfit;
    }

    public void addProfitButtonListener(ActionListener actionListener) {
        btnShowProfit.addActionListener(actionListener);
    }
}
