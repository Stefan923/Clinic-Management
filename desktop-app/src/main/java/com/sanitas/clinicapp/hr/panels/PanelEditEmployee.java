package com.sanitas.clinicapp.hr.panels;

import com.sanitas.clinicapp.ClinicApplication;
import com.sanitas.clinicapp.hr.Employee;

import com.sanitas.clinicapp.hr.HrModel;
import com.sanitas.clinicapp.hr.Schedule;
import com.sanitas.clinicapp.hr.Speciality;
import com.sanitas.clinicapp.struct.Doctor;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PanelEditEmployee extends JPanel {
    private JTextField tfCnp = new JTextField(15);
    private JTextField tfLastname = new JTextField(15);
    private JTextField tfFirstname = new JTextField(15);
    private JTextField tfPosition = new JTextField(15);
    private JTextField tfAdress = new JTextField(18);
    private JTextField tfEmail = new JTextField(18);
    private JTextField tfContract = new JTextField(15);
    private JTextField tfPhone = new JTextField(15);
    private JTextField tfIban = new JTextField(18);
    private JTextField tfDate = new JTextField(15);
    private JTextField tfSalary = new JTextField(15);
    private JTextField tfHours = new JTextField(15);
    private JTextField tftype = new JTextField(15);
    private JTextField tfrank = new JTextField(15);

    private JTextField tfsealCode = new JTextField(15);
    private JTextField tfcommission = new JTextField(15);
    private JTextField tfscientificTitle = new JTextField(15);
    private JTextField tfdidacticTitle = new JTextField(15);

    private JTable specializari = new JTable();
    private JTable acreditari = new JTable();

    private List<Speciality> specialitiesDoc=new ArrayList<Speciality>();
    private Map<Integer, String> accDoc;

    private Map<Integer, String> specialities;
    private final JComboBox<String> cbSpeciality = new JComboBox<>();

    private final JComboBox<String> cbRank = new JComboBox<>();

    private Map<Integer, String> acc;
    private final JComboBox<String> cbAcc = new JComboBox<>();

    private JButton btnAddSpec = new StyledJButton("Adaugare specializare").getButton();
    private JButton btnAddAcc = new StyledJButton("Adaugare acreditare").getButton();
    private JButton btnDeleteSpec = new StyledJButton("Stergere specializare").getButton();
    private JButton btnDeleteAcc = new StyledJButton("Stergere acreditare").getButton();
    private JButton btnSave = new StyledJButton("Salveaza").getButton();
    private JButton btnCancel = new StyledJButton("Anuleaza").getButton();

    private List<Speciality> specialitiesList=new ArrayList<Speciality>();
    private ClinicApplication.Account account;
    private String cnp;

    public PanelEditEmployee(Employee employee,ClinicApplication.Account account,String cnp,HrModel model) {
        setLayout(new BorderLayout());
        this.account=account;
        this.cnp=cnp;

        tfCnp.setEditable(false);
        tfAdress.setEditable(false);
        tfContract.setEditable(false);
        tfDate.setEditable(false);
        tfEmail.setEditable(false);
        tfHours.setEditable(false);
        tfIban.setEditable(false);
        tfPhone.setEditable(false);
        tfSalary.setEditable(false);

        if((!(account.hasPermission("hr.read.all")) || model.viewRole(cnp)=="administrator") && (!account.isSuperAdmin())) {
            tfFirstname.setEditable(false);
            tfLastname.setEditable(false);
            tfPosition.setEditable(false);
        }


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

        JPanel nurse = new JPanel(new GridLayout(1, 2));
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
        scientificPanel.setBorder(new EmptyBorder(10, 10, 0, 10));
        scientificPanel.add(new JLabel("Titlu stiintific:"));
        scientificPanel.add(tfscientificTitle);

        JPanel didacticPanel = new JPanel(new FlowLayout());
        didacticPanel.setBorder(new EmptyBorder(10, 10, 0, 10));
        didacticPanel.add(new JLabel("Titlu didactic:"));
        didacticPanel.add(tfdidacticTitle);

        cbRank.addItem("Primar");
        cbRank.addItem("Specialist");

        JPanel specialityPanel = new JPanel(new FlowLayout());
        specialityPanel.add(new JLabel("Specialitate:"));
        specialityPanel.add(cbSpeciality);
        specialityPanel.add(new JLabel("Rank:"));
        specialityPanel.add(cbRank);

        JPanel accPanel = new JPanel(new FlowLayout());
        accPanel.add(new JLabel("Acreditare:"));
        accPanel.add(cbAcc);


        JPanel btnSpec = new JPanel(new FlowLayout());
        btnSpec.add(btnAddSpec);
        btnSpec.add(btnDeleteSpec);

        JPanel btnAcc = new JPanel(new FlowLayout());
        btnAcc.add(btnAddAcc);
        btnAcc.add(btnDeleteAcc);

        JScrollPane jScrollPane = new JScrollPane(specializari);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setPreferredSize(new Dimension(250, 100));
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(jScrollPane, BorderLayout.NORTH);
        if(account.hasPermission("hr.read.all") || account.isSuperAdmin()) {
            tablePanel.add(specialityPanel, BorderLayout.CENTER);
            tablePanel.add(btnSpec, BorderLayout.SOUTH);
        }

        JScrollPane jScrollPane2 = new JScrollPane(acreditari);
        jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane2.setPreferredSize(new Dimension(250, 100));
        JPanel tablePanel2 = new JPanel(new BorderLayout());
        tablePanel2.add(jScrollPane2, BorderLayout.NORTH);
        if(account.hasPermission("hr.read.all") || account.isSuperAdmin()) {
            tablePanel2.add(accPanel, BorderLayout.CENTER);
            tablePanel2.add(btnAcc, BorderLayout.SOUTH);
        }

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

        JPanel patientData = new JPanel(new GridLayout(6, 2));
        patientData.add(cnpPanel);
        patientData.add(lastnamePanel);
        patientData.add(firstnamePanel);
        patientData.add(positionPanel);
        patientData.add(adressPanel);
        patientData.add(salaryPanel);
        patientData.add(ibanPanel);
        patientData.add(hoursPanel);
        patientData.add(emailPanel);
        patientData.add(contractPanel);
        patientData.add(phonePanel);
        patientData.add(datePanel);


        JPanel reportDataPanel = new JPanel();
        reportDataPanel.setLayout(new BorderLayout());
        reportDataPanel.add(patientData, BorderLayout.CENTER);
        if (tfPosition.getText().equals("Asistent Medical") && employee instanceof Nurse) {
            tftype.setEditable(false);
            tfrank.setEditable(false);
            tftype.setText(((Nurse) employee).getType());
            tfrank.setText(((Nurse) employee).getRank());
            reportDataPanel.add(nurse, BorderLayout.SOUTH);
        }

        if (tfPosition.getText().equals("Medic") && employee instanceof Doctor) {
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


        JPanel buttonsPanel = new JPanel(new FlowLayout());
        if((account.hasPermission("hr.read.all") && account.isSuperAdmin()) || (account.hasPermission("hr.read.all") &&
                model.viewRole(cnp)!="super_administrator" && model.viewRole(cnp)!="administrator"))
            buttonsPanel.add(btnSave);
        buttonsPanel.add(btnCancel);

        add(dataScrollPane, BorderLayout.NORTH);

        add(buttonsPanel, BorderLayout.SOUTH);

        setVisible(false);
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

    public void addSaveButtonListener(ActionListener actionListener) {
        btnSave.addActionListener(actionListener);
    }

    public void addCancelButtonListener(ActionListener actionListener) {
        btnCancel.addActionListener(actionListener);
    }

    public void updateCbSpeciality(Map<Integer, String> specialities) {
        this.specialities = specialities;

        cbSpeciality.removeAllItems();
        specialities.values().forEach(cbSpeciality::addItem);
    }

    public void updateCbAcc(Map<Integer, String> acc) {
        this.acc = acc;

        cbAcc.removeAllItems();
        acc.values().forEach(cbAcc::addItem);
    }

    public void addSpecialityComboBoxListener(ActionListener actionListener) {
        btnAddSpec.addActionListener(actionListener);
    }

    public void addAccComboBoxListener(ActionListener actionListener) {
        btnAddAcc.addActionListener(actionListener);
    }

    public Map.Entry<Integer, String> getIdSpeciality() {
        Optional<Map.Entry<Integer, String>> result = specialities
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().equalsIgnoreCase((String) cbSpeciality.getSelectedItem()))
                .findFirst();
        return result.orElse(null);
    }

    public Map.Entry<Integer, String> getIdAccreditation() {
        Optional<Map.Entry<Integer, String>> result = acc
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().equalsIgnoreCase((String) cbAcc.getSelectedItem()))
                .findFirst();
        return result.orElse(null);
    }


    public void deleteSpecListener(ActionListener actionListener) {
        btnDeleteSpec.addActionListener(actionListener);
    }

    public void deleteAccListener(ActionListener actionListener) {
        btnDeleteAcc.addActionListener(actionListener);
    }

    public void reset() {
        tfCnp.setText("");
        tfLastname.setText("");
        tfFirstname.setText("");
        tfPosition.setText("");
    }

    public List<Speciality> getSpecialitiesDoc() {
        return specialitiesDoc;
    }

    public Map<Integer, String> getAccDoc() {
        return accDoc;
    }

    public JComboBox<String> getCbRank() {
        return cbRank;
    }
}
