package com.elevationstudios.gladerunner;

import com.elevationstudios.framework.Graphics;
import com.elevationstudios.framework.Pixmap;

/**
 * Created by nickk on 2017-12-07.
 */

public class Enemy {

    protected enum Action{Idle, Attack, Dead}
    protected Action zombieAction;

    protected int health;
    protected boolean alive;
    protected int maxHealth;

    public int xLocation;
    public int yLocation;

    public int frame;

    public Enemy (Graphics g)
    {
        setAction(Action.Idle);
        maxHealth = 50;
        setHealth(maxHealth);
        xLocation = g.getWidth();
        yLocation = (int) (g.getHeight() * 0.78);


    }

    public Action getAction() {
        return zombieAction;
    }

    public void setAction(Action action){
        zombieAction = action;
        UpdateZombieAnimation();
    }

    public Pixmap getCurrentSprite()
    {

        //IF NOT ATTACKING
        if(zombieAction==Action.Idle)
        {
            if (frame > 9)
                frame = 0;
            return (Assets.zombieSprite[0][frame]);
        }
        else if (zombieAction==Action.Attack)
        {
            if (frame > 7)
                frame = 0;
            return (Assets.zombieSprite[1][frame]);
        }
        else
        {
            if (frame > 12)
                frame = 0;
            return (Assets.zombieSprite[2][frame]);
        }
    }
    public void addFrame()
    {
        frame++;
    }

    public void UpdateZombieAnimation()
    {
        frame = 0;
    }

    public Action getState() {
        return zombieAction;
    }

    public void takeDamage(int num){
        setHealth(health-num);
    }

    public int getHealth(){
        return health;
    }

    public void setHealth(int num){
        health = num;
        CheckHealth();
    }

    public void CheckHealth(){
        if (health <= 0){
            setIsAlive(false);
        } else if(health > maxHealth) {
            health = maxHealth;
            setIsAlive(true);
        } else {
            setIsAlive(true);
        }
    }

    public void setIsAlive(boolean b){
        alive = b;
    }
    public boolean isAlive() { return alive;}

}
