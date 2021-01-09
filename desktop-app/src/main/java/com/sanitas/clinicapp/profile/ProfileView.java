package com.sanitas.clinicapp.profile;

import com.sanitas.clinicapp.ui.Colors;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.swing.*;
import java.awt.*;


public class ProfileView extends JFrame {


    JButton btnBack = new StyledJButton("Inapoi").getButton();

    ProfileModel model;

    OpenProfileModel modelv = new OpenProfileModel();
    OpenProfileView viewv = new OpenProfileView(modelv);

    public ProfileView(ProfileModel model) {

        btnBack.setBackground(Colors.MAIN_COLOR.getColor());


        JPanel rightContent = new JPanel(new BorderLayout());
        rightContent.add(btnBack, BorderLayout.SOUTH);
        rightContent.setBackground(new Color(0XBDBEBF));

        JPanel content = new JPanel(new BorderLayout());
        content.add(rightContent, BorderLayout.WEST);
        content.add(viewv, BorderLayout.EAST);

        OpenProfileController controller = new OpenProfileController(modelv,viewv);

        viewv.setVisible(false);

        this.setPreferredSize(new Dimension(400, 300));
        this.setContentPane(content);
        this.pack();

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dimension.width / 2 - this.getSize().width / 2,
                dimension.height / 2 - this.getSize().height / 2);

        this.setTitle("Sanitas");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setViewvVisible() {
        viewv.setVisible(true);
    }
}
