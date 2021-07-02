package com.me.overlay.modules;

import com.me.overlay.utils.GlobalVars;
import com.me.overlay.utils.GuiUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Transparency implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        new JOptionPane();
        String output;
        if (GlobalVars.topMost.isSelected()) {
            GlobalVars.frame.setAlwaysOnTop(false);
            output = JOptionPane.showInputDialog("Opacity % (currently " + GuiUtils.getOpacity() + "):");
            GlobalVars.frame.setAlwaysOnTop(true);
        } else {
            output = JOptionPane.showInputDialog("Opacity % (currently " + GuiUtils.getOpacity() + "):");
        }

        GuiUtils.setOpacity(output);
    }
}