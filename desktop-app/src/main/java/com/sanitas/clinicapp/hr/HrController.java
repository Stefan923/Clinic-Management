package com.sanitas.clinicapp.hr;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HrController {

    private HrModel model;
    private HrView view;

    public HrController(HrModel model, HrView view) {
        this.model=model;
        this.view=view;

        view.addBtnSearchEmployeeListener(new ButtonListenerViewHr());
    }

    class ButtonListenerViewHr implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setViewvVisible();
        }
    }
}
