package com.sanitas.clinicapp.hr;

public class HrMVC {

    public HrMVC() {
        HrModel model = new HrModel();
        HrView view = new HrView();
        HrController controller = new HrController(model, view);

        view.setVisible(true);
    }

}
