package com.me.overlay.modules.clickthrough;

import com.me.overlay.utils.GlobalVars;
import com.me.overlay.utils.GuiUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClickThroughTray implements ActionListener {
    private final TrayIcon icon;
    private final SystemTray tray;


    public ClickThroughTray(TrayIcon icon, SystemTray tray) {
        this.icon = icon;
        this.tray = tray;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GuiUtils.toggleTransparent(GlobalVars.frame);
        tray.remove(icon);
        GlobalVars.clickThrough.setSelected(false);
    }
}
