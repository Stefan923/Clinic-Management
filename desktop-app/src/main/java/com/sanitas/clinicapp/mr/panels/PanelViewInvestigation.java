package com.sanitas.clinicapp.mr.panels;

import com.sanitas.clinicapp.mr.Investigation;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class PanelViewInvestigation extends JPanel {

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final JTextField tfDoctor = new JTextField(15);
    private final JTextField tfService = new JTextField(15);
    private final JTextField tfDate = new JTextField(15);
    private final JTextArea taRemarks = new JTextArea(2, 45);

    private final JButton btnCancel = new StyledJButton("Anuleaza").getButton();

    private final JPanel previousPanel;
    private final Investigation investigation;

    public PanelViewInvestigation(Investigation investigation, JPanel previousPanel) {
        super(new BorderLayout());
        this.previousPanel = previousPanel;
        this.investigation = investigation;

        loadContent();
    }

    private void loadContent() {
        add(getDataPanel(), BorderLayout.NORTH);
        add(getButtonsPanel(), BorderLayout.SOUTH);
    }

    private JPanel getDataPanel() {
        JPanel firstLinePanel = new JPanel(new FlowLayout());
        firstLinePanel.add(new JLabel("Doctor:"));
        firstLinePanel.add(tfDoctor);
        firstLinePanel.add(new JLabel("Serviciu:"));
        firstLinePanel.add(tfService);

        JPanel secondLinePanel = new JPanel(new FlowLayout());
        secondLinePanel.add(new JLabel("Data investigatiei:"));
        secondLinePanel.add(tfDate);

        JPanel thirdLinePanel = new JPanel(new FlowLayout());
        thirdLinePanel.add(new JLabel("Observatii:"));

        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        dataPanel.add(firstLinePanel);
        dataPanel.add(secondLinePanel);
        dataPanel.add(thirdLinePanel);

        loadInvestigationData();

        return dataPanel;
    }

    private void loadInvestigationData() {
        tfDoctor.setText(investigation.getDoctorFullName());
        tfService.setText(investigation.getServiceName());
        tfDate.setText(DATE_FORMAT.format(investigation.getDate()));
        taRemarks.setText(investigation.getRemarks());

        tfDoctor.setEditable(false);
        tfService.setEditable(false);
        tfDate.setEditable(false);
        taRemarks.setEditable(false);
    }

    private JPanel getButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(btnCancel);

        return buttonsPanel;
    }

    public JPanel getPreviousPanel() {
        return previousPanel;
    }

    public void addCancelButtonListener(ActionListener actionListener) {
        btnCancel.addActionListener(actionListener);
    }

}
