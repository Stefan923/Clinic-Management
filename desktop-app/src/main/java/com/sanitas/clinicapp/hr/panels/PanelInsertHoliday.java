package com.sanitas.clinicapp.hr.panels;

import com.sanitas.clinicapp.ClinicApplication;
import com.sanitas.clinicapp.hr.Schedule;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelInsertHoliday extends JPanel{
    private JTextField tfStart = new JTextField(15);
    private JTextField tfEnd = new JTextField(15);

    private JButton btnSave = new StyledJButton("Salveaza").getButton();
    private JButton btnCancel = new StyledJButton("Inapoi").getButton();

    private final PanelViewHoliday previousPanel;
    private ClinicApplication.Account account;

    public PanelInsertHoliday(PanelViewHoliday previousPanel, ClinicApplication.Account account)
    {
        this.previousPanel=previousPanel;
        this.account=account;
        setLayout(new BorderLayout());

        JPanel startPanel = new JPanel(new FlowLayout());
        startPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        startPanel.add(new JLabel("Data inceput:"));
        startPanel.add(tfStart);

        JPanel endPanel = new JPanel(new FlowLayout());
        endPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        endPanel.add(new JLabel("Data sfarsit:"));
        endPanel.add(tfEnd);

        JPanel txt=new JPanel(new GridLayout(1,2));
        txt.add(startPanel);
        txt.add(endPanel);

        JPanel btn=new JPanel(new FlowLayout());
        if(account.hasPermission("hr.read.all"))
            btn.add(btnSave);
        btn.add(btnCancel);

        add(txt,BorderLayout.CENTER);
        add(btn,BorderLayout.SOUTH);

        setVisible(false);
    }

    public JTextField getTfStart() {
        return tfStart;
    }

    public JTextField getTfEnd() {
        return tfEnd;
    }

    public void addSaveHolidayListener(ActionListener actionListener) {
        btnSave.addActionListener(actionListener);
    }

    public void addCancelHolidayListener(ActionListener actionListener) {
        btnCancel.addActionListener(actionListener);
    }

    public void reset(){
        tfStart.setText("");
        tfEnd.setText("");
    }

    public PanelViewHoliday getPreviousPanel() {
        return previousPanel;
    }
}
