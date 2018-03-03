package com.mygdx.alpacaattack.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.alpacaattack.assets.MenuAssets;

/**
 * Created by Adrien on 01-09-17.
 */

public class Bonus extends MovableEntity{

    private static final float BONUS_SIZE = 70 * com.mygdx.alpacaattack.screen.GameScreen.PIXEL_TO_METER;
    private static final float BONUS_SPEED = 1.05f * com.mygdx.alpacaattack.screen.GameScreen.PIXEL_TO_METER;
    private static final float MOVEMENT_DURATION = 0.8f;
    private static final int BONUS_SCORE = 1000;

    private float moveTimer = 0;
    private Rectangle initialLocation;

    private ParticleEffect effect;
    private Array<ParticleEmitter> emiters;

    public Bonus(float x, float y, MenuAssets assets){
        super(new Rectangle(x,y,BONUS_SIZE,BONUS_SIZE), "star", assets);

        effect = assets.createBonusParticleEffect();
        emiters = effect.getEmitters();
        for(ParticleEmitter e : emiters) {
            e.setAttached(false);
            e.setContinuous(true);
        }

        initialLocation = new Rectangle(hitbox);

        speed.set(0,BONUS_SPEED);
    }

    @Override
    public void update(){
        this.move();

        moveTimer += Gdx.graphics.getDeltaTime();
        if(moveTimer > MOVEMENT_DURATION){
            speed.y = - speed.y;
            moveTimer = 0;
        }
    }

    @Override
    public void kill() {
        hitbox.setSize(0,0);
        hitbox.setPosition(0,0);
        sprite.setRegion(textureAtlas.findRegion("void"));
        isDead = true;
    }

    @Override
    public void revive() {
        if(isDead){

            hitbox.set(initialLocation);
            sprite.setPosition(initialLocation.x, initialLocation.y);
            sprite.setRegion(textureAtlas.findRegion("star"));
            isDead = false;
        }
    }
    @Override
    public int getKillScore(){
        return BONUS_SCORE;
    }

    @Override
    public void draw(SpriteBatch batch){

        effect.update(Gdx.graphics.getDeltaTime());
        for(ParticleEmitter e : emiters) {
            e.setPosition(this.getX(), this.getY() + this.getHitbox().height/2);
        }
        effect.draw(batch);

        sprite.draw(batch);
    }

}
