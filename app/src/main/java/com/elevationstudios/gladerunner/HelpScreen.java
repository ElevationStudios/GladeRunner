package com.elevationstudios.gladerunner;

import android.util.Log;

import com.elevationstudios.framework.Game;
import com.elevationstudios.framework.Graphics;
import com.elevationstudios.framework.Input.TouchEvent;
import com.elevationstudios.framework.Pixmap;
import com.elevationstudios.framework.Screen;

import java.util.List;

public class HelpScreen extends Screen {

    private static Pixmap background;
    private static Pixmap returnButton;
    private int returnXPos;
    private int returnYPos;

    private static Pixmap nextButton;
    private int nextXPos;
    private int nextYPos;

    private static Pixmap backButton;
    private int backXPos;
    private int backYPos;

    private int slide;

    public HelpScreen(Game game) {
        super(game);

        Graphics g = game.getGraphics();
        background = g.newPixmap("helpMove.png", Graphics.PixmapFormat.RGB565);
        slide = 1;

        returnButton = g.newPixmap("returnButton.png", Graphics.PixmapFormat.ARGB4444);
        returnXPos = 0;
        returnYPos = 0;

        nextButton = g.newPixmap("nextButton.png", Graphics.PixmapFormat.ARGB4444);
        nextXPos = g.getWidth()-nextButton.getWidth();
        nextYPos = g.getHeight()-nextButton.getHeight();

        backButton = g.newPixmap("backButton.png", Graphics.PixmapFormat.ARGB4444);
        backXPos = 0;
        backYPos = g.getHeight()-backButton.getHeight();

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
                        returnButton.getWidth(), returnButton.getHeight())){
                    game.setScreen(new OptionsScreen(game));
                    Log.d("HelpScreen", "Clicked return button");
                    return;
                }

                if(inBounds(event, nextXPos, nextYPos,
                        nextButton.getWidth(), nextButton.getHeight())
                        && slide != 2){
                    slide++;
                    Log.d("HelpScreen", "Clicked next button");
                    return;
                }

                if(inBounds(event, backXPos, backYPos,
                        backButton.getWidth(), backButton.getHeight())
                        && slide != 1){
                    slide--;
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
        g.drawPixmap(background, 0, 0);
        g.drawPixmap(returnButton, returnXPos, returnYPos);
        if(slide != 1)
            g.drawPixmap(backButton, backXPos, backYPos);
        if(slide != 2)
            g.drawPixmap(nextButton, nextXPos, nextYPos);
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
