package com.sanitas.clinicapp.homepage;

import javax.swing.*;

public class HomePageMVC {

    public HomePageMVC(JFrame previousView) {
        HomePageModel model = new HomePageModel();
        HomePageView view = new HomePageView();
        HomePageController controller = new HomePageController(model, view, previousView);

        view.setVisible(true);
    }

}
