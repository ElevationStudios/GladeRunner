package com.elevationstudios.gladerunner;


import com.elevationstudios.framework.Game;
import com.elevationstudios.framework.Graphics;
import com.elevationstudios.framework.Graphics.PixmapFormat;
import com.elevationstudios.framework.Pixmap;
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
        LoadNinjaAssets(g, Assets.ninjaSprite);


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

    public void LoadNinjaAssets(Graphics g, Pixmap sprite[][]){
        sprite[0][0] = g.newPixmap("NinjaAnim/dead_0.png", Graphics.PixmapFormat.RGB565);
        sprite[0][1] = g.newPixmap("NinjaAnim/dead_1.png", Graphics.PixmapFormat.RGB565);
        sprite[0][2] = g.newPixmap("NinjaAnim/dead_2.png", Graphics.PixmapFormat.RGB565);
        sprite[0][3] = g.newPixmap("NinjaAnim/dead_3.png", Graphics.PixmapFormat.RGB565);
        sprite[0][4] = g.newPixmap("NinjaAnim/dead_4.png", Graphics.PixmapFormat.RGB565);
        sprite[0][5] = g.newPixmap("NinjaAnim/dead_5.png", Graphics.PixmapFormat.RGB565);
        sprite[0][6] = g.newPixmap("NinjaAnim/dead_6.png", Graphics.PixmapFormat.RGB565);
        sprite[0][7] = g.newPixmap("NinjaAnim/dead_7.png", Graphics.PixmapFormat.RGB565);
        sprite[0][8] = g.newPixmap("NinjaAnim/dead_8.png", Graphics.PixmapFormat.RGB565);
        sprite[0][9] = g.newPixmap("NinjaAnim/dead_9.png", Graphics.PixmapFormat.RGB565);

        sprite[1][0] = g.newPixmap("NinjaAnim/run_0.png", Graphics.PixmapFormat.RGB565);
        sprite[1][1] = g.newPixmap("NinjaAnim/run_1.png", Graphics.PixmapFormat.RGB565);
        sprite[1][2] = g.newPixmap("NinjaAnim/run_2.png", Graphics.PixmapFormat.RGB565);
        sprite[1][3] = g.newPixmap("NinjaAnim/run_3.png", Graphics.PixmapFormat.RGB565);
        sprite[1][4] = g.newPixmap("NinjaAnim/run_4.png", Graphics.PixmapFormat.RGB565);
        sprite[1][5] = g.newPixmap("NinjaAnim/run_5.png", Graphics.PixmapFormat.RGB565);
        sprite[1][6] = g.newPixmap("NinjaAnim/run_6.png", Graphics.PixmapFormat.RGB565);
        sprite[1][7] = g.newPixmap("NinjaAnim/run_7.png", Graphics.PixmapFormat.RGB565);
        sprite[1][8] = g.newPixmap("NinjaAnim/run_8.png", Graphics.PixmapFormat.RGB565);
        sprite[1][9] = g.newPixmap("NinjaAnim/run_9.png", Graphics.PixmapFormat.RGB565);

        sprite[2][0] = g.newPixmap("NinjaAnim/jump_0.png", Graphics.PixmapFormat.RGB565);
        sprite[2][1] = g.newPixmap("NinjaAnim/jump_1.png", Graphics.PixmapFormat.RGB565);
        sprite[2][2] = g.newPixmap("NinjaAnim/jump_2.png", Graphics.PixmapFormat.RGB565);
        sprite[2][3] = g.newPixmap("NinjaAnim/jump_3.png", Graphics.PixmapFormat.RGB565);
        sprite[2][4] = g.newPixmap("NinjaAnim/jump_4.png", Graphics.PixmapFormat.RGB565);
        sprite[2][5] = g.newPixmap("NinjaAnim/jump_5.png", Graphics.PixmapFormat.RGB565);
        sprite[2][6] = g.newPixmap("NinjaAnim/jump_6.png", Graphics.PixmapFormat.RGB565);
        sprite[2][7] = g.newPixmap("NinjaAnim/jump_7.png", Graphics.PixmapFormat.RGB565);
        sprite[2][8] = g.newPixmap("NinjaAnim/jump_8.png", Graphics.PixmapFormat.RGB565);
        sprite[2][9] = g.newPixmap("NinjaAnim/jump_9.png", Graphics.PixmapFormat.RGB565);
        sprite[2][0] = g.newPixmap("NinjaAnim/jump_0.png", Graphics.PixmapFormat.RGB565);

        sprite[3][0] = g.newPixmap("NinjaAnim/slide_0.png", Graphics.PixmapFormat.RGB565);
        sprite[3][1] = g.newPixmap("NinjaAnim/slide_1.png", Graphics.PixmapFormat.RGB565);
        sprite[3][2] = g.newPixmap("NinjaAnim/slide_2.png", Graphics.PixmapFormat.RGB565);
        sprite[3][3] = g.newPixmap("NinjaAnim/slide_3.png", Graphics.PixmapFormat.RGB565);
        sprite[3][4] = g.newPixmap("NinjaAnim/slide_4.png", Graphics.PixmapFormat.RGB565);
        sprite[3][5] = g.newPixmap("NinjaAnim/slide_5.png", Graphics.PixmapFormat.RGB565);
        sprite[3][6] = g.newPixmap("NinjaAnim/slide_6.png", Graphics.PixmapFormat.RGB565);
        sprite[3][7] = g.newPixmap("NinjaAnim/slide_7.png", Graphics.PixmapFormat.RGB565);
        sprite[3][8] = g.newPixmap("NinjaAnim/slide_8.png", Graphics.PixmapFormat.RGB565);
        sprite[3][9] = g.newPixmap("NinjaAnim/slide_9.png", Graphics.PixmapFormat.RGB565);

        sprite[4][0] = g.newPixmap("NinjaAnim/attack_0.png", Graphics.PixmapFormat.RGB565);
        sprite[4][1] = g.newPixmap("NinjaAnim/attack_1.png", Graphics.PixmapFormat.RGB565);
        sprite[4][2] = g.newPixmap("NinjaAnim/attack_2.png", Graphics.PixmapFormat.RGB565);
        sprite[4][3] = g.newPixmap("NinjaAnim/attack_3.png", Graphics.PixmapFormat.RGB565);
        sprite[4][4] = g.newPixmap("NinjaAnim/attack_4.png", Graphics.PixmapFormat.RGB565);
        sprite[4][5] = g.newPixmap("NinjaAnim/attack_5.png", Graphics.PixmapFormat.RGB565);
        sprite[4][6] = g.newPixmap("NinjaAnim/attack_6.png", Graphics.PixmapFormat.RGB565);
        sprite[4][7] = g.newPixmap("NinjaAnim/attack_7.png", Graphics.PixmapFormat.RGB565);
        sprite[4][8] = g.newPixmap("NinjaAnim/attack_8.png", Graphics.PixmapFormat.RGB565);
        sprite[4][9] = g.newPixmap("NinjaAnim/attack_9.png", Graphics.PixmapFormat.RGB565);

        sprite[5][0] = g.newPixmap("NinjaAnim/throw_0.png", Graphics.PixmapFormat.RGB565);
        sprite[5][1] = g.newPixmap("NinjaAnim/throw_1.png", Graphics.PixmapFormat.RGB565);
        sprite[5][2] = g.newPixmap("NinjaAnim/throw_2.png", Graphics.PixmapFormat.RGB565);
        sprite[5][3] = g.newPixmap("NinjaAnim/throw_3.png", Graphics.PixmapFormat.RGB565);
        sprite[5][4] = g.newPixmap("NinjaAnim/throw_4.png", Graphics.PixmapFormat.RGB565);
        sprite[5][5] = g.newPixmap("NinjaAnim/throw_5.png", Graphics.PixmapFormat.RGB565);
        sprite[5][6] = g.newPixmap("NinjaAnim/throw_6.png", Graphics.PixmapFormat.RGB565);
        sprite[5][7] = g.newPixmap("NinjaAnim/throw_7.png", Graphics.PixmapFormat.RGB565);
        sprite[5][8] = g.newPixmap("NinjaAnim/throw_8.png", Graphics.PixmapFormat.RGB565);
        sprite[5][9] = g.newPixmap("NinjaAnim/throw_9.png", Graphics.PixmapFormat.RGB565);

        sprite[6][0] = g.newPixmap("NinjaAnim/jump_attack_0.png", Graphics.PixmapFormat.RGB565);
        sprite[6][1] = g.newPixmap("NinjaAnim/jump_attack_1.png", Graphics.PixmapFormat.RGB565);
        sprite[6][2] = g.newPixmap("NinjaAnim/jump_attack_2.png", Graphics.PixmapFormat.RGB565);
        sprite[6][3] = g.newPixmap("NinjaAnim/jump_attack_3.png", Graphics.PixmapFormat.RGB565);
        sprite[6][4] = g.newPixmap("NinjaAnim/jump_attack_4.png", Graphics.PixmapFormat.RGB565);
        sprite[6][5] = g.newPixmap("NinjaAnim/jump_attack_5.png", Graphics.PixmapFormat.RGB565);
        sprite[6][6] = g.newPixmap("NinjaAnim/jump_attack_6.png", Graphics.PixmapFormat.RGB565);
        sprite[6][7] = g.newPixmap("NinjaAnim/jump_attack_7.png", Graphics.PixmapFormat.RGB565);
        sprite[6][8] = g.newPixmap("NinjaAnim/jump_attack_8.png", Graphics.PixmapFormat.RGB565);
        sprite[6][9] = g.newPixmap("NinjaAnim/jump_attack_9.png", Graphics.PixmapFormat.RGB565);

        sprite[7][0] = g.newPixmap("NinjaAnim/jump_throw_0.png", Graphics.PixmapFormat.RGB565);
        sprite[7][1] = g.newPixmap("NinjaAnim/jump_throw_1.png", Graphics.PixmapFormat.RGB565);
        sprite[7][2] = g.newPixmap("NinjaAnim/jump_throw_2.png", Graphics.PixmapFormat.RGB565);
        sprite[7][3] = g.newPixmap("NinjaAnim/jump_throw_3.png", Graphics.PixmapFormat.RGB565);
        sprite[7][4] = g.newPixmap("NinjaAnim/jump_throw_4.png", Graphics.PixmapFormat.RGB565);
        sprite[7][5] = g.newPixmap("NinjaAnim/jump_throw_5.png", Graphics.PixmapFormat.RGB565);
        sprite[7][6] = g.newPixmap("NinjaAnim/jump_throw_6.png", Graphics.PixmapFormat.RGB565);
        sprite[7][7] = g.newPixmap("NinjaAnim/jump_throw_7.png", Graphics.PixmapFormat.RGB565);
        sprite[7][8] = g.newPixmap("NinjaAnim/jump_throw_8.png", Graphics.PixmapFormat.RGB565);
        sprite[7][9] = g.newPixmap("NinjaAnim/jump_throw_9.png", Graphics.PixmapFormat.RGB565);
    }

}
