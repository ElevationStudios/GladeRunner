package com.elevationstudios.gladerunner;

import android.util.Log;

import com.elevationstudios.framework.Screen;
import com.elevationstudios.framework.impl.AndroidGame;


public class GladeRunner extends AndroidGame {

    @Override
    public Screen getStartScreen(){
        Log.d("GladeRunner.java", "Starting up");
        return new LoadingScreen(this);
    }

    @Override
    public void onSignInFailed(){

    }
    @Override
    public void onSignInSucceeded(){

    }

}
