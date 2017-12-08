package com.elevationstudios.gladerunner;

import android.util.Log;
import com.elevationstudios.framework.*;

import java.util.List;

public class OptionsScreen extends Screen {

	private int helpXPos = 50;
	private int helpYPos = 50;

	private int backXPos = 50;
	private int backYPos = 50;

	private int checkXPos = 50;
	private int checkYPos = 300;

    private int testX;
    private int testY;

    private boolean enableSFX = Settings.soundEnabled;

    public OptionsScreen(Game game) {
        super(game);

        Graphics g = game.getGraphics();
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
                else if(inBounds(event, helpXPos, helpXPos + Assets.backButton.getHeight(),
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
        g.drawPixmap(Assets.backButton, backXPos, backYPos);
        g.drawPixmap(Assets.helpButton, helpXPos, helpYPos + Assets.backButton.getHeight());

        g.drawPixmap(enableSFX ? Assets.checked : Assets.unchecked, checkXPos, checkYPos);

        g.drawText("Enable SFX", 165, 350);

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