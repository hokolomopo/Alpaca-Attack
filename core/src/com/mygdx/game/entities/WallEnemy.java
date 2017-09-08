package com.mygdx.game.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Adrien on 01-09-17.
 */

public class WallEnemy extends Enemy {


    private Rectangle initialLocation;

    public WallEnemy(Rectangle rect){
        super(rect, "greenBug");

        initialLocation = new Rectangle(hitbox);
        killScore = INITIAL_KILL_SCORE;
        speed = new Vector2(0,0);
    }
    @Override
    public void kill() {
        //Set the hitboxSize to 0 to not detect collision while it's dead
        //Set the hitboxY to -10 because ligdx detect collisions even when the size is 0
        //Keep the x to correctly detect the last enemy hit for computing the killScore when reseting the world
        hitbox.set(hitbox.x,-10,0,0);
        sprite.setRegion(textureAtlas.findRegion("greenBugCrying"));
        isDead = true;
    }
    @Override
    public void revive(){
        if(isDead) {
            sprite.setRegion(textureAtlas.findRegion("greenBug"));
            hitbox = new Rectangle(initialLocation);
            isDead = false;
        }
    }
    @Override
    public void update(){
    }
}
