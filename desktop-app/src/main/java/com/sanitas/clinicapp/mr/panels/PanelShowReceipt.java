package com.sanitas.clinicapp.mr.panels;

import com.sanitas.clinicapp.mr.MrController;
import com.sanitas.clinicapp.struct.Receipt;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class PanelShowReceipt extends JPanel {
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private JTextField tfMedicalUnitName = new JTextField(25);
    private JTextField tfPatientName = new JTextField(25);
    private JTextField tfAdress = new JTextField(25);
    private JTextField tfDate = new JTextField(10);
    private JTextArea tfServices = new JTextArea(2, 30);
    private JTextField tfPrice = new JTextField(13);
    private JButton btnCancel = new StyledJButton("Anuleaza").getButton();

    private final JPanel prevPanel;
    private final Receipt receipt;

    public PanelShowReceipt(JPanel prevPanel, Receipt receipt) {
        setLayout(new BorderLayout());

        tfMedicalUnitName.setEditable(false);
        tfPatientName.setEditable(false);
        tfAdress.setEditable(false);
        tfDate.setEditable(false);
        tfServices.setEditable(false);
        tfPrice.setEditable(false);

        this.prevPanel = prevPanel;
        this.receipt = receipt;

        JPanel detailsPanel = new JPanel(new GridLayout(7, 1));
        detailsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel nameMUPanel = new JPanel(new FlowLayout());
        nameMUPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        nameMUPanel.add(new JLabel("Nume Policlinica:"));
        nameMUPanel.add(tfMedicalUnitName);

        JPanel namePanel = new JPanel(new FlowLayout());
        namePanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        namePanel.add(new JLabel("Nume Pacient:"));
        namePanel.add(tfPatientName);

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

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(btnCancel);

        detailsPanel.add(nameMUPanel);
        detailsPanel.add(namePanel);
        detailsPanel.add(adressPanel);
        detailsPanel.add(datePanel);
        detailsPanel.add(servicesPanel);
        detailsPanel.add(pricePanel);

        add(detailsPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.CENTER);

        loadData();
    }

    private void loadData() {
        tfMedicalUnitName.setText(receipt.getMedicalUnitName());
        tfPatientName.setText(receipt.getPatientName());
        tfPrice.setText(String.valueOf(receipt.getPrice()));
        tfServices.setText(receipt.getServices() == null ? "" : receipt.getServices().replace(",", ", "));
        tfAdress.setText(receipt.getAdress());
        tfDate.setText(DATE_FORMAT.format(receipt.getDate()));
    }

    public JPanel getPreviousPanel() {
        return prevPanel;
    }

    public void addCancelButtonListener(ActionListener actionListener) {
        btnCancel.addActionListener(actionListener);
    }
}
