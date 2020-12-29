package com.sanitas.clinicapp.mr;

import com.sanitas.clinicapp.ClinicApplication;
import com.sanitas.clinicapp.mr.panels.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MrController {

    private MrModel model;
    private MrView view;

    private PanelShowMedicalServices gPanelSMS;

    private String cnp;

    public MrController(MrModel model, MrView view, JFrame previousView) {
        ClinicApplication.Account account = ClinicApplication.getUser();
        cnp = (account.hasPermission("mr.medical_services.read.others") ? "" : account.getCnp());

        this.model = model;
        this.view = view;

        loadListeners(previousView);
    }

    private void loadListeners(JFrame previousView) {
        PanelShowPatients panelShowPatients = view.getPanelShowPatients();
        panelShowPatients.addSearchButtonListener(new SearchButtonListener());
        panelShowPatients.addModifyButtonListener(new ModifyButtonListener());
        panelShowPatients.addDeleteButtonListener(new PatientDeleteButtonListener());

        PanelSearchPatient panelSearchPatient = new PanelSearchPatient();
        panelSearchPatient.addSearchButtonListener(new SearchByCnpButtonListener(panelSearchPatient));

        PanelAddPatient panelAddPatient = new PanelAddPatient();
        panelAddPatient.addSaveButtonListener(new SaveButtonListener());
        panelAddPatient.addCancelButtonListener(new CancelButtonListener());

        gPanelSMS = new PanelShowMedicalServices();
        gPanelSMS.updateTable(model.getMedicalServices(cnp));
        gPanelSMS.addBtnAddListener(new ServiceAddButtonListener());
        gPanelSMS.addBtnDeleteListener(new ServiceDeleteButtonListener(gPanelSMS));
        gPanelSMS.addBtnViewListener(new ServiceViewButtonListener());

        view.addBtnShowPatientsListener(new MenuButtonListener(panelShowPatients));
        view.addBtnSearchPatientListener(new MenuButtonListener(panelSearchPatient));
        view.addBtnAddPatientListener(new MenuButtonListener(panelAddPatient));
        view.addBtnMedicalServicesListener(new MenuButtonListener(gPanelSMS));
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

    class PatientDeleteButtonListener implements ActionListener {

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

    class ServiceDeleteButtonListener implements ActionListener {

        private PanelShowMedicalServices panel;

        public ServiceDeleteButtonListener(PanelShowMedicalServices panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JTable servicesTable = panel.getServicesTable();

            int[] indexes = servicesTable.getSelectedRows();
            if (indexes.length == 0) {
                view.sendError("Trebuie sa selectezi cel putin un serviciu.");
                return;
            }

            for (int index : indexes) {
                model.deleteMedicalService((int) panel.getMedicalServices().get(index).getId());
            }

            panel.updateTable(model.getMedicalServices(cnp));
            view.sendSuccessMessage("Serviciile selectate au fost sterse.");
        }

    }

    class ServiceAddButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            PanelAddMedicalService panel = new PanelAddMedicalService(cnp);
            panel.updateCbSpeciality(model.getSpecialities(cnp));
            panel.updateCbAccreditation(model.getAccreditations(cnp));
            panel.updateCbEquipment(model.getEquipments(cnp));
            panel.addSaveButtonListener(new SaveButtonListener());
            panel.addCancelButtonListener(new CancelButtonListener());
            view.setRightPanel(panel);
        }

    }

    class ServiceViewButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JTable servicesTable = gPanelSMS.getServicesTable();

            if (servicesTable.getSelectedRows().length != 1) {
                view.sendError("Trebuie sa selectezi exact un serviciu.");
            } else {
                int row = servicesTable.getSelectedRow();
                MedicalService medicalService = gPanelSMS.getMedicalServices().get(row);

                PanelViewMedicalService panel = new PanelViewMedicalService(cnp);
                panel.updateCbSpeciality(model.getSpecialities(cnp));
                panel.updateCbAccreditation(model.getAccreditations(cnp));
                panel.updateCbEquipment(model.getEquipments(cnp));
                panel.addSaveButtonListener(new SaveButtonListener());
                panel.addCancelButtonListener(new CancelButtonListener());
                panel.loadMedicalServiceData(medicalService);
                view.setRightPanel(panel);
            }
            servicesTable.clearSelection();
        }

    }

    class ReportViewButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel currentPanel = view.getCurrentPanel();

            if (!(currentPanel instanceof PanelShowReports)) {
                view.sendError("A avut loc o eroare.");
                return;
            }

            PanelShowReports panelSR = (PanelShowReports) currentPanel;
            JTable reportsTable = panelSR.getReportsTable();

            if (reportsTable.getSelectedRows().length != 1) {
                view.sendError("Trebuie sa selectezi exact un raport medical.");
            } else {
                int row = reportsTable.getSelectedRow();
                Report report = panelSR.getReports().get(row);

                PanelViewReport panel = new PanelViewReport(panelSR);
                panel.addSaveButtonListener(new SaveButtonListener());
                panel.addConfirmButtonListener(new ReportConfirmButtonListener());
                panel.addCancelButtonListener(new CancelButtonListener());
                panel.loadReportData(report);
                view.setRightPanel(panel);
            }
            reportsTable.clearSelection();
        }

    }

    class ReportConfirmButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel currentPanel = view.getCurrentPanel();

            if (!(currentPanel instanceof PanelViewReport)) {
                view.sendError("A avut loc o eroare.");
                return;
            }

            PanelViewReport panelVR = (PanelViewReport) currentPanel;

            Report report = panelVR.getReport();
            boolean validation = model
                    .confirmReport(report, model.getDoctor(cnp).getSealCode());

            if (validation) {
                view.sendSuccessMessage("Raportul medical a fost parafat cu succes.");

                PanelShowReports panelSR = panelVR.getPreviousPanel();
                panelSR.updateTable(model.getReports(panelSR.getPatient().getCnp(), null, null));
                panelVR.loadReportData(model.getReport(report.getId()));
            } else {
                view.sendError("Nu s-a putut parafa raportul medical.");
            }
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
                    view.sendSuccessMessage("Pacientul a fost adaugat cu succes.");

                    view.getPanelShowPatients().updateTable(model.getPatients("", ""));
                } else {
                    view.sendError("Deja exista in baza de date un pacient cu acest cnp.");
                }
            } else if (panel instanceof PanelAddMedicalService) {
                PanelAddMedicalService panelAMS = (PanelAddMedicalService) panel;

                boolean validation = model.addMedicalService(new MedicalService(panelAMS.getTfCnp().getText(),
                                                        panelAMS.getTfName().getText(),
                                                        panelAMS.getIdSpeciality(),
                                                        panelAMS.getIdAccreditation(),
                                                        panelAMS.getIdEquipment(),
                                                        Double.parseDouble(panelAMS.getTfPrice().getText()),
                                                        Integer.parseInt(panelAMS.getTfDuration().getText())));

                if (validation) {
                    panelAMS.reset();
                    view.sendSuccessMessage("Serviciul a fost adaugat cu succes.");

                    gPanelSMS.updateTable(model.getMedicalServices(cnp));
                } else {
                    view.sendError("Nu s-a putut adauga serviciul.");
                }
            } else if (panel instanceof PanelViewMedicalService) {
                PanelViewMedicalService panelVMS = (PanelViewMedicalService) panel;

                MedicalService medicalService = new MedicalService(panelVMS.getTfCnp().getText(),
                            panelVMS.getTfName().getText(),
                            panelVMS.getIdSpeciality(),
                            panelVMS.getIdAccreditation(),
                            panelVMS.getIdEquipment(),
                            Double.parseDouble(panelVMS.getTfPrice().getText()),
                            Integer.parseInt(panelVMS.getTfDuration().getText()));
                medicalService.setId(panelVMS.getIdMedicalService());
                boolean validation = model.saveMedicalService(medicalService);

                if (validation) {
                    view.sendSuccessMessage("Modificarile au fost salvate cu succes.");

                    gPanelSMS.updateTable(model.getMedicalServices(cnp));
                } else {
                    view.sendError("Nu s-a putut salva serviciul.");
                }
            } else if (panel instanceof PanelViewReport) {
                PanelViewReport panelVR = (PanelViewReport) panel;

                Report report = panelVR.getReport();
                report.setDiagnostic(panelVR.getTfDiagnostic().getText());
                report.setRecommendation(panelVR.getTfRecommendation().getText());
                boolean validation = model.saveReport(report);

                if (validation) {
                    view.sendSuccessMessage("Modificarile au fost salvate cu succes.");

                    gPanelSMS.updateTable(model.getMedicalServices(cnp));
                } else {
                    view.sendError("Nu s-a putut salva raportul medical.");
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
            } else if (panel instanceof PanelAddPatient) {
                ((PanelAddPatient) panel).reset();
            } else if (panel instanceof PanelAddMedicalService) {
                ((PanelAddMedicalService) panel).reset();
                view.setRightPanel(gPanelSMS);
            } else if (panel instanceof PanelViewMedicalService) {
                view.setRightPanel(gPanelSMS);
            } else if (panel instanceof PanelViewReport) {
                view.setRightPanel(((PanelViewReport) panel).getPreviousPanel());
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

            PanelShowReports panelSR = new PanelShowReports(panel.getPatient());
            panelSR.addBtnViewReportListener(new ReportViewButtonListener());
            panelSR.addBtnAddReportListener(new AddReportButtonListener(panelSR));
            panelSR.updateTable(reports);
            view.setRightPanel(panelSR);
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
