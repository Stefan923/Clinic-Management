package com.sanitas.clinicapp.mr;

import com.sanitas.clinicapp.ClinicApplication;
import com.sanitas.clinicapp.mr.panels.*;
import com.sanitas.clinicapp.struct.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class MrController {

    private final MrModel model;
    private final MrView view;

    private PanelShowMedicalServices gPanelSMS;

    private final ClinicApplication.Account account;
    private String cnp;

    public MrController(MrModel model, MrView view, JFrame previousView) {
        account = ClinicApplication.getUser();
        cnp = (account.hasPermission("mr.medical_services.read.others") ? "" : account.getCnp());

        this.model = model;
        this.view = view;

        loadListeners(previousView);
    }

    private void loadListeners(JFrame previousView) {
        PanelAddPatient panelAddPatient = new PanelAddPatient();
        panelAddPatient.addSaveButtonListener(new SaveButtonListener());
        panelAddPatient.addCancelButtonListener(new CancelButtonListener());

        PanelShowPatients panelShowPatients = view.getPanelShowPatients();
        panelShowPatients.addSearchButtonListener(new PatientSearchButtonListener());
        panelShowPatients.addAddPatientButtonListener(new MenuButtonListener(panelAddPatient));
        panelShowPatients.addViewPatientButtonListener(new PatientViewButtonListener());
        panelShowPatients.addModifyButtonListener(new PatientModifyButtonListener());
        panelShowPatients.addDeleteButtonListener(new PatientDeleteButtonListener());

        PanelSearchPatient panelSearchPatient = new PanelSearchPatient();
        panelSearchPatient.addSearchButtonListener(new SearchByCnpButtonListener(panelSearchPatient));

        PanelShowAppointments panelShowAppointments = new PanelShowAppointments();
        panelShowAppointments.addSearchButtonListener(new AppointmentSearchButtonListener(panelShowAppointments));
        panelShowAppointments.addAddButtonListener(new AppointmentAddButtonListener(panelShowAppointments));
        panelShowAppointments.addDeleteButtonListener(new AppointmentDeleteButtonListener(panelShowAppointments));
        panelShowAppointments.addViewButtonListener(new AppointmentViewButtonListener(panelShowAppointments));
        panelShowAppointments.updateTable(account.hasPermission("mr.appointments.view.all") ? model.getAppointments(null, null) : model.getAppointments(cnp, null, null));

        gPanelSMS = new PanelShowMedicalServices();
        gPanelSMS.updateTable(model.getMedicalServices(cnp));
        gPanelSMS.addBtnAddListener(new ServiceAddButtonListener());
        gPanelSMS.addBtnDeleteListener(new ServiceDeleteButtonListener(gPanelSMS));
        gPanelSMS.addBtnViewListener(new ServiceViewButtonListener());

        view.addBtnShowPatientsListener(new MenuButtonListener(panelShowPatients));
        view.addBtnSearchPatientListener(new MenuButtonListener(panelSearchPatient));
        view.addBtnAppointmentsListener(new MenuButtonListener(panelShowAppointments));
        view.addBtnMedicalServicesListener(new MenuButtonListener(gPanelSMS));
        view.addBackButtonListener(new BackButtonListener(previousView));
    }

    class PatientSearchButtonListener implements ActionListener {

        private final PanelShowPatients panel;

        public PatientSearchButtonListener() {
            panel = view.getPanelShowPatients();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Patient> patients = model.getPatients(panel.getTfLastname().getText(),
                                                       panel.getTfFirstname().getText());
            panel.updateTable(patients);
        }

    }

    class PatientModifyButtonListener implements ActionListener {

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

    class PatientViewButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JTable patientsTable = view.getPanelShowPatients().getJTable();

            if (patientsTable.getSelectedRows().length != 1) {
                view.sendError("Trebuie sa selectezi exact un pacient.");
            } else {
                int row = patientsTable.getSelectedRow();
                Patient patient = model.getPatient((String) patientsTable.getValueAt(row, 2));

                view.getPanelShowPatients().setVisible(false);
                PanelViewPatient panelVP = new PanelViewPatient(view.getPanelShowPatients(), patient);
                panelVP.addShowHistoryButtonListener(new PatientHistoryButtonListener(panelVP));
                panelVP.addShowAnalysesButtonListener(new PatientAnalysesButtonListener(panelVP));
                panelVP.addCancelButtonListener(new CancelButtonListener());
                view.setRightPanel(panelVP);
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

        private final PanelShowMedicalServices panel;

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

    class ReportSearchButtonListener implements ActionListener {

        private final PanelShowReports panel;

        public ReportSearchButtonListener(PanelShowReports panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            panel.updateTable(model.getReports(panel.getPatient().getCnp(),
                                            panel.getUtilDateModelMin().getValue(),
                                            panel.getUtilDateModelMax().getValue()));
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
                panel.addAddInvestigationButton(new InvestigationAddButtonListener(panel));
                panel.addViewInvestigationButton(new InvestigationViewButtonListener());
                panel.addSaveButtonListener(new SaveButtonListener());
                panel.addConfirmButtonListener(new ReportConfirmButtonListener());
                panel.addCancelButtonListener(new CancelButtonListener());
                panel.loadReportData(report);
                report.setInvestigations(model.getInvestigations(report.getId()));
                panel.updateTable();
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

                int reportId = report.getId();
                Report updatedReport = model.getReport(reportId);
                updatedReport.setInvestigations(model.getInvestigations(reportId));
                panelVR.loadReportData(updatedReport);
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
            } else if (panel instanceof PanelAddInvestigation) {
                PanelAddInvestigation panelAI = (PanelAddInvestigation) panel;
                int idReport = panelAI.getReport().getId();

                boolean validation = model.addInvestigation(
                        new Investigation(
                                panelAI.getIdService(),
                                panelAI.getTaRemarks().getText()),
                        idReport);

                if (validation) {
                    view.sendSuccessMessage("Investigatia a fost adaugata cu succes.");

                    panelAI.getReport().setInvestigations(model.getInvestigations(idReport));
                    ((PanelViewReport) panelAI.getPreviousPanel()).updateTable();
                } else {
                    view.sendError("Nu s-a putut adauga investigatia.");
                }
            } else if (panel instanceof PanelAddAnalyse) {
                PanelAddAnalyse panelAA = (PanelAddAnalyse) panel;
                String cnpPatient = panelAA.getPatient().getCnp();
                float value = 0.0f;

                try {
                    value = Float.parseFloat(panelAA.getTfValue().getText());
                } catch (NumberFormatException ex) {
                    view.sendError("Valoarea rezultatului trebuie sa fie un numar!");
                    return;
                }

                boolean validation = model.addAnalyse(
                        new Analyse(
                                panelAA.getIdAnalyse(),
                                value),
                        cnpPatient);

                if (validation) {
                    view.sendSuccessMessage("Analiza a fost adaugata cu succes.");

                    ((PanelShowAnalyses) panelAA.getPreviousPanel()).updateTable(model.getPatientAnalyses(cnpPatient, null, null));
                } else {
                    view.sendError("Nu s-a putut adauga analiza.");
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
                view.setRightPanel(view.getPanelShowPatients());
            } else if (panel instanceof PanelViewPatient) {
                view.setRightPanel(((PanelViewPatient) panel).getPreviousPanel());
            } else if (panel instanceof PanelAddMedicalService) {
                ((PanelAddMedicalService) panel).reset();
                view.setRightPanel(gPanelSMS);
            } else if (panel instanceof PanelViewMedicalService) {
                view.setRightPanel(gPanelSMS);
            } else if (panel instanceof PanelShowReports) {
                view.setRightPanel(((PanelShowReports) panel).getPreviousPanel());
            } else if (panel instanceof PanelViewReport) {
                view.setRightPanel(((PanelViewReport) panel).getPreviousPanel());
            } else if (panel instanceof PanelAddInvestigation) {
                view.setRightPanel(((PanelAddInvestigation) panel).getPreviousPanel());
            } else if (panel instanceof PanelViewInvestigation) {
                view.setRightPanel(((PanelViewInvestigation) panel).getPreviousPanel());
            } else if (panel instanceof PanelShowAnalyses) {
                view.setRightPanel(((PanelShowAnalyses) panel).getPreviousPanel());
            } else if (panel instanceof PanelAddAnalyse) {
                view.setRightPanel(((PanelAddAnalyse) panel).getPreviousPanel());
            } else if (panel instanceof PanelAddAppointment) {
                view.setRightPanel(((PanelAddAppointment) panel).getPreviousPanel());
            } else if (panel instanceof PanelViewAppointment) {
                view.setRightPanel(((PanelViewAppointment) panel).getPreviousPanel());
            }
        }

    }

    class SearchByCnpButtonListener implements ActionListener {

        private final PanelSearchPatient panel;

        public SearchByCnpButtonListener(PanelSearchPatient panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Patient patient = model.getPatient(panel.getTfCnp().getText());

            if (patient == null) {
                view.sendError("Nu a fost gasit niciun pacient!");
                return;
            }

            PanelViewPatient panelVP = new PanelViewPatient(panel, patient);
            panelVP.addShowHistoryButtonListener(new PatientHistoryButtonListener(panelVP));
            panelVP.addShowAnalysesButtonListener(new PatientAnalysesButtonListener(panelVP));
            panelVP.addCancelButtonListener(new CancelButtonListener());
            view.setRightPanel(panelVP);
        }

    }

    class PatientHistoryButtonListener implements ActionListener {

        private final PanelViewPatient panel;

        public PatientHistoryButtonListener(PanelViewPatient panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Report> reports = model.getReports(panel.getPatient().getCnp(), null, null);

            PanelShowReports panelSR = new PanelShowReports(panel.getPatient(), panel);
            panelSR.addSearchButtonListener(new ReportSearchButtonListener(panelSR));
            panelSR.addViewButtonListener(new ReportViewButtonListener());
            panelSR.addAddButtonListener(new ReportAddButtonListener(panelSR));
            panelSR.addCancelButtonListener(new CancelButtonListener());
            panelSR.updateTable(reports);
            view.setRightPanel(panelSR);
        }

    }

    class PatientAnalysesButtonListener implements ActionListener {

        private final PanelViewPatient panel;

        public PatientAnalysesButtonListener(PanelViewPatient panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Analyse> analyses = model.getPatientAnalyses(panel.getPatient().getCnp(), null, null);

            PanelShowAnalyses panelSA = new PanelShowAnalyses(panel.getPatient(), panel);
            panelSA.addSearchButtonListener(new AnalyseSearchButtonListener(panelSA));
            panelSA.addAddButtonListener(new AnalyseAddButtonListener(panelSA));
            panelSA.addCancelButtonListener(new CancelButtonListener());
            panelSA.updateTable(analyses);
            view.setRightPanel(panelSA);
        }

    }

    class AnalyseSearchButtonListener implements ActionListener {

        private final PanelShowAnalyses panel;

        public AnalyseSearchButtonListener(PanelShowAnalyses panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            panel.updateTable(model.getPatientAnalyses(panel.getPatient().getCnp(),
                    panel.getUtilDateModelMin().getValue(),
                    panel.getUtilDateModelMax().getValue()));
        }

    }

    class AnalyseAddButtonListener implements ActionListener {

        private final PanelShowAnalyses panel;

        public AnalyseAddButtonListener(PanelShowAnalyses panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            PanelAddAnalyse panelAA = new PanelAddAnalyse(panel.getPatient(), panel);
            panelAA.addSaveButtonListener(new SaveButtonListener());
            panelAA.addCancelButtonListener(new CancelButtonListener());
            panelAA.updateCbAnalyses(model.getAnalyses());
            view.setRightPanel(panelAA);
        }

    }

    class InvestigationViewButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel currentPanel = view.getCurrentPanel();

            if (!(currentPanel instanceof PanelViewReport)) {
                view.sendError("A avut loc o eroare.");
                return;
            }

            PanelViewReport panelVR = (PanelViewReport) currentPanel;
            JTable investigationsTable = panelVR.getInvestigationsTable();
            Report report = panelVR.getReport();

            if (investigationsTable.getSelectedRows().length != 1) {
                view.sendError("Trebuie sa selectezi exact o investigatie.");
            } else {
                int row = investigationsTable.getSelectedRow();
                Investigation investigation = report.getInvestigations().get(row);

                PanelViewInvestigation panel = new PanelViewInvestigation(investigation, panelVR);
                panel.addCancelButtonListener(new CancelButtonListener());
                view.setRightPanel(panel);
            }
            investigationsTable.clearSelection();
        }

    }

    class InvestigationAddButtonListener implements ActionListener {

        private final PanelViewReport panel;

        public InvestigationAddButtonListener(PanelViewReport panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Report report = panel.getReport();
            if (panel.getReport().getSealCode() != null) {
                view.sendError("Nu se pot adauga investigatii noi!");
                return;
            }

            PanelAddInvestigation panelAI = new PanelAddInvestigation(report, panel);
            panelAI.addSaveButtonListener(new SaveButtonListener());
            panelAI.addCancelButtonListener(new CancelButtonListener());
            panelAI.updateCbServices(model.getMedicalServices(cnp));
            view.setRightPanel(panelAI);
        }

    }

    class ReportAddButtonListener implements ActionListener {

        private final PanelShowReports panel;

        public ReportAddButtonListener(PanelShowReports panel) {
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

    class AppointmentAddButtonListener implements ActionListener {

        private final PanelShowAppointments panel;

        public AppointmentAddButtonListener(PanelShowAppointments panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            PanelAddAppointment panelAA = new PanelAddAppointment(panel);
            panelAA.addSaveButtonListener(new AppointmentSaveButtonListener(panelAA));
            panelAA.addCancelButtonListener(new CancelButtonListener());
            panelAA.addAddButtonListener(new AppointmentAddServiceButtonListener(panelAA));
            panelAA.addDeleteButtonListener(new AppointmentDeleteServiceButtonListener(panelAA));

            panelAA.updateCbPatient(model.getPatients());
            panelAA.updateCbSpeciality(model.getSpecialities());
            panelAA.updateCbCabinet(model.getCabintes(1));

            int idSpeciality = panelAA.getIdSpeciality();
            panelAA.updateCbDoctor(model.getDoctors(idSpeciality, account.getIdMedicalUnit()));
            panelAA.updateCbService(model
                    .getMedicalServices(panelAA.getCnpDoctor(),
                            idSpeciality,
                            panelAA.getIdCabinet()));

            panelAA.addSpecialityComboBoxListener(new AppointmentSpecialityComboBoxListener(panelAA));
            panelAA.addDoctorComboBoxListener(new AppointmentDoctorComboBoxListener(panelAA));
            panelAA.addCabinetComboBoxListener(new AppointmentCabinetComboBoxListener(panelAA));

            view.setRightPanel(panelAA);
        }

    }

    class AppointmentViewButtonListener implements ActionListener {

        private final PanelShowAppointments panel;

        public AppointmentViewButtonListener(PanelShowAppointments panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JTable appointmentsTable = panel.getAppointmentsTable();

            if (appointmentsTable.getSelectedRows().length != 1) {
                view.sendError("Trebuie sa selectezi exact o programare.");
            } else {
                int idAppointment = panel.getAppointments().get(appointmentsTable.getSelectedRow()).getId();

                Appointment appointment = model.getAppointment(idAppointment);

                view.getPanelShowPatients().setVisible(false);
                PanelViewAppointment panelVA = new PanelViewAppointment(appointment, panel);
                panelVA.addCancelButtonListener(new CancelButtonListener());
                view.setRightPanel(panelVA);
            }
            appointmentsTable.clearSelection();
        }

    }

    class AppointmentDeleteButtonListener implements ActionListener {

        private final PanelShowAppointments panel;

        public AppointmentDeleteButtonListener(PanelShowAppointments panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JTable appointmentsTable = panel.getAppointmentsTable();
            List<Appointment> appointments = panel.getAppointments();

            int[] indexes = appointmentsTable.getSelectedRows();
            if (indexes.length == 0) {
                view.sendError("Trebuie sa selectezi cel putin o programare.");
                return;
            }

            for (int index : indexes) {
                model.deleteAppointment(appointments.get(index).getId());
            }

            panel.updateTable(account.hasPermission("mr.appointments.view.all") ? model.getAppointments(null, null) : model.getAppointments(account.getCnp(), null, null));
            view.sendSuccessMessage("Programarile selectate au fost sterse.");
        }

    }

    class AppointmentSearchButtonListener implements ActionListener {

        private final PanelShowAppointments panel;

        public AppointmentSearchButtonListener(PanelShowAppointments panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (account.hasPermission("mr.appointments.view.all")) {
                panel.updateTable(model.getAppointments(panel.getDateMin(), panel.getDateMax()));
            } else {
                panel.updateTable(model.getAppointments(account.getCnp(), panel.getDateMin(), panel.getDateMax()));
            }
        }

    }

    class AppointmentAddServiceButtonListener implements ActionListener {

        private final PanelAddAppointment panel;

        public AppointmentAddServiceButtonListener(PanelAddAppointment panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            MedicalService medicalService = panel.getMedicalService();
            List<MedicalService> medicalServices = panel.getMedicalServices();

            if (medicalServices == null) {
                view.sendError("Trebuie sa selectati un serviciu!");
                return;
            }

            if (medicalServices.contains(medicalService)) {
                view.sendError("Acest serviciu este deja inclus in progranare!");
                return;
            }

            medicalServices.add(medicalService);
            panel.updateTable();
        }

    }

    class AppointmentDeleteServiceButtonListener implements ActionListener {

        private final PanelAddAppointment panel;

        public AppointmentDeleteServiceButtonListener(PanelAddAppointment panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            MedicalService medicalService = panel.getMedicalService();
            List<MedicalService> medicalServices = panel.getMedicalServices();

            if (medicalServices == null) {
                view.sendError("Trebuie sa selectati un serviciu!");
                return;
            }
            if (!medicalServices.contains(medicalService)) {
                view.sendError("Acest serviciu nu este inclus in progranare!");
                return;
            }

            medicalServices.remove(medicalService);
            panel.updateTable();
        }

    }

    class AppointmentSpecialityComboBoxListener implements ActionListener {

        private final PanelAddAppointment panel;

        public AppointmentSpecialityComboBoxListener(PanelAddAppointment panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            panel.updateCbDoctor(model.getDoctors(panel.getIdSpeciality(), account.getIdMedicalUnit()));
            panel.updateCbService(model.getMedicalServices(
                    panel.getCnpDoctor(),
                    panel.getIdSpeciality(),
                    panel.getIdCabinet()
            ));

            panel.getMedicalServices().clear();
            panel.updateTable();
        }

    }

    class AppointmentDoctorComboBoxListener implements ActionListener {

        private final PanelAddAppointment panel;

        public AppointmentDoctorComboBoxListener(PanelAddAppointment panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            panel.updateCbService(model.getMedicalServices(
                    panel.getCnpDoctor(),
                    panel.getIdSpeciality(),
                    panel.getIdCabinet()
            ));

            panel.getMedicalServices().clear();
            panel.updateTable();
        }

    }

    class AppointmentCabinetComboBoxListener implements ActionListener {

        private final PanelAddAppointment panel;

        public AppointmentCabinetComboBoxListener(PanelAddAppointment panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            panel.updateCbService(model.getMedicalServices(
                    panel.getCnpDoctor(),
                    panel.getIdSpeciality(),
                    panel.getIdCabinet()
            ));

            panel.getMedicalServices().clear();
            panel.updateTable();
        }

    }

    class AppointmentSaveButtonListener implements ActionListener {

        private final PanelAddAppointment panel;

        public AppointmentSaveButtonListener(PanelAddAppointment panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String cnpDoctor = panel.getCnpDoctor();
            String cnpPatient = panel.getCnpPatient();
            int idCabinet = panel.getIdCabinet();

            if (panel.getUtilDateModel().getValue() == null) {
                view.sendError("Trebuie sa selectati o data pentru programare!");
                return;
            }

            Date startDate = panel.getTime();
            int duration = panel.getMedicalServices().stream().mapToInt(MedicalService::getDuration).sum();
            System.out.println(duration);
            Date endDate = new Date(startDate.getTime() + duration * 60 * 1000);

            if (panel.getMedicalServices().isEmpty()) {
                view.sendError("Trebuie sa introduceti cel putin un serviciu medical!");
                return;
            }

            if (!model.checkAppointmentByDoctor(cnpDoctor, startDate, endDate)) {
                view.sendError("Doctorul este ocupat!");
                return;
            }

            if (!model.checkAppointmentByPacient(cnpPatient, startDate, endDate)) {
                view.sendError("Pacientul este ocupat!");
                return;
            }

            if (!model.checkAppointmentByDoctorSchedule(cnpDoctor, account.getIdMedicalUnit(), startDate, endDate)) {
                view.sendError("Doctorul nu lucreaza!");
                return;
            }

            if (!model.checkAppointmentByDoctorHolidays(cnpDoctor, startDate)) {
                view.sendError("Doctorul este in concediu!");
                return;
            }

            if (!model.checkAppointmentByCabinet(idCabinet, startDate, endDate)) {
                view.sendError("Cabinetul este ocupat!");
                return;
            }

            int idAppointment = model.addAppointment(new Appointment(cnpPatient, cnpDoctor, idCabinet, panel.getIdSpeciality(), startDate));

            if (idAppointment != 0 && model.addAppointmentServices(idAppointment, panel.getMedicalServices())) {
                ((PanelShowAppointments) panel.getPreviousPanel()).updateTable(account.hasPermission("mr.appointments.view.all") ? model.getAppointments(null, null) : model.getAppointments(account.getCnp(), null, null));

                view.sendSuccessMessage("Programarea a fost adaugata.");
                return;
            }

            view.sendError("Nu s-a putut adauga programarea.");
        }

    }

    class MenuButtonListener implements ActionListener {
        private final JPanel panel;

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

    class ShowReceiptButtonListener implements ActionListener {

        private final PanelShowReceipt panel;

        ShowReceiptButtonListener(PanelShowReceipt panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }

    }

}
