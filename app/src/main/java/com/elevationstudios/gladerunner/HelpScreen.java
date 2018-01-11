package com.elevationstudios.gladerunner;

import android.util.Log;

import com.elevationstudios.framework.Game;
import com.elevationstudios.framework.Graphics;
import com.elevationstudios.framework.Input.TouchEvent;
import com.elevationstudios.framework.Pixmap;
import com.elevationstudios.framework.Screen;

import java.util.List;

public class HelpScreen extends Screen {


    private int returnXPos;
    private int returnYPos;

    private int nextXPos;
    private int nextYPos;

    private int backXPos;
    private int backYPos;

    private int slide;

    public HelpScreen(Game game) {
        super(game);

        Graphics g = game.getGraphics();
        slide = 1;

        returnXPos = 0;
        returnYPos = 0;

        nextXPos = g.getWidth()-Assets.nextButton.getWidth();
        nextYPos = g.getHeight()-Assets.nextButton.getHeight();

        backXPos = 0;
        backYPos = g.getHeight()-Assets.backButton.getHeight();

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
                if(inBounds(event, returnXPos, returnYPos,
                        Assets.returnButton.getWidth(), Assets.returnButton.getHeight())){
                    game.setScreen(new OptionsScreen(game));
                    SoundEffect.PlaySound(SoundEffect.BUTTON_CLICK);
                    Log.d("HelpScreen", "Clicked return button");
                    return;
                }

                if(inBounds(event, nextXPos, nextYPos,
                        Assets.nextButton.getWidth(), Assets.nextButton.getHeight())
                        && slide != 2){
                    slide++;
                    SoundEffect.PlaySound(SoundEffect.BUTTON_CLICK);
                    Log.d("HelpScreen", "Clicked next button");
                    return;
                }

                if(inBounds(event, backXPos, backYPos,
                        Assets.backButton.getWidth(), Assets.backButton.getHeight())
                        && slide != 1){
                    slide--;
                    SoundEffect.PlaySound(SoundEffect.BUTTON_CLICK);
                    Log.d("HelpScreen", "Clicked back button");
                    return;
                }
            }

        }

    }


    @Override
    public void present(float deltaTime){
        Graphics g = game.getGraphics();
        //if(slide == 1)
        g.drawPixmap(Assets.helpMove, 0, 0);
        g.drawPixmap(Assets.returnButton, returnXPos, returnYPos);
        if(slide != 1)
            g.drawPixmap(Assets.backButton, backXPos, backYPos);
        if(slide != 2)
            g.drawPixmap(Assets.nextButton, nextXPos, nextYPos);
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
