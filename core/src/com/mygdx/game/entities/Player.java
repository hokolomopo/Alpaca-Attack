package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.game.Explosion;
import com.mygdx.game.screen.GameScreen;

/**
 * Created by Adrien on 31-08-17.
 */

public class Player extends AnimatedMovableEntity {
    private final static float PLAYER_SPEED = 7f *GameScreen.PIXEL_TO_METER;
    private final static float PLAYER_DASH_SPEED = 21f*GameScreen.PIXEL_TO_METER;
    private final static float JUMP_SPEED = 9f*GameScreen.PIXEL_TO_METER;

    private final static float DASH_DURATION = 0.2f;
    private final static  float DEAD_ANIMATION_DURATION = 2f;

    private Explosion explosion = new Explosion();

    public boolean isDashing = false;
    public boolean isJumping = false;

    public boolean isDying =  false;

    private float dashTimer = 0;
    private float deadTimer = 0;
    private int jumpLeft = 2;
    private int score = 0;

    private float initialX;
    private float initialY;

    private String color = "white";


    private final static float SIZE_MULTIPLIER = 0.5f;
    public Player(float x, float y){

        weight = 1;
        initialX = x;
        initialY = y;

        animation = new Animation(1/7f, textureAtlas.findRegion("run1"+"_"+color), textureAtlas.findRegion("run2"+"_"+color),textureAtlas.findRegion("run3"+"_"+color),textureAtlas.findRegion("run2"+"_"+color));

        hitbox = new Rectangle(x,y,textureAtlas.findRegion("run1"+"_"+color).getRegionWidth()* GameScreen.PIXEL_TO_METER* SIZE_MULTIPLIER,textureAtlas.findRegion("run1"+"_"+color).getRegionHeight()* GameScreen.PIXEL_TO_METER* SIZE_MULTIPLIER);
        speed = new Vector2(PLAYER_SPEED,0);

        sprite = new Sprite(textureAtlas.findRegion("run1"+"_"+color));
        sprite.setPosition(hitbox.x, hitbox.y);
        sprite.setSize(textureAtlas.findRegion("run1"+"_"+color).getRegionWidth()* GameScreen.PIXEL_TO_METER* SIZE_MULTIPLIER,textureAtlas.findRegion("run1"+"_"+color).getRegionHeight()* GameScreen.PIXEL_TO_METER* SIZE_MULTIPLIER);


    }

    public void reset(){
        isDashing = false;
        isJumping = false;
        isDying = false;
        isDead = false;
        dashTimer = 0;
        deadTimer = 0;
        jumpLeft = 2;
        score = 0;
        elapsedTime = 0;
        this.setSpeed(PLAYER_SPEED,0);

        hitbox.setPosition(initialX, initialY);
        sprite.setPosition(hitbox.x, hitbox.y);
        animation.setFrameDuration(1/7f);
    }

    @Override
    public void update(){
        if(isDying){
            deadTimer += Gdx.graphics.getDeltaTime();
            if(deadTimer > DEAD_ANIMATION_DURATION)
                isDead = true;
        }
        else if(isDashing){
            dashTimer += Gdx.graphics.getDeltaTime();
            if(dashTimer >= DASH_DURATION){
                speed.x = PLAYER_SPEED;
                animation.setFrameDuration(1/7f);
                if(dashTimer >= DASH_DURATION*5/3f){
                    dashTimer = 0;
                    isDashing = false;
                }
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch){
        elapsedTime += Gdx.graphics.getDeltaTime();
        if(!isJumping)
            batch.draw(animation.getKeyFrame(elapsedTime, true), hitbox.getX(), hitbox.getY(), sprite.getWidth(), sprite.getHeight());
        else
            batch.draw(textureAtlas.findRegion("run1"+"_"+color), hitbox.getX(), hitbox.getY(), sprite.getWidth(), sprite.getHeight());
        if(explosion !=  null)
            explosion.render(batch);

    }

    public void dash(){
        if(!isDying) {
            if (jumpLeft == 0)
                jumpLeft++;

            isDashing = true;
            speed.x = PLAYER_DASH_SPEED;
            speed.y = 0;
            animation.setFrameDuration(1 / 12f);
        }
    }

    public void jump(){
        if(jumpLeft > 0) {
            speed.y = JUMP_SPEED;
            isJumping = true;
            jumpLeft--;
        }
    }

    public int getScore(){
        return score;
    }

    public void addScore(int a){
        score += a;
    }

    public void land(){
        jumpLeft = 2;
        isJumping = false;}

    @Override
    public void kill() {
        if(!isDying) {
            speed.x = 0;
            speed.y = 0;
            explosion = new Explosion(hitbox.x + hitbox.width / 2, hitbox.y + hitbox.height / 2);
            isDying = true;
            jumpLeft = 1;
            this.jump();
        }
    }

    @Override
    public void revive() {

    }
}
