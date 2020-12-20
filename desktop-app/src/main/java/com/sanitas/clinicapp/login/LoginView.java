package com.sanitas.clinicapp.login;

import com.sanitas.clinicapp.ui.Colors;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginView extends JFrame {

    private JTextField tfUsername = new JTextField(15);
    private JPasswordField pfPassword = new JPasswordField(15);

    private JButton btnLogin = new StyledJButton("Log In").getButton();

    public LoginView() {
        JPanel userPanel = new JPanel(new FlowLayout());
        userPanel.add(new JLabel("Username"));
        userPanel.add(tfUsername);

        JPanel passPanel = new JPanel(new FlowLayout());
        passPanel.add(new JLabel("Password"), BorderLayout.WEST);
        passPanel.add(pfPassword, BorderLayout.EAST);

        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.add(userPanel, BorderLayout.NORTH);
        loginPanel.add(passPanel, BorderLayout.CENTER);
        loginPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JPanel content = new JPanel(new BorderLayout());
        content.add(loadLogo(), BorderLayout.NORTH);
        content.add(loginPanel, BorderLayout.CENTER);
        btnLogin.setBackground(Colors.MAIN_COLOR.getColor());
        content.add(btnLogin, BorderLayout.SOUTH);
        content.setBorder(new EmptyBorder(0, 5, 10, 5));

        this.setContentPane(content);
        this.pack();

        this.setTitle("Sanitas");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JLabel loadLogo() {
        try {
            JLabel jLabel = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("/logo.png"))
                    .getScaledInstance(198, 48, Image.SCALE_SMOOTH)));
            jLabel.setBorder(new EmptyBorder(20, 20, 20, 20));

            return jLabel;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void addBtnLoginListener(ActionListener actionListener) {
        btnLogin.addActionListener(actionListener);
    }

    public JTextField getTfUsername() {
        return tfUsername;
    }

    public JPasswordField getPfPassword() {
        return pfPassword;
    }
}
