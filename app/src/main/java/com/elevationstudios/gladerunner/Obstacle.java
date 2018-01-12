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
            objectPix = Assets.rocks;
            boxWidth = g.getHeight() / 8;
            boxHeight = boxWidth;
        }
        else
        {
            yLocation = (int) (g.getHeight() * 0.54);
            objectPix = Assets.spikes;
            boxWidth = g.getHeight() / 5;
            boxHeight = boxWidth;
        }

        boxHeightScale = (float)boxHeight / (float) objectPix.getHeight();

    }
}
