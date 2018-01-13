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
    protected int maxHealth = Settings.getMaxHealth();//(100 * Settings.boughtItems[0]) + (100 * Settings.boughtItems[4]) + 100;

    protected int yVelocity;

    public int frame;
    public int frameTimer = 0;
    public int attackTimer = 0;
    public int slideTime = 35;



    public Ninja(){
        Settings.updateMaxHealth();
        maxHealth = Settings.getMaxHealth();
        setHealth(maxHealth);
        setState(State.Ground);
        setAction(Action.Idle);
    }
    //grab assets
    public void PrepareAssets(Graphics g){

    }

    public State getState() {
        return ninjaState;
    }

    public void setState(State state){
        ninjaState = state;
        frameTimer = 0;
        if(state == State.Dead)
            frame = 0;
    }

    public Action getAction() {
        return ninjaAction;
    }

    public void setAction(Action action){
        ninjaAction = action;
        frame = 0;
    }

    public int getHealth(){
        return health;
    }
    public void setHealth(int num){
        health = num;
        if (health < 0)
            health = 0;
        if (health > maxHealth)
            health = maxHealth;
        CheckHealth();
    }
    public void takeDamage(int num){
        setHealth(health-num);
    }

    public void CheckHealth(){
        if (health <= 0){
            alive = false;
        } else if(health > maxHealth) {
            health = maxHealth;
            alive = true;
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
        if(ninjaState == State.Dead) {
            if(frame > 9)
                return(Assets.ninjaSprite[0][9]);
            return(Assets.ninjaSprite[0][frame]);
        }

        if(ninjaAction==Action.Idle) {
            if (ninjaState == State.Ground) {
                if (frame > 9)
                    frame = 0;
                return (Assets.ninjaSprite[1][frame]);
            } else if (ninjaState == State.Jump) {
                if (yVelocity < 0) { //going up
                    if (frame > 5)
                        frame = 5;
                } else if (yVelocity >= 0) {//going down
                    if (frame > 9)
                        frame = 9;
                }
                return (Assets.ninjaSprite[2][frame]);
            } else if (ninjaState == State.Slide){
                if(frame>9)
                    frame = 0;
                return (Assets.ninjaSprite[3][frame]);
            }
        } else {
            //IF ATTACKING
            if(ninjaAction == Action.MeleeAttack) {
                if (ninjaState == State.Ground || ninjaState == State.Slide) {
                    if (frame > 9) //Finished attacking
                        setAction(Action.Idle);
                    return (Assets.ninjaSprite[4][frame]);
                } else if (ninjaState == State.Jump) {
                    if (frame > 9) //Finished attacking
                        setAction(Action.Idle);
                    return (Assets.ninjaSprite[6][frame]);
                }
            }
            if(ninjaAction == Action.RangedAttack) {
                if (ninjaState == State.Ground || ninjaState == State.Slide) {
                    if (frame > 9)
                        setAction(Action.Idle);
                    return (Assets.ninjaSprite[5][frame]);
                } else if (ninjaState == State.Jump) {
                    if (frame > 9)
                        setAction(Action.Idle);
                    return (Assets.ninjaSprite[7][frame]);
                }
            }
        }
        return Assets.ninjaSprite[1][0];
    }

    public void addFrame() {
        //control for sliding
        if (ninjaState == State.Slide) {
            if (frameTimer < slideTime) {
                frameTimer++;
            } else {
                setState(State.Ground);

            }
        }
        frame++;
    }

    public int getFrameNum() {
        return frame;
    }

    public void getNinjaYVelocity(int velocity){
        yVelocity = velocity;
    }


}
