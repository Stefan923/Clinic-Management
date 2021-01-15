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
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public class PanelProfitByDoctor extends JPanel {
    private JComboBox<String> cbName = new JComboBox<>();
    private JTextField tfProfit = new JTextField(10);
    private JButton btnShowProfit = new StyledJButton("Afisare").getButton();

    private UtilDateModel utilDateModelMin = new UtilDateModel();
    private UtilDateModel utilDateModelMax = new UtilDateModel();

    private Map<String, String> doctors;

    public PanelProfitByDoctor() {
        setLayout(new BorderLayout());

        tfProfit.setEditable(false);

        JPanel detailsPanel = new JPanel(new GridLayout(5, 1));
        detailsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel doctorsName = new JPanel(new FlowLayout());
        doctorsName.setBorder(new EmptyBorder(0, 0, 10, 0));
        doctorsName.add(new JLabel("Nume Medic:"));
        doctorsName.add(cbName);

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

        JPanel profitByDoctorPanel = new JPanel(new FlowLayout());
        profitByDoctorPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        profitByDoctorPanel.add(new JLabel("Profit per medic:"));
        profitByDoctorPanel.add(tfProfit);

        detailsPanel.add(doctorsName);
        detailsPanel.add(searchPanel);
        detailsPanel.add(profitByDoctorPanel);

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.add(btnShowProfit);
        btnPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        add(detailsPanel, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.SOUTH);
    }

    public JComboBox<String> getCbName() {
        return cbName;
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

    public void updateDoctorsNames(Map<String, String> doctorsName) {
        doctors = doctorsName;
        cbName.removeAllItems();
        doctorsName.values().forEach(name -> cbName.addItem(name));
    }

    public String getDoctorsCNP() {
        Optional<Map.Entry<String, String>> result = doctors
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().equalsIgnoreCase((String) cbName.getSelectedItem()))
                .findFirst();
        return result.map(Map.Entry::getKey).orElse("1");
    }

    public void addProfitButtonListener(ActionListener actionListener) {
        btnShowProfit.addActionListener(actionListener);
    }

}
