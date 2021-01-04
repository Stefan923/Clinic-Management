package com.sanitas.clinicapp.homepage;

import com.sanitas.clinicapp.ClinicApplication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePageController {

    private HomePageModel model;
    private HomePageView view;

    private ClinicApplication.Account account;

    public HomePageController(HomePageModel model, HomePageView view) {
        this.model = model;
        this.view = view;

        account = ClinicApplication.getUser();

        view.addBtnHRListener(new ButtonListener(1, "hr.read"));
        view.addBtnFRListener(new ButtonListener(2, "fr.read"));
        view.addBtnMRListener(new ButtonListener(3, "mr.read"));
        view.addBtnProfileListener(new ButtonListener(4, "profile.read"));
    }

    class ButtonListener implements ActionListener {

        private int number = 1; // default = 1
        private String permission;

        public ButtonListener(int number, String permission) {
            this.number = number;
            this.permission = permission;
        }

        public void actionPerformed(ActionEvent e) {
            if (!account.hasPermission(permission)) {
                view.sendError("Nu ai permisiunea de a vedea aceasta categorie!");
                return;
            }

            view.setVisible(false);
            model.openMVC(number, view);
        }

    }

}
