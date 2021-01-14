package com.sanitas.clinicapp.mr;

import com.sanitas.clinicapp.ClinicApplication;

import javax.swing.*;

public class MrMVC {

    public MrMVC(JFrame previousView) {
        MrModel model = new MrModel();
        MrView view = new MrView(model, ClinicApplication.getUser());
        MrController controller = new MrController(model, view, previousView);

        view.setVisible(true);
    }

}
