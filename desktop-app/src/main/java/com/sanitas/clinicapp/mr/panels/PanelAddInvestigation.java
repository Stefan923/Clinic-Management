package com.sanitas.clinicapp.mr.panels;

import com.sanitas.clinicapp.mr.MedicalService;
import com.sanitas.clinicapp.mr.Report;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

public class PanelAddInvestigation extends JPanel {

    private final JComboBox<String> cbServices = new JComboBox<>();
    private final JTextArea taRemarks = new JTextArea(2, 45);

    private final JButton btnSave = new StyledJButton("Salveaza").getButton();
    private final JButton btnCancel = new StyledJButton("Anuleaza").getButton();

    private List<MedicalService> services;
    private final Report report;
    private final JPanel previousPanel;

    public PanelAddInvestigation(Report report, JPanel previousPanel) {
        super(new BorderLayout());
        this.previousPanel = previousPanel;
        this.report = report;

        loadContent();
    }

    private void loadContent() {
        add(getDataPanel(), BorderLayout.NORTH);
        add(getButtonsPanel(), BorderLayout.SOUTH);
    }

    private JPanel getDataPanel() {
        JPanel cbServicesPanel = new JPanel(new FlowLayout());
        cbServicesPanel.add(new JLabel("Serviciu:"));
        cbServicesPanel.add(cbServicesPanel);
        cbServicesPanel.setBorder(new EmptyBorder(20, 5, 20, 5));

        JPanel taRemarksPanel = new JPanel(new FlowLayout());
        taRemarksPanel.add(new JLabel("Observatii:"));
        taRemarksPanel.add(taRemarks);

        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        dataPanel.add(cbServicesPanel);
        dataPanel.add(taRemarksPanel);

        return dataPanel;
    }

    private JPanel getButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(btnSave);
        buttonsPanel.add(btnCancel);

        return buttonsPanel;
    }

    public void updateCbServices(List<MedicalService> services) {
        this.services = services;

        cbServices.removeAllItems();
        services.forEach(medicalService -> cbServices.addItem(medicalService.getName()));
    }

    public Report getReport() {
        return report;
    }

    public JTextArea getTaRemarks() {
        return taRemarks;
    }

    public Integer getIdService() {
        String selectedItem = (String) cbServices.getSelectedItem();

        Optional<MedicalService> result = services
                .stream()
                .filter(medicalService -> medicalService.getName().equalsIgnoreCase(selectedItem))
                .findFirst();
        return result.map(MedicalService::getId).orElse(-1);
    }

    public List<MedicalService> getServices() {
        return services;
    }

    public JPanel getPreviousPanel() {
        return previousPanel;
    }

    public void addSaveButtonListener(ActionListener actionListener) {
        btnSave.addActionListener(actionListener);
    }

    public void addCancelButtonListener(ActionListener actionListener) {
        btnCancel.addActionListener(actionListener);
    }

}
