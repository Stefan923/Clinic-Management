package com.sanitas.clinicapp.mr.panels;

import com.sanitas.clinicapp.struct.Appointment;
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

public class PanelShowAppointments extends JPanel {

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final JButton btnSearch = new StyledJButton("Cauta").getButton();
    private final JButton btnAdd = new StyledJButton("Adauga o programare").getButton();
    private final JButton btnView = new StyledJButton("Afiseaza").getButton();
    private final JButton btnDelete = new StyledJButton("Sterge").getButton();

    private final UtilDateModel utilDateModelMin = new UtilDateModel();
    private final UtilDateModel utilDateModelMax = new UtilDateModel();

    private final JTable appointmentsTable = new JTable();

    private List<Appointment> appointments;

    public PanelShowAppointments() {
        super(new BorderLayout());

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
        searchPanel.setBorder(new EmptyBorder(20, 0, 15, 0));

        return searchPanel;
    }

    private JPanel getDataPanel() {
        JScrollPane jScrollPane = new JScrollPane(appointmentsTable);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setPreferredSize(new Dimension(580, 240));

        JPanel tablePanel = new JPanel(new FlowLayout());
        tablePanel.add(jScrollPane);

        JPanel dataPanel = new JPanel(new FlowLayout());
        dataPanel.add(tablePanel);

        return dataPanel;
    }

    public void updateTable(List<Appointment> appointments) {
        this.appointments = appointments;

        String[] columns = { "Pacient", "Doctor", "Cabinet", "Specialitate", "Durata", "Data" };

        Object[][] analysesData = new Object[appointments.size()][columns.length];
        for (int i = 0; i < appointments.size(); ++i) {
            Appointment appointment = appointments.get(i);

            analysesData[i][0] = appointment.getPatientName();
            analysesData[i][1] = appointment.getDoctorName();
            analysesData[i][2] = appointment.getCabinetName();
            analysesData[i][3] = appointment.getSpecialityName();
            analysesData[i][4] = appointment.getDuration();
            analysesData[i][5] = DATE_FORMAT.format(appointment.getDate());
        }

        appointmentsTable.setModel(new DefaultTableModel(analysesData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        appointmentsTable.getColumn("Pacient").setPreferredWidth(110);
        appointmentsTable.getColumn("Doctor").setPreferredWidth(110);
        appointmentsTable.getColumn("Data").setPreferredWidth(120);
        appointmentsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        appointmentsTable.setFillsViewportHeight(true);
    }

    private JPanel getButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(btnAdd);
        buttonsPanel.add(btnView);
        buttonsPanel.add(btnDelete);
        buttonsPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        return buttonsPanel;
    }

    public JTable getAppointmentsTable() {
        return appointmentsTable;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void addSearchButtonListener(ActionListener actionListener) {
        btnSearch.addActionListener(actionListener);
    }

    public void addAddButtonListener(ActionListener actionListener) {
        btnAdd.addActionListener(actionListener);
    }

    public void addViewButtonListener(ActionListener actionListener) {
        btnView.addActionListener(actionListener);
    }

    public void addDeleteButtonListener(ActionListener actionListener) {
        btnDelete.addActionListener(actionListener);
    }

}
