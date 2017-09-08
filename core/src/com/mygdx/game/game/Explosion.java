package com.mygdx.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.screen.GameScreen;

/**
 * Created by Adrien on 07-09-17.
 */

public class Explosion {
    private static final TextureAtlas atlas = new TextureAtlas("explosion.txt");;
    private static final float FRAME_DURATION = 1/9f;
    private static final Animation explosion = new Animation(FRAME_DURATION, atlas.findRegion("explosion1"), atlas.findRegion("explosion2"), atlas.findRegion("explosion3"), atlas.findRegion("explosion4"),
            atlas.findRegion("explosion5"), atlas.findRegion("explosion6"), atlas.findRegion("explosion7"), atlas.findRegion("explosion8"), atlas.findRegion("explosion9"));


    private float x = 0;
    private float y = 0;
    private float width = 0;
    private float height = 0;

    private float elapsedTime = 0;

    public Explosion(){}

    public Explosion(float argX, float argY){
        x = argX;
        y = argY;


        width = 200 * GameScreen.PIXEL_TO_METER;
        height = width * ((float)atlas.findRegion("explosion5").getRegionHeight() / (float)atlas.findRegion("explosion5").getRegionWidth());
    }

    public void render(SpriteBatch batch){
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw(explosion.getKeyFrame(elapsedTime, false), x - width/2 , y - height/2, width, height);
    }
}
