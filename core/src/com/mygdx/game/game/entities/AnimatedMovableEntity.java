package com.mygdx.game.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.assets.GameAssets;

/**
 * Created by Adrien on 31-08-17.
 */

public abstract class AnimatedMovableEntity extends MovableEntity {

    protected Animation animation;

    protected float elapsedTime = 0;

    protected AnimatedMovableEntity(GameAssets assets){
        super(assets);
    }

    @Override
    public void draw(SpriteBatch batch){
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw(animation.getKeyFrame(elapsedTime, true), hitbox.getX(), hitbox.getY(), hitbox.getWidth(), hitbox.getHeight());
    }


}
