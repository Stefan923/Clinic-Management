package com.sanitas.clinicapp.fr;
import com.sanitas.clinicapp.ClinicApplication;
import com.sanitas.clinicapp.fr.panels.*;
import com.sanitas.clinicapp.struct.Transaction;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FrController {

    private final FrModel model;
    private final FrView view;

    private final ClinicApplication.Account account;

    public FrController(FrModel model, FrView view, JFrame previousView) {
        this.model = model;
        this.view = view;

        account = ClinicApplication.getUser();
        loadListeners(previousView);
    }

    private void loadListeners(JFrame previousView) {
        PanelMedicalUnitProfit panelMedicalUnitProfit = new PanelMedicalUnitProfit();
        panelMedicalUnitProfit.addUnitsNameComboBoxListener(new ComboBoxMUPListener());
        HashMap<String, String> medicalUnits = model.getMedicalUnits();
        panelMedicalUnitProfit.updateUnitName(medicalUnits);
        panelMedicalUnitProfit.updateIBAN(panelMedicalUnitProfit.getIbanMedicalUnit());
        panelMedicalUnitProfit.addProfitButtonListener(new ProfitByMedicalUnitButtonListener());

        PanelDoctorProfitTotal panelDoctorProfitTotal = new PanelDoctorProfitTotal();
        panelDoctorProfitTotal.addProfitButtonListener(new ViewButtonListener());

        PanelEmployeeSalary panelEmployeeSalary = new PanelEmployeeSalary();
        panelEmployeeSalary.addShowSalaryButtonListener(new EmployeeSalaryButtonListener());
        if(account.hasPermission("fr.read.employee") && !account.hasPermission("fr.read.all")){
            HashMap<String, String> employeeDetails = new HashMap<>();
            Optional<Map.Entry<String, String>> ent = model.getEmployeesName()
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().equalsIgnoreCase(account.getCnp()))
                    .findFirst();
            employeeDetails.put(ent.get().getKey(), ent.get().getValue());
            panelEmployeeSalary.updateEmployeesName(employeeDetails);
        }
        else if (account.hasPermission("fr.read.all")){
            panelEmployeeSalary.updateEmployeesName(model.getEmployeesName());
        }

        PanelDoctorSalary panelDoctorSalary = new PanelDoctorSalary();
        panelDoctorSalary.addShowSalaryButtonListener(new DoctorSalaryButtonListener());
        if(account.hasPermission("fr.read.doctor") && !account.hasPermission("fr.read.all")){
            HashMap<String, String> doctorDetails = new HashMap<>();
            Optional<Map.Entry<String, String>> ent = model.getDoctorsName()
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().equalsIgnoreCase(account.getCnp()))
                    .findFirst();
            doctorDetails.put(ent.get().getKey(), ent.get().getValue());
            panelDoctorSalary.updateDoctorsName(doctorDetails);
        }
        else if (account.hasPermission("fr.read.all")){
            panelDoctorSalary.updateDoctorsName(model.getDoctorsName());
        }

        PanelProfitByDoctor panelProfitByDoctor = new PanelProfitByDoctor();
        panelProfitByDoctor.addProfitButtonListener(new ProfitByDoctorButtonListener());
        if(account.hasPermission("fr.read.doctor") && !account.hasPermission("fr.read.all")){
            HashMap<String, String> doctorDetails2 = new HashMap<>();
            Optional<Map.Entry<String, String>> ent = model.getDoctorsName()
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().equalsIgnoreCase(account.getCnp()))
                    .findFirst();
            doctorDetails2.put(ent.get().getKey(), ent.get().getValue());
            panelProfitByDoctor.updateDoctorsNames(doctorDetails2);
        }
        else if (account.hasPermission("fr.read.all")){
            panelProfitByDoctor.updateDoctorsNames(model.getDoctorsName());
        }

        PanelProfitBySpeciality panelProfitBySpeciality = new PanelProfitBySpeciality();
        panelProfitBySpeciality.addProfitButtonListener(new ProfitBySpecialityButtonListener());
        panelProfitBySpeciality.updateCbSpeciality(model.getSpecialities());

        PanelTotalProfit panelTotalProfit = new PanelTotalProfit();
        panelTotalProfit.addProfitButtonListener(new TotalProfitButtonListener());

        PanelShowTransactions panelShowTransactions = new PanelShowTransactions();
        panelShowTransactions.addViewButtonListener(new TransactionViewButtonListener());

        view.addBtnMedicalUnitProfitListener(new FrController.MenuButtonListener(panelMedicalUnitProfit));
        view.addBtnProfitByDoctorListener(new FrController.MenuButtonListener(panelProfitByDoctor));
        view.addBtnProfitBySpecialityListener(new FrController.MenuButtonListener(panelProfitBySpeciality));
        view.addBtnTotalProfitListener(new FrController.MenuButtonListener(panelTotalProfit));
        view.addBtnEmployeeSalaryListener(new FrController.MenuButtonListener(panelEmployeeSalary));
        view.addBtnDoctorSalaryListener(new FrController.MenuButtonListener(panelDoctorSalary));
        view.addBtnDoctorProfitTotalListener(new FrController.MenuButtonListener(panelDoctorProfitTotal));
        view.addBtnShowTransactionsListener(new FrController.MenuButtonListener(panelShowTransactions));
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

                String unitIban = panelMUP.getIbanMedicalUnit();
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

                double salary = model.getDoctorSalary(panelDS.getDoctorsCNP(),
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

                double salary = model.getEmployeeSalary(panelES.getEmployeesCNP(),
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

                double profit = model.getProfitByDoctor(panelPBD.getDoctorsCNP(),
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

                double profit = model.getMedicalUnitProfit(panelMUP.getIbanMedicalUnit(),
                        panelMUP.getUtilDateModelMin().getValue(),
                        panelMUP.getUtilDateModelMax().getValue());

                panelMUP.getTfProfit().setText(String.valueOf(profit));
            }
        }
    }

    class TransactionViewButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel currentPanel = view.getCurrentPanel();

            if (!(currentPanel instanceof PanelShowTransactions)) {
                view.sendError("A avut loc o eroare.");
                return;
            }

            List<Transaction> transactions = model.getTransactions(((PanelShowTransactions) currentPanel).getUtilDateModelMin().getValue(),
                                                                    ((PanelShowTransactions) currentPanel).getUtilDateModelMax().getValue());

            PanelShowTransactions panelST = (PanelShowTransactions) currentPanel;
            panelST.updateTable(transactions);
            view.setRightPanel(panelST);
        }

    }

}
