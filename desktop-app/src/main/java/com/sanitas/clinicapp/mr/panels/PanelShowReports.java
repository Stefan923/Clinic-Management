package com.sanitas.clinicapp.mr.panels;

import com.sanitas.clinicapp.mr.Patient;
import com.sanitas.clinicapp.mr.Report;
import com.sanitas.clinicapp.ui.DateLabelFormatter;
import com.sanitas.clinicapp.ui.StyledJButton;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

public class PanelShowReports extends JPanel {

    private final JTable historyTable = new JTable();

    private JButton btnSearch = new StyledJButton("Cauta").getButton();
    private JButton btnViewReport = new StyledJButton("Afiseaza").getButton();
    private JButton btnAddReport = new StyledJButton("Raport nou").getButton();

    private final Patient patient;

    public PanelShowReports(Patient patient) {
        this.patient = patient;

        setLayout(new BorderLayout());

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
        searchPanel.add(btnSearch);

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(btnViewReport);
        buttonsPanel.add(btnAddReport);

        historyTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        historyTable.setFillsViewportHeight(true);
        JScrollPane jScrollPane = new JScrollPane(historyTable);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setPreferredSize(new Dimension(540, 240));

        JPanel tablePanel = new JPanel(new FlowLayout());
        tablePanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        tablePanel.add(jScrollPane);

        add(searchPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    public void updateTable(List<Report> reports) {
        String[] columns = { "Numar", "Data scrierii", "Ultima editare", "Parafa" };

        Object[][] historyData = new Object[reports.size()][columns.length];
        for (int i = 0; i < reports.size(); ++i) {
            Report report = reports.get(i);

            historyData[i][0] = i + 1;
            historyData[i][1] = report.getDate();
            historyData[i][2] = report.getLastEdit();
            historyData[i][3] = report.getSealCode();
        }

        historyTable.setModel(new DefaultTableModel(historyData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    public void addBtnSearchListener(ActionListener actionListener) {
        btnSearch.addActionListener(actionListener);
    }

    public void addBtnAddReportListener(ActionListener actionListener) {
        btnAddReport.addActionListener(actionListener);
    }

    public Patient getPatient() {
        return patient;
    }
}
