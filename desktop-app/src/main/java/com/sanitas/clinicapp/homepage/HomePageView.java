package com.sanitas.clinicapp.homepage;

import com.sanitas.clinicapp.ui.Colors;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class HomePageView extends JFrame {

    JButton btnHR = new StyledJButton("Human resources").getButton(); // human resources
    JButton btnFR = new StyledJButton("Financial resources").getButton(); // financial resources
    JButton btnMR = new StyledJButton("Medical resources").getButton(); // medical resources
    JButton btnProfile = new StyledJButton("Your profile").getButton(); // user's profile

    public HomePageView() {
        JPanel content = new JPanel(new BorderLayout());
        content.add(loadLogo(), BorderLayout.NORTH);
        content.add(loadButtons(), BorderLayout.CENTER);

        this.setContentPane(content);
        this.pack();

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dimension.width / 2 - this.getSize().width / 2,
                dimension.height / 2 - this.getSize().height / 2);

        this.setTitle("Sanitas");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel loadButtons() {
        JPanel buttonsPanel = new JPanel(new GridLayout(2, 2));
        buttonsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        Border btnBorder = new EmptyBorder(5, 5, 5, 5);

        try {
            JPanel btnPanel = new JPanel(new FlowLayout());

            btnHR.setBackground(Colors.MAIN_COLOR.getColor());
            btnHR.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/btn-hr.png"))
                    .getScaledInstance(128, 128, java.awt.Image.SCALE_SMOOTH)));
            btnHR.setVerticalTextPosition(SwingConstants.BOTTOM);
            btnHR.setHorizontalTextPosition(SwingConstants.CENTER);
            btnPanel.add(btnHR);
            btnPanel.setBorder(btnBorder);
            buttonsPanel.add(btnPanel);

            btnPanel = new JPanel(new FlowLayout());
            btnFR.setBackground(Colors.MAIN_COLOR.getColor());
            btnFR.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/btn-fr.png"))
                    .getScaledInstance(128, 128, java.awt.Image.SCALE_SMOOTH)));
            btnFR.setVerticalTextPosition(SwingConstants.BOTTOM);
            btnFR.setHorizontalTextPosition(SwingConstants.CENTER);
            btnPanel.add(btnFR);
            btnPanel.setBorder(btnBorder);
            buttonsPanel.add(btnPanel);

            btnPanel = new JPanel(new FlowLayout());
            btnMR.setBackground(Colors.MAIN_COLOR.getColor());
            btnMR.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/btn-mr.png"))
                    .getScaledInstance(128, 128, java.awt.Image.SCALE_SMOOTH)));
            btnMR.setVerticalTextPosition(SwingConstants.BOTTOM);
            btnMR.setHorizontalTextPosition(SwingConstants.CENTER);
            btnPanel.add(btnMR);
            btnPanel.setBorder(btnBorder);
            buttonsPanel.add(btnPanel);

            btnPanel = new JPanel(new FlowLayout());
            btnProfile.setBackground(Colors.MAIN_COLOR.getColor());
            btnProfile.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/btn-profile.png"))
                    .getScaledInstance(128, 128, java.awt.Image.SCALE_SMOOTH)));
            btnProfile.setVerticalTextPosition(SwingConstants.BOTTOM);
            btnProfile.setHorizontalTextPosition(SwingConstants.CENTER);
            btnPanel.add(btnProfile);
            btnPanel.setBorder(btnBorder);
            buttonsPanel.add(btnPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buttonsPanel;
    }

    private JLabel loadLogo() {
        try {
            JLabel jLabel = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("/logo.png"))
                    .getScaledInstance(198, 48, Image.SCALE_SMOOTH)));
            jLabel.setBorder(new EmptyBorder(20, 0, 0, 0));

            return jLabel;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void addBtnHRListener(ActionListener actionListener) {
        btnHR.addActionListener(actionListener);
    }

    public void addBtnFRListener(ActionListener actionListener) {
        btnFR.addActionListener(actionListener);
    }

    public void addBtnMRListener(ActionListener actionListener) {
        btnMR.addActionListener(actionListener);
    }

    public void addBtnProfileListener(ActionListener actionListener) {
        btnProfile.addActionListener(actionListener);
    }

}
