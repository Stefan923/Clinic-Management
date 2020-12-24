package com.sanitas.clinicapp.fr;

import javax.swing.*;

    public class FrMVC {
        public FrMVC(JFrame previousView) {
            FrModel model = new FrModel();
            FrView view = new FrView(model);
            FrController controller = new FrController(model, view, previousView);

            view.setVisible(true);
        }
    }

