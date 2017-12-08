package com.elevationstudios.gladerunner;


import com.elevationstudios.framework.Game;
import com.elevationstudios.framework.Graphics;
import com.elevationstudios.framework.Input;
import com.elevationstudios.framework.Input.TouchEvent;
import com.elevationstudios.framework.Pixmap;
import com.elevationstudios.framework.Screen;
import com.google.android.gms.games.Games;

import android.graphics.Color;
import android.util.Log;
import java.util.List;
import java.util.Random;

public class GameScreen extends Screen {


    private int dieButtonXPos;
    private int dieButtonYPos;
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


    private int playButtonXPos;
    private int playButtonYPos;

    private int returnButtonXPos;
    private int returnButtonYPos;

    private boolean isPaused = false;

    public Ninja ninja;
    private int ninjaXPos;
    public int ninjaYPos;
    private int ninjaYVelocity;
    private int groundYPos;
    public Knife knife;

    private int gravity = -3;
    private int jumpStrength = 35;

    private float ninjaScale = 0.3f;

    private Obstacle[] obstacle;
    private Enemy[] zombies;
    private float timer;
    private Random r;

    private HealthPickup[] hpPickup;
    private float hpTimer;
    private Random rand;

    private int points = 0;
    private int moneyEarned = 0;
    private int initialMoney = 0;//

    public GameScreen(Game game) {
        super(game);

        Graphics g = game.getGraphics();
        SetupUIValues();

        dieButtonXPos = g.getWidth() - uiBarHeight*2 + 1;
        dieButtonYPos =  0 + 1;

        pauseButtonXPos = g.getWidth()-uiBarHeight+1;
        pauseButtonYPos = 1;


        playButtonXPos = g.getWidth() / 2 - Assets.playButton.getWidth() / 2;
        playButtonYPos = g.getHeight() / 2 - Assets.playButton.getHeight();
        returnButtonXPos = playButtonXPos;
        returnButtonYPos = playButtonYPos + 10 + Assets.playButton.getHeight();

        obstacle = new Obstacle[5];
        zombies = new Enemy[5];
        r = new Random();

        hpPickup = new HealthPickup[5];
        rand = new Random();

        ninja = new Ninja();
        ninja.PrepareAssets(g);
        ninjaXPos = (int) (game.getGraphics().getWidth() * 0.1);
        ninjaYPos = (int) (game.getGraphics().getHeight() * 0.8);
        groundYPos = ninjaYPos;

        initialMoney = Settings.gold;
    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {

                if (!isPaused) {
                    if (inBounds(event, dieButtonXPos, dieButtonYPos,
                            uiBarHeight-1,
                            uiBarHeight-1)) {
                        ninja.takeDamage(25);
                        Log.d("GameScreen", "Clicked Die button");
                        return;
                    }
                    if (inBounds(event, pauseButtonXPos, pauseButtonYPos,
                            uiBarHeight-1, uiBarHeight - 1)) {
                        isPaused = !isPaused;
                        //CheckPause(isPaused);
                        Log.d("GameScreen", "Clicked Pause button");
                        return;
                    }
                } else if (isPaused) {
                    if (inBounds(event, playButtonXPos, playButtonYPos,
                            Assets.playButton.getWidth(), Assets.playButton.getHeight())) {
                        isPaused = !isPaused;
                        Log.d("GameScreen", "Clicked Play button");

                    }
                    if (inBounds(event, returnButtonXPos, returnButtonYPos,
                            Assets.returnButton.getWidth(), Assets.returnButton.getHeight())) {
                        isPaused = !isPaused;
                        game.setScreen(new ShopScreen(game));
                        Log.d("GameScreen", "Clicked Return button");

                    }
                }
            }

            if (event.type == TouchEvent.TOUCH_SWIPED_UP && ninjaYVelocity == 0) {
                Jump();
                ninja.setState(Ninja.State.Jump);
            }
            if (event.type == TouchEvent.TOUCH_SWIPED_DOWN && ninjaYVelocity == 0) {
                ninja.setState(Ninja.State.Slide);
            }
            if (event.type == TouchEvent.TOUCH_SWIPED_RIGHT) {
                Log.d("SwipeEvent", "Swiped Right");
                ninja.setAction(Ninja.Action.MeleeAttack);
            }

            if(event.type == TouchEvent.TOUCH_SWIPED_LEFT && ninja.isAlive()){
                Log.d("SwipeEvent", "Swiped Left");
                ninja.setAction(Ninja.Action.RangedAttack);
                if(knife == null) {
                    Log.d("Knife", "Spawned knife");
                    knife = new Knife(this);
                    knife.yLocation = ninjaYPos - game.getGraphics().getHeight() / 20;
                    if (ninja.getState() == Ninja.State.Jump) {
                        knife.isUp = true;
                    } else{
                        knife.isUp = false;
                    }
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        UpdateNinja();

        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.background, 0, 0);
        DrawUIBar(g);
        DrawEntities(g);


        if (isPaused) {
            DrawPauseScreen(g);
        } else {
            //g.drawPixmap(dieButton, dieButtonXPos, dieButtonYPos);
            g.drawPixmapScaled(Assets.dieButton,
                    dieButtonXPos, dieButtonYPos,
                    g.getWidth() - (g.getWidth()-pauseButtonXPos) - 1,
                    uiBarHeight - 1);
            g.drawPixmapScaled(Assets.pauseButton,
                    pauseButtonXPos, pauseButtonYPos,
                    g.getWidth() - 1, uiBarHeight - 1);


            UpdateKnife(deltaTime, g);
            UpdateObstacles(deltaTime, g);
            UpdateHealthSpawn(deltaTime, g);
        }
        //g.drawPixmap(playButton, playXPos, playYPos);

        //g.drawText("TestString", g.getWidth()/2-10, g.getHeight()/2, 20.0f);
        for (int i = 0; i < obstacle.length; i++)
            CheckCollision(deltaTime);

        for (int i = 0; i < hpPickup.length; i++)
            CheckHealthCol();
    }

    @Override
    public void pause() {
        Settings.updateLastRunDistance(points);//(int)Math.floor(points/5));
        Settings.updateLastRunGold(moneyEarned);
        Settings.addGold(moneyEarned);
        Settings.save(game.getFileIO());
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    //DRAW Functions


    public void SetupUIValues() {
        uiBarHeight = (int) (game.getGraphics().getHeight() * uiBarHeightPercentage);

        pauseScreenWidth = (int) (game.getGraphics().getWidth() * pauseScreenWidthPercentage);
        pauseScreenHeight = (int) (game.getGraphics().getHeight() * pauseScreenHeightPercentage);
    }

    public void DrawUIBar(Graphics g) {
        g.drawRect(0, 0, g.getWidth() + 5, uiBarHeight, Color.BLACK);
        g.drawRect(uiBarOutline, uiBarOutline,
                g.getWidth() - uiBarOutline * 2, uiBarHeight - uiBarOutline * 2,
                Color.rgb(239, 207, 112));

        //gold
        g.drawText("Gold: " + (initialMoney + moneyEarned) + "g", g.getWidth() * 1 / 20, uiBarHeight / 2 + 11, 24);
        g.drawText("Dist: " + (points) + "m", g.getWidth() * 4 / 20, uiBarHeight / 2 + 11, 24);

        //health bar outline
        g.drawRect(g.getWidth() * 7 / 20 - uiHealthbarOutline,
                (int) (uiBarHeight / 2 - (g.getHeight() * uiBarHeightPercentage / 2 * uiHealthbarHeight) - uiHealthbarOutline),
                g.getWidth() * 8 / 20 + uiHealthbarOutline * 2,
                (int) (uiBarHeight * uiHealthbarHeight + uiHealthbarOutline * 2),
                Color.BLACK);
        //health bar
        g.drawRect(g.getWidth() * 7 / 20,
                (int) (uiBarHeight / 2 - (g.getHeight() * uiBarHeightPercentage / 2 * uiHealthbarHeight)),
                g.getWidth() * 8 / 20 * ninja.getHealth() / ninja.getMaxHealth(),
                (int) (uiBarHeight * uiHealthbarHeight),
                Color.GREEN);


    }

    public void DrawPauseScreen(Graphics g) {

        g.drawRect((int) (g.getWidth() * ((1 - pauseScreenWidthPercentage) / 2)) - uiBarOutline,
                (int) (g.getHeight() * ((1 - pauseScreenHeightPercentage) / 2)) - uiBarOutline,
                (int) (pauseScreenWidth) + uiBarOutline * 2,
                (int) (pauseScreenHeight) + uiBarOutline * 2,
                Color.BLACK);
        g.drawRect((int) (g.getWidth() * ((1 - pauseScreenWidthPercentage) / 2)),
                (int) (g.getHeight() * ((1 - pauseScreenHeightPercentage) / 2)),
                (int) (pauseScreenWidth),
                (int) (pauseScreenHeight),
                Color.rgb(239, 207, 112));

        g.drawPixmap(Assets.playButton, playButtonXPos, playButtonYPos);
        g.drawPixmap(Assets.returnButton, returnButtonXPos, returnButtonYPos);
    }

    public void DrawEntities(Graphics g) {
        DrawNinja(g);
        DrawZombies(g);
        DrawObstacles(g);
        DrawKnife(g);
        DrawHealthPickup(g);
    }

    public void DrawKnife(Graphics g) {
        if(knife != null) {
            g.drawPixmapScaled(Assets.knife,
                    knife.xLocation, knife.yLocation,
                    0.25f);
        }
    }

    public void UpdateKnife(float deltaTime, Graphics g) {
        if(knife != null) {
            knife.xLocation += g.getWidth() * 2 / 3 * deltaTime;
            if(knife.xLocation >= game.getGraphics().getWidth()) {
                knife = null;
            }
        }
    }

    public void DrawNinja(Graphics g) {
        /*g.drawPixmap(ninja.getCurrentSprite(),
                ninjaXPos-ninja.getCurrentSprite().getWidth()/2,
                ninjaYPos-ninja.getCurrentSprite().getHeight());
        */
        g.drawPixmapScaled(ninja.getCurrentSprite(),
                ninjaXPos - (int) (ninja.getCurrentSprite().getWidth() * ninjaScale / 2),
                ninjaYPos - (int) (ninja.getCurrentSprite().getHeight() * ninjaScale / 2),
                ninjaScale);
        if (!isPaused)
        {
            ninja.addFrame();
        }

    }
    public void DrawZombies(Graphics g) {
        for (int i = 0; i < zombies.length; i++)
        {
            if (zombies[i] != null)
            {
                g.drawPixmapScaled(zombies[i].getCurrentSprite(),
                        zombies[i].xLocation - (int) (zombies[i].getCurrentSprite().getWidth() * ninjaScale / 2),
                        zombies[i].yLocation - (int) (zombies[i].getCurrentSprite().getHeight() * ninjaScale / 2),
                        ninjaScale);
                if (!isPaused)
                    zombies[i].addFrame();
            }
        }
    }

    public void DrawObstacles(Graphics g) {
        for (int i = 0; i < obstacle.length; i++)
            if (obstacle[i] != null) {
                g.drawPixmapScaled(obstacle[i].objectPix,
                        obstacle[i].xLocation, obstacle[i].yLocation,
                        obstacle[i].boxHeightScale);
            }
    }

    public void DrawHealthPickup(Graphics g){
        for (int i = 0; i < hpPickup.length; i++)
            if (hpPickup[i] != null) {
                g.drawPixmapScaled(Assets.health,
                        hpPickup[i].xLocation, hpPickup[i].yLocation,
                        hpPickup[i].boxHeightScale);
            }

    }

    public void Jump() {
        ninjaYVelocity = -jumpStrength;
    }

    public void UpdateNinja() {
        //Log.d("GameScreenNinja", "Updating");
        ninjaYPos += ninjaYVelocity;
        ninja.getNinjaYVelocity(ninjaYVelocity);
        ninjaYVelocity -= gravity;

        if (ninjaYPos >= groundYPos) {
            ninjaYVelocity = 0;
            ninjaYPos = groundYPos;
            if (ninja.getState() == Ninja.State.Jump) {
                ninja.setState(Ninja.State.Ground);
            }
        }
        if (!ninja.isAlive()) {
            Log.d("GameScreen", "Ninja Health <= 0, dead");
            game.unlockDeathAchieve();
            game.setScreen(new GameOverScreen(game));
            game.submitScore(points);
        }
    }

    public void UpdateObstacles(float deltaTime, Graphics g) {
        timer += deltaTime;
        for (int i = 0; i < obstacle.length; i++)  {
            if (obstacle[i] != null)
                obstacle[i].xLocation -= g.getWidth() * 2 / 3 * deltaTime;
        }
        for (int i = 0; i < zombies.length; i++) {
            if (zombies[i] != null)
                zombies[i].xLocation -= g.getWidth() * 5 / 6 * deltaTime;
        }

        if (timer >= 1 ) {
            timer = 0;
            points += 1;
            if (r.nextFloat() > 0.5)
            {
                for (int i = 0; i < obstacle.length; i++) {
                    if (obstacle[i] == null) {
                        obstacle[i] = new Obstacle(g, r.nextBoolean());
                        Log.d("GameScreen", "Spawned new obstacle");
                        break;
                    }
                }
            }
            else
            {
                for (int i = 0; i < zombies.length; i++) {
                    if (zombies[i] == null) {
                        zombies[i] = new Enemy(g);
                        Log.d("GameScreen", "Spawned new zombie");
                        break;
                    }
                }
            }
        }
    }

    public void UpdateHealthSpawn(float deltaTime, Graphics g){
        hpTimer += deltaTime;
        for (int i = 0; i < hpPickup.length; i++)
        {
            if (hpPickup[i] != null)
                hpPickup[i].xLocation -= g.getWidth() * 2 / 3 * deltaTime;

        }
        if (hpTimer >= 2.5 )
        {
            hpTimer = 0;
            for (int i = 0; i < hpPickup.length; i++)
            {
                if (hpPickup[i] == null)
                {
                    hpPickup[i] = new HealthPickup(g, rand.nextBoolean());
                    break;
                }
            }
            Log.d("GameScreen", "Spawned Health");
        }

    }

    public void CheckCollision(float deltaTime) {
        for (int i = 0; i < obstacle.length; i++)
        {
            //Check if obstacle exists
            if (obstacle[i] != null) {
                //Check if off screen
                if (obstacle[i].xLocation <= -obstacle[i].boxWidth * 2) {
                    obstacle[i] = null;
                    break;
                }
                //Check if touching player
                if ((obstacle[i].xLocation < (ninjaXPos + Assets.ninjaSprite[1][0].getWidth() * ninjaScale / 2)) &&
                        (obstacle[i].xLocation > (ninjaXPos - Assets.ninjaSprite[1][0].getWidth() * ninjaScale / 2))) {
                    if (!obstacle[i].isUp && ninja.getState() != Ninja.State.Jump) {
                        ninja.takeDamage(25);
                        obstacle[i] = null;
                    } else if (obstacle[i].isUp && ninja.getState() != Ninja.State.Slide) {
                        ninja.takeDamage(25);
                        obstacle[i] = null;
                    } else {
                        moneyEarned += 5;
                    }
                    break;
                }

            }

            //Against else
        }
        for (int i = 0; i < zombies.length; i++)
        {
            if (zombies[i] != null)
            {
                if (ninja != null)
                {
                    if (zombies[i].xLocation <= ninjaXPos + ninja.getCurrentSprite().getWidth() * ninjaScale + game.getGraphics().getWidth() * 5 * deltaTime &&
                            zombies[i].getAction() == Enemy.Action.Idle)
                    {
                        zombies[i].setAction(Enemy.Action.Attack);
                    }
                    if (zombies[i].xLocation <= (ninjaXPos + ninja.getCurrentSprite().getWidth() * ninjaScale / 4) &&
                            zombies[i].getAction() == Enemy.Action.Attack)
                    {
                        if (ninja.ninjaAction == Ninja.Action.MeleeAttack)
                        {
                            zombies[i].takeDamage(50);
                            moneyEarned += 100;
                            zombies[i].setAction(Enemy.Action.Dead);
                            break;
                        }
                        else
                        {
                            ninja.takeDamage(40);
                            zombies[i] = null;
                            break;
                        }
                    }
                }

                if (knife != null) {
                    if ((zombies[i].xLocation < knife.xLocation + Assets.knife.getWidth() * 0.25f) &&
                            (zombies[i].xLocation > (knife.xLocation)) &&
                            knife.yLocation >= (zombies[i].yLocation - zombies[i].getCurrentSprite().getHeight() * ninjaScale / 2))
                    {
                        Log.d("GameScreen.java", "Knife hit");
                        zombies[i].takeDamage(50);
                        if (zombies[i].isAlive() == false)
                        {
                            zombies[i].setAction(Enemy.Action.Dead);
                        }
                        knife = null;
                        Log.d("GameScreen.java", "Knife deleted");
                        break;
                    }
                }
                if (zombies[i].getAction() == Enemy.Action.Dead)
                {
                    if (zombies[i].frame > 11)
                    {
                        zombies[i] = null;
                        moneyEarned += 100;
                    }
                }
            }


        }
    }

    void CheckHealthCol(){
        for (int i = 0; i < hpPickup.length; i++) {
            //Check if pickup exists
            if (hpPickup[i] != null) {
                //Check if off screen
                if (hpPickup[i].xLocation <= -hpPickup[i].boxWidth * 2) {
                    hpPickup[i] = null;
                    break;
                }
                //Check if touching player
                if ((hpPickup[i].xLocation < (ninjaXPos + Assets.ninjaSprite[1][0].getWidth() * ninjaScale / 2)) &&
                        (hpPickup[i].xLocation > (ninjaXPos - Assets.ninjaSprite[1][0].getWidth() * ninjaScale / 2))) {
                    if (!hpPickup[i].isUp && ninja.getState() != Ninja.State.Jump) {
                        ninja.takeDamage(-50);
                        hpPickup[i] = null;
                    } else if (hpPickup[i].isUp && ninja.getState() == Ninja.State.Jump) {
                        ninja.takeDamage(-50);
                        hpPickup[i] = null;
                    }
                }
            }
        }
    }
}
