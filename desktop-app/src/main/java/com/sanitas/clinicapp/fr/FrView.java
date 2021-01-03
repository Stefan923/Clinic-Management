package com.sanitas.clinicapp.fr;

import com.sanitas.clinicapp.mr.MrModel;
import com.sanitas.clinicapp.mr.panels.PanelShowPatients;
import com.sanitas.clinicapp.ui.Colors;
import com.sanitas.clinicapp.ui.StyledJButton;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
    private JButton btnShowTransactions = new StyledJButton("Afisare Tranzactii").getButton();

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
        btnShowTransactions.setBackground(Colors.MAIN_COLOR.getColor());
        btnBack.setBackground(Colors.MAIN_COLOR.getColor());

        JPanel btnMedicalUnitProfitPanel = new JPanel(new GridLayout(1, 1));
        btnMedicalUnitProfitPanel.setBorder(new EmptyBorder(0, 0, 3, 0));
        btnMedicalUnitProfitPanel.setBackground(Colors.MENU_COLOR.getColor());
        btnMedicalUnitProfitPanel.add(btnMedicalUnitProfit);

        JPanel btnProfitByDoctorPanel = new JPanel(new GridLayout(1, 1));
        btnProfitByDoctorPanel.setBorder(new EmptyBorder(0, 0, 3, 0));
        btnProfitByDoctorPanel.setBackground(Colors.MENU_COLOR.getColor());
        btnProfitByDoctorPanel.add(btnProfitByDoctor);

        JPanel btnProfitBySpecialityPanel = new JPanel(new GridLayout(1, 1));
        btnProfitBySpecialityPanel.setBorder(new EmptyBorder(0, 0, 3, 0));
        btnProfitBySpecialityPanel.setBackground(Colors.MENU_COLOR.getColor());
        btnProfitBySpecialityPanel.add(btnProfitBySpeciality);

        JPanel btnTotalProfitPanel = new JPanel(new GridLayout(1, 1));
        btnTotalProfitPanel.setBorder(new EmptyBorder(0, 0, 3, 0));
        btnTotalProfitPanel.setBackground(Colors.MENU_COLOR.getColor());
        btnTotalProfitPanel.add(btnTotalProfit);

        JPanel btnEmployeeSalaryPanel = new JPanel(new GridLayout(1, 1));
        btnEmployeeSalaryPanel.setBorder(new EmptyBorder(0, 0, 3, 0));
        btnEmployeeSalaryPanel.setBackground(Colors.MENU_COLOR.getColor());
        btnEmployeeSalaryPanel.add(btnEmployeeSalary);

        JPanel btnDoctorSalaryPanel = new JPanel(new GridLayout(1, 1));
        btnDoctorSalaryPanel.setBorder(new EmptyBorder(0, 0, 3, 0));
        btnDoctorSalaryPanel.setBackground(Colors.MENU_COLOR.getColor());
        btnDoctorSalaryPanel.add(btnDoctorSalary);

        JPanel btnDoctorProfitTotalPanel = new JPanel(new GridLayout(1, 1));
        btnDoctorProfitTotalPanel.setBorder(new EmptyBorder(0, 0, 3, 0));
        btnDoctorProfitTotalPanel.setBackground(Colors.MENU_COLOR.getColor());
        btnDoctorProfitTotalPanel.add(btnDoctorProfitTotal);

        JPanel btnShowTransactionsPanel = new JPanel(new GridLayout(1, 1));
        btnShowTransactionsPanel.setBorder(new EmptyBorder(0, 0, 3, 0));
        btnShowTransactionsPanel.setBackground(Colors.MENU_COLOR.getColor());
        btnShowTransactionsPanel.add(btnShowTransactions);

        JPanel btnBackPanel = new JPanel(new GridLayout(1, 1));
        btnBackPanel.add(btnBack);
        btnBackPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
        btnBackPanel.setBackground(Colors.MENU_COLOR.getColor());

        JPanel menuContent = new JPanel(new GridLayout(8, 1));
        menuContent.add(btnMedicalUnitProfitPanel);
        menuContent.add(btnProfitByDoctorPanel);
        menuContent.add(btnProfitBySpecialityPanel);
        menuContent.add(btnTotalProfitPanel);
        menuContent.add(btnEmployeeSalaryPanel);
        menuContent.add(btnDoctorSalaryPanel);
        menuContent.add(btnDoctorProfitTotalPanel);
        menuContent.add(btnShowTransactionsPanel);

        JPanel leftContent = new JPanel(new BorderLayout());
        leftContent.add(menuContent, BorderLayout.NORTH);
        leftContent.add(btnBackPanel, BorderLayout.SOUTH);
        leftContent.setBorder(BorderFactory
                .createMatteBorder(0, 0, 0, 1, Colors.MENU_BORDER_COLOR.getColor()));
        leftContent.setBackground(Colors.MENU_COLOR.getColor());

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

    public JButton getBtnShowTransactions() { return btnShowTransactions; }

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

    public void addBtnShowTransactionsListener(ActionListener actionListener) {
        btnShowTransactions.addActionListener(actionListener);
    }

    public void addBackButtonListener(ActionListener actionListener) {
        btnBack.addActionListener(actionListener);
    }

}