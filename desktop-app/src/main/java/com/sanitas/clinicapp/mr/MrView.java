package com.sanitas.clinicapp.mr;

import com.sanitas.clinicapp.mr.panels.PanelShowPatients;
import com.sanitas.clinicapp.ui.Colors;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class MrView extends JFrame {

    private PanelShowPatients panelShowPatients;

    private JPanel currentPanel;

    private JButton btnShowPatients = new StyledJButton("Afisare Pacienti").getButton();
    private JButton btnSearchPatient = new StyledJButton("Cautare Pacient").getButton();
    private JButton btnMedicalServices = new StyledJButton("Servicii Medicale").getButton();

    private JButton btnBack = new StyledJButton("Inapoi").getButton();

    public MrView(MrModel model) {
        panelShowPatients = new PanelShowPatients(model);
        currentPanel = panelShowPatients;
        panelShowPatients.setVisible(true);

        btnShowPatients.setBackground(Colors.MAIN_COLOR.getColor());
        btnSearchPatient.setBackground(Colors.MAIN_COLOR.getColor());
        btnMedicalServices.setBackground(Colors.MAIN_COLOR.getColor());
        btnBack.setBackground(Colors.MAIN_COLOR.getColor());

        JPanel btnShowPatientsPanel = new JPanel(new GridLayout(1, 1));
        btnShowPatientsPanel.setBorder(new EmptyBorder(0, 0, 3, 0));
        btnShowPatientsPanel.setBackground(Colors.MENU_COLOR.getColor());
        btnShowPatientsPanel.add(btnShowPatients);

        JPanel btnSearchPatientPanel = new JPanel(new GridLayout(1, 1));
        btnSearchPatientPanel.setBorder(new EmptyBorder(0, 0, 3, 0));
        btnSearchPatientPanel.setBackground(Colors.MENU_COLOR.getColor());
        btnSearchPatientPanel.add(btnSearchPatient);

        JPanel btnMedicalServicesPanel = new JPanel(new GridLayout(1, 1));
        btnMedicalServicesPanel.setBackground(Colors.MENU_COLOR.getColor());
        btnMedicalServicesPanel.add(btnMedicalServices);

        JPanel menuContent = new JPanel(new GridLayout(4, 1));
        menuContent.add(btnShowPatientsPanel);
        menuContent.add(btnSearchPatientPanel);
        menuContent.add(btnMedicalServicesPanel);
        menuContent.setBackground(Colors.MENU_COLOR.getColor());
        menuContent.setBorder(new EmptyBorder(10, 10, 0, 10));

        JPanel btnBackPanel = new JPanel(new GridLayout(1, 1));
        btnBackPanel.add(btnBack);
        btnBackPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
        btnBackPanel.setBackground(Colors.MENU_COLOR.getColor());

        JPanel leftContent = new JPanel(new BorderLayout());
        leftContent.add(menuContent, BorderLayout.NORTH);
        leftContent.add(btnBackPanel, BorderLayout.SOUTH);
        leftContent.setBorder(BorderFactory
                .createMatteBorder(0, 0, 0, 1, Colors.MENU_BORDER_COLOR.getColor()));
        leftContent.setBackground(Colors.MENU_COLOR.getColor());

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

    public void addBtnMedicalServicesListener(ActionListener actionListener) {
        btnMedicalServices.addActionListener(actionListener);
    }

    public void addBackButtonListener(ActionListener actionListener) {
        btnBack.addActionListener(actionListener);
    }

}
