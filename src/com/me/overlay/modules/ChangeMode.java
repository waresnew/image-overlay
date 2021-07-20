package com.me.overlay.modules;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import com.me.overlay.Mode;
import com.me.overlay.utils.GlobalVars;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeMode implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (GlobalVars.mode == Mode.IMAGE) {
            GlobalVars.mode = Mode.VIDEO;
            GlobalVars.drawingBoard.setVisible(false);
            GlobalVars.pic = null;
            GlobalVars.modeButton.setText("Video");
        } else if (GlobalVars.mode == Mode.VIDEO) {
            GlobalVars.mode = Mode.IMAGE;
            GlobalVars.youtubeUrl = null;
            GlobalVars.drawingBoard.setVisible(true);
            for (Component component : GlobalVars.frame.getContentPane().getComponents()) {
                if (component instanceof JWebBrowser) {
                    GlobalVars.frame.getContentPane().remove(component);
                    break;
                }
            }
            GlobalVars.modeButton.setText("Image");
        }
    }
}
