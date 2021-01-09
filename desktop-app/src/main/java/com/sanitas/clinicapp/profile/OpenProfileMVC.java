package com.sanitas.clinicapp.profile;

public class OpenProfileMVC {

    public OpenProfileMVC() {
        OpenProfileModel model= new OpenProfileModel();
        OpenProfileView view=new OpenProfileView(model);
        OpenProfileController controller=new OpenProfileController(model,view);

        view.setVisible(true);
    }
}
