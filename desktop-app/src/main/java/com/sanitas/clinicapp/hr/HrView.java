package com.sanitas.clinicapp.hr;

import com.sanitas.clinicapp.ClinicApplication;
import com.sanitas.clinicapp.hr.panels.PanelShowEmployee;
import com.sanitas.clinicapp.hr.panels.PanelViewEmployee;
import com.sanitas.clinicapp.ui.Colors;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HrView extends JFrame {

    private JButton btnShowEmployees = new StyledJButton("Adaugare Angajat").getButton();
    private JButton btnSearchEmployee = new StyledJButton("Cautare Angajat").getButton();

    private JButton btnBack = new StyledJButton("Inapoi").getButton();

    private HrModel model;
    private JPanel currentPanel;
    private PanelShowEmployee viewv ;
    private ClinicApplication.Account account;

    public HrView(HrModel model, ClinicApplication.Account account) {

        viewv=new PanelShowEmployee(model,ClinicApplication.getUser());
        currentPanel=viewv;
        viewv.setVisible(true);

        btnShowEmployees.setBackground(Colors.MAIN_COLOR.getColor());
        btnSearchEmployee.setBackground(Colors.MAIN_COLOR.getColor());
        btnBack.setBackground(Colors.MAIN_COLOR.getColor());

        JPanel menuContent = new JPanel(new GridLayout(2, 1));
        if(account.hasPermission("hr.read.all"))
            menuContent.add(btnShowEmployees);
        menuContent.add(btnSearchEmployee);

        JPanel leftContent = new JPanel(new BorderLayout());
        leftContent.add(menuContent, BorderLayout.NORTH);
        leftContent.add(btnBack, BorderLayout.SOUTH);
        leftContent.setBackground(new Color(0XBDBEBF));

        JPanel content = new JPanel(new BorderLayout());
        content.add(leftContent, BorderLayout.WEST);
        content.add(viewv, BorderLayout.CENTER);


        this.setPreferredSize(new Dimension(920, 420));
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

    public PanelShowEmployee getViewv() {
        return viewv;
    }

    public JButton getBtnBack() {
        return btnBack;
    }

    public JButton getBtnSearchEmployee() {
        return btnSearchEmployee;
    }

    public JButton getBtnShowEmployees() {
        return btnShowEmployees;
    }

    public JPanel getCurrentPanel() {
        return currentPanel;
    }

    public void addBtnAddEmployeesListener(ActionListener actionListener) {
        btnShowEmployees.addActionListener(actionListener);
    }

    public void addBtnSearchEmployeeListener(ActionListener actionListener) {
        btnSearchEmployee.addActionListener(actionListener);
    }

    public void addBackListener(ActionListener actionListener) {
        btnBack.addActionListener(actionListener);
    }

    public void sendError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error!", JOptionPane.ERROR_MESSAGE);
    }

    public void sendSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Succes!", JOptionPane.INFORMATION_MESSAGE);
    }


}
