package com.elevationstudios.framework.impl;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.elevationstudios.framework.Audio;
import com.elevationstudios.framework.FileIO;
import com.elevationstudios.framework.Game;
import com.elevationstudios.framework.Graphics;
import com.elevationstudios.framework.Input;
import com.elevationstudios.framework.Screen;
import com.elevationstudios.gladerunner.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;

public abstract class AndroidGame extends BaseGameActivity implements Game {
    AndroidFastRenderView renderView;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    AdView adView;
    InterstitialAd mInterstitialAd;

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


        //Ads
        MobileAds.initialize(this, "ca-app-pub-3019710358791217~5384423978");

        //Create and load adview
        adView = new AdView(this);
        adView.setAdUnitId(getString(R.string.banner_ad_unit_id));
        adView.setAdSize(AdSize.SMART_BANNER);

        //create a realtivelayout as the main layout and add the gameview
        RelativeLayout mainLayout = new RelativeLayout(this);
        mainLayout.addView(renderView);

        //add Adview on top of this screen
        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        mainLayout.addView(adView, adParams);

        //Interstitial Ad (Full screen)
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3019710358791217/9536912665");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed(){
                //load next interstitial
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });



        screen = getStartScreen();

        setContentView(mainLayout);
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

    public boolean isSignedIn(){
        return getGameHelper().isSignedIn();
    }
    public void signIn(){
        getGameHelper().beginUserInitiatedSignIn();
    }
    public void submitScore(int score){
        Games.Leaderboards.submitScore(getGameHelper().getApiClient(), getString(R.string.leaderboard_high_scores), score);
    }
    public void showLeaderboards(){
        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(), getString(R.string.leaderboard_high_scores)), REQUEST_LEADERBOARD);
    }
    public void showAchievements(){
       startActivityForResult(Games.Achievements.getAchievementsIntent(getApiClient()), REQUEST_ACHIEVEMENTS);
    }

    public void incrementDeaths(){
        Games.Achievements.unlock(getApiClient(), getString(R.string.achievement_death));
        Games.Achievements.increment(getApiClient(), getString(R.string.achievement_dj_vu), 1);
        Games.Achievements.increment(getApiClient(), getString(R.string.achievement_ive_been_in_this_place_before), 1);
    }

    public void incrementRunDistance(int num) {
        Games.Achievements.increment(getApiClient(), getString(R.string.achievement_casual_jogger), num);
        Games.Achievements.increment(getApiClient(), getString(R.string.achievement_getting_in_shape), num);
        Games.Achievements.increment(getApiClient(), getString(R.string.achievement_adrenaline_junkie), num);
        if (num > 10)
            Games.Achievements.increment(getApiClient(), getString(R.string.achievement_marathon_runner), (num/10));
        checkRunAchieves(num);

    }
    public void checkRunAchieves(int num) {
        if (num >= 40)
            Games.Achievements.unlock(getApiClient(), getString(R.string.achievement_beginner_runner));

        if (num >= 100)
            Games.Achievements.unlock(getApiClient(), getString(R.string.achievement_100m_dash));

        if (num >= 200)
            Games.Achievements.unlock(getApiClient(), getString(R.string.achievement_endurance_runner));
    }

    public void incrementGoldCollected(int num){
        if (num > 10) {
            Games.Achievements.increment(getApiClient(), getString(R.string.achievement_treasure_hunter), num / 10);
            Games.Achievements.increment(getApiClient(), getString(R.string.achievement_hoarder), num / 10);
            Games.Achievements.increment(getApiClient(), getString(R.string.achievement_kleptomaniac), num / 10);
        }
        checkGoldAchieves(num);
    }

    public void checkGoldAchieves(int num) {
        if (num >= 1000)
            Games.Achievements.unlock(getApiClient(), getString(R.string.achievement_makin_some_cash));
        if (num >= 5000)
            Games.Achievements.unlock(getApiClient(), getString(R.string.achievement_getting_greedy));
        if (num >= 10000)
            Games.Achievements.unlock(getApiClient(), getString(R.string.achievement_ninjapreneur));

    }

    public void incrementZombieKills() {
        Games.Achievements.increment(getApiClient(), getString(R.string.achievement_hunter), 1);
        Games.Achievements.increment(getApiClient(), getString(R.string.achievement_witcher), 1);
        Games.Achievements.increment(getApiClient(), getString(R.string.achievement_massacre), 1);
    }

    public void showBanner(){
        this.runOnUiThread(new Runnable(){
            public void run(){
                adView.setVisibility(View.VISIBLE);
                adView.loadAd(new AdRequest.Builder().build());
                //adView.loadAd(new AdRequest.Builder().addTestDevice("516A69C16880F784D6CEBD97C4CC403E").build());
                Log.d("BannerAd", "Showing Banner");
            }
        });

    }
    public void hideBanner(){
        this.runOnUiThread(new Runnable() {
               public void run() {
                   adView.setVisibility(View.GONE);
                   Log.d("BannerAd", "Hiding Banner");
               }
        });
    }
    public void showInterstitialAd(){
        this.runOnUiThread(new Runnable() {
            public void run() {
                if(mInterstitialAd.isLoaded()){
                    mInterstitialAd.show();
                    Log.d("InterstitialAd", "Showing interstitial");
                }
            }
        });
    }

}