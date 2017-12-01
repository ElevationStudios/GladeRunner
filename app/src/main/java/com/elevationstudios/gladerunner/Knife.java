package com.elevationstudios.gladerunner;

import com.elevationstudios.framework.Game;
import com.elevationstudios.framework.Graphics;
import com.elevationstudios.framework.Pixmap;

/**
 * @author Andrew Caruso
 * @since 30.11.2017
 */
public class Knife {

    public int xLocation;
    public int yLocation;
    public float boxHeightScale;
    public int boxWidth;
    public int boxHeight;
    public Pixmap objectPix;

    public Knife(GameScreen game) {
        final Graphics g = game.game.getGraphics();
        objectPix = g.newPixmap("knife.png", Graphics.PixmapFormat.RGB565);
        xLocation = (int) (g.getWidth()*0.1) + 20;
        yLocation = g.getHeight() - 200;


        boxWidth = g.getHeight() / 7;
        boxHeight = boxWidth;

        boxHeightScale = (float)boxHeight / (float) objectPix.getHeight();

    }
}
