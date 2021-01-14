package com.sanitas.clinicapp.fr;

import com.sanitas.clinicapp.ClinicApplication;

import javax.swing.*;

    public class FrMVC {
        public FrMVC(JFrame previousView) {
            FrModel model = new FrModel();
            FrView view = new FrView(model, ClinicApplication.getUser());
            FrController controller = new FrController(model, view, previousView);

            view.setVisible(true);
        }
    }

