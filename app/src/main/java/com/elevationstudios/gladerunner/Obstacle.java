package com.elevationstudios.gladerunner;

import android.graphics.Color;

import com.elevationstudios.framework.Graphics;

public class Obstacle {

    public int xLocation;
    public int yLocation;
    public int boxHeight;
    public int boxWidth;

    public Obstacle(Graphics g) {
        xLocation = g.getWidth();
        yLocation = (int)(g.getHeight()*0.8);
        boxHeight = g.getHeight() / 7;
        boxWidth = boxHeight;
    }
}
