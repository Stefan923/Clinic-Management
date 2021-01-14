package com.sanitas.clinicapp.ui;

import java.awt.*;

public enum Colors {
    MAIN_COLOR(new Color(0x22B5E7)),
    MENU_COLOR(new Color(0xDBDBDB)),
    MENU_BORDER_COLOR(new Color(0xE2E2E2)),
    BTN_DISCONNECT_COLOR(new Color(0xFD0E0E));

    private Color color;

    Colors(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
