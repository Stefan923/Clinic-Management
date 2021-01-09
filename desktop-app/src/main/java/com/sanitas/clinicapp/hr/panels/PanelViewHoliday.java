package com.sanitas.clinicapp.hr.panels;

import com.sanitas.clinicapp.ClinicApplication;
import com.sanitas.clinicapp.hr.HrModel;
import com.sanitas.clinicapp.hr.Schedule;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class PanelViewHoliday extends JPanel{
    private JTextField tfCnp = new JTextField(15);
    private JTextField tfMedicalUnit = new JTextField(15);
    private JTextField tfDay = new JTextField(15);
    private JTextField tfStart = new JTextField(15);
    private JTextField tfEnd = new JTextField(15);
    private JTable holidaysTable;

    private JButton insert=new StyledJButton("Inserare").getButton();
    private JButton back=new StyledJButton("Inapoi").getButton();

    private HrModel model;
    private List<Holiday> holidays;
    private ClinicApplication.Account account;

    public PanelViewHoliday(HrModel model, List<Holiday> holidays, ClinicApplication.Account account) {
        setLayout(new BorderLayout());

        this.holidays=holidays;
        holidaysTable = initializeTable(model,holidays);

        JScrollPane jScrollPane = new JScrollPane(holidaysTable);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setPreferredSize(new Dimension(540, 240));
        JPanel tablePanel = new JPanel(new FlowLayout());
        tablePanel.add(jScrollPane);

        JPanel editPanel = new JPanel(new FlowLayout());
        if(account.hasPermission("hr.read.all"))
            editPanel.add(insert);
        editPanel.add(back);

        add(tablePanel, BorderLayout.CENTER);
        add(editPanel, BorderLayout.SOUTH);

        setVisible(false);
    }

    private JTable initializeTable(HrModel model,List<Holiday> holidays) {
        String[] columns = { "Data inceput","Data sfarsit"};

        Object[][] holidayData = new Object[holidays.size()][columns.length];
        for (int i = 0; i < holidays.size(); ++i) {
            Holiday holiday = holidays.get(i);

            holidayData[i][0] = holiday.getStartDate();
            holidayData[i][1] = holiday.getEndDate();

        }

        JTable table = new JTable();
        table.setModel(new DefaultTableModel(holidayData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);

        return table;
    }

    public void updateTable(List<Holiday> holidays) {
        this.holidays=holidays;
        String[] columns = {"Data inceput","Data sfarsit" };

        Object[][] holidayData = new Object[holidays.size()][columns.length];
        for (int i = 0; i < holidays.size(); ++i) {
            Holiday holiday = holidays.get(i);

            holidayData[i][0] = holiday.getStartDate();
            holidayData[i][1] = holiday.getEndDate();
        }

        holidaysTable.setModel(new DefaultTableModel(holidayData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    public JTable getJTable() {
        return holidaysTable;
    }

    public void addInsertButtonListener(ActionListener actionListener) {
        insert.addActionListener(actionListener);
    }

    public void addBackButtonListener(ActionListener actionListener) {
        back.addActionListener(actionListener);
    }


    public List<Holiday> getHolidays() {
        return holidays;
    }
}
