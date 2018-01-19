package com.elevationstudios.gladerunner;

import android.util.Log;

import com.elevationstudios.framework.Game;
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

    @Override
    protected void onStop() {
        super.onStop();
        Assets.currentMusic.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed()
    {

    }

}

