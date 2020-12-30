package com.sanitas.clinicapp.mr.panels;

import com.sanitas.clinicapp.mr.Analyse;
import com.sanitas.clinicapp.mr.Patient;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class PanelAddAnalyse extends JPanel {

    private final JTextField tfNe

    private final JComboBox<String> cbAnalyses = new JComboBox<>();

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


        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));

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

    public List<Analyse> getAnalyses() {
        return analyses;
    }

    public JPanel getPreviousPanel() {
        return previousPanel;
    }

}
