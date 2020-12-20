package com.sanitas.clinicapp.hr;

import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HrView extends JFrame {

    JButton btnShowEmployees = new StyledJButton("Afisare Angajatii").getButton();
    JButton btnSearchEmployee = new StyledJButton("Cautare Angajat").getButton();

    public HrView() {
        JPanel menuContent = new JPanel(new GridLayout(2, 1));
        menuContent.add(btnShowEmployees);
        menuContent.add(btnSearchEmployee);

        JPanel content = new JPanel(new BorderLayout());
        content.add(menuContent, BorderLayout.WEST);

        this.setContentPane(content);
        this.pack();

        this.setTitle("Sanitas");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addBtnShowEmployeesListener(ActionListener actionListener) {
        btnShowEmployees.addActionListener(actionListener);
    }

    public void addBtnSearchEmployeeListener(ActionListener actionListener) {
        btnSearchEmployee.addActionListener(actionListener);
    }

}
