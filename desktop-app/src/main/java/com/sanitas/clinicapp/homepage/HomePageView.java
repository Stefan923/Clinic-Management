package com.sanitas.clinicapp.homepage;

import com.sanitas.clinicapp.ui.Colors;
import com.sanitas.clinicapp.ui.StyledJButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class HomePageView extends JFrame {

    private final JButton btnHR = new StyledJButton("Human resources").getButton(); // human resources
    private final JButton btnFR = new StyledJButton("Financial resources").getButton(); // financial resources
    private final JButton btnMR = new StyledJButton("Medical resources").getButton(); // medical resources
    private final JButton btnProfile = new StyledJButton("Your profile").getButton(); // user's profile

    private final JButton btnDisconnect = new StyledJButton("Deconecteaza-te").getButton(); // disconnect button

    public HomePageView() {
        JPanel disconnectPanel = new JPanel(new FlowLayout());
        btnDisconnect.setBackground(Colors.BTN_DISCONNECT_COLOR.getColor());
        disconnectPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        disconnectPanel.add(btnDisconnect);

        JPanel content = new JPanel(new BorderLayout());
        content.add(loadLogo(), BorderLayout.NORTH);
        content.add(loadButtons(), BorderLayout.CENTER);
        content.add(disconnectPanel, BorderLayout.SOUTH);

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

        try {
            buttonsPanel.add(getButtonPanel(btnHR, "/btn-hr.png"));
            buttonsPanel.add(getButtonPanel(btnFR, "/btn-fr.png"));
            buttonsPanel.add(getButtonPanel(btnMR, "/btn-mr.png"));
            buttonsPanel.add(getButtonPanel(btnProfile, "/btn-profile.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buttonsPanel;
    }

    private JPanel getButtonPanel(JButton btn, String file) throws IOException {
        JPanel btnPanel = new JPanel(new FlowLayout());
        btn.setBackground(Colors.MAIN_COLOR.getColor());
        btn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource(file))
                .getScaledInstance(128, 128, Image.SCALE_SMOOTH)));
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btnPanel.add(btn);
        btnPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        return btnPanel;
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

    public void sendError(String message) {
        JOptionPane.showMessageDialog(this, message, "Eroare!", JOptionPane.ERROR_MESSAGE);
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

    public void addBtnDisconnectListener(ActionListener actionListener) {
        btnDisconnect.addActionListener(actionListener);
    }

}
