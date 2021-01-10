package com.sanitas.clinicapp.profile.panels;

import com.sanitas.clinicapp.hr.Employee;
import com.sanitas.clinicapp.hr.Speciality;
import com.sanitas.clinicapp.hr.panels.Nurse;
import com.sanitas.clinicapp.profile.ProfileModel;
import com.sanitas.clinicapp.struct.Doctor;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PanelEmployeeProfile extends JPanel{
    private JTextField tfCnp = new JTextField(15);
    private JTextField tfLastname = new JTextField(15);
    private JTextField tfFirstname = new JTextField(15);
    private JTextField tfPosition= new JTextField(15);
    private JTextField tfAdress= new JTextField(18);
    private JTextField tfEmail= new JTextField(18);
    private JTextField tfContract= new JTextField(15);
    private JTextField tfPhone= new JTextField(15);
    private JTextField tfIban= new JTextField(18);
    private JTextField tfDate= new JTextField(15);
    private JTextField tfSalary= new JTextField(15);
    private JTextField tfHours= new JTextField(15);
    private JTextField tftype= new JTextField(15);
    private JTextField tfrank= new JTextField(15);

    private JTextField tfsealCode=new JTextField(15);
    private JTextField tfcommission=new JTextField(15);
    private JTextField tfscientificTitle=new JTextField(15);
    private JTextField tfdidacticTitle=new JTextField(15);

    private JTable specializari = new JTable();
    private JTable acreditari = new JTable();

    private List<Speciality> specialitiesDoc=new ArrayList<Speciality>();
    private Map<Integer, String> accDoc;


    public PanelEmployeeProfile(Employee employee,ProfileModel model) {
        setLayout(new BorderLayout());

        tfCnp.setEditable(false);
        tfLastname.setEditable(false);
        tfFirstname.setEditable(false);
        tfPosition.setEditable(false);
        tfAdress.setEditable(false);
        tfContract.setEditable(false);
        tfDate.setEditable(false);
        tfEmail.setEditable(false);
        tfHours.setEditable(false);
        tfIban.setEditable(false);
        tfPhone.setEditable(false);
        tfSalary.setEditable(false);

        tfCnp.setText(employee.getCnp());
        tfLastname.setText(employee.getLastname());
        tfFirstname.setText(employee.getFirstname());
        tfPosition.setText(employee.getPosition());
        tfAdress.setText(employee.getAdress());
        tfContract.setText(String.valueOf(employee.getContract()));
        tfDate.setText(String.valueOf(employee.getDate()));
        tfEmail.setText(employee.getEmail());
        tfHours.setText(String.valueOf(employee.getHours()));
        tfIban.setText(employee.getIban());
        tfPhone.setText(employee.getPhone());
        tfSalary.setText(String.valueOf(employee.getSalary()));


        JPanel cnpPanel = new JPanel(new FlowLayout());
        cnpPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        cnpPanel.add(new JLabel("CNP:"));
        cnpPanel.add(tfCnp);

        JPanel lastnamePanel = new JPanel(new FlowLayout());
        lastnamePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        lastnamePanel.add(new JLabel("Nume:"));
        lastnamePanel.add(tfLastname);

        JPanel firstnamePanel = new JPanel(new FlowLayout());
        firstnamePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        firstnamePanel.add(new JLabel("Prenume:"));
        firstnamePanel.add(tfFirstname);

        JPanel positionPanel = new JPanel(new FlowLayout());
        positionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        positionPanel.add(new JLabel("Functie:"));
        positionPanel.add(tfPosition);

        JPanel adressPanel = new JPanel(new FlowLayout());
        adressPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        adressPanel.add(new JLabel("Adresa:"));
        adressPanel.add(tfAdress);

        JPanel salaryPanel = new JPanel(new FlowLayout());
        salaryPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        salaryPanel.add(new JLabel("Salar:"));
        salaryPanel.add(tfSalary);

        JPanel ibanPanel = new JPanel(new FlowLayout());
        ibanPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        ibanPanel.add(new JLabel("Iban:"));
        ibanPanel.add(tfIban);

        JPanel hoursPanel = new JPanel(new FlowLayout());
        hoursPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        hoursPanel.add(new JLabel("Ore negociate:"));
        hoursPanel.add(tfHours);

        JPanel emailPanel = new JPanel(new FlowLayout());
        emailPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        emailPanel.add(new JLabel("E-mail:"));
        emailPanel.add(tfEmail);

        JPanel contractPanel = new JPanel(new FlowLayout());
        contractPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        contractPanel.add(new JLabel("Nr. contract:"));
        contractPanel.add(tfContract);

        JPanel phonePanel = new JPanel(new FlowLayout());
        phonePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        phonePanel.add(new JLabel("Telefon:"));
        phonePanel.add(tfPhone);

        JPanel datePanel = new JPanel(new FlowLayout());
        datePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        datePanel.add(new JLabel("Data angajare:"));
        datePanel.add(tfDate);

        JPanel ntypePanel = new JPanel(new FlowLayout());
        ntypePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        ntypePanel.add(new JLabel("Tip asistenta:"));
        ntypePanel.add(tftype);

        JPanel nrankPanel = new JPanel(new FlowLayout());
        nrankPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        nrankPanel.add(new JLabel("Rank asistenta:"));
        nrankPanel.add(tfrank);

        JPanel nurse = new JPanel(new GridLayout(1,2));
        nurse.add(nrankPanel);
        nurse.add(ntypePanel);

        JPanel sealPanel = new JPanel(new FlowLayout());
        sealPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        sealPanel.add(new JLabel("Parafa:"));
        sealPanel.add(tfsealCode);

        JPanel commisionPanel = new JPanel(new FlowLayout());
        commisionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        commisionPanel.add(new JLabel("Comision:"));
        commisionPanel.add(tfcommission);

        JPanel scientificPanel = new JPanel(new FlowLayout());
        scientificPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        scientificPanel.add(new JLabel("Titlu stiintific:"));
        scientificPanel.add(tfscientificTitle);

        JPanel didacticPanel = new JPanel(new FlowLayout());
        didacticPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        didacticPanel.add(new JLabel("Titlu didactic:"));
        didacticPanel.add(tfdidacticTitle);

        updateTable(model.getSpecialities(tfCnp.getText()));
        JScrollPane jScrollPane = new JScrollPane(specializari);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setPreferredSize(new Dimension(250, 100));
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(jScrollPane, BorderLayout.NORTH);

        updateTable2(model.getAccreditations(tfCnp.getText()));
        JScrollPane jScrollPane2 = new JScrollPane(acreditari);
        jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane2.setPreferredSize(new Dimension(250, 100));
        JPanel tablePanel2 = new JPanel(new BorderLayout());
        tablePanel2.add(jScrollPane2, BorderLayout.NORTH);


        JPanel employeeData = new JPanel(new GridLayout(6, 2));
        employeeData.add(cnpPanel);
        employeeData.add(lastnamePanel);
        employeeData.add(firstnamePanel);
        employeeData.add(positionPanel);
        employeeData.add(adressPanel);
        employeeData.add(salaryPanel);
        employeeData.add(ibanPanel);
        employeeData.add(hoursPanel);
        employeeData.add(emailPanel);
        employeeData.add(contractPanel);
        employeeData.add(phonePanel);
        employeeData.add(datePanel);

        JPanel tables = new JPanel(new FlowLayout());
        tables.add(tablePanel);
        tables.add(tablePanel2);

        JPanel doctor = new JPanel(new GridLayout(2, 2));
        doctor.add(sealPanel);
        doctor.add(commisionPanel);
        doctor.add(scientificPanel);
        doctor.add(didacticPanel);

        JPanel doctors = new JPanel(new BorderLayout());
        doctors.add(doctor, BorderLayout.CENTER);
        doctors.add(tables, BorderLayout.SOUTH);

        if (employee.getPosition().equals("Asistent Medical"))
            employee = model.getNurse(employee);
        if (employee.getPosition().equals("Medic")) {
            employee = model.getDoctor(employee);
        }

        JPanel reportDataPanel = new JPanel();
        reportDataPanel.setLayout(new BorderLayout());
        reportDataPanel.add(employeeData, BorderLayout.CENTER);
        if (tfPosition.getText().equals("Asistent Medical") && employee instanceof Nurse) {
            tftype.setEditable(false);
            tfrank.setEditable(false);
            tftype.setText(((Nurse) employee).getType());
            tfrank.setText(((Nurse) employee).getRank());
            reportDataPanel.add(nurse, BorderLayout.SOUTH);
        }

        if (tfPosition.getText().equals("Medic") && employee instanceof Doctor) {
            tfsealCode.setEditable(false);
            tfcommission.setEditable(false);
            tfscientificTitle.setEditable(false);
            tfdidacticTitle.setEditable(false);
            tfsealCode.setText(((Doctor) employee).getSealCode());
            tfcommission.setText(String.valueOf(((Doctor) employee).getCommission()));
            tfscientificTitle.setText(((Doctor) employee).getScientificTitle());
            tfdidacticTitle.setText(((Doctor) employee).getDidacticTitle());
            reportDataPanel.add(doctors, BorderLayout.SOUTH);
        }


        JScrollPane dataScrollPane = new JScrollPane(reportDataPanel);
        dataScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        dataScrollPane.setPreferredSize(new Dimension(500, 325));
        dataScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

        add(dataScrollPane,BorderLayout.CENTER);
    }
    public void updateTable(List<Speciality> obj) {
        this.specialitiesDoc = obj;
        String[] columns = {"Specializari","Rank"};
        Object[][] objData = new Object[obj.size()][columns.length];
        int i = 0;
        for (Speciality ob : obj) {
            objData[i][0] = ob.getSpeciality();
            objData[i][1] = ob.getRank();
            i++;
        }

        specializari.setModel(new DefaultTableModel(objData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    public void updateTable2(Map<Integer, String> obj) {
        this.accDoc = obj;
        String[] columns = {"Acreditari"};
        Object[][] objData = new Object[obj.size()][columns.length];
        int i = 0;
        for (String ob : obj.values()) {
            objData[i][0] = ob;
            i++;
        }

        acreditari.setModel(new DefaultTableModel(objData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }


    public JTextField getTfCnp() {
        return tfCnp;
    }

    public JTextField getTfLastname() {
        return tfLastname;
    }

    public JTextField getTfFirstname() {
        return tfFirstname;
    }

    public JTextField getTfPosition() {
        return tfPosition;
    }

    public JTextField getTfsealCode() {
        return tfsealCode;
    }

    public JTextField getTfscientificTitle() {
        return tfscientificTitle;
    }

    public JTextField getTfdidacticTitle() {
        return tfdidacticTitle;
    }

    public JTextField getTfcommission() {
        return tfcommission;
    }

}



