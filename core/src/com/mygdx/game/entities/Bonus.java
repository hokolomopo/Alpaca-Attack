package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.screen.GameScreen;

/**
 * Created by Adrien on 01-09-17.
 */

public class Bonus extends MovableEntity{

    private static final float BONUS_SIZE = 70 * GameScreen.PIXEL_TO_METER;
    private static final float BONUS_SPEED = 1.05f * GameScreen.PIXEL_TO_METER;
    private static final float MOVEMENT_DURATION = 0.8f;
    private static final int BONUS_SCORE = 1000;

    private float moveTimer = 0;
    private Rectangle initialLocation;


    public Bonus(float x, float y){
        super(new Rectangle(x,y,BONUS_SIZE,BONUS_SIZE), "star");

        initialLocation = new Rectangle(hitbox);

        killScore = 1000;

        speed = new Vector2(0,BONUS_SPEED);
    }

    @Override
    public void update(){
        this.move();

        moveTimer += Gdx.graphics.getDeltaTime();
        if(moveTimer > MOVEMENT_DURATION){
            speed.y = - speed.y;
            moveTimer = 0;
        }
    }

    @Override
    public void kill() {
        hitbox.setSize(0,0);
        hitbox.setPosition(0,0);
        sprite.setRegion(textureAtlas.findRegion("void"));
        isDead = true;
    }

    @Override
    public void revive() {
        if(isDead){

            hitbox = new Rectangle(initialLocation);
            sprite.setRegion(textureAtlas.findRegion("star"));
            isDead = false;
        }
    }
    @Override
    public int getKillScore(){
        return BONUS_SCORE;
    }
}
