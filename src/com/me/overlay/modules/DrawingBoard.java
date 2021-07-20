package com.me.overlay.modules;

import com.me.overlay.Mode;
import com.me.overlay.utils.GlobalVars;

import javax.swing.*;
import java.awt.*;

public class DrawingBoard extends JPanel {

    @Override
    public void paintComponent(Graphics g) {
        if (GlobalVars.mode == Mode.IMAGE) {
            if (GlobalVars.pic != null) {
                g.drawImage(GlobalVars.pic, 0, 0, this);
            }
        }
    }
}
