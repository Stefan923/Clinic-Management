package com.sanitas.clinicapp.mr;

import com.sanitas.clinicapp.mr.panels.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MrController {

    MrModel model;
    MrView view;

    public MrController(MrModel model, MrView view, JFrame previousView) {
        this.model = model;
        this.view = view;

        loadListeners(previousView);
    }

    private void loadListeners(JFrame previousView) {
        PanelShowPatients panelShowPatients = view.getPanelShowPatients();
        panelShowPatients.addSearchButtonListener(new SearchButtonListener());
        panelShowPatients.addModifyButtonListener(new ModifyButtonListener());
        panelShowPatients.addDeleteButtonListener(new DeleteButtonListener());

        PanelSearchPatient panelSearchPatient = new PanelSearchPatient();
        panelSearchPatient.addSearchButtonListener(new SearchByCnpButtonListener(panelSearchPatient));

        PanelAddPatient panelAddPatient = new PanelAddPatient();
        panelAddPatient.addSaveButtonListener(new SaveButtonListener());
        panelAddPatient.addCancelButtonListener(new CancelButtonListener());

        view.addBtnShowPatientsListener(new MenuButtonListener(view.getBtnShowPatients(), panelShowPatients));
        view.addBtnSearchPatientListener(new MenuButtonListener(view.getBtnSearchPatient(), panelSearchPatient));
        view.addBtnAddPatientListener(new MenuButtonListener(view.getBtnAddPatient(), panelAddPatient));
        view.addBackButtonListener(new BackButtonListener(previousView));
    }

    class SearchButtonListener implements ActionListener {

        PanelShowPatients panel;

        public SearchButtonListener() {
            panel = view.getPanelShowPatients();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Patient> patients = model.getPatients(panel.getTfLastname().getText(),
                                                        panel.getTfFirstname().getText());
            panel.updateTable(patients);
        }

    }

    class ModifyButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JTable patientsTable = view.getPanelShowPatients().getJTable();

            if (patientsTable.getSelectedRows().length != 1) {
                view.sendError("Trebuie sa selectezi exact un pacient.");
            } else {
                int row = patientsTable.getSelectedRow();
                Patient patient = model.getPatient((String) patientsTable.getValueAt(row, 2));

                view.getPanelShowPatients().setVisible(false);
                PanelEditPatient panel = new PanelEditPatient(patient);
                panel.addSaveButtonListener(new SaveButtonListener());
                panel.addCancelButtonListener(new CancelButtonListener());
                view.setRightPanel(panel);
            }
            patientsTable.clearSelection();
        }

    }

    class DeleteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JTable patientsTable = view.getPanelShowPatients().getJTable();

            int[] indexes = patientsTable.getSelectedRows();
            if (indexes.length == 0) {
                view.sendError("Trebuie sa selectezi cel putin un pacient.");

                return;
            }

            for (int index : indexes) {
                model.deletePatient((String) patientsTable.getValueAt(index, 2));
            }

            view.getPanelShowPatients().updateTable(model.getPatients("", ""));
            view.sendSuccessMessage("Pacientii selectati au fost stersi.");
        }

    }

    class SaveButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel panel = view.getCurrentPanel();

            if (panel instanceof PanelEditPatient) {
                PanelEditPatient panelEditPatient = (PanelEditPatient) view.getCurrentPanel();
                boolean validation = model.updatePatient(panelEditPatient.getTfCnp().getText(),
                        panelEditPatient.getTfLastname().getText(),
                        panelEditPatient.getTfFirstname().getText(),
                        panelEditPatient.getTfIban().getText());

                if (validation) {
                    view.sendSuccessMessage("Datele pacientului au fost actualizate.");

                    view.getPanelShowPatients().updateTable(model.getPatients("", ""));
                } else {
                    view.sendError("Datele pacientului NU au putut fi actualizate.");
                }
            } else if (panel instanceof  PanelAddPatient) {
                PanelAddPatient panelAddPatient = (PanelAddPatient) view.getCurrentPanel();
                boolean validation = model.addPatient(panelAddPatient.getTfCnp().getText(),
                        panelAddPatient.getTfLastname().getText(),
                        panelAddPatient.getTfFirstname().getText(),
                        panelAddPatient.getTfIban().getText());

                if (validation) {
                    panelAddPatient.reset();
                    view.sendSuccessMessage("Pacientul a fost adaugat in baza de date.");

                    view.getPanelShowPatients().updateTable(model.getPatients("", ""));
                } else {
                    view.sendError("Deja exista in baza de date un pacient cu acest cnp.");
                }
            }
        }

    }

    class CancelButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel panel = view.getCurrentPanel();
            if (panel instanceof PanelEditPatient) {
                view.setRightPanel(view.getPanelShowPatients());
            } else {
                ((PanelAddPatient) panel).reset();
            }
        }

    }

    class SearchByCnpButtonListener implements ActionListener {

        PanelSearchPatient panel;

        public SearchByCnpButtonListener(PanelSearchPatient panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Patient patient = model.getPatient(panel.getTfCnp().getText());

            PanelViewPatient panelViewPatient = new PanelViewPatient(patient);
            panelViewPatient.addShowHistoryButtonListener(new PatientHistoryButtonListener(panelViewPatient));
            view.setRightPanel(panelViewPatient);
        }

    }

    class PatientHistoryButtonListener implements ActionListener {

        private PanelViewPatient panel;

        public PatientHistoryButtonListener(PanelViewPatient panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Report> reports = model.getReports(panel.getPatient().getCnp(), null, null);

            PanelShowReports panelShowReports = new PanelShowReports(panel.getPatient());
            panelShowReports.addBtnAddReportListener(new AddReportButtonListener(panelShowReports));
            panelShowReports.updateTable(reports);
            view.setRightPanel(panelShowReports);
        }

    }

    class AddReportButtonListener implements ActionListener {

        private PanelShowReports panel;

        public AddReportButtonListener(PanelShowReports panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            model.addReport(panel.getPatient().getCnp());

            List<Report> reports = model.getReports(panel.getPatient().getCnp(), null, null);
            panel.updateTable(reports);
            view.setRightPanel(panel);
        }

    }

    class MenuButtonListener implements ActionListener {
        private JPanel panel;

        public MenuButtonListener(JButton button, JPanel panel) {
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
