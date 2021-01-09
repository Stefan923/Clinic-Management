package com.sanitas.clinicapp.profile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileController {

    private ProfileModel model;
    private ProfileView view;
    private JFrame previousView;

    public ProfileController(ProfileModel model, ProfileView view, JFrame previousView) {
        this.model=model;
        this.view=view;
        this.previousView=previousView;
    }

    class ButtonListenerViewHr implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setViewvVisible();
        }
    }
}
