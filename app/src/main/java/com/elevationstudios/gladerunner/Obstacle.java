package com.elevationstudios.gladerunner;

import android.graphics.Color;
import android.util.Log;

import com.elevationstudios.framework.Graphics;
import com.elevationstudios.framework.Pixmap;

import java.util.Random;

public class Obstacle {

    public int xLocation;
    public int yLocation;
    public float boxHeightScale;
    public int boxWidth;
    public boolean isUp;
    public Pixmap objectPix;

    public Obstacle(Graphics g, boolean up) {
        objectPix = g.newPixmap("rocks.png", Graphics.PixmapFormat.RGB565);
        isUp = up;
        xLocation = g.getWidth();
        if (!isUp)
            yLocation = (int)(g.getHeight()*0.78);
        else
            yLocation = (int)(g.getHeight()*0.63);
        boxWidth = g.getHeight() / 7;
        boxHeightScale = (float)boxWidth / (float) objectPix.getWidth();

    }
}
