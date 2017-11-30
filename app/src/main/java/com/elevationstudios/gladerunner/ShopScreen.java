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

    private static Pixmap background;
    private static Pixmap playButton;
    private static Pixmap optionButton;
    private static Pixmap buyButton;

    private int playXPos;
    private int playYPos;

    private int optionXPos;
    private int optionYPos;

    private int buyXPos;
    private int buyYPos;

    private int shopTextXPos;
    private int shopTextYPos;
    private float shopTextFontSize;

    private String playerMoney;
    private String priceText;
    private int moneyXPos;
    private int moneyYPos;
    private float moneyFontSize;
    private float buyFontSize;

    private int buyBoxXPos;
    private int buyBoxYPos;

    public ShopScreen(Game game) {
        super(game);

        Graphics g = game.getGraphics();
        background = g.newPixmap("background.png", Graphics.PixmapFormat.RGB565);
        playButton = g.newPixmap("playButton.png", Graphics.PixmapFormat.ARGB4444);
        optionButton = g.newPixmap("optionButton.png", Graphics.PixmapFormat.ARGB4444);
        buyButton = g.newPixmap("buyButton.png", Graphics.PixmapFormat.ARGB4444);

        playXPos = g.getWidth() - playButton.getWidth();
        playYPos = g.getHeight() - playButton.getHeight();

        //setting location , then subtracting left/up to center the button
        // here we are setting it to be 3/4 to the right, 3/4 to the bottom

        optionXPos = 0;
        optionYPos = g.getHeight() - optionButton.getHeight();

        shopTextFontSize = 80.f;
        shopTextXPos = g.getWidth() * 1/2 - (int) shopTextFontSize;
        shopTextYPos = g.getHeight() * 1/10 + (int) shopTextFontSize / 2;

        moneyFontSize = 80.f;
        playerMoney = "$" + Integer.toString(1000);
        moneyXPos = 0;
        moneyYPos = (int) moneyFontSize;
        buyFontSize = 40.f;
        priceText = "Price: $100";

        buyBoxXPos = g.getWidth()/8;
        buyBoxYPos = g.getHeight()/4;
        buyXPos = buyBoxXPos;
        buyYPos = buyBoxYPos;


    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++){
            TouchEvent event = touchEvents.get(i);

            if(event.type == TouchEvent.TOUCH_UP){
                if(inBounds(event, playXPos, playYPos,
                        playButton.getWidth(), playButton.getHeight())){
                    game.setScreen(new GameScreen(game));
                    Log.d("ShopScreen", "Clicked Play button");
                    return;
                }

            }

            if(event.type == TouchEvent.TOUCH_UP){
                if(inBounds(event, optionXPos, optionYPos,
                        optionButton.getWidth(), optionButton.getHeight())){
                    game.setScreen(new OptionsScreen(game));
                    Log.d("ShopScreen", "Clicked Options button");
                    return;
                }

            }

            if(event.type == TouchEvent.TOUCH_UP){
                if(inBounds(event, buyXPos, buyYPos,
                        buyButton.getWidth(), buyButton.getHeight())){
                    //this is where you change screen
                    Log.d("ShopScreen", "Clicked Buy button");
                    return;
                }

            }

        }

    }


    @Override
    public void present(float deltaTime){
        Graphics g = game.getGraphics();

        g.drawPixmap(background, 0, 0);
        g.drawPixmap(playButton, playXPos, playYPos);
        g.drawPixmap(optionButton, optionXPos, optionYPos);
        g.drawText("Shop", shopTextXPos, shopTextYPos, shopTextFontSize);
        g.drawText(playerMoney, moneyXPos, moneyYPos, moneyFontSize);

        for (int i = 0; i < 5; i++)
        {
            g.drawRect(buyBoxXPos, buyBoxYPos + i * g.getHeight()/8, g.getWidth()*3/4, buyButton.getHeight(), Color.GRAY);
            g.drawPixmap(buyButton, buyXPos, buyYPos + i * g.getHeight()/8);
            g.drawText("Buyable " + Integer.toString(i+1), g.getWidth() /3, buyYPos + buyButton.getHeight() / 2 + i * g.getHeight()/8, 40.f);
            g.drawText(priceText, g.getWidth() *3/4 - (int) buyFontSize, buyYPos + buyButton.getHeight() / 2 + i * g.getHeight()/8, buyFontSize);
        }

    }

    @Override
    public void pause(){
        Settings.save(game.getFileIO());
    }

    @Override
    public void resume(){

    }

    @Override
    public void dispose(){

    }
}
