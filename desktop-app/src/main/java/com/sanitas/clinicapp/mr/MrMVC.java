package com.sanitas.clinicapp.mr;

import javax.swing.*;

public class MrMVC {

    public MrMVC(JFrame previousView) {
        MrModel model = new MrModel();
        MrView view = new MrView(model);
        MrController controller = new MrController(model, view, previousView);

        view.setVisible(true);
    }

}
