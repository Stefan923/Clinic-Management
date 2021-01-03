package com.sanitas.clinicapp.fr.panels;

import com.sanitas.clinicapp.struct.Transaction;
import com.sanitas.clinicapp.ui.DateLabelFormatter;
import com.sanitas.clinicapp.ui.StyledJButton;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

public class PanelShowTransactions extends JPanel {
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final JTable transactionsTable = new JTable();

    private List<Transaction> transactions;

    private final JButton btnViewTransactions = new StyledJButton("Afiseaza").getButton();

    private final UtilDateModel utilDateModelMin = new UtilDateModel();
    private final UtilDateModel utilDateModelMax = new UtilDateModel();

    public PanelShowTransactions() {
        setLayout(new BorderLayout());

        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        JDatePanelImpl jDatePanelMin = new JDatePanelImpl(utilDateModelMin, properties);
        JDatePickerImpl jDatePickerMin = new JDatePickerImpl(jDatePanelMin, new DateLabelFormatter());

        JDatePanelImpl jDatePanelMax = new JDatePanelImpl(utilDateModelMax, properties);
        JDatePickerImpl jDatePickerMax = new JDatePickerImpl(jDatePanelMax, new DateLabelFormatter());

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Inceput:"));
        searchPanel.add(jDatePickerMin);
        searchPanel.add(new JLabel("Sfarsit:"));
        searchPanel.add(jDatePickerMax);
        searchPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(btnViewTransactions);
        buttonsPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        JScrollPane jScrollPane = new JScrollPane(transactionsTable);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setPreferredSize(new Dimension(540, 240));

        JPanel tablePanel = new JPanel(new FlowLayout());
        tablePanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        tablePanel.add(jScrollPane);

        add(searchPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    public void updateTable(List<Transaction> transactions) {
        this.transactions = transactions;

        String[] columns = {"Tip", "Data", "Suma", "Platitor", "Beneficiar"};

        Object[][] reportsData = new Object[transactions.size()][columns.length];
        for (int i = 0; i < transactions.size(); ++i) {
            Transaction transaction = transactions.get(i);

            reportsData[i][0] = transaction.getType();
            reportsData[i][1] = DATE_FORMAT.format(transaction.getDate());
            reportsData[i][2] = transaction.getAmount();
            reportsData[i][3] = transaction.getSender();
            reportsData[i][4] = transaction.getReceiver();
        }

        transactionsTable.setModel(new DefaultTableModel(reportsData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        transactionsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        transactionsTable.setFillsViewportHeight(true);
    }

    public JTable getTransactionsTable() {
        return transactionsTable;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public UtilDateModel getUtilDateModelMin() {
        return utilDateModelMin;
    }

    public UtilDateModel getUtilDateModelMax() {
        return utilDateModelMax;
    }


    public void addViewButtonListener(ActionListener actionListener) {
        btnViewTransactions.addActionListener(actionListener);

    }
}
