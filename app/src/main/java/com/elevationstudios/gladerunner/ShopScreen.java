package com.elevationstudios.gladerunner;

import android.graphics.Color;
import android.util.Log;

import com.elevationstudios.framework.Game;
import com.elevationstudios.framework.Graphics;
import com.elevationstudios.framework.Input.TouchEvent;
import com.elevationstudios.framework.Pixmap;
import com.elevationstudios.framework.Screen;

import java.util.List;

/**
 * Created by nickk on 2017-10-17.
 */

public class ShopScreen extends Screen {
    private int playXPos;
    private int playYPos;

    private int optionXPos;
    private int optionYPos;

    private int buyXPos;
    private int buyYPos;

    private int shopTextXPos;
    private int shopTextYPos;
    private float shopTextFontSize;

    private int playerMoney;
    private int moneyXPos;
    private int moneyYPos;
    private float moneyFontSize;
    private float buyFontSize;

    //Cost of items one through five, specific amounts and updated in update function
    private int One;
    private int Two;
    private int Three;


    private int buyBoxXPos;
    private int buyBoxYPos;

    private float boxScale = 0.6f;
    private int achieveBoxXPos;
    private int achieveBoxYPos;
    private int leaderBoxXPos;
    private int leaderBoxYPos;

    public ShopScreen(Game game) {
        super(game);

        Graphics g = game.getGraphics();

        playXPos = g.getWidth() - Assets.playButton.getWidth();
        playYPos = g.getHeight() - Assets.playButton.getHeight();

        //setting location , then subtracting left/up to center the button
        // here we are setting it to be 3/4 to the right, 3/4 to the bottom

        optionXPos = 0;
        optionYPos = g.getHeight() - Assets.optionButton.getHeight();

        shopTextFontSize = 80.f;
        shopTextXPos = g.getWidth() * 1/2 - (int) shopTextFontSize;
        shopTextYPos = g.getHeight() * 3/20 + (int) shopTextFontSize / 2;

        moneyFontSize = 80.f;
        playerMoney = Settings.gold;
        moneyXPos = 0;
        moneyYPos = g.getHeight() * 3/20 + (int) shopTextFontSize / 2;
        buyFontSize = 40.f;

        buyBoxXPos = g.getWidth()/8;
        buyBoxYPos = g.getHeight()/4;
        buyXPos = buyBoxXPos;
        buyYPos = buyBoxYPos;

        achieveBoxXPos = (int)(g.getWidth()-Assets.btnLeaderboard.getWidth()*boxScale);
        achieveBoxYPos = moneyYPos-Assets.btnAchievement.getHeight()/2;
        leaderBoxXPos = (int)(achieveBoxXPos - Assets.btnAchievement.getWidth()*boxScale);
        leaderBoxYPos = moneyYPos-Assets.btnLeaderboard.getHeight()/2;

        game.showBanner();
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        One = ((Settings.boughtItems[0] + 1) + (((Settings.boughtItems[0]) * (Settings.boughtItems[0]))/2));
        Two = ((Settings.boughtItems[1] + 1) + (((Settings.boughtItems[1]) * (Settings.boughtItems[1]))/2));
        Three = ((Settings.boughtItems[2] + 1) + (((Settings.boughtItems[2]) * (Settings.boughtItems[2]))/2));

        int len = touchEvents.size();
        present(deltaTime);
        for(int i = 0; i < len; i++){
            TouchEvent event = touchEvents.get(i);

            if(event.type == TouchEvent.TOUCH_UP){
                if(inBounds(event, playXPos, playYPos,
                        Assets.playButton.getWidth(), Assets.playButton.getHeight())){
                    game.setScreen(new GameScreen(game));
                    Log.d("ShopScreen", "Clicked Play button");
                    return;
                }

                if(inBounds(event, optionXPos, optionYPos,
                        Assets.optionButton.getWidth(), Assets.optionButton.getHeight())){
                    game.setScreen(new OptionsScreen(game));
                    Log.d("ShopScreen", "Clicked Options button");
                    return;
                }
                //increase health below (shop)
                if(inBounds(event, buyXPos, buyYPos,
                        Assets.buyButton.getWidth(), Assets.buyButton.getHeight())){
                    //this is where you change screen

                    Log.d("ShopScreen", "Clicked Buy button");
                    if (playerMoney >= (100 * One)) {
                        playerMoney -= (100 * One);
                        Settings.boughtItems[0] += 1;
                        Settings.save(game.getFileIO());
                        return;
                    }
                }
                if(inBounds(event, buyXPos, buyYPos + 1 * g.getHeight()/8,
                        Assets.buyButton.getWidth(), Assets.buyButton.getHeight())){
                    //this is where you change screen

                    Log.d("ShopScreen", "Clicked Buy button");
                    if (playerMoney >= (100 * Two)) {
                        playerMoney -= (100 * Two);
                        Settings.boughtItems[1] += 1;
                        Settings.save(game.getFileIO());
                        return;
                    }
                }
                if(inBounds(event, buyXPos, buyYPos + 2 * g.getHeight()/8,
                        Assets.buyButton.getWidth(), Assets.buyButton.getHeight())){
                    //this is where you change screen

                    Log.d("ShopScreen", "Clicked Buy button");
                    if (playerMoney >= (100 * Three)) {
                        playerMoney -= (100 * Three);
                        Settings.boughtItems[2] += 1;
                        Settings.save(game.getFileIO());
                        return;
                    }
                }



                if (inBounds(event, achieveBoxXPos, achieveBoxYPos,
                        (int)(Assets.btnAchievement.getWidth() * boxScale),
                        (int)(Assets.btnAchievement.getHeight() * boxScale))){
                    game.showAchievements();
                }

                if (inBounds(event, leaderBoxXPos,leaderBoxYPos,
                        (int)(Assets.btnLeaderboard.getWidth() * boxScale),
                        (int)(Assets.btnLeaderboard.getHeight() * boxScale))){
                    game.showLeaderboards();
                }
            }

        }

    }


    @Override
    public void present(float deltaTime){
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.background, 0, 0);
        g.drawPixmap(Assets.playButton, playXPos, playYPos);
        g.drawPixmap(Assets.optionButton, optionXPos, optionYPos);
        g.drawPixmapScaled(Assets.btnAchievement, achieveBoxXPos, achieveBoxYPos, boxScale);
        g.drawPixmapScaled(Assets.btnLeaderboard,leaderBoxXPos,leaderBoxYPos, boxScale);
        g.drawText("Shop", shopTextXPos, shopTextYPos, shopTextFontSize);
        g.drawText("$" + Integer.toString(playerMoney), moneyXPos, moneyYPos, moneyFontSize);

        for (int i = 0; i < 3; i++)
        {
            g.drawRect(buyBoxXPos, buyBoxYPos + (i) * g.getHeight()/6, g.getWidth()*3/4, Assets.buyButton.getHeight(), Color.GRAY);
            g.drawPixmap(Assets.buyButton, buyXPos, buyYPos + i * g.getHeight()/6);
            g.drawText("Buy Health ", g.getWidth() /3, buyYPos + Assets.buyButton.getHeight() / 2 + 0 * g.getHeight()/6, 40.f);
            g.drawText("Increase Money Gain ", g.getWidth() /3, buyYPos + Assets.buyButton.getHeight() / 2 + 1 * g.getHeight()/6, 40.f);
            g.drawText("Increase Distance Gain ", g.getWidth() /3, buyYPos + Assets.buyButton.getHeight() / 2 + 2 * g.getHeight()/6, 40.f);

            g.drawText("Price = " + Integer.toString(One * 100), g.getWidth() *3/4 - (int) buyFontSize, buyYPos + Assets.buyButton.getHeight() / 2 + 0 * g.getHeight()/6, buyFontSize);
            g.drawText("Price = " + Integer.toString(Two * 100), g.getWidth() *3/4 - (int) buyFontSize, buyYPos + Assets.buyButton.getHeight() / 2 + 1 * g.getHeight()/6, buyFontSize);
            g.drawText("Price = " + Integer.toString(Three * 100), g.getWidth() *3/4 - (int) buyFontSize, buyYPos + Assets.buyButton.getHeight() / 2 + 2 * g.getHeight()/6, buyFontSize);

        }

    }

    @Override
    public void pause(){
        Settings.setGold(playerMoney);

        //  Update bought stats
        // Settings.boughtItems;
        Settings.save(game.getFileIO());
        game.hideBanner();
    }

    @Override
    public void resume(){

    }

    @Override
    public void dispose(){

    }
}
