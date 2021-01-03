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

public class PanelProfitBySpeciality extends JPanel {
    private JTextField tfCNP = new JTextField(20);
    private JTextField tfSalary = new JTextField(10);
    private JButton btnShowProfit = new StyledJButton("Afisare profit per specialitate").getButton();

    public PanelProfitBySpeciality(){
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
        searchPanel.add(btnShowProfit);

        JPanel profitBySpecialityPanel = new JPanel(new FlowLayout());
        profitBySpecialityPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        profitBySpecialityPanel.add(new JLabel("Profit per specialitate:"));
        profitBySpecialityPanel.add(tfSalary);

        detailsPanel.add(doctorsCNP);
        detailsPanel.add(searchPanel);
        detailsPanel.add(profitBySpecialityPanel);

        add(detailsPanel, BorderLayout.NORTH);
    }

    public JTextField getTfCNP() {
        return tfCNP;
    }

    public void updateCNP(String cnp) {
        tfCNP.setText(cnp);
    }
}
