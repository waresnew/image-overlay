package com.me.overlay;

import com.sun.jna.*;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.SystemTray;
import java.lang.reflect.Field;
import java.net.URL;

class Gui implements Serializable {
    File saveLocation = new File(System.getenv("APPDATA") + "\\ImageOverlay");
    transient boolean isTransparent = false;
    int origWl;
    transient BufferedImage origPic;
    transient BufferedImage pic;
    JFrame frame;
    JCheckBox topMost;
    transient JCheckBox clickThrough;
    JTextField sizeMultiplier;
    DrawingBoard image;
    private String opacity = "100";
    JCheckBox fillFrame;

    public void toggleTransparent(Component w) {
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

    public WinDef.HWND getHWnd(Component w) {
        WinDef.HWND hwnd = new WinDef.HWND();
        hwnd.setPointer(Native.getComponentPointer(w));
        return hwnd;
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();

        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String getOpacity() {
        return opacity;
    }

    public void setOpacity(String n) {
        if (isInteger(n) && Integer.parseInt(n) <= 100 && Integer.parseInt(n) >= 5) {
            frame.setOpacity(Integer.parseInt(n) * 0.01f);
            opacity = n;
        } else {
            new JOptionPane();
            if (topMost.isSelected()) {
                frame.setAlwaysOnTop(false);
                JOptionPane.showMessageDialog(null, "Choose an integer from 5-100");
                frame.setAlwaysOnTop(true);
            } else {
                JOptionPane.showMessageDialog(null, "Choose an integer from 5-100");
            }

        }
    }

    private static void unDecorate(Frame frame) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Field undecoratedField = Frame.class.getDeclaredField("undecorated");
        undecoratedField.setAccessible(true);
        undecoratedField.set(frame, true);
    }

    public Gui() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        saveLocation.mkdir();
        frame = new JFrame("Image Overlay");
        image = new DrawingBoard();
        fillFrame = new JCheckBox("Stretch Image");
        fillFrame.addActionListener(new FillFrame());
        JPanel Options = new JPanel();
        topMost = new JCheckBox("Topmost");
        clickThrough = new JCheckBox("ClickThrough");
        topMost.addItemListener(new TopMost());
        clickThrough.addItemListener(new ClickThrough());
        sizeMultiplier = new JTextField(5);
        sizeMultiplier.setText("1");
        sizeMultiplier.getDocument().addDocumentListener(new SizeMulti());
        frame.setSize(750, 750);
        JButton openFile = new JButton("Open");
        JButton changeTransparency = new JButton("Opacity");
        frame.setResizable(true);
        JMenuBar bar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem load = new JMenuItem("Load...");
        JMenuItem save = new JMenuItem("Save...");
        load.addActionListener(new LoadLayout());
        save.addActionListener(new SaveLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        openFile.addActionListener(new LoadImage());
        changeTransparency.addActionListener(new Transparency());
        Options.add(openFile);
        Options.add(changeTransparency);
        Options.add(topMost);
        Options.add(clickThrough);
        Options.add(fillFrame);
        Options.add(sizeMultiplier);
        file.add(load);
        file.add(save);
        bar.add(file);
        frame.setJMenuBar(bar);
        frame.getContentPane().add(Options, BorderLayout.SOUTH);
        frame.getContentPane().add(image);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        try {
            unDecorate(frame);
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                if (fillFrame.isSelected()) {
                    pic = resize(pic, frame.getWidth(), frame.getHeight());
                    frame.repaint();
                }
            }
        });
    }

    class FillFrame implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (pic != null) {
                if (fillFrame.isSelected()) {
                    pic = resize(pic, frame.getWidth(), frame.getHeight());
                    frame.repaint();
                } else {
                    if (isDouble(sizeMultiplier.getText()) && pic != null && Double.parseDouble(sizeMultiplier.getText()) > 0) {
                        double factor = Double.parseDouble(sizeMultiplier.getText());
                        pic = resize(pic, (int) Math.round(origPic.getWidth() * factor), (int) Math.round(origPic.getHeight() * factor));
                        frame.repaint();
                    }
                }
            }
        }
    }



    class LoadLayout implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser dialog = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Serialization files (.ser)", "ser");
            dialog.setFileFilter(filter);
            dialog.setCurrentDirectory(saveLocation);
            if (topMost.isSelected()) {
                frame.setAlwaysOnTop(false);
                dialog.showOpenDialog(null);
                frame.setAlwaysOnTop(true);
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
                    frame.setSize(newGui.width, newGui.height);
                    topMost.setSelected(newGui.alwaysOnTop);
                    frame.setLocation(newGui.xLoc, newGui.yLoc);
                    setOpacity(newGui.opacity);
                    sizeMultiplier.setText(newGui.sizeMulti);
                    if (new File(dialog.getSelectedFile().getParent() + "\\canvas.png").exists()) {
                        pic = ImageIO.read(new File(dialog.getSelectedFile().getParent() + "\\canvas.png"));
                        frame.repaint();
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }

        }
    }

    class SaveLayout implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                new JOptionPane();
                String folderName;
                if (topMost.isSelected()) {
                    frame.setAlwaysOnTop(false);
                    folderName = JOptionPane.showInputDialog("Please specify the name of the save:");
                    frame.setAlwaysOnTop(true);
                } else {
                    folderName = JOptionPane.showInputDialog("Please specify the name of the save:");
                }

                File output = new File(System.getenv("APPDATA") + "\\ImageOverlay\\" + folderName);
                output.mkdir();
                GuiAttributes saveFile = new GuiAttributes();
                saveFile.width = frame.getWidth();
                saveFile.height = frame.getHeight();
                saveFile.alwaysOnTop = topMost.isSelected();
                saveFile.xLoc = frame.getLocation().x;
                saveFile.yLoc = frame.getLocation().y;
                saveFile.opacity = getOpacity();
                saveFile.sizeMulti = sizeMultiplier.getText();
                FileOutputStream fileStream = new FileOutputStream(output + "\\gui.ser");
                ObjectOutputStream outputStream = new ObjectOutputStream(fileStream);
                outputStream.writeObject(saveFile);
                if (pic != null) {
                    ImageIO.write(pic, "png", new File(output + "\\canvas.png"));
                }
                outputStream.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }


        }
    }

    class SizeMulti implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            if (isDouble(sizeMultiplier.getText()) && pic != null && Double.parseDouble(sizeMultiplier.getText()) > 0) {
                if (sizeMultiplier.getText().equals("1")) {
                    pic = origPic;
                } else {
                    double factor = Double.parseDouble(sizeMultiplier.getText());
                    pic = resize(pic, (int) Math.round(origPic.getWidth() * factor), (int) Math.round(origPic.getHeight() * factor));
                }
                frame.repaint();
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (isDouble(sizeMultiplier.getText()) && pic != null) {
                double factor = Double.parseDouble(sizeMultiplier.getText());
                pic = resize(pic, (int) Math.round(origPic.getWidth() * factor), (int) Math.round(origPic.getHeight() * factor));
                frame.repaint();
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    }

    class TopMost implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent ev) {
            frame.setAlwaysOnTop(topMost.isSelected());
        }
    }

    class ClickThrough implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent ev) {
            if (clickThrough.isSelected()) {
                TrayIcon trayIcon;
                toggleTransparent(frame);
                SystemTray tray = SystemTray.getSystemTray();
                PopupMenu popup = new PopupMenu();
                MenuItem exit = new MenuItem("Exit");
                MenuItem disableClickthrough = new MenuItem("Disable ClickThrough");
                try {
                    URL icon = Gui.class.getResource("resources/file.jpg");
                    trayIcon = new TrayIcon(ImageIO.read(icon));

                    trayIcon.setImageAutoSize(true);
                    tray.add(trayIcon);
                    disableClickthrough.addActionListener(new DisableClickThrough(trayIcon, tray));
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



    class DisableClickThrough implements ActionListener {
        TrayIcon icon;
        SystemTray tray;

        public DisableClickThrough(TrayIcon icon, SystemTray tray) {
            this.icon = icon;
            this.tray = tray;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            toggleTransparent(frame);
            tray.remove(icon);
            clickThrough.setSelected(false);
        }
    }

    class LoadImage implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent a) {
            JFileChooser dialog = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG & JPG Images", "png", "jpg");
            dialog.setFileFilter(filter);
            if (topMost.isSelected()) {
                frame.setAlwaysOnTop(false);
                dialog.showOpenDialog(null);
                frame.setAlwaysOnTop(true);
            } else {
                dialog.showOpenDialog(null);
            }

            try {
                if (dialog.getSelectedFile() != null) {
                    pic = ImageIO.read(dialog.getSelectedFile());
                    origPic = pic;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            frame.repaint();

        }
    }

    class Transparency implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new JOptionPane();
            String output;
            if (topMost.isSelected()) {
                frame.setAlwaysOnTop(false);
                output = JOptionPane.showInputDialog("Opacity % (currently " + getOpacity() + "):");
                frame.setAlwaysOnTop(true);
            } else {
                output = JOptionPane.showInputDialog("Opacity % (currently " + getOpacity() + "):");
            }

            setOpacity(output);
        }
    }

    class DrawingBoard extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            if (pic != null) {
                g.drawImage(pic, 0, 0, this);
            }
        }
    }

}


public class ImageOverlay {
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        new Gui();
    }
}
