package com.sanitas.clinicapp.hr;

import com.sanitas.clinicapp.ui.Colors;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class openViewView extends JPanel {

    private JTextField TxtFirst= new JTextField(15);
    private JTextField TxtLast= new JTextField(15);
    private JTextField TxtPosition= new JTextField(15);
    private JButton search=new StyledJButton("Search").getButton();;

    private openViewModel model;

    openViewView(openViewModel model) {
        this.model = model;
        search.setBackground(Colors.MAIN_COLOR.getColor());

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
        searchData.add(lastname, BorderLayout.NORTH);
        searchData.add(firstname, BorderLayout.CENTER);
        searchData.add(position, BorderLayout.SOUTH);
        searchData.setBorder(new EmptyBorder(0, 0, 20, 0));

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(searchData, BorderLayout.NORTH);
        searchPanel.add(search, BorderLayout.CENTER);
        searchPanel.setBorder(new EmptyBorder(0, 5, 10, 5));

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

    public void sendError(String message) {
        JOptionPane.showMessageDialog(this, message, "Not found!", 0);
    }
}
