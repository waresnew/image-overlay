package com.me.overlay.modules;

import com.me.overlay.utils.GlobalVars;
import com.me.overlay.utils.NumberUtils;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import static com.me.overlay.utils.GuiUtils.resize;

public class SizeMulti implements DocumentListener {
    @Override
    public void insertUpdate(DocumentEvent e) {
        if (NumberUtils.isDouble(GlobalVars.sizeMultiplier.getText()) && GlobalVars.pic != null && Double.parseDouble(GlobalVars.sizeMultiplier.getText()) > 0) {
            if (GlobalVars.sizeMultiplier.getText().equals("1")) {
                GlobalVars.pic = GlobalVars.origPic;
            } else {
                double factor = Double.parseDouble(GlobalVars.sizeMultiplier.getText());
                GlobalVars.pic = resize(GlobalVars.pic, (int) Math.round(GlobalVars.origPic.getWidth() * factor), (int) Math.round(GlobalVars.origPic.getHeight() * factor));
            }
            GlobalVars.frame.repaint();
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (NumberUtils.isDouble(GlobalVars.sizeMultiplier.getText()) && GlobalVars.pic != null) {
            double factor = Double.parseDouble(GlobalVars.sizeMultiplier.getText());
            GlobalVars.pic = resize(GlobalVars.pic, (int) Math.round(GlobalVars.origPic.getWidth() * factor), (int) Math.round(GlobalVars.origPic.getHeight() * factor));
            GlobalVars.frame.repaint();
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
}