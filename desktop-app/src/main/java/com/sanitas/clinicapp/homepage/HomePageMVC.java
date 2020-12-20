package com.sanitas.clinicapp.homepage;

public class HomePageMVC {

    public HomePageMVC() {
        HomePageModel model = new HomePageModel();
        HomePageView view = new HomePageView();
        HomePageController controller = new HomePageController(model, view);

        view.setVisible(true);
    }

}
