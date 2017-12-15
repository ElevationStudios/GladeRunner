package com.elevationstudios.gladerunner;

import android.util.Log;
import com.elevationstudios.framework.*;

import java.util.List;

public class OptionsScreen extends Screen {

    private float optionsTextFont;
    private String optionsText;
    private int optionsXPos;
    private int optionsYPos;

	private int helpXPos = 50;
	private int helpYPos = 50;

	private int backXPos = 50;
	private int backYPos = 50;

	private int checkXPos = 50; //textx = 165
	private int checkYPos = 300; //texty = 350

    private int testX;
    private int testY;

    private boolean enableSFX = Settings.soundEnabled;

    public OptionsScreen(Game game) {
        super(game);

        Graphics g = game.getGraphics();

        optionsTextFont = 80.0f;
        optionsText = "Options";
        optionsXPos= g.getWidth()*1/2 - (int)optionsTextFont;
        optionsYPos= g.getHeight() * 1/10;


        backXPos = g.getWidth()/2 - Assets.backButton.getWidth()/2;
        backYPos = g.getHeight() * 4/10 - Assets.backButton.getHeight()/2;
        helpXPos = g.getWidth()/2 - Assets.helpButton.getWidth()/2;
        helpYPos = backYPos + g.getHeight()*1/20 + Assets.helpButton.getHeight();


        checkXPos = g.getWidth()/2 - Assets.checked.getWidth()/2;
        checkYPos = helpYPos + g.getHeight() * 2/20 + Assets.checked.getHeight();

    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();

        for(Input.TouchEvent event : touchEvents){

            if(event.type == Input.TouchEvent.TOUCH_DOWN){

                testX = event.x;
                testY = event.y;

                // Back button
                if(inBounds(event, backXPos, backYPos,
                        Assets.backButton.getWidth(), Assets.backButton.getHeight())){
                    game.setScreen(new ShopScreen(game));
                    Log.d("OptionsScreen", "Clicked back button");
                    return;
                }


                // Help Button
                else if(inBounds(event, helpXPos, helpXPos,
                        Assets.helpButton.getWidth(), Assets.helpButton.getHeight())){
                    game.setScreen(new HelpScreen(game));
                    Log.d("OptionsScreen", "Clicked Help button");
                    return;
                }

                // Checkbox
                else if(inBounds(event, checkXPos, checkYPos,
                        Assets.checked.getWidth(), Assets.checked.getHeight())){

                    enableSFX =! enableSFX;
                    Log.d("OptionsScreen", "Clicked checkbox ");
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime){
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.background, 0, 0);

        g.drawText(optionsText, optionsXPos, optionsYPos, optionsTextFont);
        g.drawPixmap(Assets.backButton, backXPos, backYPos);
        g.drawPixmap(Assets.helpButton, helpXPos, helpYPos);

        g.drawPixmap(enableSFX ? Assets.checked : Assets.unchecked, checkXPos, checkYPos);

        g.drawText("Enable SFX", checkXPos + 115, checkYPos + 50);

        //g.drawText("X = " + testX + " Y = " + testY + " testing2", 100, 600);
    }

    @Override
    public void pause() {
        Settings.soundEnabled = enableSFX;
        Settings.save(game.getFileIO());
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}