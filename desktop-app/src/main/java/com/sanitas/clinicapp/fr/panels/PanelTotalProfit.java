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

public class PanelTotalProfit extends JPanel {
    private JTextField tfProfit = new JTextField(10);
    private JButton btnShowProfit = new StyledJButton("Afisare").getButton();

    private UtilDateModel utilDateModelMin = new UtilDateModel();
    private UtilDateModel utilDateModelMax = new UtilDateModel();

    public PanelTotalProfit() {
        setLayout(new BorderLayout());

        tfProfit.setEditable(false);

        JPanel detailsPanel = new JPanel(new GridLayout(3, 1));
        detailsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        JDatePanelImpl jDatePanelMin = new JDatePanelImpl(utilDateModelMin, properties);
        JDatePickerImpl jDatePickerMin = new JDatePickerImpl(jDatePanelMin, new DateLabelFormatter());

        JDatePanelImpl jDatePanelMax = new JDatePanelImpl(utilDateModelMax, properties);
        JDatePickerImpl jDatePickerMax = new JDatePickerImpl(jDatePanelMax, new DateLabelFormatter());

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Inceput:"));
        searchPanel.add(jDatePickerMin);
        searchPanel.add(new JLabel("Sfarsit:"));
        searchPanel.add(jDatePickerMax);

        JPanel totalProfitPanel = new JPanel(new FlowLayout());
        totalProfitPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        totalProfitPanel.add(new JLabel("Profit Total:"));
        totalProfitPanel.add(tfProfit);

        detailsPanel.add(searchPanel);
        detailsPanel.add(totalProfitPanel);

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.add(btnShowProfit);
        btnPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        add(detailsPanel, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.SOUTH);
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
