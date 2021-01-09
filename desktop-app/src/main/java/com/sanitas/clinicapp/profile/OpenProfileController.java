package com.sanitas.clinicapp.profile;

import com.sanitas.clinicapp.hr.Employee;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class OpenProfileController {

    private OpenProfileModel model;
    private OpenProfileView view;

    OpenProfileController(OpenProfileModel model,OpenProfileView view)
    {
        this.model=model;
        this.view=view;
    }

    class ButtonListenerViewEmp implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String cnp = view.getTxtCnp().getText();
            String lastname = view.getTxtLast().getText();
            String firstname = view.getTxtFirst().getText();
            String address = view.getTxtAddress().getText();
            String phoneNum = view.getTxtPhone().getText();
            String email = view.getTxtEmail().getText();
            String iban = view.getTxtIban().getText();
            String contractNum = view.getTxtContract().getText();
            String employmentDate = view.getTxtEmploymentDate().getText();
            String position = view.getTxtPosition().getText();
            String salary = view.getTxtSalary().getText();
            String workedHrs = view.getTxtWorkedHrs().getText();

            Employee employee = new Employee();

            employee = model.getEmployee(cnp, lastname,
                    firstname, address, phoneNum, email, iban, contractNum, employmentDate,position, salary, workedHrs);
                         System.out.println(employee);


        }
    }
}
