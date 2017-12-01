package com.elevationstudios.framework.impl;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.elevationstudios.framework.Audio;
import com.elevationstudios.framework.FileIO;
import com.elevationstudios.framework.Game;
import com.elevationstudios.framework.Graphics;
import com.elevationstudios.framework.Input;
import com.elevationstudios.framework.Screen;
//import com.elevationstudios.gladerunner.R;
//import com.google.android.gms.games.Games;
//import com.google.example.games.basegameutils.BaseGameActivity;

public abstract class AndroidGame extends Activity implements Game {
    AndroidFastRenderView renderView;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;

    static final int REQUEST_LEADERBOARD = 100;
    static final int REQUEST_ACHIEVEMENTS = 200;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        int frameBufferWidth = isLandscape ? 1280 : 800;
        int frameBufferHeight = isLandscape ? 800 : 1280;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Config.RGB_565);

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);

        // determine the scale based on our framebuffer and our display sizes
        float scaleX = (float) frameBufferWidth / size.x;
        float scaleY = (float) frameBufferHeight / size.y;

        renderView = new AndroidFastRenderView(this, frameBuffer);
        graphics = new AndroidGraphics(getAssets(), frameBuffer);
        fileIO = new AndroidFileIO(getAssets());
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, renderView, scaleX, scaleY);
        screen = getStartScreen();



        setContentView(renderView);
    }

    @Override
    public void onResume() {
        super.onResume();
        screen.resume();
        renderView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        renderView.pause();
        screen.pause();
        if (isFinishing())
            screen.dispose();
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    @Override
    public Graphics getGraphics() {
        return graphics;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen is null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }
    
    public Screen getCurrentScreen() {
        return screen;
    }

   /* public boolean isSignedIn(){
        return getGameHelper().isSignedIn();
    }
    public void signIn(){
        getGameHelper().beginUserInitiatedSignIn();
    }
    public void submitScore(int score){
       // Games.Leaderboards.submitScore(getGameHelper().getApiClient(), getString(R.string.Leaderboard_top_score), score);
    }
    public void showLeaderboards(){
        //startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(), getString(R.string.Leaderboard_top_score)), REQUEST_LEADERBOARD);
    }
    public void showAchievements(){
       startActivityForResult(Games.Achievements.getAchievementsIntent(getApiClient()), REQUEST_ACHIEVEMENTS);
    }
*/

}