package com.me.overlay.utils;

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
}
