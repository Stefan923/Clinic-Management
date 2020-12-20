package com.sanitas.clinicapp.ui;

import java.awt.*;

public enum Colors {
    MAIN_COLOR(new Color(0x22B5E7));

    private Color color;

    Colors(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
