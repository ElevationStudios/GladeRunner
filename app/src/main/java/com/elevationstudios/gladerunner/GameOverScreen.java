package com.elevationstudios.gladerunner;

import android.util.Log;

import com.elevationstudios.framework.Game;
import com.elevationstudios.framework.Graphics;
import com.elevationstudios.framework.Input.TouchEvent;
import com.elevationstudios.framework.Pixmap;
import com.elevationstudios.framework.Screen;

import java.util.List;

public class GameOverScreen extends Screen {

    private int playXPos;
    private int playYPos;

    public int pointsGained;
    public float endTime;
    public int moneyGained;

    private float scoreFont;
    private float gameOverFont;
    private float moneyFont;
    private float timeFont;

    private int scoreXPos;
    private int scoreYPos;

    private int gameOXPos;
    private int gameOYPos;

    private int moneyXPos;
    private int moneyYPos;

    private int timeXPos;
    private int timeYPos;


    public GameOverScreen(Game game) {
        super(game);

        Graphics g = game.getGraphics();

        playXPos = g.getWidth()*1/2-Assets.shopButton.getWidth()/2;
        playYPos = g.getHeight()*8/10-Assets.shopButton.getHeight()/2;


        gameOXPos= g.getWidth()*1/2;
        gameOYPos= g.getHeight() * 1/10;

        timeXPos= g.getWidth()*1/4;
        timeYPos= g.getHeight() * 3/10;

        scoreXPos= g.getWidth()*1/4;
        scoreYPos= g.getHeight() * 4/10;

        moneyXPos= g.getWidth()*1/4;
        moneyYPos= g.getHeight() * 5/10;

        gameOverFont = 80.0f;
        timeFont = 40.0f;
        scoreFont = 40.0f;
        moneyFont = 40.0f;

        SoundEffect.PlayMusic(SoundEffect.ORCHESTRA_MUSIC);
        //setting location , then subtracting left/up to center the button
        // here we are setting it to be 3/4 to the right, 3/4 to the bottom

    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++){
            TouchEvent event = touchEvents.get(i);

            if(event.type == TouchEvent.TOUCH_UP){
                if(inBounds(event, playXPos, playYPos,
                        Assets.shopButton.getWidth(), Assets.shopButton.getHeight())){
                    game.setScreen(new ShopScreen(game));
                    //this is where you change screen
                    Log.d("GameOverScreen", "Clicked button");
                    return;
                }

            }

        }

    }



    @Override
    public void present(float deltaTime){
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.background, 0, 0);
        g.drawPixmap(Assets.shopButton, playXPos, playYPos);
        g.drawText("Game Over!", gameOXPos, gameOYPos, gameOverFont);
       // g.drawText("Time Survived: "+Float.toString(endTime)+" seconds", timeXPos, timeYPos, timeFont);
        g.drawText("Points: "+ Integer.toString(Settings.lastRunDistance) + "m", scoreXPos, scoreYPos, scoreFont);
        g.drawText("Money: $"+ Integer.toString(Settings.lastRunGold), moneyXPos, moneyYPos, moneyFont);
    }

    @Override
    public void pause(){}

    @Override
    public void resume(){

    }

    @Override
    public void dispose(){

    }


}
