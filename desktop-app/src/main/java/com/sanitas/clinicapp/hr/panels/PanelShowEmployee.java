package com.sanitas.clinicapp.hr.panels;

import com.sanitas.clinicapp.ClinicApplication;
import com.sanitas.clinicapp.hr.Employee;
import com.sanitas.clinicapp.hr.HrModel;

import com.sanitas.clinicapp.ui.Colors;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PanelShowEmployee extends JPanel{
    private JTextField TxtFirst = new JTextField(10);
    private JTextField TxtLast = new JTextField(10);
    private JTextField TxtPosition = new JTextField(10);


    private JButton btnSearch = new StyledJButton("Cauta").getButton();
    private JButton btnModify = new StyledJButton("Vizualizeaza").getButton();
    private JButton btnOrar= new StyledJButton("Orar").getButton();
    private JButton btnDelete = new StyledJButton("Sterge").getButton();
    private JButton btnHoliday = new StyledJButton("Zile libere").getButton();

    private JTable employeeTable;
    private ClinicApplication.Account account;

    public PanelShowEmployee(HrModel model, ClinicApplication.Account account) {
        super(new BorderLayout());
        this.account=account;
        btnSearch.setBackground(Colors.MAIN_COLOR.getColor());

        employeeTable = initializeTable(model);

        JScrollPane jScrollPane = new JScrollPane(employeeTable);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setPreferredSize(new Dimension(540, 240));
        JPanel tablePanel = new JPanel(new FlowLayout());
        tablePanel.add(jScrollPane);


        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Nume:"));
        searchPanel.add(TxtLast);
        searchPanel.add(new JLabel("Prenume:"));
        searchPanel.add(TxtFirst);
        searchPanel.add(new JLabel("Position: "));
        searchPanel.add(TxtPosition);
        searchPanel.add(btnSearch);



        JPanel editPanel = new JPanel(new FlowLayout());
        editPanel.add(btnOrar);
        editPanel.add(btnHoliday);
        editPanel.add(btnModify);
        if((account.hasPermission("hr.read.all")) || account.isSuperAdmin())
            editPanel.add(btnDelete);

        if(account.hasPermission("hr.read.all") || account.isSuperAdmin()) {
            add(searchPanel, BorderLayout.NORTH);
        }
        add(tablePanel, BorderLayout.CENTER);
        add(editPanel, BorderLayout.SOUTH);

        setVisible(false);
    }

    private JTable initializeTable(HrModel model) {

        String[] columns = { "Nume", "Prenume","Functie","CNP" };
        java.util.List<Employee> employees = model.getAllData("", "","");

        List<Employee> accountEmployee=new ArrayList<>();
        if(account.hasPermission("hr.read.all") || account.isSuperAdmin()){
            accountEmployee=employees;
        }
        else
            accountEmployee=employees.stream().filter(employee1 ->employee1.getCnp().equals(account.getCnp()) ).collect(Collectors.toList());

        Object[][] employeesData = new Object[accountEmployee.size()][columns.length];
        for (int i = 0; i < accountEmployee.size(); ++i) {
            Employee employee = accountEmployee.get(i);

            employeesData[i][0] = employee.getLastname();
            employeesData[i][1] = employee.getFirstname();
            employeesData[i][2] = employee.getPosition();
            employeesData[i][3] = employee.getCnp();
        }

        JTable table = new JTable();
        table.setModel(new DefaultTableModel(employeesData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);

        return table;
    }

    public void updateTable(List<Employee> employee) {
        List<Employee> accountEmployee=new ArrayList<>();
        if(account.hasPermission("hr.read.all") || account.isSuperAdmin()){
            accountEmployee=employee;
        }
        else
            accountEmployee=employee.stream().filter(employee1 ->employee1.getCnp().equals(account.getCnp()) ).collect(Collectors.toList());
        String[] columns = { "Nume", "Prenume", "Functie","CNP" };

        Object[][] employeeData = new Object[accountEmployee.size()][columns.length];
        for (int i = 0; i < accountEmployee.size(); ++i) {
            Employee employee1 = accountEmployee.get(i);

            employeeData[i][0] = employee1.getLastname();
            employeeData[i][1] = employee1.getFirstname();
            employeeData[i][2] = employee1.getPosition();
            employeeData[i][3] = employee1.getCnp();
        }

        employeeTable.setModel(new DefaultTableModel(employeeData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    public JTextField getTxtFirst() {
        return TxtFirst;
    }

    public JTextField getTxtLast() {
        return TxtLast;
    }

    public JTextField getTxtPosition() {
        return TxtPosition;
    }

    public JTable getJTable() {
        return employeeTable;
    }

    public void addSearchButtonListener(ActionListener actionListener) {
        btnSearch.addActionListener(actionListener);
    }

    public void addModifyButtonListener(ActionListener actionListener) {
        btnModify.addActionListener(actionListener);
    }

    public void addDeleteButtonListener(ActionListener actionListener) {
        btnDelete.addActionListener(actionListener);
    }

    public void addScheduleButtonListener(ActionListener actionListener) {
        btnOrar.addActionListener(actionListener);
    }

    public void addHolidayButtonListener(ActionListener actionListener) {
        btnHoliday.addActionListener(actionListener);
    }

}
