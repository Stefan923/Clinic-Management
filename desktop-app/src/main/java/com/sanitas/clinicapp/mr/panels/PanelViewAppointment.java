package com.sanitas.clinicapp.mr.panels;

import com.sanitas.clinicapp.struct.Appointment;
import com.sanitas.clinicapp.struct.MedicalService;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class PanelViewAppointment extends JPanel {

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final JTextField tfPatient = new JTextField(15);
    private final JTextField tfDoctor = new JTextField(15);
    private final JTextField tfSpeciality = new JTextField(15);
    private final JTextField tfCabinet = new JTextField(15);
    private final JTextField tfDate = new JTextField(15);

    private final JButton btnCancel = new StyledJButton("Anuleaza").getButton();

    private final JTable servicesTable = new JTable();

    private final JPanel previousPanel;
    private final Appointment appointment;

    public PanelViewAppointment(Appointment appointment, JPanel previousPanel) {
        super(new BorderLayout());
        this.previousPanel = previousPanel;
        this.appointment = appointment;

        loadContent();
    }

    private void loadContent() {
        add(getDataPanel(), BorderLayout.NORTH);
        add(getButtonsPanel(), BorderLayout.SOUTH);
    }

    private JPanel getDataPanel() {
        JPanel patientPanel = new JPanel(new FlowLayout());
        patientPanel.add(new JLabel("Pacient:"));
        patientPanel.add(tfPatient);

        JPanel doctorPanel = new JPanel(new FlowLayout());
        doctorPanel.add(new JLabel("Doctor:"));
        doctorPanel.add(tfDoctor);

        JPanel specialityPanel = new JPanel(new FlowLayout());
        specialityPanel.add(new JLabel("Specialitate:"));
        specialityPanel.add(tfSpeciality);

        JPanel cabinetPanel = new JPanel(new FlowLayout());
        cabinetPanel.add(new JLabel("Cabinet:"));
        cabinetPanel.add(tfCabinet);

        JPanel firstLinePanel = new JPanel(new FlowLayout());
        firstLinePanel.add(patientPanel);
        firstLinePanel.add(doctorPanel);
        doctorPanel.setBorder(new EmptyBorder(0, 0, 0, 10));

        JPanel secondLinePanel = new JPanel(new FlowLayout());
        secondLinePanel.add(specialityPanel);
        secondLinePanel.add(cabinetPanel);
        secondLinePanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        JPanel thirdLinePanel = new JPanel(new FlowLayout());
        thirdLinePanel.add(new JLabel("Data:"));
        thirdLinePanel.add(tfDate);
        thirdLinePanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        dataPanel.add(firstLinePanel);
        dataPanel.add(secondLinePanel);
        dataPanel.add(thirdLinePanel);
        dataPanel.add(getTablePanel());
        dataPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        loadAppointmentData();

        return dataPanel;
    }

    private JPanel getTablePanel() {
        JScrollPane jScrollPane = new JScrollPane(servicesTable);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setPreferredSize(new Dimension(500, 90));

        JPanel tablePanel = new JPanel(new FlowLayout());
        tablePanel.add(jScrollPane);
        updateTable();

        return tablePanel;
    }

    private JPanel getButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(btnCancel);
        buttonsPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        return buttonsPanel;
    }

    private void loadAppointmentData() {
        tfDoctor.setText(appointment.getDoctorName());
        tfPatient.setText(appointment.getPatientName());
        tfSpeciality.setText(appointment.getSpecialityName());
        tfCabinet.setText(appointment.getCabinetName());
        tfDate.setText(DATE_FORMAT.format(appointment.getDate()));

        tfPatient.setEditable(false);
        tfDoctor.setEditable(false);
        tfSpeciality.setEditable(false);
        tfCabinet.setEditable(false);
        tfDate.setEditable(false);

        updateTable();
    }

    public void updateTable() {
        List<MedicalService> medicalServices = appointment.getServices();

        String[] columns = { "Service", "Duration", "Price" };

        Object[][] analysesData = new Object[medicalServices.size()][columns.length];
        for (int i = 0; i < medicalServices.size(); ++i) {
            MedicalService medicalService = medicalServices.get(i);

            analysesData[i][0] = medicalService.getName();
            analysesData[i][1] = medicalService.getFormattedDuration();
            analysesData[i][2] = medicalService.getPrice();
        }

        servicesTable.setModel(new DefaultTableModel(analysesData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        servicesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        servicesTable.setFillsViewportHeight(true);
    }

    public JPanel getPreviousPanel() {
        return previousPanel;
    }

    public void addCancelButtonListener(ActionListener actionListener) {
        btnCancel.addActionListener(actionListener);
    }

}
