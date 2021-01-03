package com.sanitas.clinicapp.hr.panels;

import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelAddEmployee extends JPanel {

    private JTextField[] txt=new JTextField[12];
    private JButton add=new StyledJButton("Adaugare").getButton();

    public PanelAddEmployee(){
        String[] labels = {"CNP: ", "Nume: ", "Prenume: ", "Adresa: ","Telefon: ","E-mail: ","Iban: ","Nr. Contract: ",
                "Data angajare: ","Functie: ","Salar: ","Ore negociate: "};
        int numPairs = labels.length;
        GridLayout layout=new GridLayout(numPairs,2);
        JPanel contentPane=new JPanel(layout);
        for(int i=0;i<numPairs;i++) {
            contentPane.add(new JLabel(labels[i]));
            txt[i]=new JTextField(10);
            contentPane.add(txt[i]);
        }

        contentPane.setBorder(new EmptyBorder(20,20,20,20));

        JPanel addPanel=new JPanel(new FlowLayout());
        addPanel.add(add);

        add(contentPane,BorderLayout.CENTER);
        add(addPanel,BorderLayout.SOUTH);
    }

    public void addBtnAddEmployeeListener(ActionListener actionListener) {
        add.addActionListener(actionListener);
    }

    public String getTxt(int i) {
        return txt[i].getText();
    }

    public void setTxtEmpty() {
        for(int i=0;i<12;i++)
            txt[i].setText("");
    }

}
