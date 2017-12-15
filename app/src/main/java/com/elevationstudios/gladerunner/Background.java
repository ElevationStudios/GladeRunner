package com.elevationstudios.gladerunner;

import com.elevationstudios.framework.Graphics;
import com.elevationstudios.framework.Pixmap;

public class Background {
    public int xLocation;
    public Pixmap background;


    public Background(int xLoc, Pixmap map) {
        this.xLocation  = xLoc;
        this.background = map;
    }

}
