package com.sanitas.clinicapp.fr;
import com.sanitas.clinicapp.fr.panels.*;
import com.sanitas.clinicapp.mr.MrController;
import com.sanitas.clinicapp.mr.Patient;
import com.sanitas.clinicapp.mr.panels.PanelEditPatient;
import com.sanitas.clinicapp.mr.panels.PanelShowPatients;
import com.sanitas.clinicapp.ui.Colors;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

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
        panelMedicalUnitProfit.addUnitsNameComboBoxListener(new ComboBoxMUPListener());
        HashMap<String, String> medicalUnits = model.getMedicalUnits();
        panelMedicalUnitProfit.updateUnitName(medicalUnits);
        panelMedicalUnitProfit.updateIBAN(medicalUnits.get(panelMedicalUnitProfit.getCbUnitsName().getSelectedItem()));

        PanelDoctorProfitTotal panelDoctorProfitTotal = new PanelDoctorProfitTotal();
        panelDoctorProfitTotal.addViewButtonListener(new ViewButtonListener());

        PanelEmployeeSalary panelEmployeeSalary = new PanelEmployeeSalary();
        PanelDoctorSalary panelDoctorSalary = new PanelDoctorSalary();
        PanelProfitByDoctor panelProfitByDoctor = new PanelProfitByDoctor();
        PanelProfitBySpeciality panelProfitBySpeciality = new PanelProfitBySpeciality();
        PanelTotalProfit panelTotalProfit = new PanelTotalProfit();

        view.addBtnMedicalUnitProfitListener(new FrController.MenuButtonListener(panelMedicalUnitProfit));
        view.addBtnProfitByDoctorListener(new FrController.MenuButtonListener(panelProfitByDoctor));
        view.addBtnProfitBySpecialityListener(new FrController.MenuButtonListener(panelProfitBySpeciality));
        view.addBtnTotalProfitListener(new FrController.MenuButtonListener(panelTotalProfit));
        view.addBtnEmployeeSalaryListener(new FrController.MenuButtonListener(panelEmployeeSalary));
        view.addBtnDoctorSalaryListener(new FrController.MenuButtonListener(panelDoctorSalary));
        view.addBtnDoctorProfitTotalListener(new FrController.MenuButtonListener(panelDoctorProfitTotal));
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

    class ComboBoxMUPListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel panel = view.getCurrentPanel();
            if(panel instanceof PanelMedicalUnitProfit){
                PanelMedicalUnitProfit panelMUP = (PanelMedicalUnitProfit) panel;

                String unitIban = panelMUP.getMedicalUnits().get(panelMUP.getCbUnitsName().getSelectedItem());
                panelMUP.getTfIBAN().setText(unitIban);
            }
        }

    }

    class ViewButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel panel = view.getCurrentPanel();
            if(panel instanceof PanelDoctorProfitTotal){
                PanelDoctorProfitTotal panelDPT = (PanelDoctorProfitTotal) panel;

                double profit = model.getDoctorProfitTotal(panelDPT.getTfCNP().getText(),
                                                            panelDPT.getUtilDateModelMin().getValue(),
                                                            panelDPT.getUtilDateModelMax().getValue());

                panelDPT.getTfProfit().setText(String.valueOf(profit));
            }
        }
    }


}
