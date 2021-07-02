package com.me.overlay.modules.savelayout;

import com.me.overlay.utils.GlobalVars;
import com.me.overlay.utils.GuiUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LoadLayout implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser dialog = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Serialization files (.ser)", "ser");
        dialog.setFileFilter(filter);
        dialog.setCurrentDirectory(GlobalVars.saveLocation);
        if (GlobalVars.topMost.isSelected()) {
            GlobalVars.frame.setAlwaysOnTop(false);
            dialog.showOpenDialog(null);
            GlobalVars.frame.setAlwaysOnTop(true);
        } else {
            dialog.showOpenDialog(null);
        }

        try {
            if (dialog.getSelectedFile() != null) {
                FileInputStream fileStream = new FileInputStream(dialog.getSelectedFile());
                ObjectInputStream inputStream = new ObjectInputStream(fileStream);
                Object one = inputStream.readObject();
                GuiAttributes newGui = (GuiAttributes) one;
                inputStream.close();
                GlobalVars.frame.setSize(newGui.getWidth(), newGui.getHeight());
                GlobalVars.topMost.setSelected(newGui.isAlwaysOnTop());
                GlobalVars.frame.setLocation(newGui.getxLoc(), newGui.getyLoc());
                GuiUtils.setOpacity(newGui.getOpacity());
                GlobalVars.sizeMultiplier.setText(newGui.getSizeMulti());
                if (new File(dialog.getSelectedFile().getParent() + "\\canvas.png").exists()) {
                    GlobalVars.pic = ImageIO.read(new File(dialog.getSelectedFile().getParent() + "\\canvas.png"));
                    GlobalVars.frame.repaint();
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }
}