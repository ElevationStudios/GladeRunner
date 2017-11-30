package com.elevationstudios.gladerunner;


import com.elevationstudios.framework.Game;
import com.elevationstudios.framework.Graphics;
import com.elevationstudios.framework.Graphics.PixmapFormat;
import com.elevationstudios.framework.Screen;
/**
 * Created by Kenneth on 30-Nov-17.
 */

public class LoadingScreen extends Screen {
    public LoadingScreen(Game game){ super(game); }

    @Override
    public void update(float deltaTime){
        Graphics g = game.getGraphics();
        Assets.background = g.newPixmap("background.png", PixmapFormat.RGB565);

        //TODO: Move all assets - buttons, images, everything, to Assets and use this instead
        //Assets.ninja = g.newPixmap("ninja1.png", PixmapFormat.ARGB4444);

        Settings.load(game.getFileIO());
        game.setScreen(new MainMenuScreen(game));
    }

    @Override
    public void present (float deltaTime){}

    @Override
    public void pause(){}

    @Override
    public void resume(){}

    @Override
    public void dispose(){}

}
