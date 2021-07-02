package com.me.overlay.modules.clickthrough;

import com.me.overlay.Exit;
import com.me.overlay.Gui;
import com.me.overlay.utils.GlobalVars;
import com.me.overlay.utils.GuiUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;

public class ClickThrough implements ItemListener {

    @Override
    public void itemStateChanged(ItemEvent ev) {
        if (GlobalVars.clickThrough.isSelected()) {
            TrayIcon trayIcon;
            GuiUtils.toggleTransparent(GlobalVars.frame);
            SystemTray tray = SystemTray.getSystemTray();
            PopupMenu popup = new PopupMenu();
            MenuItem exit = new MenuItem("Exit");
            MenuItem disableClickthrough = new MenuItem("Disable ClickThrough");
            try {
                URL icon = Gui.class.getResource("resources/file.jpg");
                trayIcon = new TrayIcon(ImageIO.read(icon));

                trayIcon.setImageAutoSize(true);
                tray.add(trayIcon);
                disableClickthrough.addActionListener(new ClickThroughTray(trayIcon, tray));
                exit.addActionListener(new Exit());
                popup.add(exit);
                popup.add(disableClickthrough);
                trayIcon.setPopupMenu(popup);
                trayIcon.displayMessage("Read", "Right click this tray icon and click \"Disable ClickThrough\" in order to be able to click on the window again", TrayIcon.MessageType.NONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
