package com.me.overlay.utils;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import com.me.overlay.Mode;
import com.me.overlay.modules.DrawingBoard;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class GlobalVars {

    public static final File saveLocation = new File(System.getenv("APPDATA") + "\\ImageOverlay");
    public static JFrame frame;
    public static JCheckBox topMost;
    public static JCheckBox clickThrough;
    public static JTextField sizeMultiplier;
    public static DrawingBoard drawingBoard;
    public static BufferedImage origPic;
    public static BufferedImage pic;
    public static JCheckBox fillFrame;
    public static String youtubeUrl;
    public static Mode mode = Mode.IMAGE;
    public static JButton modeButton;
}
