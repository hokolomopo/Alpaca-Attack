package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Adrien on 01-09-17.
 */

public class Bonus extends MovableEntity{

    private static final float BONUS_SIZE = 2;
    private static final float BONUS_SPEED = 0.03f;
    private static final float MOVEMENT_DURATION = 0.8f;

    private float moveTimer = 0;


    public Bonus(float x, float y){
        super(new Rectangle(x,y,BONUS_SIZE,BONUS_SIZE), "star");

        speed = new Vector2(0,BONUS_SPEED);
    }

    @Override
    public void update(){
        super.update();

        moveTimer += Gdx.graphics.getDeltaTime();
        if(moveTimer > MOVEMENT_DURATION){
            speed.y = - speed.y;
            moveTimer = 0;
        }
    }

    @Override
    public void kill() {
        hitbox.setSize(0,0);
        sprite.setRegion(textureAtlas.findRegion("void"));
        isDead = true;
    }

    @Override
    public void revive() {
        if(isDead){
            hitbox.setSize(BONUS_SIZE,BONUS_SIZE);
            sprite.setRegion(textureAtlas.findRegion("star"));
            isDead = false;
        }
    }
}
