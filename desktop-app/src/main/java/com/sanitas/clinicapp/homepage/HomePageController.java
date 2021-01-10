package com.sanitas.clinicapp.homepage;

import com.sanitas.clinicapp.ClinicApplication;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePageController {

    private final HomePageModel model;
    private final HomePageView view;

    private final ClinicApplication.Account account;

    public HomePageController(HomePageModel model, HomePageView view, JFrame previousView) {
        this.model = model;
        this.view = view;

        account = ClinicApplication.getUser();

        view.addBtnHRListener(new ButtonListener(1, "hr.read"));
        view.addBtnFRListener(new ButtonListener(2, "fr.read"));
        view.addBtnMRListener(new ButtonListener(3, "mr.read"));
        view.addBtnProfileListener(new ButtonListener(4, "profile.read"));
        view.addBtnDisconnectListener(new ButtonDisconnectListener(previousView));
    }

    class ButtonListener implements ActionListener {

        private int number = 1; // default = 1
        private final String permission;

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

    class ButtonDisconnectListener implements ActionListener {

        private final JFrame previousView;

        public ButtonDisconnectListener(JFrame previousView) {
            this.previousView = previousView;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ClinicApplication.disconnectUser();
            previousView.setVisible(true);
            view.setVisible(false);
            view.dispose();
        }
    }

}
