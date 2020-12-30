package com.sanitas.clinicapp.fr;

import com.sanitas.clinicapp.mr.MrModel;
import com.sanitas.clinicapp.mr.panels.PanelShowPatients;
import com.sanitas.clinicapp.ui.Colors;
import com.sanitas.clinicapp.ui.StyledJButton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class FrView extends JFrame {
    private JPanel currentPanel;

    private JButton btnMedicalUnitProfit = new StyledJButton("Afisare Profit Policlinică").getButton();
    private JButton btnProfitByDoctor = new StyledJButton("Afișare Profit Doctor").getButton();
    private JButton btnProfitBySpeciality = new StyledJButton("Afisare Profit Per Specialitate").getButton();
    private JButton btnTotalProfit = new StyledJButton("Afisare Profit Total").getButton();
    private JButton btnEmployeeSalary = new StyledJButton("Afisare Salariu Angajat").getButton();
    private JButton btnDoctorSalary = new StyledJButton("Afisare Salariu Doctor").getButton();
    private JButton btnDoctorProfitTotal = new StyledJButton("Afisare Profit Total Per Doctor").getButton();

    private JButton btnBack = new StyledJButton("Inapoi").getButton();

    public FrView(FrModel model) {

        currentPanel= new JPanel();
        currentPanel.setVisible(true);

        btnMedicalUnitProfit.setBackground(Colors.MAIN_COLOR.getColor());
        btnProfitByDoctor.setBackground(Colors.MAIN_COLOR.getColor());
        btnProfitBySpeciality.setBackground(Colors.MAIN_COLOR.getColor());
        btnTotalProfit.setBackground(Colors.MAIN_COLOR.getColor());
        btnEmployeeSalary.setBackground(Colors.MAIN_COLOR.getColor());
        btnDoctorSalary.setBackground(Colors.MAIN_COLOR.getColor());
        btnDoctorProfitTotal.setBackground(Colors.MAIN_COLOR.getColor());
        btnBack.setBackground(Colors.MAIN_COLOR.getColor());

        JPanel menuContent = new JPanel(new GridLayout(7, 1));
        menuContent.add(btnMedicalUnitProfit);
        menuContent.add(btnProfitByDoctor);
        menuContent.add(btnProfitBySpeciality);
        menuContent.add(btnTotalProfit);
        menuContent.add(btnEmployeeSalary);
        menuContent.add(btnDoctorSalary);
        menuContent.add(btnDoctorProfitTotal);


        JPanel leftContent = new JPanel(new BorderLayout());
        leftContent.add(menuContent, BorderLayout.NORTH);
        leftContent.add(btnBack, BorderLayout.SOUTH);

        JPanel content = new JPanel(new BorderLayout());
        content.add(leftContent, BorderLayout.WEST);
        content.add(currentPanel, BorderLayout.CENTER);

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

    public JPanel getCurrentPanel() {
        return currentPanel;
    }

    public JButton getBtnMedicalUnitProfit() {
        return btnMedicalUnitProfit;
    }

    public JButton getBtnProfitByDoctor() {
        return btnProfitByDoctor;
    }

    public JButton getBtnProfitBySpeciality() {
        return btnProfitBySpeciality;
    }

    public JButton getBtnTotalProfit() {
        return btnTotalProfit;
    }

    public JButton getBtnEmployeeSalary() {
        return btnEmployeeSalary;
    }

    public JButton getBtnDoctorSalary() {
        return btnDoctorSalary;
    }

    public JButton getBtnDoctorProfitTotal() {
        return btnDoctorProfitTotal;
    }

    public JButton getBtnBack() {
        return btnBack;
    }

    public void sendError(String message) {
        JOptionPane.showMessageDialog(this, message, "Eroare!", JOptionPane.ERROR_MESSAGE);
    }

    public void sendSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Succes!", JOptionPane.INFORMATION_MESSAGE);
    }

    public void addBtnMedicalUnitProfitListener(ActionListener actionListener) {
        btnMedicalUnitProfit.addActionListener(actionListener);
    }

    public void addBtnProfitByDoctorListener(ActionListener actionListener) {
        btnProfitByDoctor.addActionListener(actionListener);
    }

    public void addBtnProfitBySpecialityListener(ActionListener actionListener) {
        btnProfitBySpeciality.addActionListener(actionListener);
    }

    public void addBtnTotalProfitListener(ActionListener actionListener) {
        btnTotalProfit.addActionListener(actionListener);
    }

    public void addBtnEmployeeSalaryListener(ActionListener actionListener) {
        btnEmployeeSalary.addActionListener(actionListener);
    }
    public void addBtnDoctorSalaryListener(ActionListener actionListener) {
        btnDoctorSalary.addActionListener(actionListener);
    }
    public void addBtnDoctorProfitTotalListener(ActionListener actionListener) {
        btnDoctorProfitTotal.addActionListener(actionListener);
    }

    public void addBackButtonListener(ActionListener actionListener) {
        btnBack.addActionListener(actionListener);
    }

}