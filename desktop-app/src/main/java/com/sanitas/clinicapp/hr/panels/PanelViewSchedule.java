package com.sanitas.clinicapp.hr.panels;

import com.sanitas.clinicapp.hr.Employee;
import com.sanitas.clinicapp.hr.HrModel;
import com.sanitas.clinicapp.hr.Schedule;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PanelViewSchedule extends JPanel{

    private JTextField tfCnp = new JTextField(15);
    private JTextField tfMedicalUnit = new JTextField(15);
    private JTextField tfDay = new JTextField(15);
    private JTextField tfStart = new JTextField(15);
    private JTextField tfEnd = new JTextField(15);
    private JTable scheduleTable;

    private JButton delete=new StyledJButton("Sterge").getButton();
    private JButton update=new StyledJButton("Editare").getButton();
    private JButton insert=new StyledJButton("Inserare").getButton();
    private JButton back=new StyledJButton("Inapoi").getButton();

    private HrModel model;
    private List<Schedule> schedules;

    public PanelViewSchedule(HrModel model,List<Schedule> schedules) {
        setLayout(new BorderLayout());

        this.schedules=schedules;
        scheduleTable = initializeTable(model,schedules);

        JScrollPane jScrollPane = new JScrollPane(scheduleTable);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setPreferredSize(new Dimension(540, 240));
        JPanel tablePanel = new JPanel(new FlowLayout());
        tablePanel.add(jScrollPane);

        JPanel editPanel = new JPanel(new FlowLayout());
        editPanel.add(delete);
        editPanel.add(update);
        editPanel.add(insert);
        editPanel.add(back);

        add(tablePanel, BorderLayout.CENTER);
        add(editPanel, BorderLayout.SOUTH);

        setVisible(false);
    }

    private JTable initializeTable(HrModel model,List<Schedule> schedules) {
        String[] columns = { "Nr. Unitate medicala","Ziua Saptamanii","Ora incepere","Ora finalizare"};

        Object[][] scheduleData = new Object[schedules.size()][columns.length];
        for (int i = 0; i < schedules.size(); ++i) {
            Schedule schedule = schedules.get(i);

            scheduleData[i][0] = schedule.getIdMedicalUnit();
            scheduleData[i][1] = schedule.getDayOfWeek();
            scheduleData[i][2] = schedule.getStartHour();
            scheduleData[i][3] = schedule.getEndHour();
        }

        JTable table = new JTable();
        table.setModel(new DefaultTableModel(scheduleData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);

        return table;
    }

    public void updateTable(List<Schedule> schedules) {
        this.schedules=schedules;
        String[] columns = {"Nr. Unitate medicala","Ziua Saptamanii","Ora incepere","Ora finalizare" };

        Object[][] scheduleData = new Object[schedules.size()][columns.length];
        for (int i = 0; i < schedules.size(); ++i) {
            Schedule schedule = schedules.get(i);

            scheduleData[i][0] = schedule.getIdMedicalUnit();
            scheduleData[i][1] = schedule.getDayOfWeek();
            scheduleData[i][2] = schedule.getStartHour();
            scheduleData[i][3] = schedule.getEndHour();
        }

        scheduleTable.setModel(new DefaultTableModel(scheduleData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    public JTable getJTable() {
        return scheduleTable;
    }

    public void addInsertButtonListener(ActionListener actionListener) {
        insert.addActionListener(actionListener);
    }

    public void addUpdateButtonListener(ActionListener actionListener) {
        update.addActionListener(actionListener);
    }

    public void addDeleteButtonListener(ActionListener actionListener) {
        delete.addActionListener(actionListener);
    }

    public void addBackButtonListener(ActionListener actionListener) {
        back.addActionListener(actionListener);
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }
}
