package com.sanitas.clinicapp.hr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class openViewController {

    private openViewModel model;
    private openViewView view;

    openViewController(openViewModel model,openViewView view)
    {
        this.model=model;
        this.view=view;

        view.addBtnSearchListener(new ButtonListenerViewEmp());
    }

    class ButtonListenerViewEmp implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String lastname = view.getTxtLast().getText();
            String firstname = view.getTxtFirst().getText();
            String position = view.getTxtPosition().getText();

            List<Employee> employeeList = new ArrayList<Employee>();

            employeeList = model.getAllData(lastname, firstname, position);
            if (employeeList.isEmpty())
                view.sendError("Angajatul nu poate fi gasit!");
            else {
                for (Employee i : employeeList)
                    System.out.println(i);
            }

        }
    }
}
