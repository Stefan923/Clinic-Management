package com.sanitas.clinicapp.profile;

import com.sanitas.clinicapp.ClinicApplication;
import com.sanitas.clinicapp.profile.panels.PanelEmployeeProfile;
import com.sanitas.clinicapp.ui.Colors;
import com.sanitas.clinicapp.ui.StyledJButton;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ProfileView extends JFrame {

    private JButton btnBack = new StyledJButton("Inapoi").getButton();

    private ProfileModel model;
    private JPanel currentPanel;
    private PanelEmployeeProfile viewv ;

    public ProfileView(ProfileModel model) {

        viewv= new PanelEmployeeProfile(model.getEmployee(ClinicApplication.getUser().getCnp()));
        currentPanel=viewv;
        viewv.setVisible(true);

        btnBack.setBackground(Colors.MAIN_COLOR.getColor());


        JPanel leftContent = new JPanel(new BorderLayout());
        leftContent.add(btnBack, BorderLayout.SOUTH);
        leftContent.setBackground(new Color(0XBDBEBF));

        JPanel content = new JPanel(new BorderLayout());
        content.add(leftContent, BorderLayout.WEST);
        content.add(viewv, BorderLayout.CENTER);


        this.setPreferredSize(new Dimension(820, 420));
        this.setContentPane(content);
        this.pack();

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dimension.width / 2 - this.getSize().width / 2,
                dimension.height / 2 - this.getSize().height / 2);

        this.setTitle("Sanitas");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setRightPanel(JPanel jPanel) {
        currentPanel.setVisible(false);

        getContentPane().add(jPanel, BorderLayout.CENTER);
        currentPanel = jPanel;
        currentPanel.setVisible(true);
    }

    public PanelEmployeeProfile getViewv() {
        return viewv;
    }

    public JButton getBtnBack() {
        return btnBack;
    }


    public JPanel getCurrentPanel() {
        return currentPanel;
    }


    public void addBackListener(ActionListener actionListener) {
        btnBack.addActionListener(actionListener);
    }

    public void sendError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error!", JOptionPane.ERROR_MESSAGE);
    }

    public void sendSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Succes!", JOptionPane.INFORMATION_MESSAGE);
    }


}
