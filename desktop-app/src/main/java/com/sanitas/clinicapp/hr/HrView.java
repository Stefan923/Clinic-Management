package com.sanitas.clinicapp.hr;

import com.sanitas.clinicapp.ui.Colors;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HrView extends JFrame {

    JButton btnShowEmployees = new StyledJButton("Adauga Angajat").getButton();
    JButton btnSearchEmployee = new StyledJButton("Cautare Angajat").getButton();

    JButton btnBack = new StyledJButton("Inapoi").getButton();

    HrModel model;


    openViewModel modelv=new openViewModel();
    openViewView viewv=new openViewView(modelv);


    public HrView(HrModel model) {
        btnShowEmployees.setBackground(Colors.MAIN_COLOR.getColor());
        btnSearchEmployee.setBackground(Colors.MAIN_COLOR.getColor());
        btnBack.setBackground(Colors.MAIN_COLOR.getColor());

        JPanel menuContent = new JPanel(new GridLayout(2, 1));
        menuContent.add(btnShowEmployees);
        menuContent.add(btnSearchEmployee);

        JPanel rightContent = new JPanel(new BorderLayout());
        rightContent.add(menuContent, BorderLayout.NORTH);
        rightContent.add(btnBack, BorderLayout.SOUTH);
        rightContent.setBackground(new Color(0XBDBEBF));

        JPanel content = new JPanel(new BorderLayout());
        content.add(rightContent, BorderLayout.WEST);

        content.add(viewv, BorderLayout.EAST);
        openViewController controller=new openViewController(modelv,viewv);

        viewv.setVisible(false);

        this.setPreferredSize(new Dimension(400, 300));
        this.setContentPane(content);
        this.pack();

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dimension.width / 2 - this.getSize().width / 2,
                dimension.height / 2 - this.getSize().height / 2);

        this.setTitle("Sanitas");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addBtnAddEmployeesListener(ActionListener actionListener) {
        btnShowEmployees.addActionListener(actionListener);
    }

    public void addBtnSearchEmployeeListener(ActionListener actionListener) {
        btnSearchEmployee.addActionListener(actionListener);
    }

    public void setViewvVisible() {viewv.setVisible(true);}
}
