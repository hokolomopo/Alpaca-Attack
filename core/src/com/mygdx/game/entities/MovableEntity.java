package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Adrien on 31-08-17.
 */

public abstract class MovableEntity extends Entity implements Destroyable{
    protected Vector2 speed =  new Vector2(0,0);

    boolean isDead = false;

    protected float weight = 0;

    public MovableEntity(){
    }

    public MovableEntity(Rectangle rect, String atlasRegion){
        super(rect, atlasRegion);
    }

    public float getWeight(){
        return weight;
    }


    public void move(float x, float y){
        sprite.translate(x,y);
        hitbox.setPosition(sprite.getX(),sprite.getY());
    }

    public void move(){
        sprite.translate(speed.x, speed.y);
        hitbox.setPosition(sprite.getX(),sprite.getY());
    }

    public void setSpeed(float x, float y){
        speed.x = x;
        speed.y = y;
    }
    public void setYSpeed(float y){
        speed.y = y;
    }
    public void setXSpeed(float x){
        speed.x = x;
    }
    public Vector2 getSpeed(){
        return speed;
    }

    public void update(){
        move();
    }





}
