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
        panelMedicalUnitProfit.addProfitButtonListener(new ProfitByMedicalUnitButtonListener());

        PanelDoctorProfitTotal panelDoctorProfitTotal = new PanelDoctorProfitTotal();
        panelDoctorProfitTotal.addProfitButtonListener(new ViewButtonListener());

        PanelEmployeeSalary panelEmployeeSalary = new PanelEmployeeSalary();
        panelEmployeeSalary.addShowSalaryButtonListener(new EmployeeSalaryButtonListener());

        PanelDoctorSalary panelDoctorSalary = new PanelDoctorSalary();
        panelDoctorSalary.addShowSalaryButtonListener(new DoctorSalaryButtonListener());

        PanelProfitByDoctor panelProfitByDoctor = new PanelProfitByDoctor();
        panelProfitByDoctor.addProfitButtonListener(new ProfitByDoctorButtonListener());

        PanelProfitBySpeciality panelProfitBySpeciality = new PanelProfitBySpeciality();
        panelProfitBySpeciality.addProfitButtonListener(new ProfitBySpecialityButtonListener());
        panelProfitBySpeciality.updateCbSpeciality(model.getSpecialities());

        PanelTotalProfit panelTotalProfit = new PanelTotalProfit();
        panelTotalProfit.addProfitButtonListener(new TotalProfitButtonListener());

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
            if (panel instanceof PanelMedicalUnitProfit) {
                PanelMedicalUnitProfit panelMUP = (PanelMedicalUnitProfit) panel;

                String unitIban = panelMUP.getMedicalUnits().get(panelMUP.getCbUnitsName().getSelectedItem());
                panelMUP.getTfIBAN().setText(unitIban);
            }
        }

    }

    class ViewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel panel = view.getCurrentPanel();
            if (panel instanceof PanelDoctorProfitTotal) {
                PanelDoctorProfitTotal panelDPT = (PanelDoctorProfitTotal) panel;

                double profit = model.getDoctorProfitTotal(panelDPT.getTfCNP().getText(),
                        panelDPT.getUtilDateModelMin().getValue(),
                        panelDPT.getUtilDateModelMax().getValue());

                panelDPT.getTfProfit().setText(String.valueOf(profit));
            }
        }
    }

    class DoctorSalaryButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel panel = view.getCurrentPanel();
            if (panel instanceof PanelDoctorSalary) {
                PanelDoctorSalary panelDS = (PanelDoctorSalary) panel;

                double salary = model.getDoctorSalary(panelDS.getTfCNP().getText(),
                        panelDS.getUtilDateModelMin().getValue(),
                        panelDS.getUtilDateModelMax().getValue());

                panelDS.getTfSalary().setText(String.valueOf(salary));
            }
        }
    }

    class EmployeeSalaryButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel panel = view.getCurrentPanel();
            if (panel instanceof PanelEmployeeSalary) {
                PanelEmployeeSalary panelES = (PanelEmployeeSalary) panel;

                double salary = model.getEmployeeSalary(panelES.getTfCNP().getText(),
                        panelES.getUtilDateModelMin().getValue(),
                        panelES.getUtilDateModelMax().getValue());

                panelES.getTfSalary().setText(String.valueOf(salary));
            }
        }
    }

    class ProfitByDoctorButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel panel = view.getCurrentPanel();
            if (panel instanceof PanelProfitByDoctor) {
                PanelProfitByDoctor panelPBD = (PanelProfitByDoctor) panel;

                double profit = model.getProfitByDoctor(panelPBD.getTfCNP().getText(),
                        panelPBD.getUtilDateModelMin().getValue(),
                        panelPBD.getUtilDateModelMax().getValue());

                panelPBD.getTfProfit().setText(String.valueOf(profit));
            }
        }
    }

    class TotalProfitButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel panel = view.getCurrentPanel();
            if (panel instanceof PanelTotalProfit) {
                PanelTotalProfit panelTP = (PanelTotalProfit) panel;

                double profit = model.getTotalProfit(panelTP.getUtilDateModelMin().getValue(),
                                                        panelTP.getUtilDateModelMax().getValue());

                panelTP.getTfProfit().setText(String.valueOf(profit));
            }
        }
    }

    class ProfitBySpecialityButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel panel = view.getCurrentPanel();
            if (panel instanceof PanelProfitBySpeciality) {
                PanelProfitBySpeciality panelPBS = (PanelProfitBySpeciality) panel;

                double profit = model.getProfitBySpeciality(panelPBS.getIdSpeciality(),
                                                            panelPBS.getUtilDateModelMin().getValue(),
                                                            panelPBS.getUtilDateModelMax().getValue());

                panelPBS.getTfProfit().setText(String.valueOf(profit));
            }
        }
    }

    class ProfitByMedicalUnitButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel panel = view.getCurrentPanel();
            if (panel instanceof PanelMedicalUnitProfit) {
                PanelMedicalUnitProfit panelMUP = (PanelMedicalUnitProfit) panel;

                double profit = model.getMedicalUnitProfit(Integer.parseInt(panelMUP.getIdMedicalUnit()),
                        panelMUP.getUtilDateModelMin().getValue(),
                        panelMUP.getUtilDateModelMax().getValue());

                panelMUP.getTfProfit().setText(String.valueOf(profit));
            }
        }
    }

}
