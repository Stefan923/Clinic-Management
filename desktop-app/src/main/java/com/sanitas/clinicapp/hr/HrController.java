package com.sanitas.clinicapp.hr;

import com.sanitas.clinicapp.hr.panels.PanelAddEmployee;
import com.sanitas.clinicapp.hr.panels.PanelEditEmployee;
import com.sanitas.clinicapp.hr.panels.PanelShowEmployee;
import com.sanitas.clinicapp.hr.panels.PanelViewEmployee;
import com.sanitas.clinicapp.mr.MrController;
import com.sanitas.clinicapp.mr.panels.PanelAddPatient;
import com.sanitas.clinicapp.mr.panels.PanelEditPatient;
import com.sanitas.clinicapp.mr.panels.PanelShowPatients;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class HrController {

    private HrModel model;
    private HrView view;

    public HrController(HrModel model, HrView view,JFrame previousView) {
        this.model=model;
        this.view=view;

        loadListenersHr(previousView);
    }

    private void loadListenersHr(JFrame previousView) {
        PanelShowEmployee openView=view.getViewv();
        openView.addSearchButtonListener(new ButtonListenerViewEmp());
        openView.addDeleteButtonListener(new DeleteButtonListenerHr());
        openView.addModifyButtonListener(new ModifyButtonListenerHr());


        PanelAddEmployee panelAddEmployee =new PanelAddEmployee();
        panelAddEmployee.addBtnAddEmployeeListener(new AddButtonListener());

        view.addBtnSearchEmployeeListener(new MenuButtonListenerHr(view.getBtnShowEmployees(), openView));
        view.addBtnAddEmployeesListener(new MenuButtonListenerHr(view.getBtnSearchEmployee(), panelAddEmployee));
        view.addBackListener(new BackButtonListenerHr(previousView));
    }

    class MenuButtonListenerHr implements ActionListener {
        private JPanel panel;

        public MenuButtonListenerHr(JButton button, JPanel panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setRightPanel(panel);
        }

    }

    class BackButtonListenerHr implements ActionListener {

        private final JFrame previousView;

        public BackButtonListenerHr(JFrame jFrame) {
            this.previousView = jFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose();
            previousView.setVisible(true);
        }

    }

    class DeleteButtonListenerHr implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JTable employeeTable = view.getViewv().getJTable();

            int[] indexes = employeeTable.getSelectedRows();
            if (indexes.length == 0) {
                view.sendError("Trebuie sa selectezi cel putin un angajat.");

                return;
            }

            for (int index : indexes) {
                model.deleteEmployee((String) employeeTable.getValueAt(index, 3));
            }

            view.getViewv().updateTable(model.getAllData("", "", ""));
            view.sendSuccessMessage("Angajatii selectati au fost stersi.");
        }
    }

    class ModifyButtonListenerHr implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JTable employeeTable = view.getViewv().getJTable();

            if (employeeTable.getSelectedRows().length != 1) {
                view.sendError("Trebuie sa selectezi exact un angajat.");
            } else {
                int row = employeeTable.getSelectedRow();
                Employee employee = model.getEmployee((String) employeeTable.getValueAt(row, 3));
                view.getViewv().setVisible(false);
                PanelEditEmployee panel = new PanelEditEmployee(employee);
                panel.addSaveButtonListener(new SaveButtonListenerHr());
                panel.addCancelButtonListener(new CancelButtonListenerHr());
                view.setRightPanel(panel);
            }
            employeeTable.clearSelection();
        }

    }

    class SaveButtonListenerHr implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel panel = view.getCurrentPanel();

            if (panel instanceof PanelEditEmployee) {
                PanelEditEmployee panelEditEmployee = (PanelEditEmployee) view.getCurrentPanel();
                boolean validation = model.updateEmployee(panelEditEmployee.getTfCnp().getText(),
                        panelEditEmployee.getTfLastname().getText(),
                        panelEditEmployee.getTfFirstname().getText(),
                        panelEditEmployee.getTfPosition().getText());

                if (validation) {
                    view.sendSuccessMessage("Datele pacientului au fost actualizate.");

                    view.getViewv().updateTable(model.getAllData("","",""));
                } else {
                    view.sendError("Datele pacientului NU au putut fi actualizate.");
                }
            }

        }

    }

    class CancelButtonListenerHr implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel panel = view.getCurrentPanel();
            if (panel instanceof PanelEditEmployee) {
                view.setRightPanel(view.getViewv());
            } else {
                ((PanelEditEmployee) panel).reset();
            }
        }

    }

    class ButtonListenerViewHr implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.getViewv();
        }
    }

    class ButtonListenerViewEmp implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel p=view.getCurrentPanel();
            if(p instanceof PanelShowEmployee) {
                PanelShowEmployee panel=(PanelShowEmployee) view.getCurrentPanel();
                String lastname = panel.getTxtLast().getText();
                String firstname = panel.getTxtFirst().getText();
                String position = panel.getTxtPosition().getText();

                List<Employee> employeeList = new ArrayList<Employee>();

                employeeList = model.getAllData(lastname, firstname, position);
                if (employeeList.isEmpty()) {
                    view.sendError("Angajatul nu poate fi gasit!");
                }
                else {
                    panel.updateTable(employeeList);
                }
            }

        }
    }

    class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            JPanel p=view.getCurrentPanel();
            List<String> txt=new ArrayList<>();
            if (p instanceof PanelAddEmployee) {
                PanelAddEmployee panel=(PanelAddEmployee)view.getCurrentPanel();
                for (int i = 0; i < 12; i++) {
                    txt.add(panel.getTxt(i));
                    if(panel.getTxt(i).isEmpty()) {
                        view.sendError("Completati toate campurile!");
                        return;
                    }
                }
                try {
                    if (model.insertEmployee(txt.get(0), txt.get(1), txt.get(2),
                            txt.get(3), txt.get(4), txt.get(5), txt.get(6),
                            Integer.parseInt(txt.get(7)), txt.get(8),
                            txt.get(9), Double.parseDouble(txt.get(10)),
                            Integer.parseInt(txt.get(11)))) {
                        view.sendSuccessMessage("Datele angajatului au fost introduse.");
                        panel.setTxtEmpty();
                    } else {
                        view.sendError("Angajat existent!");
                        //panel.setTxtEmpty();
                    }
                }catch (NumberFormatException ex) {
                    view.sendError("Date introduse gresit!");
                }
            }
        }

    }
}
