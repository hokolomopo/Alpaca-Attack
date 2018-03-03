package com.mygdx.alpacaattack.game.entities;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.alpacaattack.assets.MenuAssets;

/**
 * Created by Adrien on 01-09-17.
 */

public abstract class Enemy extends MovableEntity {

    protected static int killScore = 1000;
    protected static final int INITIAL_KILL_SCORE = 200;
    public int number = 0;

    Enemy(Rectangle rect, String atlasRegion, MenuAssets assets){
        super(rect, atlasRegion, assets);
    }
    public void resetKillScore(){
        killScore = INITIAL_KILL_SCORE;
    }
    public void addKillScore(){
        killScore += INITIAL_KILL_SCORE;
    }

}
