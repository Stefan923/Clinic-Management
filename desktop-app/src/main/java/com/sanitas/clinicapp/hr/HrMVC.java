package com.sanitas.clinicapp.hr;

import com.sanitas.clinicapp.ClinicApplication;

import javax.swing.*;

public class HrMVC {

    public HrMVC(JFrame previousView) {
        HrModel model = new HrModel();
        HrView view = new HrView(model,ClinicApplication.getUser());
        HrController controller = new HrController(model, view,previousView, ClinicApplication.getUser());

        view.setVisible(true);
    }

}
