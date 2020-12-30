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

public class PanelTotalProfit extends JPanel {
    private JTextField tfProfit = new JTextField(10);
    private JButton btnShowProfit = new StyledJButton("Afisare profit total").getButton();

    public PanelTotalProfit(){
        setLayout(new BorderLayout());

        tfProfit.setEditable(false);

        JPanel detailsPanel = new JPanel(new GridLayout(7, 1));
        detailsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

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
        searchPanel.add(btnShowProfit);

        JPanel totalProfitPanel = new JPanel(new FlowLayout());
        totalProfitPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        totalProfitPanel.add(new JLabel("Profit Total:"));
        totalProfitPanel.add(tfProfit);

        detailsPanel.add(searchPanel);
        detailsPanel.add(totalProfitPanel);

        add(detailsPanel, BorderLayout.NORTH);
    }
}
