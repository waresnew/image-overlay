package com.me.overlay.modules;

import com.me.overlay.utils.GlobalVars;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class TopMost implements ItemListener {
    @Override
    public void itemStateChanged(ItemEvent ev) {
        GlobalVars.frame.setAlwaysOnTop(GlobalVars.topMost.isSelected());
    }
}