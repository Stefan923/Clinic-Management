package com.sanitas.clinicapp.hr.panels;

import com.sanitas.clinicapp.ui.Colors;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelViewEmployee extends JPanel {

    private JTextField TxtFirst = new JTextField(15);
    private JTextField TxtLast = new JTextField(15);
    private JTextField TxtPosition = new JTextField(15);
    private JButton search = new StyledJButton("Search").getButton();;


    public PanelViewEmployee() {

        search.setBackground(Colors.MAIN_COLOR.getColor());

        setLayout(new BorderLayout());
        JPanel lastname = new JPanel(new FlowLayout());
        lastname.add(new JLabel("Last Name: "));
        lastname.add(TxtLast);

        JPanel firstname = new JPanel(new FlowLayout());
        firstname.add(new JLabel("First Name: "));
        firstname.add(TxtFirst);

        JPanel position = new JPanel(new FlowLayout());
        position.add(new JLabel("Position: "));
        position.add(TxtPosition);

        JPanel searchData = new JPanel(new BorderLayout());
        searchData.add(lastname, BorderLayout.WEST);
        searchData.add(firstname, BorderLayout.CENTER);
        searchData.add(position, BorderLayout.EAST);
        searchData.setBorder(new EmptyBorder(0, 0, 20, 0));

        JPanel button=new JPanel(new FlowLayout());
        button.add(search);

        add(searchData, BorderLayout.NORTH);
        add(button, BorderLayout.CENTER);
        setBorder(new EmptyBorder(0, 5, 10, 5));

    }

    public void addBtnSearchListener(ActionListener actionListener) {
        search.addActionListener(actionListener);
    }

    public JTextField getTxtFirst() {
        return TxtFirst;
    }

    public JTextField getTxtLast() {
        return TxtLast;
    }

    public JTextField getTxtPosition() {
        return TxtPosition;
    }

}
