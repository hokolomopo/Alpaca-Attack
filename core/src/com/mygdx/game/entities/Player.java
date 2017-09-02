package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.AlpacaAttack;
import com.mygdx.game.GameScreen;

/**
 * Created by Adrien on 31-08-17.
 */

public class Player extends AnimatedMovableEntity {
    private final static float PLAYER_SPEED = 0.2f;
    private final static float PLAYER_DASH_SPEED = 0.6f;
    private final static float DASH_DURATION = 0.2f;
    private final static float JUMP_SPEED = 0.4f;

    private Animation rainbow_animation;
    private Vector2 rainbow_size;

    public boolean isDashing = false;
    public boolean isJumping = false;
    private float dashTimer = 0;


    public Player(float x, float y){

        weight = 1;

        animation = new Animation(1/7f, textureAtlas.findRegion("run1"), textureAtlas.findRegion("run2"),textureAtlas.findRegion("run3"),textureAtlas.findRegion("run2"));
        /*rainbow_animation = new Animation(1/7f, textureAtlas.findRegion("rainbow1"), textureAtlas.findRegion("rainbow2"));

        rainbow_size = new Vector2(textureAtlas.findRegion("rainbow1").getRegionWidth()* AlpacaAttack.PIXEL_TO_METER, textureAtlas.findRegion("rainbow1").getRegionHeight()* AlpacaAttack.PIXEL_TO_METER);
        //Adjust rainbow size
        rainbow_size.x = rainbow_size.x*0.8f;
        rainbow_size.y = rainbow_size.y/2;*/

        hitbox = new Rectangle(x,y,textureAtlas.findRegion("run1").getRegionWidth()* GameScreen.PIXEL_TO_METER,textureAtlas.findRegion("run1").getRegionHeight()* GameScreen.PIXEL_TO_METER);
        speed = new Vector2(PLAYER_SPEED,0);

        sprite = new Sprite(textureAtlas.findRegion("run1"));
        sprite.setPosition(hitbox.x, hitbox.y);
        sprite.setSize(hitbox.width, hitbox.height);


    }

    @Override
    public void update(){
        if(isDashing){
            dashTimer += Gdx.graphics.getDeltaTime();
            if(dashTimer >= DASH_DURATION){
                speed.x = PLAYER_SPEED;
                dashTimer = 0;
                isDashing = false;
                animation.setFrameDuration(1/7f);
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch){
        elapsedTime += Gdx.graphics.getDeltaTime();
        //batch.draw(rainbow_animation.getKeyFrame(elapsedTime, true), hitbox.getX() - rainbow_size.x + hitbox.getWidth()/4, hitbox.getY() + hitbox.getHeight()/10, rainbow_size.x, rainbow_size.y);
        if(!isJumping)
            batch.draw(animation.getKeyFrame(elapsedTime, true), hitbox.getX(), hitbox.getY(), hitbox.getWidth(), hitbox.getHeight());
        else
            batch.draw(textureAtlas.findRegion("run1"), hitbox.getX(), hitbox.getY(), hitbox.getWidth(), hitbox.getHeight());

    }

    public void dash(){
        isDashing = true;
        speed.x = PLAYER_DASH_SPEED;
        speed.y = 0;
        animation.setFrameDuration(1/12f);
    }

    public void jump(){
        speed.y = JUMP_SPEED;
        isJumping = true;
    }

    public void land(){isJumping = false;}

    @Override
    public void kill() {

    }

    @Override
    public void revive() {

    }
}
