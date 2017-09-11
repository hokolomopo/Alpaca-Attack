package com.mygdx.game.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.assets.GameAssets;
import com.mygdx.game.screen.GameScreen;

/**
 * Created by Adrien on 07-09-17.
 */

public class Explosion {
    private TextureAtlas atlas;// = new TextureAtlas("explosion.txt");
    private static final float FRAME_DURATION = 1/10f;
    private Animation explosion;
    private Sound sound;

    private float x = 0;
    private float y = 0;
    private float width = 0;
    private float height = 0;

    private float elapsedTime = 0;
    private boolean isDead = false;

    public Explosion(GameAssets assets){
        atlas = assets.manager.get("explosion.txt", TextureAtlas.class);
        explosion = new Animation(FRAME_DURATION, atlas.findRegion("explosion1"), atlas.findRegion("explosion2"), atlas.findRegion("explosion3"), atlas.findRegion("explosion4"),
                atlas.findRegion("explosion5"), atlas.findRegion("explosion6"), atlas.findRegion("explosion7"), atlas.findRegion("explosion8"), atlas.findRegion("explosion9"));

        sound = assets.manager.get("sound/sfx/explosion.wav");
        sound.setVolume(sound.play(), 0.4f);
    }

    public Explosion(float argX, float argY, GameAssets assets){
        this(assets);

        x = argX;
        y = argY;


        width = 200 * GameScreen.PIXEL_TO_METER;
        height = width * ((float)atlas.findRegion("explosion5").getRegionHeight() / (float)atlas.findRegion("explosion5").getRegionWidth());
    }

    public void render(SpriteBatch batch){
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw(explosion.getKeyFrame(elapsedTime, false), x - width/2 , y - height/2, width, height);
        if(explosion.isAnimationFinished(elapsedTime))
            isDead = true;
    }
    public boolean isDead(){
        return isDead;
    }
}
