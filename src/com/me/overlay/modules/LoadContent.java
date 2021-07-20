package com.me.overlay.modules;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import com.me.overlay.Mode;
import com.me.overlay.utils.GlobalVars;
import com.me.overlay.utils.GuiUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoadContent implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent a) {
        if (GlobalVars.mode == Mode.IMAGE) {
            JFileChooser dialog = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG & JPG Images", "png", "jpg");
            dialog.setFileFilter(filter);
            if (GlobalVars.topMost.isSelected()) {
                GlobalVars.frame.setAlwaysOnTop(false);
                dialog.showOpenDialog(null);
                GlobalVars.frame.setAlwaysOnTop(true);
            } else {
                dialog.showOpenDialog(null);
            }

            try {
                if (dialog.getSelectedFile() != null) {
                    GlobalVars.pic = ImageIO.read(dialog.getSelectedFile());
                    GlobalVars.origPic = GlobalVars.pic;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            GlobalVars.frame.repaint();

        } else if (GlobalVars.mode == Mode.VIDEO) {
            GlobalVars.frame.repaint();
            if (GlobalVars.topMost.isSelected()) {
                GlobalVars.frame.setAlwaysOnTop(false);
                GlobalVars.youtubeUrl = JOptionPane.showInputDialog("Youtube URL:");
                GlobalVars.frame.setAlwaysOnTop(true);
            } else {
                GlobalVars.youtubeUrl = JOptionPane.showInputDialog("Youtube URL:");
            }




            if (GlobalVars.youtubeUrl != null) {

                if (GlobalVars.youtubeUrl.matches("(http|https)://www\\.youtube\\.com/watch\\?v=.+")) {
                    JWebBrowser browser = new JWebBrowser();
                    browser.setBarsVisible(false);
                    browser.navigate("http://www.youtube.com/embed/" + GlobalVars.youtubeUrl.substring(GlobalVars.youtubeUrl.lastIndexOf("=") + 1));
                    GlobalVars.frame.getContentPane().add(browser);
                    GlobalVars.drawingBoard.setVisible(true);
                } else {
                    //error dialog with criteriaa here
                }
            } else {
                //same
            }
        }
    }
}
