package com.mygdx.game.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Adrien on 31-08-17.
 */

public class Platform extends Entity{

    public Platform(Rectangle rect){
        hitbox = new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    @Override
    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }

}
