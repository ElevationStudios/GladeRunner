package com.elevationstudios.gladerunner;


import com.elevationstudios.framework.Graphics;
import com.elevationstudios.framework.Pixmap;

public class HealthPickup {
    public int xLocation;
    public int yLocation;
    public float boxHeightScale;
    public int boxWidth;
    public int boxHeight;
    public boolean isUp;
    public Pixmap objectPix;

    public HealthPickup(Graphics g, boolean up) {

        isUp = up;
        if (!isUp)
        {
            yLocation = (int) (g.getHeight() * 0.78);
        }
        else
        {
            yLocation = (int) (g.getHeight() * 0.54);
        }

        objectPix = g.newPixmap("health.png", Graphics.PixmapFormat.RGB565);
        boxWidth = g.getHeight() / 15;
        boxHeight = boxWidth;

        boxHeightScale = (float)boxHeight / (float) objectPix.getHeight();

    }
}