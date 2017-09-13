package com.mygdx.game.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.assets.GameAssets;
import com.mygdx.game.game.util.Explosion;
import com.mygdx.game.menu.shop.ShopItem;
import com.mygdx.game.screen.GameScreen;

/**
 * Created by Adrien on 31-08-17.
 */

public class Player extends AnimatedMovableEntity {
    private final static float PLAYER_SPEED = 7f *GameScreen.PIXEL_TO_METER;
    private final static float PLAYER_SPEED_INCREMENT = PLAYER_SPEED / 50f;
    private final static float PLAYER_DASH_SPEED_MULTIPLICATOR = 3f;
    private final static float JUMP_SPEED = 9f*GameScreen.PIXEL_TO_METER;

    private final static float DASH_DURATION = 0.2f;
    private final static  float DEAD_ANIMATION_DURATION = 2f;

    private Explosion explosion;
    private Sound jumpSound;
    private Sound dashSound;

    public boolean isDashing = false;
    public boolean isJumping = false;
    public boolean isDying =  false;

    private float dashTimer = 0;
    private float deadTimer = 0;
    private int jumpLeft = 2;
    private int score = 0;

    private float initialX;
    private float initialY;

    private float currentXSpeed = PLAYER_SPEED;

    private String color = "white";

    private GameAssets assets;
    private ParticleEffect boostEffect;
    private ParticleEffect dragEffect;


    private final static float SIZE_MULTIPLIER = 0.5f;

    public Player(float x, float y, GameAssets a){
        super(a);
        assets = a;

        //Load sounds
        jumpSound = assets.manager.get(GameAssets.jumpPath, Sound.class);
        dashSound = assets.manager.get(GameAssets.dashPath, Sound.class);
        boostEffect = assets.getPlayerDash();
        dragEffect = assets.getPlayerDrag();


        color = getEquippedColor();

        weight = 1;
        initialX = x;
        initialY = y;

        animation = new Animation(1/7f, textureAtlas.findRegion("run1"+"_"+color), textureAtlas.findRegion("run2"+"_"+color),textureAtlas.findRegion("run3"+"_"+color),textureAtlas.findRegion("run2"+"_"+color));

        hitbox = new Rectangle(x,y,textureAtlas.findRegion("run1"+"_"+color).getRegionWidth()* GameScreen.PIXEL_TO_METER* SIZE_MULTIPLIER,textureAtlas.findRegion("run1"+"_"+color).getRegionHeight()* GameScreen.PIXEL_TO_METER* SIZE_MULTIPLIER);
        speed = new Vector2(currentXSpeed,0);

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
        currentXSpeed = PLAYER_SPEED;

        hitbox.setPosition(initialX, initialY);
        sprite.setPosition(hitbox.x, hitbox.y);
        animation.setFrameDuration(1/7f);
    }

    public void incrementSpeed(){
        currentXSpeed += PLAYER_SPEED_INCREMENT;
    }

    @Override
    public void update(){
        //Update the current speed
        if(!isDashing && !isDying)
            this.setXSpeed(currentXSpeed);

        if(isDying){
            deadTimer += Gdx.graphics.getDeltaTime();
            if(deadTimer > DEAD_ANIMATION_DURATION)
                isDead = true;
        }
        else if(isDashing){
            dashTimer += Gdx.graphics.getDeltaTime();
            if(dashTimer >= DASH_DURATION){
                speed.x = currentXSpeed;
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


        if(dragEffect != null) {
            dragEffect.update(Gdx.graphics.getDeltaTime());
            for (ParticleEmitter e : dragEffect.getEmitters()) {
                if(isJumping)
                     e.setPosition(hitbox.getX(), hitbox.getY());
                else
                    e.setPosition(0,0);
            }
            dragEffect.draw(batch);
        }

        if(boostEffect != null) {
            boostEffect.update(Gdx.graphics.getDeltaTime());
            for (ParticleEmitter e : boostEffect.getEmitters())
                e.setPosition(hitbox.getX() + hitbox.getWidth(), hitbox.getY());
            boostEffect.draw(batch);
        }

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

            if(boostEffect != null)
                boostEffect.start();

            dashSound.play(0.2f * assets.getSoundVolume());
            isDashing = true;
            speed.x = currentXSpeed*PLAYER_DASH_SPEED_MULTIPLICATOR;
            speed.y = 0;
            animation.setFrameDuration(1 / 12f);
        }
    }

    public void jump(){
        //gallop.pause(gallopID);
        if(!isDying)
            jumpSound.play(0.2f * assets.getSoundVolume());
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
            explosion = new Explosion(hitbox.x + hitbox.width / 2, hitbox.y + hitbox.height / 2, assets);
            isDying = true;
            jumpLeft = 1;
            this.jump();
        }
    }

    @Override
    public void revive() {

    }

    private String getEquippedColor(){
        String equipped = Gdx.app.getPreferences("prefs").getString("equippedSkin", ShopItem.ALPACA_WHITE.getName());
        if(equipped.equals(ShopItem.ALPACA_WHITE.getName()))
            return "white";
        else if(equipped.equals(ShopItem.ALPACA_BLACK.getName()))
            return "black";
        else if(equipped.equals(ShopItem.ALPACA_BLUE.getName()))
            return "blue";
        else if(equipped.equals(ShopItem.ALPACA_RED.getName()))
            return "red";
        else if(equipped.equals(ShopItem.ALPACA_PINK.getName()))
            return "pink";
        else if(equipped.equals(ShopItem.ALPACA_YELLOW.getName()))
            return "yellow";
        else if(equipped.equals(ShopItem.ALPACA_BROWN.getName()))
            return "brown";

        return "white";
    }
}
