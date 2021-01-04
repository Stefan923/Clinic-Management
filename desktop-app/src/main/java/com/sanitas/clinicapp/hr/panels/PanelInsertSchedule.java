package com.sanitas.clinicapp.hr.panels;

import com.sanitas.clinicapp.hr.Schedule;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelInsertSchedule extends JPanel{
    private JTextField tfCnp = new JTextField(15);
    private JTextField tfMedicalUnit = new JTextField(15);
    private JTextField tfDay = new JTextField(15);
    private JTextField tfStart = new JTextField(15);
    private JTextField tfEnd = new JTextField(15);

    private JButton btnSave = new StyledJButton("Salveaza").getButton();
    private JButton btnCancel = new StyledJButton("Anuleaza").getButton();

    private final PanelViewSchedule previousPanel;

    public PanelInsertSchedule(PanelViewSchedule previousPanel,String cnp)
    {
        this.previousPanel = previousPanel;
        setLayout(new BorderLayout());

        tfCnp.setEditable(false);
        tfCnp.setText(cnp);

        JPanel cnpPanel = new JPanel(new FlowLayout());
        cnpPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        cnpPanel.add(new JLabel("CNP:"));
        cnpPanel.add(tfCnp);

        JPanel muPanel = new JPanel(new FlowLayout());
        muPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        muPanel.add(new JLabel("Nr. unitate medicala:"));
        muPanel.add(tfMedicalUnit);

        JPanel dayPanel = new JPanel(new FlowLayout());
        dayPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        dayPanel.add(new JLabel("Ziua:"));
        dayPanel.add(tfDay);

        JPanel startPanel = new JPanel(new FlowLayout());
        startPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        startPanel.add(new JLabel("Ora incepere:"));
        startPanel.add(tfStart);

        JPanel endPanel = new JPanel(new FlowLayout());
        endPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        endPanel.add(new JLabel("Ora finalizare:"));
        endPanel.add(tfEnd);

        JPanel sch=new JPanel(new GridLayout(3,2));
        sch.add(startPanel);
        sch.add(endPanel);
        sch.add(cnpPanel);
        sch.add(muPanel);
        sch.add(dayPanel);

        JPanel buttons=new JPanel(new FlowLayout());
        buttons.add(btnSave);
        buttons.add(btnCancel);

        add(sch,BorderLayout.CENTER);
        add(buttons,BorderLayout.SOUTH);

        setVisible(false);
    }

    public PanelViewSchedule getPreviousPanel() {
        return previousPanel;
    }

    public JTextField getTfEnd() {
        return tfEnd;
    }

    public JTextField getTfStart() {
        return tfStart;
    }

    public JTextField getTfCnp() {
        return tfCnp;
    }

    public JTextField getTfDay() {
        return tfDay;
    }

    public JTextField getTfMedicalUnit() {
        return tfMedicalUnit;
    }

    public void addSaveScheduleListener(ActionListener actionListener) {
        btnSave.addActionListener(actionListener);
    }

    public void addCancelScheduleListener(ActionListener actionListener) {
        btnCancel.addActionListener(actionListener);
    }

    public void reset() {
        tfStart.setText("");
        tfEnd.setText("");
    }
}
