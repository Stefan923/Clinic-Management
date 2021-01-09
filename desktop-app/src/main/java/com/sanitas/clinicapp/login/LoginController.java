package com.sanitas.clinicapp.login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {

    private final LoginModel model;
    private final LoginView view;

    public LoginController(LoginModel model, LoginView view) {
        this.model = model;
        this.view = view;

        view.addBtnLoginListener(new ButtonListener());
    }

    class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField username = view.getTfUsername();
            JPasswordField password = view.getPfPassword();

            if (model.checkCreditentials(username.getText(), String.valueOf(password.getPassword()))) {
                model.loadUserData(username.getText());
                model.openHomePage(view);

                view.setVisible(false);
            } else {
                view.sendError("Numele sau parola sunt gresite!");
            }

            view.clear();
        }
    }

}
