package com.sanitas.clinicapp.hr;

public class openViewMVC {

    public openViewMVC() {
        openViewModel model=new openViewModel();
        openViewView view=new openViewView(model);
        openViewController controller=new openViewController(model,view);

        view.setVisible(true);
    }

}
