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

public class PanelEmployeeSalary extends JPanel {
    private JComboBox<String> cbName = new JComboBox<>();
    private JTextField tfSalary = new JTextField(10);
    private JButton btnShowSalary = new StyledJButton("Afisare").getButton();

    private UtilDateModel utilDateModelMin = new UtilDateModel();
    private UtilDateModel utilDateModelMax = new UtilDateModel();

    private Map<String, String> employees;

    public PanelEmployeeSalary(){
        setLayout(new BorderLayout());

        tfSalary.setEditable(false);

        JPanel detailsPanel = new JPanel(new GridLayout(5, 1));
        detailsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel employeesName = new JPanel(new FlowLayout());
        employeesName.setBorder(new EmptyBorder(0, 0, 10, 0));
        employeesName.add(new JLabel("Nume Angajat:"));
        employeesName.add(cbName);

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

        detailsPanel.add(employeesName);
        detailsPanel.add(searchPanel);
        detailsPanel.add(employeeSalaryPanel);

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.add(btnShowSalary);
        btnPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        add(detailsPanel, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.SOUTH);
    }

    public JComboBox<String> getCbName() {
        return cbName;
    }

    public JTextField getTfSalary() {
        return tfSalary;
    }

    public void updateEmployeesName(Map<String, String> doctorsName) {
        employees = doctorsName;
        cbName.removeAllItems();
        doctorsName.values().forEach(name -> cbName.addItem(name));
    }

    public String getEmployeesCNP() {
        Optional<Map.Entry<String, String>> result = employees
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().equalsIgnoreCase((String) cbName.getSelectedItem()))
                .findFirst();
        System.out.println(result);
        return result.map(Map.Entry::getKey).orElse("1");
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
