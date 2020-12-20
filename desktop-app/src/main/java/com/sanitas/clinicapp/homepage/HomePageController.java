package com.sanitas.clinicapp.homepage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePageController {

    private HomePageModel model;
    private HomePageView view;

    public HomePageController(HomePageModel model, HomePageView view) {
        this.model = model;
        this.view = view;

        view.addBtnHRListener(new ButtonListener(1));
        view.addBtnFRListener(new ButtonListener(2));
        view.addBtnMRListener(new ButtonListener(3));
        view.addBtnProfileListener(new ButtonListener(4));
    }

    class ButtonListener implements ActionListener {

        int number = 1; // default = 1

        public ButtonListener(int number) {
            this.number = number;
        }

        public void actionPerformed(ActionEvent e) {
            view.setVisible(false);
            model.openMVC(number);
        }

    }

}
