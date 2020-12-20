package com.sanitas.clinicapp.login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {

    private LoginModel model;
    private LoginView view;

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
                model.openHomePage();

                view.setVisible(false);
            } else {
                username.setText("");
                password.setText("");
            }
        }
    }

}
