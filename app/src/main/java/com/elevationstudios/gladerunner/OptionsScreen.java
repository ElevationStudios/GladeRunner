package com.elevationstudios.gladerunner;

import android.util.Log;
import com.elevationstudios.framework.*;

import java.util.List;

public class OptionsScreen extends Screen {

    private static Pixmap background;
	
    private static Pixmap helpButton;
	private int helpXPos = 50;
	private int helpYPos = 50;
	
    private static Pixmap backButton;
	private int backXPos = 50;
	private int backYPos = 50;

    private static Pixmap checkdBox;
    private static Pixmap uncheckdBox;
	private int checkXPos = 50;
	private int checkYPos = 300;

    private int testX;
    private int testY;

    private boolean enableSFX = true;

    public OptionsScreen(Game game) {
        super(game);

        Graphics g = game.getGraphics();
        background = g.newPixmap("background.png", Graphics.PixmapFormat.RGB565);
        helpButton = g.newPixmap("helpButton.png", Graphics.PixmapFormat.ARGB4444);
        backButton = g.newPixmap("backButton.png", Graphics.PixmapFormat.ARGB4444);

        checkdBox = g.newPixmap("checked.png", Graphics.PixmapFormat.ARGB4444);
        uncheckdBox = g.newPixmap("unchecked.png", Graphics.PixmapFormat.ARGB4444);
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
                        backButton.getWidth(), backButton.getHeight())){
                    game.setScreen(new ShopScreen(game));
                    Log.d("OptionsScreen", "Clicked back button");
                    return;
                }


                // Help Button
                else if(inBounds(event, helpXPos, helpXPos + backButton.getHeight(),
                        helpButton.getWidth(), helpButton.getHeight())){
                    game.setScreen(new HelpScreen(game));
                    Log.d("OptionsScreen", "Clicked Help button");
                    return;
                }

                // Checkbox
                else if(inBounds(event, checkXPos, checkYPos,
                        checkdBox.getWidth(), checkdBox.getHeight())){

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
        g.drawPixmap(background, 0, 0);
        g.drawPixmap(backButton, backXPos, backYPos);
        g.drawPixmap(helpButton, helpXPos, helpYPos + backButton.getHeight());

        g.drawPixmap(enableSFX ? checkdBox : uncheckdBox, checkXPos, checkYPos);

        g.drawText("Enable SFX", 165, 350);

        //g.drawText("X = " + testX + " Y = " + testY + " testing2", 100, 600);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}