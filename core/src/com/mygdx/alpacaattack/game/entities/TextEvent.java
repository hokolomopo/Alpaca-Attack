package com.mygdx.alpacaattack.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Adrien on 15-09-17.
 */

public class TextEvent extends Entity {
    private boolean isActivated = false;
    private String text;

    public TextEvent(float x, float worldHeight, String text){
        hitbox = new Rectangle(x, 0, 1, worldHeight);
        this.text = text;
    }

    public boolean isActivated(){
        return isActivated;
    }

    public String getString(){
        isActivated = true;
        return  text;
    }

    public void reset(){
        isActivated = false;
    }

    @Override
    public void draw(SpriteBatch batch){

    }

}
