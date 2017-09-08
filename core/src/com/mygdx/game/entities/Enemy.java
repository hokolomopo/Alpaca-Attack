package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.MovableEntity;

/**
 * Created by Adrien on 01-09-17.
 */

public abstract class Enemy extends MovableEntity {

    protected static final int INITIAL_KILL_SCORE = 200;
    public int number = 0;

    Enemy(Rectangle rect, String atlasRegion){
        super(rect, atlasRegion);
    }
    public void resetKillScore(){
        killScore = INITIAL_KILL_SCORE;
    }
    public void addKillScore(){
        killScore += INITIAL_KILL_SCORE;
    }

}
