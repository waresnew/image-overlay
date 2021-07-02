package com.me.overlay.modules;

import com.me.overlay.utils.GlobalVars;
import com.me.overlay.utils.GuiUtils;
import com.me.overlay.utils.NumberUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FillFrame implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if (GlobalVars.pic != null) {
            if (GlobalVars.fillFrame.isSelected()) {
                GlobalVars.pic = GuiUtils.resize(GlobalVars.pic, GlobalVars.frame.getWidth(), GlobalVars.frame.getHeight());
                GlobalVars.frame.repaint();
            } else {
                if (NumberUtils.isDouble(GlobalVars.sizeMultiplier.getText()) && GlobalVars.pic != null && Double.parseDouble(GlobalVars.sizeMultiplier.getText()) > 0) {
                    double factor = Double.parseDouble(GlobalVars.sizeMultiplier.getText());
                    GlobalVars.pic = GuiUtils.resize(GlobalVars.pic, (int) Math.round(GlobalVars.origPic.getWidth() * factor), (int) Math.round(GlobalVars.origPic.getHeight() * factor));
                    GlobalVars.frame.repaint();
                }
            }
        }
    }
}
