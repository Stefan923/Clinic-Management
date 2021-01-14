package com.sanitas.clinicapp.mr.panels;

import com.sanitas.clinicapp.struct.Patient;
import com.sanitas.clinicapp.struct.Report;
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
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

public class PanelShowReports extends JPanel {

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final JTable reportsTable = new JTable();

    private List<Report> reports;

    private final JButton btnSearch = new StyledJButton("Cauta").getButton();
    private final JButton btnViewReport = new StyledJButton("Afiseaza").getButton();
    private final JButton btnAddReport = new StyledJButton("Raport nou").getButton();
    private final JButton btnCancel = new StyledJButton("Anuleaza").getButton();

    private final UtilDateModel utilDateModelMin = new UtilDateModel();
    private final UtilDateModel utilDateModelMax = new UtilDateModel();

    private final JPanel previousPanel;
    private final Patient patient;

    public PanelShowReports(Patient patient, JPanel previousPanel) {
        this.previousPanel = previousPanel;
        this.patient = patient;

        setLayout(new BorderLayout());

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
        searchPanel.add(btnSearch);
        searchPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(btnViewReport);
        buttonsPanel.add(btnAddReport);
        buttonsPanel.add(btnCancel);
        buttonsPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        reportsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        reportsTable.setFillsViewportHeight(true);
        JScrollPane jScrollPane = new JScrollPane(reportsTable);
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
        this.reports = reports;

        String[] columns = { "Numar", "Data scrierii", "Ultima editare", "Parafa" };

        Object[][] reportsData = new Object[reports.size()][columns.length];
        for (int i = 0; i < reports.size(); ++i) {
            Report report = reports.get(i);

            reportsData[i][0] = i + 1;
            reportsData[i][1] = DATE_FORMAT.format(report.getDate());
            reportsData[i][2] = DATE_FORMAT.format(report.getLastEdit());
            reportsData[i][3] = report.getSealCode() == null ? "" : report.getSealCode();
        }

        reportsTable.setModel(new DefaultTableModel(reportsData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    public UtilDateModel getUtilDateModelMin() {
        return utilDateModelMin;
    }

    public UtilDateModel getUtilDateModelMax() {
        return utilDateModelMax;
    }

    public JTable getReportsTable() {
        return reportsTable;
    }

    public List<Report> getReports() {
        return reports;
    }

    public Patient getPatient() {
        return patient;
    }

    public JPanel getPreviousPanel() {
        return previousPanel;
    }

    public void addSearchButtonListener(ActionListener actionListener) {
        btnSearch.addActionListener(actionListener);
    }

    public void addViewButtonListener(ActionListener actionListener) {
        btnViewReport.addActionListener(actionListener);
    }

    public void addAddButtonListener(ActionListener actionListener) {
        btnAddReport.addActionListener(actionListener);
    }

    public void addCancelButtonListener(ActionListener actionListener) {
        btnCancel.addActionListener(actionListener);
    }

}
