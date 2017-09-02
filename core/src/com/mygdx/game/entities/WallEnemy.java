package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Adrien on 01-09-17.
 */

public class WallEnemy extends Enemy {

    private static final float DYING_DURATION = 2;

    Rectangle initialLocation;

    public WallEnemy(Rectangle rect){
        super(rect, "greenBug");

        initialLocation = new Rectangle(hitbox);

        speed = new Vector2(0,0);
        dyingTimer=0;
    }
    @Override
    public void kill() {
        hitbox.setSize(0,0);
        sprite.setRegion(textureAtlas.findRegion("greenBugCrying"));
        isDying = true;
        dyingTimer = 0;
    }
    @Override
    public void revive(){
        if(isDead || isDying) {
            sprite.setRegion(textureAtlas.findRegion("greenBug"));
            hitbox = new Rectangle(initialLocation);
            isDead = false;
            isDying = false;
            dyingTimer = 0;
        }
    }
    @Override
    public void update(){
        if(isDying){
            dyingTimer += Gdx.graphics.getDeltaTime();
            if(dyingTimer > DYING_DURATION){
                sprite.setRegion(textureAtlas.findRegion("void"));
                isDead =true;
                isDying = false;
                dyingTimer = 0;
            }
        }
    }
}
