package com.elevationstudios.gladerunner;

import android.util.Log;

import com.elevationstudios.framework.Graphics;
import com.elevationstudios.framework.Pixmap;
import com.elevationstudios.framework.Sound;
/**
 * Created by Kenneth on 30-Nov-17.
 */

public class Assets {
    public static Pixmap background;

    public static Pixmap ninjaSprite[][] = new Pixmap[8][10];
    //0-3 = state, 4-5 = groundattacks, 6-7 = aerialattacks

    public static Pixmap zombieSprite[][] = new Pixmap[3][12];
    //0 = idle, 1 = attack, 2 = dead

}
