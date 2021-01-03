package com.sanitas.clinicapp.mr.panels;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PanelShowReceipt extends JPanel {
    private JTextField tfName = new JTextField(25);
    private JTextField tfAdress = new JTextField(25);
    private JTextField tfDate = new JTextField(10);
    private JTextField tfServices = new JTextField(25);
    private JTextField tfPrice = new JTextField(13);

    public PanelShowReceipt() {
        setLayout(new BorderLayout());

        tfName.setEditable(false);
        tfAdress.setEditable(false);
        tfDate.setEditable(false);
        tfServices.setEditable(false);
        tfPrice.setEditable(false);

        JPanel detailsPanel = new JPanel(new GridLayout(7, 1));
        detailsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel namePanel = new JPanel(new FlowLayout());
        namePanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        namePanel.add(new JLabel("Nume Pacient:"));
        namePanel.add(tfName);

        JPanel adressPanel = new JPanel(new FlowLayout());
        adressPanel.setBorder(new EmptyBorder(0, 0, 5, 0));
        adressPanel.add(new JLabel("Adresa:"));
        adressPanel.add(tfAdress);

        JPanel datePanel = new JPanel(new FlowLayout());
        datePanel.setBorder(new EmptyBorder(0, 0, 5, 0));
        datePanel.add(new JLabel("Data:"));
        datePanel.add(tfDate);

        JPanel servicesPanel = new JPanel(new FlowLayout());
        servicesPanel.setBorder(new EmptyBorder(0, 0, 5, 0));
        servicesPanel.add(new JLabel("Servicii:"));
        servicesPanel.add(tfServices);

        JPanel pricePanel = new JPanel(new FlowLayout());
        pricePanel.setBorder(new EmptyBorder(0, 0, 5, 0));
        pricePanel.add(new JLabel("Pret:"));
        pricePanel.add(tfPrice);

        detailsPanel.add(namePanel);
        detailsPanel.add(adressPanel);
        detailsPanel.add(datePanel);
        detailsPanel.add(servicesPanel);
        detailsPanel.add(pricePanel);

        add(detailsPanel, BorderLayout.NORTH);
    }

    public JTextField getTfName() {
        return tfName;
    }

    public JTextField getTfAdress() {
        return tfAdress;
    }

    public JTextField getTfDate() {
        return tfDate;
    }

    public JTextField getTfServices() {
        return tfServices;
    }

    public JTextField getTfPrice() {
        return tfPrice;
    }
}
