package com.sanitas.clinicapp.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class StyledJButton extends BasicButtonUI {

    JButton button;

    public StyledJButton(String text) {
        button = new JButton(text);

        button.setFont(new Font("Calibri", Font.PLAIN, 14));
        button.setBackground(new Color(0x2dce98));
        button.setForeground(Color.white);

        button.setUI(this);
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setOpaque(false);
        button.setBorder(new EmptyBorder(5, 15, 5, 15));
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        paintBackground(g, b, b.getModel().isPressed() ? 2 : 0);
        super.paint(g, c);
    }

    private void paintBackground(Graphics g, JComponent c, int yOffset) {
        Dimension size = c.getSize();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(c.getBackground().darker());
        g.fillRoundRect(0, yOffset, size.width, size.height - yOffset, 10, 10);
        g.setColor(c.getBackground());
        g.fillRoundRect(0, yOffset, size.width, size.height + yOffset - 5, 10, 10);
    }

    public JButton getButton() {
        return button;
    }

}
