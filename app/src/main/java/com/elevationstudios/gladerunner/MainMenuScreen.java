package com.elevationstudios.gladerunner;


import com.elevationstudios.framework.Game;
import com.elevationstudios.framework.Graphics;
import com.elevationstudios.framework.Input.TouchEvent;
import com.elevationstudios.framework.Pixmap;
import com.elevationstudios.framework.Screen;

import android.util.Log;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import static java.security.AccessController.getContext;

public class MainMenuScreen extends Screen {

    private static Pixmap background;
    private static Pixmap playButton;

    private int playXPos;
    private int playYPos;

    public MainMenuScreen(Game game) {
        super(game);

        Graphics g = game.getGraphics();

        playXPos = g.getWidth()*3/4-Assets.playButton.getWidth()/2;
        playYPos = g.getHeight()*3/4-Assets.playButton.getHeight()/2;

        //setting location , then subtracting left/up to center the button
        // here we are setting it to be 3/4 to the right, 3/4 to the bottom
       SoundEffect.PlayMusic(SoundEffect.ORCHESTRA_MUSIC);

    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++){
            TouchEvent event = touchEvents.get(i);

            if(event.type == TouchEvent.TOUCH_UP){
                if(inBounds(event, playXPos, playYPos,
                        Assets.playButton.getWidth(), Assets.playButton.getHeight())){
                    game.setScreen(new ShopScreen(game));
                    SoundEffect.PlaySound(SoundEffect.BUTTON_CLICK);
                    //this is where you change screen
                    Log.d("MainMenuScreen", "Clicked button");
                    return;
                }
            }
        }
    }


    @Override
    public void present(float deltaTime){
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.background, 0, 0);
        g.drawPixmap(Assets.playButton, playXPos, playYPos);

        //g.drawText("TestString", g.getWidth()/2-10, g.getHeight()/2, 20.0f);
    }

    @Override
    public void pause(){
        Log.d("Sound", "MainMenu Paused");
        Settings.save(game.getFileIO());
    }

    @Override
    public void resume(){

    }

    @Override
    public void dispose() {

    }

}
