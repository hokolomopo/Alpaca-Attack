package com.mygdx.alpacaattack.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.alpacaattack.assets.MenuAssets;

/**
 * Created by Adrien on 07-09-17.
 */

public class Explosion {
    private TextureAtlas atlas;
    private static final float FRAME_DURATION = 1/10f;
    private Animation explosion;
    private Sound sound;

    private float x = 0;
    private float y = 0;
    private float width = 0;
    private float height = 0;

    private float elapsedTime = 0;
    private boolean isDead = false;

    public Explosion(MenuAssets assets){
        atlas = assets.manager.get(MenuAssets.explosionAtlasPath, TextureAtlas.class);
        explosion = new Animation(FRAME_DURATION, atlas.findRegion("explosion1"), atlas.findRegion("explosion2"), atlas.findRegion("explosion3"), atlas.findRegion("explosion4"),
                atlas.findRegion("explosion5"), atlas.findRegion("explosion6"), atlas.findRegion("explosion7"), atlas.findRegion("explosion8"), atlas.findRegion("explosion9"));

        sound = assets.manager.get(MenuAssets.explosionPath, Sound.class);
        sound.play(0.6f * assets.getSoundVolume());
    }

    public Explosion(float argX, float argY, float width, MenuAssets assets){
        this(assets);

        x = argX;
        y = argY;


        this.width = width;
        height = this.width * ((float)atlas.findRegion("explosion5").getRegionHeight() / (float)atlas.findRegion("explosion5").getRegionWidth());
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
