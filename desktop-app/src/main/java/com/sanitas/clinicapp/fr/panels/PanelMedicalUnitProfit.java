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

public class PanelMedicalUnitProfit extends JPanel {
    private JTextField tfIBAN = new JTextField(20);
    private JTextField tfProfit = new JTextField(10);
    private JComboBox<String> cbUnitsName = new JComboBox<>();
    private JButton btnShowProfit = new StyledJButton("Afisare profit").getButton();

    private Map<String, String> medicalUnits;

    private UtilDateModel utilDateModelMin = new UtilDateModel();
    private UtilDateModel utilDateModelMax = new UtilDateModel();

    public PanelMedicalUnitProfit(){
        setLayout(new BorderLayout());

        tfIBAN.setEditable(false);
        tfProfit.setEditable(false);

        JPanel detailsPanel = new JPanel(new GridLayout(7, 1));
        detailsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel medicalUnitNamePanel = new JPanel(new FlowLayout());
        medicalUnitNamePanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        medicalUnitNamePanel.add(new JLabel("Nume Unitate:"));
        medicalUnitNamePanel.add(cbUnitsName);

        JPanel medicalUnitIBANPanel = new JPanel(new FlowLayout());
        medicalUnitIBANPanel.setBorder(new EmptyBorder(0, 0, 5, 0));
        medicalUnitIBANPanel.add(new JLabel("Cont Unitate:"));
        medicalUnitIBANPanel.add(tfIBAN);

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

        JPanel medicalUnitProfitPanel = new JPanel(new FlowLayout());
        medicalUnitProfitPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        medicalUnitProfitPanel.add(new JLabel("Profit:"));
        medicalUnitProfitPanel.add(tfProfit);

        detailsPanel.add(medicalUnitNamePanel);
        detailsPanel.add(medicalUnitIBANPanel);
        detailsPanel.add(searchPanel);
        detailsPanel.add(medicalUnitProfitPanel);

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.add(btnShowProfit);
        btnPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        add(detailsPanel, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.SOUTH);
    }

    public JTextField getTfIBAN() {
        return tfIBAN;
    }

    public JTextField getTfProfit() {
        return tfProfit;
    }

    public JComboBox<String> getCbUnitsName() {
        return cbUnitsName;
    }

    public Map<String, String> getMedicalUnits() {
        return medicalUnits;
    }

    public UtilDateModel getUtilDateModelMin() {
        return utilDateModelMin;
    }

    public UtilDateModel getUtilDateModelMax() {
        return utilDateModelMax;
    }

    public void updateUnitName(Map<String, String> unitsName) {
        medicalUnits = unitsName;
        cbUnitsName.removeAllItems();
        unitsName.keySet().forEach(name -> cbUnitsName.addItem(name));
    }

    public void updateIBAN(String iban) {
        tfIBAN.setText(iban);
    }

    public String getIbanMedicalUnit() {
        Optional<Map.Entry<String, String>> result = medicalUnits
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase((String) cbUnitsName.getSelectedItem()))
                .findFirst();
        System.out.println(result);
        return result.map(Map.Entry::getValue).orElse("1");
    }

    public void addUnitsNameComboBoxListener(ActionListener actionListener) { cbUnitsName.addActionListener(actionListener);
    }

    public void addProfitButtonListener(ActionListener actionListener) {
        btnShowProfit.addActionListener(actionListener);
    }


}
