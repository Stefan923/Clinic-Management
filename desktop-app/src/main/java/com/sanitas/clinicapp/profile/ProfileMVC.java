package com.sanitas.clinicapp.profile;


import javax.swing.*;

public class ProfileMVC {

    public ProfileMVC(JFrame previousView) {
        ProfileModel model = new ProfileModel();
        ProfileView view = new ProfileView(model);
        ProfileController controller = new ProfileController(model, view, previousView);

        view.setVisible(true);
    }
}
