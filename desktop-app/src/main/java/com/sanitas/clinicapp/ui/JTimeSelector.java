package com.sanitas.clinicapp.ui;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.logging.Logger;

public final class JTimeSelector extends javax.swing.JPanel {

    private Date currentTime;
    private static final Logger LOGGER = Logger.getLogger(JTimeSelector.class.getName());
    private javax.swing.JSpinner hourSpin;
    private javax.swing.JSpinner minuteSpin;

    /**
     * Creates a Hour and Minute Chooser with the specified values.
     *
     * @param hour 24 hour format hour
     * @param minute minutes
     */
    public JTimeSelector(int hour, int minute) {
        setName("JTimeSelector");
        initComponents();
        currentTime = new Date();
        currentTime.setHours(hour);
        currentTime.setMinutes(minute);
        updateCurrentTimeInGUI();
    }

    public JTimeSelector() {
        setName("JTimeSelector");
        initComponents();
        setCurrentTime();
    }

    @Override
    public void setEnabled(boolean enable) {
        hourSpin.setEnabled(enable);
        minuteSpin.setEnabled(enable);
    }

    public void setCurrentTime() {
        currentTime = new Date();
        updateCurrentTimeInGUI();
    }

    public void setTimeFromString(String toBeParsed){
        if(toBeParsed.matches("\\d{1,2}\\s*\\:\\s*\\d{2}\\s+[AP]M")){
            String[] splitted = toBeParsed.split("\\:");
            currentTime = new Date();
            int hour=Integer.parseInt(splitted[0]);
            if("PM".equals(splitted[2])){
                hour+=12;
            }
            currentTime.setHours(hour);
            int minutes=Integer.parseInt(splitted[1]);
            currentTime.setMinutes(minutes);
        }
        updateCurrentTimeInGUI();
    }

    private void updateCurrentTimeInGUI() {
        LOGGER.finest(currentTime.toString());
        hourSpin.setValue(numberFormat(currentTime.getHours(), "##"));

        minuteSpin.setValue(numberFormat(currentTime.getMinutes(), "##"));
    }

    public static String numberFormat(long src, String fmt) {//Format : ###.####
        DecimalFormat df = new DecimalFormat(fmt.replaceAll("#", "0"));
        return df.format(src);
    }

    public String getHour() {
        return numberFormat(Integer.parseInt(hourSpin.getValue().toString().trim()), "##");
    }

    public String getMinute() {
        return numberFormat(Integer.parseInt(minuteSpin.getValue().toString().trim()), "##");
    }

    public long getTime() {
        return (Integer.parseInt(hourSpin.getValue().toString().trim()) * 60 + Integer.parseInt(minuteSpin.getValue().toString().trim())) * 60 * 1000;
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        hourSpin = new javax.swing.JSpinner();
        minuteSpin = new javax.swing.JSpinner();

        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 0));

        hourSpin.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        hourSpin.setModel(new javax.swing.SpinnerListModel(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));
        add(hourSpin);

        minuteSpin.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        minuteSpin.setModel(new javax.swing.SpinnerListModel(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"}));
        add(minuteSpin);
    }


    public Date getCurrentTime() {
        return new Date(currentTime.getTime());
    }
}