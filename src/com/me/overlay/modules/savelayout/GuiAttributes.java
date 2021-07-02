package com.me.overlay.modules.savelayout;

import java.io.Serializable;

public class GuiAttributes implements Serializable {
    private int width;
    private int height;
    private boolean alwaysOnTop;
    private int xLoc;
    private int yLoc;
    private String opacity;
    private String sizeMulti;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isAlwaysOnTop() {
        return alwaysOnTop;
    }

    public void setAlwaysOnTop(boolean alwaysOnTop) {
        this.alwaysOnTop = alwaysOnTop;
    }

    public int getxLoc() {
        return xLoc;
    }

    public void setxLoc(int xLoc) {
        this.xLoc = xLoc;
    }

    public int getyLoc() {
        return yLoc;
    }

    public void setyLoc(int yLoc) {
        this.yLoc = yLoc;
    }

    public String getOpacity() {
        return opacity;
    }

    public void setOpacity(String opacity) {
        this.opacity = opacity;
    }

    public String getSizeMulti() {
        return sizeMulti;
    }

    public void setSizeMulti(String sizeMulti) {
        this.sizeMulti = sizeMulti;
    }
}
