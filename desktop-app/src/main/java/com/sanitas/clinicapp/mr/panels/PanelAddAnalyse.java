package com.sanitas.clinicapp.mr.panels;

import com.sanitas.clinicapp.mr.Analyse;
import com.sanitas.clinicapp.mr.MedicalService;
import com.sanitas.clinicapp.mr.Patient;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

public class PanelAddAnalyse extends JPanel {

    private final JComboBox<String> cbAnalyses = new JComboBox<>();

    private final JTextField tfValue = new JTextField(15);

    private final JButton btnSave = new StyledJButton("Salveaza").getButton();
    private final JButton btnCancel = new StyledJButton("Anuleaza").getButton();

    private final JPanel previousPanel;
    private final Patient patient;

    private List<Analyse> analyses;

    public PanelAddAnalyse(Patient patient, JPanel previousPanel) {
        super(new BorderLayout());
        this.patient = patient;
        this.previousPanel = previousPanel;

        loadContent();
    }

    private void loadContent() {
        add(getDataPanel(), BorderLayout.NORTH);
        add(getButtonsPanel(), BorderLayout.SOUTH);
    }

    private JPanel getDataPanel() {
        JPanel cbAnalysesPanel = new JPanel(new FlowLayout());
        cbAnalysesPanel.add(new JLabel("Analiza:"));
        cbAnalysesPanel.add(cbAnalyses);
        cbAnalysesPanel.setBorder(new EmptyBorder(0, 0, 0, 10));

        JPanel tfValuePanel = new JPanel(new FlowLayout());
        tfValuePanel.add(new JLabel("Valoare:"));
        tfValuePanel.add(tfValue);
        tfValuePanel.setBorder(new EmptyBorder(0, 10, 0, 0));

        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new FlowLayout());
        dataPanel.add(cbAnalysesPanel);
        dataPanel.add(tfValuePanel);
        dataPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        return dataPanel;
    }

    private JPanel getButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(btnSave);
        buttonsPanel.add(btnCancel);
        buttonsPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        return buttonsPanel;
    }

    public void updateCbAnalyses(List<Analyse> analyses) {
        this.analyses = analyses;

        cbAnalyses.removeAllItems();
        analyses.forEach(analyse -> cbAnalyses.addItem(analyse.getName()));
    }

    public Patient getPatient() {
        return patient;
    }

    public Integer getIdAnalyse() {
        String selectedItem = (String) cbAnalyses.getSelectedItem();

        Optional<Analyse> result = analyses
                .stream()
                .filter(analyse -> analyse.getName().equalsIgnoreCase(selectedItem))
                .findFirst();
        return result.map(Analyse::getIdAnalyse).orElse(-1);
    }

    public JTextField getTfValue() {
        return tfValue;
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
