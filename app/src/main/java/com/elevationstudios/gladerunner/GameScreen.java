package com.elevationstudios.gladerunner;


import com.elevationstudios.framework.Game;
import com.elevationstudios.framework.Graphics;
import com.elevationstudios.framework.Input;
import com.elevationstudios.framework.Input.TouchEvent;
import com.elevationstudios.framework.Pixmap;
import com.elevationstudios.framework.Screen;

import android.graphics.Color;
import android.util.Log;
import java.util.List;
import java.util.Random;

public class GameScreen extends Screen {

    private Pixmap background;

    private static Pixmap dieButton;
    private int dieButtonXPos;
    private int dieButtonYPos;
    private static Pixmap pauseButton;
    private int pauseButtonXPos;
    private int pauseButtonYPos;

    private float uiBarHeightPercentage = 0.08f; //percentage of screen it takes up
    private int uiBarHeight;
    private int uiBarOutline = 2; //how many pixel width

    private float uiHealthbarHeight = 0.4f; //percentage of uibar it takes up
    private int uiHealthbarOutline = 1;

    private float pauseScreenHeightPercentage = 0.4f;
    private float pauseScreenWidthPercentage = 0.3f;
    private int pauseScreenHeight;
    private int pauseScreenWidth;

    private static Pixmap playButton;
    private int playButtonXPos;
    private int playButtonYPos;
    private static Pixmap returnButton;
    private int returnButtonXPos;
    private int returnButtonYPos;





    private boolean isPaused = false;


    private Ninja ninja;
    private int ninjaXPos;
    private int ninjaYPos;
    private int ninjaYVelocity;
    private int groundYPos;

    private int gravity = -2;
    private int jumpStrength = 35;

    private float ninjaScale = 0.3f;

    private Obstacle[] obstacle;
    private float timer;
    private Random r;

    public GameScreen(Game game) {
        super(game);


        Graphics g = game.getGraphics();
        background = g.newPixmap("background.png", Graphics.PixmapFormat.RGB565);
        SetupUIValues();


        dieButton = g.newPixmap("dieButton.png", Graphics.PixmapFormat.ARGB4444);
        dieButtonXPos = g.getWidth()*4/5-dieButton.getWidth()/2;
        dieButtonYPos = g.getHeight()*3/5-dieButton.getHeight()/2;

        pauseButton = g.newPixmap("pauseButton.png", Graphics.PixmapFormat.ARGB4444);
        pauseButtonXPos = g.getWidth()-uiBarHeight+1;
        pauseButtonYPos = 0+1;


        playButton = g.newPixmap("playButton.png", Graphics.PixmapFormat.ARGB4444);
        playButtonXPos = g.getWidth()/2-playButton.getWidth()/2;
        playButtonYPos = g.getHeight()/2-playButton.getHeight();
        returnButton = g.newPixmap("returnButton.png", Graphics.PixmapFormat.ARGB4444);
        returnButtonXPos = playButtonXPos;
        returnButtonYPos = playButtonYPos + 10 + playButton.getHeight();

        obstacle = new Obstacle[5];
        r = new Random();

        ninja = new Ninja();
        ninja.PrepareAssets(g);
        ninjaXPos = (int)(game.getGraphics().getWidth()*0.1);
        ninjaYPos = (int)(game.getGraphics().getHeight()*0.8);
        groundYPos = ninjaYPos;


    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        int len = touchEvents.size();
        for(int i = 0; i < len; i++){
            Input.TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP){

                if(!isPaused) {
                    if (inBounds(event, dieButtonXPos, dieButtonYPos,
                            dieButton.getWidth(), dieButton.getHeight())) {
                        ninja.takeDamage(25);
                        Log.d("GameScreen", "Clicked Die button");
                        return;
                    }
                    if (inBounds(event, pauseButtonXPos, pauseButtonYPos,
                            game.getGraphics().getWidth()-1, uiBarHeight-1)) {
                        isPaused = !isPaused;
                        //CheckPause(isPaused);
                        Log.d("GameScreen", "Clicked Pause button");
                        return;
                    }
                } else if (isPaused) {
                    if(inBounds(event, playButtonXPos, playButtonYPos,
                            playButton.getWidth(), playButton.getHeight())) {
                        isPaused = !isPaused;
                        Log.d("GameScreen", "Clicked Play button");

                    }
                    if(inBounds(event, returnButtonXPos, returnButtonYPos,
                            returnButton.getWidth(), returnButton.getHeight())) {
                        isPaused = !isPaused;
                        game.setScreen(new ShopScreen(game));
                        Log.d("GameScreen", "Clicked Return button");

                    }
                }
            }
            if(event.type == TouchEvent.TOUCH_SWIPED_UP){
                Jump();
                ninja.setState(Ninja.State.Jump);
            }
            if(event.type == TouchEvent.TOUCH_SWIPED_DOWN && ninjaYVelocity == 0){
                ninja.setState(Ninja.State.Slide);
            }
            if(event.type == TouchEvent.TOUCH_SWIPED_RIGHT) {
                Log.d("SwipeEvent", "Swiped Right");
                ninja.setAction(Ninja.Action.MeleeAttack);
            }
            if(event.type == TouchEvent.TOUCH_SWIPED_LEFT){
                Log.d("SwipeEvent", "Swiped Left");
                ninja.setAction(Ninja.Action.RangedAttack);
            }
        }
    }


    @Override
    public void present(float deltaTime){
        UpdateNinja();


        Graphics g = game.getGraphics();
        g.drawPixmap(background, 0, 0);
        DrawUIBar(g);
        DrawEntities(g);

        UpdateObstacles(deltaTime, g);

        if(isPaused) {
            DrawPauseScreen(g);
        } else {
            g.drawPixmap(dieButton, dieButtonXPos, dieButtonYPos);
            g.drawPixmapScaled(pauseButton,
                    pauseButtonXPos, pauseButtonYPos,
                    g.getWidth()-1, uiBarHeight-1 );
            //g.drawPixmap(pauseButton, pauseButtonXPos, pauseButtonYPos);
        }
        //g.drawPixmap(playButton, playXPos, playYPos);

        //g.drawText("TestString", g.getWidth()/2-10, g.getHeight()/2, 20.0f);
        for (int i = 0; i < obstacle.length; i++)
            CheckCollision();
    }



    @Override
    public void pause(){}

    @Override
    public void resume(){

    }
    @Override
    public void dispose(){

    }

    //DRAW Functions


    public void SetupUIValues() {
        uiBarHeight = (int)(game.getGraphics().getHeight()*uiBarHeightPercentage);

        pauseScreenWidth = (int)(game.getGraphics().getWidth()*pauseScreenWidthPercentage);
        pauseScreenHeight = (int)(game.getGraphics().getHeight()*pauseScreenHeightPercentage);
    }

    public void DrawUIBar(Graphics g) {
        g.drawRect(0, 0, g.getWidth()+5, uiBarHeight, Color.BLACK);
        g.drawRect(uiBarOutline, uiBarOutline,
                g.getWidth()-uiBarOutline*2, uiBarHeight-uiBarOutline*2,
                Color.rgb(239, 207, 112));

        //gold
        g.drawText("Gold: 5000g", g.getWidth()*1/20, uiBarHeight/2+11, 24);

        //health bar outline
        g.drawRect(g.getWidth()*4/20-uiHealthbarOutline,
                (int)(uiBarHeight/2-(g.getHeight()*uiBarHeightPercentage/2*uiHealthbarHeight)-uiHealthbarOutline),
                g.getWidth()*8/20+uiHealthbarOutline*2,
                (int)(uiBarHeight*uiHealthbarHeight+uiHealthbarOutline*2),
                Color.BLACK);
        //health bar
        g.drawRect(g.getWidth()*4/20,
                (int)(uiBarHeight/2-(g.getHeight()*uiBarHeightPercentage/2*uiHealthbarHeight)),
                g.getWidth() * 8/20 * ninja.getHealth()/ninja.getMaxHealth(),
                (int)(uiBarHeight*uiHealthbarHeight),
                Color.GREEN);
    }

    public void DrawPauseScreen(Graphics g){

        g.drawRect((int)(g.getWidth()*((1-pauseScreenWidthPercentage)/2))-uiBarOutline,
                (int)(g.getHeight()*((1-pauseScreenHeightPercentage)/2))-uiBarOutline,
                (int)(pauseScreenWidth)+uiBarOutline*2,
                (int)(pauseScreenHeight)+uiBarOutline*2,
                Color.BLACK);
        g.drawRect((int)(g.getWidth()*((1-pauseScreenWidthPercentage)/2)),
                (int)(g.getHeight()*((1-pauseScreenHeightPercentage)/2)),
                (int)(pauseScreenWidth),
                (int)(pauseScreenHeight),
                Color.rgb(239, 207, 112));

        g.drawPixmap(playButton, playButtonXPos,playButtonYPos);
        g.drawPixmap(returnButton, returnButtonXPos,returnButtonYPos);
    }

    public void DrawEntities(Graphics g){
        DrawNinja(g);
        DrawObstacles(g);

    }

    public void DrawNinja(Graphics g){
        /*g.drawPixmap(ninja.getCurrentSprite(),
                ninjaXPos-ninja.getCurrentSprite().getWidth()/2,
                ninjaYPos-ninja.getCurrentSprite().getHeight());
        */
        g.drawPixmapScaled(ninja.getCurrentSprite(),
                ninjaXPos - (int)(ninja.getCurrentSprite().getWidth()*ninjaScale/2),
                ninjaYPos - (int)(ninja.getCurrentSprite().getHeight()*ninjaScale/2),
                ninjaScale);
        if(!isPaused)
            ninja.addFrame();
    }

    public void DrawObstacles(Graphics g){
        for (int i = 0; i < obstacle.length; i++)
        if (obstacle[i] != null) {
            g.drawPixmapScaled(obstacle[i].objectPix,
                    obstacle[i].xLocation, obstacle[i].yLocation,
                    obstacle[i].boxHeightScale);
        }

    }

    public void Jump(){
        ninjaYVelocity = -jumpStrength;
    }

    public void UpdateNinja(){
        //Log.d("GameScreenNinja", "Updating");
        ninjaYPos += ninjaYVelocity;
        ninja.getNinjaYVelocity(ninjaYVelocity);
        ninjaYVelocity -= gravity;

        if(ninjaYPos >= groundYPos){
            ninjaYVelocity = 0;
            ninjaYPos = groundYPos;
            if(ninja.getState()==Ninja.State.Jump){
                ninja.setState(Ninja.State.Ground);
            }
        }
        if (!ninja.isAlive()){
            Log.d("GameScreen", "Ninja Health <= 0, dead");
            game.setScreen(new GameOverScreen(game));
        }



    }

    public void UpdateObstacles(float deltaTime, Graphics g){
        timer += deltaTime;
        for (int i = 0; i < obstacle.length; i++)
        {
            if (obstacle[i] != null)
                obstacle[i].xLocation -= g.getWidth() * 2 / 3 * deltaTime;

        }
        if (timer >= 1 )
        {
            timer = 0;
            for (int i = 0; i < obstacle.length; i++)
            {
                if (obstacle[i] == null)
                {
                    obstacle[i] = new Obstacle(g, r.nextBoolean());
                    break;
                }
            }
            Log.d("GameScreen", "Spawned new box");
        }

    }

    public void CheckCollision(){
        for (int i = 0; i < obstacle.length; i++)
        {
            //Check if obstacle exists
            if (obstacle[i] != null)
            {
                //Check if off screen
                if (obstacle[i].xLocation <= -obstacle[i].boxWidth*2)
                {
                    obstacle[i] = null;
                    break;
                }
                //Check if touching player
                if ((obstacle[i].xLocation < (ninjaXPos + ninja.sprite[1][0].getWidth() * ninjaScale / 2)) &&
                        (obstacle[i].xLocation > (ninjaXPos - ninja.sprite[1][0].getWidth() * ninjaScale / 2)))
                {
                    if (!obstacle[i].isUp && ninja.getState() != Ninja.State.Jump)
                    {
                        ninja.takeDamage(25);
                        obstacle[i] = null;
                    }
                    else if (obstacle[i].isUp && ninja.getState() != Ninja.State.Slide)
                    {
                        ninja.takeDamage(25);
                        obstacle[i] = null;
                    }
                }
            }
        }

        //Against else
    }

}
