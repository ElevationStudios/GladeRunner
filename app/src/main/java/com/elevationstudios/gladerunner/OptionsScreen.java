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

	private int shopXPos = 50;
	private int shopYPos = 50;

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
        optionsXPos= g.getWidth()*2 - (int)optionsTextFont;
        optionsYPos= g.getHeight() * 1/10;


        shopXPos = g.getWidth()/2 - Assets.shopButton.getWidth()/2;
        shopYPos = g.getHeight() * 4/10 - Assets.shopButton.getHeight()/2;

        helpXPos = g.getWidth()/2 - Assets.helpButton.getWidth()/2;
        helpYPos = g.getHeight() * 6/10 - Assets.helpButton.getHeight()/2;


        checkXPos = g.getWidth()/2 - Assets.checked.getWidth()/2;
        checkYPos = g.getHeight() * 8/10 - Assets.checked.getHeight()/2;

    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();

        for(Input.TouchEvent event : touchEvents){

            if(event.type == Input.TouchEvent.TOUCH_DOWN){

                testX = event.x;
                testY = event.y;

                // Shop button
                if(inBounds(event, shopXPos, shopYPos,
                        Assets.shopButton.getWidth(), Assets.shopButton.getHeight())){
                    game.setScreen(new ShopScreen(game));
                    Log.d("OptionsScreen", "Clicked Shop button");
                    return;
                }


                // Help Button
                else if(inBounds(event, helpXPos, helpYPos,
                        Assets.helpButton.getWidth(), Assets.helpButton.getHeight())){
                    game.setScreen(new HelpScreen(game));
                    Log.d("OptionsScreen", "Clicked Help button");
                    return;
                }

                // Checkbox
                else if(inBounds(event, checkXPos, checkYPos,
                        Assets.checked.getWidth(), Assets.checked.getHeight())){

                    enableSFX =! enableSFX;
                    if (!enableSFX)
                        Assets.currentMusic.pause();
                    else
                        Assets.currentMusic.play();
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
        g.drawPixmap(Assets.shopButton, shopXPos, shopYPos);
        g.drawPixmap(Assets.helpButton, helpXPos, helpYPos);

        g.drawPixmap(enableSFX ? Assets.checked : Assets.unchecked, checkXPos, checkYPos);

        g.drawText("Enable SFX", checkXPos + 115, checkYPos + 50);

        //g.drawText("X = " + testX + " Y = " + testY + " testing2", 100, 600);
    }

    @Override
    public void pause() {
        Settings.setSoundEnabled(enableSFX);
        Settings.save(game.getFileIO());
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}