package com.me.overlay.modules.savelayout;

import com.me.overlay.utils.GlobalVars;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static com.me.overlay.utils.GuiUtils.getOpacity;

public class SaveLayout implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            new JOptionPane();
            String folderName;
            if (GlobalVars.topMost.isSelected()) {
                GlobalVars.frame.setAlwaysOnTop(false);
                folderName = JOptionPane.showInputDialog("Please specify the name of the save:");
                GlobalVars.frame.setAlwaysOnTop(true);
            } else {
                folderName = JOptionPane.showInputDialog("Please specify the name of the save:");
            }

            File output = new File(System.getenv("APPDATA") + "\\ImageOverlay\\" + folderName);
            output.mkdir();
            GuiAttributes saveFile = new GuiAttributes();
            saveFile.setWidth(GlobalVars.frame.getWidth());
            saveFile.setHeight(GlobalVars.frame.getHeight());
            saveFile.setAlwaysOnTop(GlobalVars.topMost.isSelected());
            saveFile.setxLoc(GlobalVars.frame.getLocation().x);
            saveFile.setyLoc(GlobalVars.frame.getLocation().y);
            saveFile.setOpacity(getOpacity());
            saveFile.setSizeMulti(GlobalVars.sizeMultiplier.getText());
            FileOutputStream fileStream = new FileOutputStream(output + "\\gui.ser");
            ObjectOutputStream outputStream = new ObjectOutputStream(fileStream);
            outputStream.writeObject(saveFile);
            if (GlobalVars.pic != null) {
                ImageIO.write(GlobalVars.pic, "png", new File(output + "\\canvas.png"));
            }
            outputStream.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }
}
