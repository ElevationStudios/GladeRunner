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
    public int slideTimer = 35;



    public Ninja(){
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
            if (frameTimer < slideTimer) {
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
