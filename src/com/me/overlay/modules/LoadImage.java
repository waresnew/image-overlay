package com.me.overlay.modules;

import com.me.overlay.utils.GlobalVars;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoadImage implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent a) {
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

    }
}
