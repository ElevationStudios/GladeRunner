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

    public HealthPickup(Graphics g, boolean up) {

        isUp = up;
        xLocation = g.getWidth();
        if (!isUp)
        {
            yLocation = (int) (g.getHeight() * 0.78);
        }
        else
        {
            yLocation = (int) (g.getHeight() * 0.64);
        }

        boxWidth = g.getHeight() / 15;
        boxHeight = boxWidth;

        boxHeightScale = (float)boxHeight / (float) Assets.health.getHeight();

    }
}