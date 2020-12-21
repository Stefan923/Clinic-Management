package com.sanitas.clinicapp.mr;

import com.sanitas.clinicapp.mr.panels.PanelEditPatient;
import com.sanitas.clinicapp.mr.panels.PanelShowPatients;

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

        view.getPanelShowPatients().addSearchButtonListener(new SearchButtonListener());
        view.getPanelShowPatients().addModifyButtonListener(new ModifyButtonListener());
        view.getPanelShowPatients().addDeleteButtonListener(new DeleteButtonListener());
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
            PanelEditPatient panel = (PanelEditPatient) view.getCurrentPanel();
            boolean validation = model.updatePatient(panel.getTfCnp().getText(),
                                panel.getTfLastname().getText(),
                                panel.getTfFirstname().getText(),
                                panel.getTfIban().getText());

            if (validation) {
                view.sendSuccessMessage("Datele pacientului au fost actualizate.");

                view.getPanelShowPatients().updateTable(model.getPatients("", ""));
            } else {
                view.sendError("Datele pacientului NU au putut fi actualizate.");
            }
        }

    }

    class CancelButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setRightPanel(view.getPanelShowPatients());
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
