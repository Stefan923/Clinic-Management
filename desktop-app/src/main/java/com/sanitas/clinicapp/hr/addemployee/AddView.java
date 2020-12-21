package com.sanitas.clinicapp.hr.addemployee;

import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class AddView extends JFrame {

    private JTextField[] txt=new JTextField[12];
    private JButton add=new StyledJButton("Adaugare").getButton();

    public AddView(){
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
        this.setPreferredSize(new Dimension(400, 300));
        contentPane.setBorder(new EmptyBorder(20,20,20,20));

        JPanel addPanel=new JPanel(new BorderLayout());
        addPanel.add(contentPane,BorderLayout.CENTER);
        addPanel.add(add,BorderLayout.SOUTH);

        this.setContentPane(addPanel);
        this.pack();

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dimension.width / 2 - this.getSize().width / 2,
                dimension.height / 2 - this.getSize().height / 2);

        this.setTitle("Sanitas");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

    public void sendError(String message) {
        JOptionPane.showMessageDialog(this, message, "Eroare", 0);
    }
}
