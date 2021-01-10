package com.sanitas.clinicapp.profile;

import com.sanitas.clinicapp.profile.panels.PanelEmployeeProfile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileController {

    private ProfileModel model;
    private ProfileView view;

    public ProfileController(ProfileModel model, ProfileView view, JFrame previousView) {
        this.model=model;
        this.view=view;

        loadListenersProfile(previousView);
    }

    private void loadListenersProfile(JFrame previousView) {
        PanelEmployeeProfile openView=view.getViewv();

        view.addBackListener(new ProfileController.BackButtonListenerProfile(previousView));
    }

    class MenuButtonListenerProfile implements ActionListener {
        private JPanel panel;

        public MenuButtonListenerProfile(JButton button, JPanel panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setRightPanel(panel);
        }

    }

    class BackButtonListenerProfile implements ActionListener {

        private final JFrame previousView;

        public BackButtonListenerProfile(JFrame previousView) {
            this.previousView = previousView;
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose();
            previousView.setVisible(true);
        }

    }

}
