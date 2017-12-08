package com.elevationstudios.gladerunner;

import com.elevationstudios.framework.Graphics;
import com.elevationstudios.framework.Pixmap;

import java.util.Random;

public class Obstacle {

    public int xLocation;
    public int yLocation;
    public float boxHeightScale;
    public int boxWidth;
    public int boxHeight;
    public boolean isUp;
    public Pixmap objectPix;

    public Obstacle(Graphics g, boolean up) {
        isUp = up;
        xLocation = g.getWidth();
        if (!isUp)
        {
            yLocation = (int) (g.getHeight() * 0.78);
            objectPix = g.newPixmap("rocks.png", Graphics.PixmapFormat.RGB565);
            boxWidth = g.getHeight() / 7;
            boxHeight = boxWidth;
        }
        else
        {
            yLocation = (int) (g.getHeight() * 0.54);
            objectPix = g.newPixmap("spikes.png", Graphics.PixmapFormat.RGB565);
            boxWidth = g.getHeight() / 5;
            boxHeight = boxWidth;
        }

        boxHeightScale = (float)boxHeight / (float) objectPix.getHeight();

    }
}
