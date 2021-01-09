package com.sanitas.clinicapp.hr;

import javax.swing.*;

public class HrMVC {

    public HrMVC(JFrame previousView) {
        HrModel model = new HrModel();
        HrView view = new HrView(model);
        HrController controller = new HrController(model, view,previousView);

        view.setVisible(true);
    }

}
