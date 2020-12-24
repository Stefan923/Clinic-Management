package com.sanitas.clinicapp.fr;
import com.sanitas.clinicapp.fr.panels.PanelMedicalUnitProfit;
import com.sanitas.clinicapp.mr.MrController;
import com.sanitas.clinicapp.mr.Patient;
import com.sanitas.clinicapp.mr.panels.PanelEditPatient;
import com.sanitas.clinicapp.mr.panels.PanelShowPatients;
import com.sanitas.clinicapp.ui.Colors;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrController {

    FrModel model;
    FrView view;

    public FrController(FrModel model, FrView view, JFrame previousView) {
        this.model = model;
        this.view = view;

        loadListeners(previousView);
    }
    private void loadListeners(JFrame previousView) {
        PanelMedicalUnitProfit panelMedicalUnitProfit = new PanelMedicalUnitProfit();

        view.addBtnMedicalUnitProfitListener(new FrController.MenuButtonListener(panelMedicalUnitProfit));
        view.addBtnProfitByDoctorListener(new FrController.MenuButtonListener(panelMedicalUnitProfit));
        view.addBtnProfitBySpecialityListener(new FrController.MenuButtonListener(panelMedicalUnitProfit));
        view.addBtnTotalProfitListener(new FrController.MenuButtonListener(panelMedicalUnitProfit));
        view.addBtnEmployeeSalaryListener(new FrController.MenuButtonListener(panelMedicalUnitProfit));
        view.addBtnDoctorSalaryListener(new FrController.MenuButtonListener(panelMedicalUnitProfit));
        view.addBtnDoctorProfitTotalListener(new FrController.MenuButtonListener(panelMedicalUnitProfit));
        view.addBackButtonListener(new FrController.BackButtonListener(previousView));
    }

    class MenuButtonListener implements ActionListener {
        private JPanel panel;

        public MenuButtonListener(JPanel panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setRightPanel(panel);
        }

    }

    class BackButtonListener implements ActionListener {

        private final JFrame previousView;

        public BackButtonListener(JFrame jFrame) {
            this.previousView = jFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose();
            previousView.setVisible(true);
        }

    }
}
