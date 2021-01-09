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

public class PanelProfitBySpeciality extends JPanel {
    private JTextField tfProfit = new JTextField(10);
    private JButton btnShowProfit = new StyledJButton("Afisare").getButton();
    private JComboBox<String> cbSpeciality = new JComboBox<>();

    private UtilDateModel utilDateModelMin = new UtilDateModel();
    private UtilDateModel utilDateModelMax = new UtilDateModel();

    private Map<Integer, String> specialities;

    public PanelProfitBySpeciality(){
        setLayout(new BorderLayout());

        tfProfit.setEditable(false);

        JPanel detailsPanel = new JPanel(new GridLayout(5, 1));
        detailsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel doctorsCNP = new JPanel(new FlowLayout());
        doctorsCNP.setBorder(new EmptyBorder(0, 0, 10, 0));
        doctorsCNP.add(new JLabel("Specialitate:"));
        doctorsCNP.add(cbSpeciality);

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

        JPanel profitBySpecialityPanel = new JPanel(new FlowLayout());
        profitBySpecialityPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        profitBySpecialityPanel.add(new JLabel("Profit per specialitate:"));
        profitBySpecialityPanel.add(tfProfit);

        detailsPanel.add(doctorsCNP);
        detailsPanel.add(searchPanel);
        detailsPanel.add(profitBySpecialityPanel);

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

    public void updateCbSpeciality(Map<Integer, String> specialities) {
        this.specialities = specialities;

        cbSpeciality.removeAllItems();
        specialities.values().forEach(speciality -> cbSpeciality.addItem(speciality));
    }

    public int getIdSpeciality() {
        Optional<Map.Entry<Integer, String>> result = specialities
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().equalsIgnoreCase((String) cbSpeciality.getSelectedItem()))
                .findFirst();
        return result.map(Map.Entry::getKey).orElse(1);
    }

    public void addProfitButtonListener(ActionListener actionListener) {
        btnShowProfit.addActionListener(actionListener);
    }
}
