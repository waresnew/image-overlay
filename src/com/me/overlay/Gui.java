package com.me.overlay;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import com.me.overlay.modules.Transparency;
import com.me.overlay.modules.*;
import com.me.overlay.modules.clickthrough.ClickThrough;
import com.me.overlay.modules.savelayout.LoadLayout;
import com.me.overlay.modules.savelayout.SaveLayout;
import com.me.overlay.utils.GlobalVars;
import com.me.overlay.utils.GuiUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.lang.reflect.Field;

public class Gui {

    private static Gui instance;

    private Gui() {
    }

    public static Gui getInstance() {
        if (instance == null) {
            synchronized (Gui.class) {
                if (instance == null) {
                    instance = new Gui();
                }
            }
        }
        return instance;
    }

    public void init() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        NativeInterface.open();
        GlobalVars.saveLocation.mkdir();
        GlobalVars.frame = new JFrame("Image Overlay");
        GlobalVars.drawingBoard = new DrawingBoard();
        GlobalVars.fillFrame = new JCheckBox("Stretch Image");
        GlobalVars.fillFrame.addActionListener(new FillFrame());
        JPanel Options = new JPanel();
        GlobalVars.topMost = new JCheckBox("Topmost");
        GlobalVars.clickThrough = new JCheckBox("ClickThrough");
        GlobalVars.modeButton = new JButton("Image");
        GlobalVars.topMost.addItemListener(new TopMost());
        GlobalVars.clickThrough.addItemListener(new ClickThrough());
        GlobalVars.sizeMultiplier = new JTextField(5);
        GlobalVars.sizeMultiplier.setText("1");
        GlobalVars.sizeMultiplier.getDocument().addDocumentListener(new SizeMulti());
        GlobalVars.frame.setSize(750, 750);
        JButton openFile = new JButton("Open");
        JButton changeTransparency = new JButton("Opacity");
        GlobalVars.frame.setResizable(true);
        JMenuBar bar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem load = new JMenuItem("Load...");
        JMenuItem save = new JMenuItem("Save...");
        load.addActionListener(new LoadLayout());
        save.addActionListener(new SaveLayout());
        GlobalVars.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        openFile.addActionListener(new LoadContent());
        changeTransparency.addActionListener(new Transparency());
        GlobalVars.modeButton.addActionListener(new ChangeMode());
        Options.add(openFile);
        Options.add(changeTransparency);
        Options.add(GlobalVars.topMost);
        Options.add(GlobalVars.clickThrough);
        Options.add(GlobalVars.fillFrame);
        Options.add(GlobalVars.modeButton);
        Options.add(GlobalVars.sizeMultiplier);
        file.add(load);
        file.add(save);
        bar.add(file);
        GlobalVars.frame.setJMenuBar(bar);
        GlobalVars.frame.getContentPane().add(Options, BorderLayout.SOUTH);
        GlobalVars.frame.getContentPane().add(GlobalVars.drawingBoard);
        GlobalVars.frame.setLocationRelativeTo(null);
        GlobalVars.mode = Mode.IMAGE;
        GlobalVars.frame.setVisible(true);
        try {
            unDecorate(GlobalVars.frame);
        } catch (Exception e) {
            e.printStackTrace();
        }
        NativeInterface.runEventPump();
        Runtime.getRuntime().addShutdownHook(new Thread(NativeInterface::close));
        GlobalVars.frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                if (GlobalVars.fillFrame.isSelected()) {
                    GlobalVars.pic = GuiUtils.resize(GlobalVars.pic, GlobalVars.frame.getWidth(), GlobalVars.frame.getHeight());
                    GlobalVars.frame.repaint();
                }
            }
        });
    }
    private void unDecorate(Frame frame) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Field undecoratedField = Frame.class.getDeclaredField("undecorated");
        undecoratedField.setAccessible(true);
        undecoratedField.set(frame, true);
    }
}

