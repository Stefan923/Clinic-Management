package com.sanitas.clinicapp.mr.panels;

import com.sanitas.clinicapp.struct.MedicalService;
import com.sanitas.clinicapp.ui.DateLabelFormatter;
import com.sanitas.clinicapp.ui.JTimeSelector;
import com.sanitas.clinicapp.ui.StyledJButton;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class PanelAddAppointment extends JPanel {

    private final JComboBox<String> cbPatient = new JComboBox<>();
    private final JComboBox<String> cbDoctor = new JComboBox<>();
    private final JComboBox<String> cbSpeciality = new JComboBox<>();
    private final JComboBox<String> cbCabinet = new JComboBox<>();
    private final JComboBox<String> cbService = new JComboBox<>();
    private final UtilDateModel utilDateModel = new UtilDateModel();
    private final JTimeSelector timeSelector = new JTimeSelector();

    private final JButton btnAddService = new StyledJButton("Adauga serviciul").getButton();
    private final JButton btnDeleteService = new StyledJButton("Sterge serviciul").getButton();
    private final JButton btnSave = new StyledJButton("Salveaza").getButton();
    private final JButton btnCancel = new StyledJButton("Anuleaza").getButton();

    private final JTable servicesTable = new JTable();

    private Map<String, String> patients;
    private Map<String, String> doctors;
    private Map<Integer, String> specialities;
    private Map<Integer, String> cabinets;
    private List<MedicalService> doctorServices;

    private final JPanel previousPanel;
    private final List<MedicalService> medicalServices = new ArrayList<>();

    public PanelAddAppointment(JPanel previousPanel) {
        super(new BorderLayout());
        this.previousPanel = previousPanel;

        loadContent();
    }

    private void loadContent() {
        add(getDataPanel(), BorderLayout.NORTH);
        add(getButtonsPanel(), BorderLayout.SOUTH);
    }

    private JPanel getDataPanel() {
        JPanel patientPanel = new JPanel(new FlowLayout());
        patientPanel.add(new JLabel("Pacient:"));
        patientPanel.add(cbPatient);

        JPanel specialityPanel = new JPanel(new FlowLayout());
        specialityPanel.add(new JLabel("Specialitate:"));
        specialityPanel.add(cbSpeciality);

        JPanel doctorPanel = new JPanel(new FlowLayout());
        doctorPanel.add(new JLabel("Doctor:"));
        doctorPanel.add(cbDoctor);

        JPanel cabinetPanel = new JPanel(new FlowLayout());
        cabinetPanel.add(new JLabel("Cabinet:"));
        cabinetPanel.add(cbCabinet);

        Properties properties = new Properties();
        properties.put("text.today","Today");
        properties.put("text.month","Month");
        properties.put("text.year","Year");

        JDatePanelImpl datePickerPanel = new JDatePanelImpl(utilDateModel, properties);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePickerPanel, new DateLabelFormatter());

        JPanel datePanel = new JPanel(new FlowLayout());
        datePanel.add(new JLabel("Data:"));
        datePanel.add(datePicker);

        JPanel timePanel = new JPanel(new FlowLayout());
        timePanel.add(new JLabel("Ora:"));
        timePanel.add(timeSelector);

        JPanel firstLine = new JPanel(new FlowLayout());
        firstLine.add(patientPanel);
        firstLine.add(specialityPanel);
        firstLine.setBorder(new EmptyBorder(0, 0, 10, 0));

        JPanel secondLine = new JPanel(new FlowLayout());
        secondLine.add(doctorPanel);
        secondLine.add(cabinetPanel);
        secondLine.setBorder(new EmptyBorder(0, 0, 10, 0));

        JPanel thirdLine = new JPanel(new FlowLayout());
        thirdLine.add(new JLabel("Serviciu medical:"));
        thirdLine.add(cbService);
        thirdLine.add(btnAddService);
        thirdLine.add(btnDeleteService);
        thirdLine.setBorder(new EmptyBorder(0, 0, 10, 0));

        JPanel fourthLine = new JPanel(new FlowLayout());
        fourthLine.add(getTablePanel());
        thirdLine.setBorder(new EmptyBorder(0, 0, 10, 0));

        JPanel fifthLine = new JPanel(new FlowLayout());
        fifthLine.add(datePanel);
        fifthLine.add(timePanel);

        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        dataPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        dataPanel.add(firstLine);
        dataPanel.add(secondLine);
        dataPanel.add(thirdLine);
        dataPanel.add(fourthLine);
        dataPanel.add(fifthLine);

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
        buttonsPanel.add(btnSave);
        buttonsPanel.add(btnCancel);
        buttonsPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        return buttonsPanel;
    }

    public void updateTable() {
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

    public void updateCbPatient(Map<String, String> patients) {
        this.patients = patients;

        cbPatient.removeAllItems();
        patients.values().forEach(cbPatient::addItem);
    }

    public void updateCbDoctor(Map<String, String> doctors) {
        this.doctors = doctors;

        cbDoctor.removeAllItems();
        doctors.values().forEach(cbDoctor::addItem);
    }

    public void updateCbSpeciality(Map<Integer, String> specialities) {
        this.specialities = specialities;

        cbSpeciality.removeAllItems();
        specialities.values().forEach(cbSpeciality::addItem);
    }

    public void updateCbCabinet(Map<Integer, String> cabinets) {
        this.cabinets = cabinets;

        cbCabinet.removeAllItems();
        cabinets.values().forEach(cbCabinet::addItem);
    }

    public void updateCbService(List<MedicalService> services) {
        this.doctorServices = services;

        cbService.removeAllItems();
        services.forEach(service -> cbService.addItem(service.getName()));
    }

    public String getCnpPatient() {
        Optional<Map.Entry<String, String>> result = patients
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().equalsIgnoreCase((String) cbPatient.getSelectedItem()))
                .findFirst();
        return result.map(Map.Entry::getKey).orElse("");
    }

    public String getCnpDoctor() {
        Optional<Map.Entry<String, String>> result = doctors
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().equalsIgnoreCase((String) cbDoctor.getSelectedItem()))
                .findFirst();
        return result.map(Map.Entry::getKey).orElse("");
    }

    public int getIdSpeciality() {
        Optional<Map.Entry<Integer, String>> result = specialities
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().equalsIgnoreCase((String) cbSpeciality.getSelectedItem()))
                .findFirst();
        return result.map(Map.Entry::getKey).orElse(0);
    }

    public int getIdCabinet() {
        Optional<Map.Entry<Integer, String>> result = cabinets
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().equalsIgnoreCase((String) cbCabinet.getSelectedItem()))
                .findFirst();
        return result.map(Map.Entry::getKey).orElse(0);
    }

    public MedicalService getMedicalService() {
        Optional<MedicalService> result = doctorServices
                .stream()
                .filter(medicalService -> medicalService.getName().equalsIgnoreCase((String) cbService.getSelectedItem()))
                .findFirst();
        return (result.orElse(null));
    }

    public List<MedicalService> getMedicalServices() {
        return medicalServices;
    }

    public Date getTime() {
        Date value = utilDateModel.getValue();
        value.setHours(0);
        value.setMinutes(0);
        value.setSeconds(0);
        return new Date(value.getTime() + timeSelector.getTime());
    }

    public UtilDateModel getUtilDateModel() {
        return utilDateModel;
    }

    public JPanel getPreviousPanel() {
        return previousPanel;
    }

    public void addDoctorComboBoxListener(ActionListener actionListener) {
        cbDoctor.addActionListener(actionListener);
    }

    public void addSpecialityComboBoxListener(ActionListener actionListener) {
        cbSpeciality.addActionListener(actionListener);
    }

    public void addCabinetComboBoxListener(ActionListener actionListener) {
        cbCabinet.addActionListener(actionListener);
    }

    public void addAddButtonListener(ActionListener actionListener) {
        btnAddService.addActionListener(actionListener);
    }

    public void addDeleteButtonListener(ActionListener actionListener) {
        btnDeleteService.addActionListener(actionListener);
    }

    public void addSaveButtonListener(ActionListener actionListener) {
        btnSave.addActionListener(actionListener);
    }

    public void addCancelButtonListener(ActionListener actionListener) {
        btnCancel.addActionListener(actionListener);
    }

}
