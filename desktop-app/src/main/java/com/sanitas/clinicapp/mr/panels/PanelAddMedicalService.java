package com.sanitas.clinicapp.mr.panels;

import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Optional;

public class PanelAddMedicalService extends JPanel {

    private JTextField tfCnp = new JTextField(15);
    private JTextField tfName = new JTextField(15);
    private JComboBox<String> cbSpeciality = new JComboBox<>();
    private JComboBox<String> cbAccreditation = new JComboBox<>();
    private JComboBox<String> cbEquipment = new JComboBox<>();
    private JTextField tfPrice = new JTextField(15);
    private JTextField tfDuration = new JTextField(15);

    private JButton btnSave = new StyledJButton("Salveaza").getButton();
    private JButton btnCancel = new StyledJButton("Anuleaza").getButton();

    private String cnpDoctor;

    Map<Integer, String> specialities;
    Map<Integer, String> accreditations;
    Map<Integer, String> equipments;

    public PanelAddMedicalService() {
        this(null);
    }

    public PanelAddMedicalService(String cnpDoctor) {
        this.cnpDoctor = cnpDoctor;

        if (cnpDoctor != null) {
            tfCnp.setEditable(false);
            tfCnp.setText(cnpDoctor);
        }

        setLayout(new BorderLayout());

        JPanel cnpPanel = new JPanel(new FlowLayout());
        cnpPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        cnpPanel.add(new JLabel("CNP:"));
        cnpPanel.add(tfCnp);

        JPanel namePanel = new JPanel(new FlowLayout());
        namePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        namePanel.add(new JLabel("Nume:"));
        namePanel.add(tfName);

        JPanel specialityPanel = new JPanel(new FlowLayout());
        specialityPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        specialityPanel.add(new JLabel("Specialitate:"));
        specialityPanel.add(cbSpeciality);

        JPanel accreditationPanel = new JPanel(new FlowLayout());
        accreditationPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        accreditationPanel.add(new JLabel("Acreditare:"));
        accreditationPanel.add(cbAccreditation);

        JPanel equipmentPanel = new JPanel(new FlowLayout());
        equipmentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        equipmentPanel.add(new JLabel("Echipament:"));
        equipmentPanel.add(cbEquipment);

        JPanel durationPanel = new JPanel(new FlowLayout());
        durationPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        durationPanel.add(new JLabel("Durata:"));
        durationPanel.add(tfDuration);

        JPanel pricePanel = new JPanel(new FlowLayout());
        pricePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        pricePanel.add(new JLabel("Pret:"));
        pricePanel.add(tfPrice);

        JPanel firstLinePanel = new JPanel(new GridLayout(1, 2));
        firstLinePanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        firstLinePanel.add(cnpPanel);
        firstLinePanel.add(namePanel);

        JPanel secondLinePanel = new JPanel(new GridLayout(1, 3));
        secondLinePanel.add(durationPanel);
        secondLinePanel.add(pricePanel);

        JPanel thirdLinePanel = new JPanel(new GridLayout(1, 2));
        thirdLinePanel.add(specialityPanel);
        thirdLinePanel.add(accreditationPanel);

        JPanel fourthLinePanel = new JPanel(new GridLayout(1, 1));
        fourthLinePanel.add(equipmentPanel);

        JPanel serviceData = new JPanel(new GridLayout(4, 1));
        serviceData.add(firstLinePanel);
        serviceData.add(secondLinePanel);
        serviceData.add(thirdLinePanel);
        serviceData.add(fourthLinePanel);

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(btnSave);
        buttonsPanel.add(btnCancel);

        add(serviceData, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);

        setVisible(false);
    }

    public void updateCbSpeciality(Map<Integer, String> specialities) {
        this.specialities = specialities;

        cbSpeciality.removeAllItems();
        specialities.values().forEach(speciality -> cbSpeciality.addItem(speciality));
    }

    public void updateCbAccreditation(Map<Integer, String> accreditations) {
        this.accreditations = accreditations;

        cbAccreditation.removeAllItems();
        cbAccreditation.addItem("-");
        accreditations.values().forEach(accreditation -> cbAccreditation.addItem(accreditation));
    }

    public void updateCbEquipment(Map<Integer, String> equipments) {
        this.equipments = equipments;

        cbEquipment.removeAllItems();
        cbEquipment.addItem("-");
        equipments.values().forEach(equipment -> cbEquipment.addItem(equipment));
    }

    public JTextField getTfCnp() {
        return tfCnp;
    }

    public JTextField getTfName() {
        return tfName;
    }

    public JComboBox<String> getCbSpeciality() {
        return cbSpeciality;
    }

    public JComboBox<String> getCbAccreditation() {
        return cbAccreditation;
    }

    public JComboBox<String> getCbEquipment() {
        return cbEquipment;
    }

    public JTextField getTfDuration() {
        return tfDuration;
    }

    public JTextField getTfPrice() {
        return tfPrice;
    }

    public int getIdSpeciality() {
        Optional<Map.Entry<Integer, String>> result = specialities
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().equalsIgnoreCase((String) cbSpeciality.getSelectedItem()))
                .findFirst();
        return result.map(Map.Entry::getKey).orElse(1);
    }

    public Integer getIdAccreditation() {
        Optional<Map.Entry<Integer, String>> result = accreditations
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().equalsIgnoreCase((String) cbAccreditation.getSelectedItem()))
                .findFirst();
        return result.map(Map.Entry::getKey).orElse(null);
    }

    public Integer getIdEquipment() {
        Optional<Map.Entry<Integer, String>> result = equipments
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().equalsIgnoreCase((String) cbEquipment.getSelectedItem()))
                .findFirst();
        return result.map(Map.Entry::getKey).orElse(null);
    }

    public void reset() {
        tfCnp.setText("");
        tfName.setText("");
        tfDuration.setText("");
        tfPrice.setText("");
    }

    public void addSaveButtonListener(ActionListener actionListener) {
        btnSave.addActionListener(actionListener);
    }

    public void addCancelButtonListener(ActionListener actionListener) {
        btnCancel.addActionListener(actionListener);
    }

}
