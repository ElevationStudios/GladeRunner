package com.elevationstudios.gladerunner;

import android.util.Log;

import com.elevationstudios.framework.Graphics;
import com.elevationstudios.framework.Pixmap;


public class Ninja {

    protected enum State{Dead, Ground, Jump, Slide}
    protected enum Action{Idle, MeleeAttack, RangedAttack}
    protected State ninjaState;
    protected Action ninjaAction;

    protected boolean alive;
    protected int health;
    protected int maxHealth = 100;

    protected int yVelocity;

    public int frame;
    public int frameTimer = 0;

    public Pixmap sprite[][] = new Pixmap[8][10];
    //0-3 = state, 4-5 = groundattacks, 6-7 = aerialattacks


    public Ninja(){
        setHealth(maxHealth);
        setState(State.Ground);
        setAction(Action.Idle);
    }
    //grab assets
    public void PrepareAssets(Graphics g){
        Log.d("Ninja.java", "Starting PrepareAssets");
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


        Log.d("Ninja.java", "Finished PrepareAssets");
    }

    public State getState() {
        return ninjaState;
    }

    public void setState(State state){
        ninjaState = state;
        UpdateNinjaAnimation();
    }

    public Action getAction() {
        return ninjaAction;
    }

    public void setAction(Action action){
        ninjaAction = action;
        UpdateNinjaAnimation();
    }

    public int getHealth(){
        return health;
    }
    public void setHealth(int num){
        health = num;
        CheckHealth();
    }
    public void takeDamage(int num){
        setHealth(health-num);
    }

    public void CheckHealth(){
        if (health <= 0){
            alive = false;
        } else {
            alive = true;
        }
    }

    public int getMaxHealth(){return maxHealth;}

    public boolean isAlive(){
        return alive;
    }

    public void setIsAlive(boolean b){
        alive = b;
    }

    public Pixmap getCurrentSprite(){

            //IF NOT ATTACKING
        if(ninjaAction==Action.Idle) {
            if (ninjaState == State.Ground) {
                if (frame > 9)
                    frame = 0;
                return (sprite[1][frame]);
            } else if (ninjaState == State.Jump) {
                if (yVelocity < 0) { //going up
                    if (frame > 5)
                        frame = 5;
                } else if (yVelocity >= 0) {//going down
                    if (frame > 9)
                        frame = 9;
                }
                return (sprite[2][frame]);
            } else if (ninjaState == State.Slide){
                if(frame>9)
                    frame = 0;
                return (sprite[3][frame]);
            }
        } else {
            //IF ATTACKING
            if(ninjaAction == Action.MeleeAttack) {
                if (ninjaState == State.Ground || ninjaState == State.Slide) {
                    if (frame > 9) //Finished attacking
                        setAction(Action.Idle);
                    return (sprite[4][frame]);
                } else if (ninjaState == State.Jump) {
                    if (frame > 9) //Finished attacking
                        setAction(Action.Idle);
                    return (sprite[6][frame]);
                }
            }
            if(ninjaAction == Action.RangedAttack) {
                if (ninjaState == State.Ground || ninjaState == State.Slide) {
                    if (frame > 9)
                        setAction(Action.Idle);
                    return (sprite[5][frame]);
                } else if (ninjaState == State.Jump) {
                    if (frame > 9)
                        setAction(Action.Idle);
                    return (sprite[7][frame]);
                }
            }
        }
        return sprite[1][0];
    }

    public void addFrame() {
        //control for sliding
        if (ninjaState == State.Slide) {
            if (frameTimer < 55) {
                frameTimer++;
            } else {
                setState(State.Ground);

            }
        }
        frame++;
    }

    public void UpdateNinjaAnimation(){
        frame = 0;
        frameTimer = 0;
    }

    public void getNinjaYVelocity(int velocity){
        yVelocity = velocity;
    }


}
