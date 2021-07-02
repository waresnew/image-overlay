package com.me.overlay.utils;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GuiUtils {
    private static int origWl;
    private static boolean isTransparent;
    private static String opacity = "100";

    public static void toggleTransparent(Component w) {
        WinDef.HWND hwnd = getHWnd(w);
        if (!isTransparent) {
            int wl = User32.INSTANCE.GetWindowLong(hwnd, WinUser.GWL_EXSTYLE);
            origWl = User32.INSTANCE.GetWindowLong(hwnd, WinUser.GWL_EXSTYLE);
            wl = wl | WinUser.WS_EX_LAYERED | WinUser.WS_EX_TRANSPARENT;
            User32.INSTANCE.SetWindowLong(hwnd, WinUser.GWL_EXSTYLE, wl);
            isTransparent = true;
        } else {
            User32.INSTANCE.SetWindowLong(hwnd, WinUser.GWL_EXSTYLE, origWl);
            isTransparent = false;
        }
    }

    private static WinDef.HWND getHWnd(Component w) {
        WinDef.HWND hwnd = new WinDef.HWND();
        hwnd.setPointer(Native.getComponentPointer(w));
        return hwnd;
    }

    public static String getOpacity() {
        return opacity;
    }

    public static void setOpacity(String n) {
        if (NumberUtils.isInteger(n) && Integer.parseInt(n) <= 100 && Integer.parseInt(n) >= 5) {
            GlobalVars.frame.setOpacity(Integer.parseInt(n) * 0.01f);
            opacity = n;
        } else {
            new JOptionPane();
            if (GlobalVars.topMost.isSelected()) {
                GlobalVars.frame.setAlwaysOnTop(false);
                JOptionPane.showMessageDialog(null, "Choose an integer from 5-100");
                GlobalVars.frame.setAlwaysOnTop(true);
            } else {
                JOptionPane.showMessageDialog(null, "Choose an integer from 5-100");
            }

        }
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();

        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}
