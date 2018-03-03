package com.mygdx.alpacaattack.game.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.alpacaattack.assets.MenuAssets;

/**
 * Created by Adrien on 31-08-17.
 */

public abstract class MovableEntity extends Entity implements Destroyable {
    protected Vector2 speed =  new Vector2(0,0);

    public boolean isDead = false;

    protected float weight = 0;

    public MovableEntity(MenuAssets assets){
        super(assets);
    }

    public MovableEntity(Rectangle rect, String atlasRegion, MenuAssets assets){
        super(rect, atlasRegion, assets);
    }

    public float getWeight(){
        return weight;
    }

    public int getKillScore(){return 0;}

    public void move(float x, float y){
        sprite.translate(x,y);
        hitbox.setPosition(sprite.getX(),sprite.getY());
    }

    public void move(){
        sprite.translate(speed.x, speed.y);
        hitbox.setPosition(hitbox.getX() + speed.x, hitbox.getY() + speed.y);
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
