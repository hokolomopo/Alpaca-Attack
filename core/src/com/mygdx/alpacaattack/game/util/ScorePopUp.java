package com.mygdx.alpacaattack.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.alpacaattack.assets.MenuAssets;

/**
 * Created by Adrien on 04-09-17.
 */

public class ScorePopUp {
    //private static Skin skin = new Skin(Gdx.files.internal("UI/arcade-ui.json"));

    private  BitmapFont text;// = new BitmapFont(Gdx.files.internal("UI/rdmFont.fnt"));
    private static final float MOVE_UP_SPEED = 2 * com.mygdx.alpacaattack.screen.GameScreen.PIXEL_TO_METER;
    private static final float DURATION = 1;
    private float x;
    private float y;
    private int value;
    float timer = 0;
    public boolean isDead;


    public ScorePopUp(int score, float argX, float argY, MenuAssets assets){
        text = assets.manager.get(MenuAssets.floatingTextFont, BitmapFont.class);
        text.setColor(Color.WHITE);
        x = argX;
        y = argY;
        value = score;
    }

    public void update(){
        y+= MOVE_UP_SPEED;
        timer += Gdx.graphics.getDeltaTime();
        if(timer > DURATION){
            isDead = true;
        }
    }

    public void draw(SpriteBatch batch){
        text.draw(batch,"+"+Integer.toString(value),x,y);
    }
}
