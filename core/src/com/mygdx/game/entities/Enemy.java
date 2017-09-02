package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Adrien on 01-09-17.
 */

public abstract class Enemy extends MovableEntity{
    Enemy(Rectangle rect, String atlasRegion){
        super(rect, atlasRegion);
    }

    protected float dyingTimer = 0;
    public boolean isDying = false;


}
