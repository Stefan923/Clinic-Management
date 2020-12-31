package com.sanitas.clinicapp.mr.panels;

import com.sanitas.clinicapp.mr.Analyse;
import com.sanitas.clinicapp.mr.Patient;
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

public class PanelShowAnalyses extends JPanel {

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final JButton btnSearch = new StyledJButton("Cauta").getButton();
    private final JButton btnAdd = new StyledJButton("Adauga o analiza").getButton();
    private final JButton btnCancel = new StyledJButton("Anuleaza").getButton();

    private final UtilDateModel utilDateModelMin = new UtilDateModel();
    private final UtilDateModel utilDateModelMax = new UtilDateModel();

    private final JTable analysesTable = new JTable();

    private final JPanel previousPanel;
    private final Patient patient;

    public PanelShowAnalyses(Patient patient, JPanel previousPanel) {
        super(new BorderLayout());
        this.previousPanel = previousPanel;
        this.patient = patient;

        loadContent();
    }

    private void loadContent() {
        add(getSearchPanel(), BorderLayout.NORTH);
        add(getDataPanel(), BorderLayout.CENTER);
        add(getButtonsPanel(), BorderLayout.SOUTH);
    }

    private JPanel getSearchPanel() {
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

        return searchPanel;
    }

    private JPanel getDataPanel() {
        JScrollPane jScrollPane = new JScrollPane(analysesTable);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setPreferredSize(new Dimension(540, 240));

        JPanel tablePanel = new JPanel(new FlowLayout());
        tablePanel.add(jScrollPane);

        JPanel dataPanel = new JPanel(new FlowLayout());
        dataPanel.add(tablePanel);
        dataPanel.setBorder(new EmptyBorder(15, 0, 0, 0));

        return dataPanel;
    }

    private JPanel getButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(btnAdd);
        buttonsPanel.add(btnCancel);
        buttonsPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        return buttonsPanel;
    }

    public void updateTable(List<Analyse> analyses) {

        String[] columns = { "Analiza", "Rezultat", "Valoare", "Minimum", "Maximum", "Data" };

        Object[][] analysesData = new Object[analyses.size()][columns.length];
        for (int i = 0; i < analyses.size(); ++i) {
            Analyse analyse = analyses.get(i);

            analysesData[i][0] = analyse.getName();
            analysesData[i][1] = analyse.isPositive() ? "Pozitiv" : "Negativ";
            analysesData[i][2] = analyse.getValue();
            analysesData[i][3] = analyse.getMinimum();
            analysesData[i][4] = analyse.getMaximum();
            analysesData[i][5] = DATE_FORMAT.format(analyse.getDate());
        }

        analysesTable.setModel(new DefaultTableModel(analysesData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        analysesTable.getColumn("Data").setPreferredWidth(120);
        analysesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        analysesTable.setFillsViewportHeight(true);
    }

    public Patient getPatient() {
        return patient;
    }

    public UtilDateModel getUtilDateModelMin() {
        return utilDateModelMin;
    }

    public UtilDateModel getUtilDateModelMax() {
        return utilDateModelMax;
    }

    public JPanel getPreviousPanel() {
        return previousPanel;
    }

    public void addSearchButtonListener(ActionListener actionListener) {
        btnSearch.addActionListener(actionListener);
    }

    public void addAddButtonListener(ActionListener actionListener) {
        btnAdd.addActionListener(actionListener);
    }

    public void addCancelButtonListener(ActionListener actionListener) {
        btnCancel.addActionListener(actionListener);
    }

}
