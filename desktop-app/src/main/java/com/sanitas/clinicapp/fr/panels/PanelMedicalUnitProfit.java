package com.sanitas.clinicapp.fr.panels;

import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Map;

public class PanelMedicalUnitProfit extends JPanel {
    private JButton btnSearch = new StyledJButton("Cauta").getButton();
    private JButton btnViewProfit = new StyledJButton("Afiseaza").getButton();
    private JTextField unitName= new JTextField(15);
    private JComboBox<String> cbIBAN = new JComboBox<>();

    private Map<String, String> medicalUnits;

    public PanelMedicalUnitProfit(){
        setLayout(new BorderLayout());

        JPanel IBANPanel = new JPanel(new FlowLayout());
        IBANPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        IBANPanel.add(new JLabel("Nume Unitate:"));
        IBANPanel.add(cbIBAN);
        IBANPanel.add(new JLabel("Cont Unitate:"));
        IBANPanel.add(unitName);

        add(IBANPanel,BorderLayout.NORTH);

    }
    public void updateIBAN(Map<String, String> ibanList) {

        medicalUnits=ibanList;
        cbIBAN.removeAllItems();
        ibanList.values().forEach(name -> cbIBAN.addItem(name));
    }


}
