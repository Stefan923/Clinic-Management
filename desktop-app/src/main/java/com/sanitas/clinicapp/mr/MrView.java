package com.sanitas.clinicapp.mr;

import com.sanitas.clinicapp.mr.panels.PanelShowPatients;
import com.sanitas.clinicapp.ui.Colors;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MrView extends JFrame {

    private PanelShowPatients panelShowPatients;

    private JPanel currentPanel;

    private JButton btnShowPatients = new StyledJButton("Afisare Pacienti").getButton();
    private JButton btnSearchPatient = new StyledJButton("Cautare Pacient").getButton();
    private JButton btnAddPatient = new StyledJButton("Adaugare Pacient").getButton();
    private JButton btnMedicalServices = new StyledJButton("Servicii Medicale").getButton();

    private JButton btnBack = new StyledJButton("Inapoi").getButton();

    public MrView(MrModel model) {
        panelShowPatients = new PanelShowPatients(model);
        currentPanel = panelShowPatients;
        panelShowPatients.setVisible(true);

        btnShowPatients.setBackground(Colors.MAIN_COLOR.getColor());
        btnSearchPatient.setBackground(Colors.MAIN_COLOR.getColor());
        btnAddPatient.setBackground(Colors.MAIN_COLOR.getColor());
        btnMedicalServices.setBackground(Colors.MAIN_COLOR.getColor());
        btnBack.setBackground(Colors.MAIN_COLOR.getColor());

        JPanel menuContent = new JPanel(new GridLayout(4, 1));
        menuContent.add(btnShowPatients);
        menuContent.add(btnSearchPatient);
        menuContent.add(btnAddPatient);
        menuContent.add(btnMedicalServices);

        JPanel leftContent = new JPanel(new BorderLayout());
        leftContent.add(menuContent, BorderLayout.NORTH);
        leftContent.add(btnBack, BorderLayout.SOUTH);

        JPanel content = new JPanel(new BorderLayout());
        content.add(leftContent, BorderLayout.WEST);
        content.add(panelShowPatients, BorderLayout.CENTER);

        this.setPreferredSize(new Dimension(820, 420));
        this.setContentPane(content);
        this.pack();

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dimension.width / 2 - this.getSize().width / 2,
                dimension.height / 2 - this.getSize().height / 2);

        this.setTitle("Sanitas");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setRightPanel(JPanel jPanel) {
        currentPanel.setVisible(false);

        getContentPane().add(jPanel, BorderLayout.CENTER);
        currentPanel = jPanel;
        currentPanel.setVisible(true);
    }

    public PanelShowPatients getPanelShowPatients() {
        return panelShowPatients;
    }

    public JPanel getCurrentPanel() {
        return currentPanel;
    }

    public JButton getBtnShowPatients() {
        return btnShowPatients;
    }

    public JButton getBtnSearchPatient() {
        return btnSearchPatient;
    }

    public JButton getBtnAddPatient() {
        return btnAddPatient;
    }

    public void sendError(String message) {
        JOptionPane.showMessageDialog(this, message, "Eroare!", JOptionPane.ERROR_MESSAGE);
    }

    public void sendSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Succes!", JOptionPane.INFORMATION_MESSAGE);
    }

    public void addBtnShowPatientsListener(ActionListener actionListener) {
        btnShowPatients.addActionListener(actionListener);
    }

    public void addBtnSearchPatientListener(ActionListener actionListener) {
        btnSearchPatient.addActionListener(actionListener);
    }

    public void addBtnAddPatientListener(ActionListener actionListener) {
        btnAddPatient.addActionListener(actionListener);
    }

    public void addBtnMedicalServicesListener(ActionListener actionListener) {
        btnMedicalServices.addActionListener(actionListener);
    }

    public void addBackButtonListener(ActionListener actionListener) {
        btnBack.addActionListener(actionListener);
    }

}
