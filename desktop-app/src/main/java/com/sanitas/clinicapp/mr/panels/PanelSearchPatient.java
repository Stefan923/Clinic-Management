package com.sanitas.clinicapp.mr.panels;

import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelSearchPatient extends JPanel {

    private JTextField tfCnp = new JTextField(15);

    private JButton btnSearch = new StyledJButton("Cauta").getButton();

    public PanelSearchPatient() {
        setLayout(new BorderLayout());

        JPanel cnpPanel = new JPanel(new FlowLayout());
        cnpPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        cnpPanel.add(new JLabel("Introduceti CNP-ul:"));
        cnpPanel.add(tfCnp);

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        searchPanel.add(btnSearch);

        add(cnpPanel, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.SOUTH);

        setVisible(false);
    }

    public JTextField getTfCnp() {
        return tfCnp;
    }

    public void addSearchButtonListener(ActionListener actionListener) {
        btnSearch.addActionListener(actionListener);
    }

}
