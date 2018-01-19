package com.elevationstudios.framework;

public interface Game
{
    public Input getInput();
    public FileIO getFileIO();
    public Graphics getGraphics();
    public Audio getAudio();
    public void setScreen(Screen screen);
    public Screen getCurrentScreen();
    public Screen getStartScreen();

    public boolean isSignedIn();
    public void signIn();
    public void submitScore(int score);
    public void showLeaderboards();
    public void showAchievements();

    public void incrementDeaths();
    public void incrementRunDistance(int num);
    public void incrementGoldCollected(int num);
    public void incrementZombieKills();
    public void checkRunAchieves(int num);
    public void checkGoldAchieves(int num);

    //ads
    public void showBanner();
    public void hideBanner();

    public void showInterstitialAd();
}

