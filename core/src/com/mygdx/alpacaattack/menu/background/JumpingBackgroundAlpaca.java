package com.mygdx.alpacaattack.menu.background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

/**
 * Created by Adrien on 11-09-17.
 */

public class JumpingBackgroundAlpaca {
    private Sprite onLand;
    private Sprite inAir;
    private Sound sound;

    private float initialY;

    private float x;
    private float y;
    private float width;
    private float height;
    private float speedY = 0;

    boolean jumping = false;

    private float elapsedTime = 0;
    private Random random = new Random();
    private float waitTime = 0;//Time to wait for the next jump
    private float volume;

    private static final float ALPACA_WIDTH = Gdx.graphics.getWidth() / 8f;
    private static final float MAXIMAL_WAIT_TIME = 1f;//Maximal time to wait between 2 jumps
    private static final float JUMP_SPEED = Gdx.graphics.getHeight()/54f;
    private static final float GRAVITY = Gdx.graphics.getHeight()/1080f;


    JumpingBackgroundAlpaca(float argX, float argY, TextureRegion land, TextureRegion air, boolean flip, Sound jump, float soundVolume){
        inAir = new Sprite(air);
        onLand = new Sprite(land);
        sound  = jump;
        volume = soundVolume;

        this.x = argX;
        this.y = initialY = argY;
        this.width = ALPACA_WIDTH;
        this.height = ALPACA_WIDTH *((float)land.getRegionHeight()/(float)land.getRegionWidth());

        inAir.setSize(width, height);
        onLand.setSize(width, height);

        if(flip){
            onLand.flip(true,false);
            inAir.flip(true, false);
        }
    }

    public void draw(SpriteBatch batch){
        this.update();

        Sprite toDraw = onLand;
        if(jumping)
            toDraw = inAir;

        toDraw.setPosition(x,y);
        toDraw.draw(batch);
    }

    public void update(){
        elapsedTime+=Gdx.graphics.getDeltaTime();
        y += speedY;
        if(elapsedTime > waitTime){
            //Jump if he's not already
            if(!jumping){
                sound.play(0.2f * volume);
                speedY = JUMP_SPEED;
                jumping = true;
            }
            else{
                speedY-= GRAVITY;
                if(y < initialY){
                    jumping = false;
                    elapsedTime = 0;
                    waitTime = random.nextFloat() * MAXIMAL_WAIT_TIME;
                    y = initialY;
                    speedY = 0;
                }
            }
        }
    }

    public static float getWidth(){ return ALPACA_WIDTH;}
}
