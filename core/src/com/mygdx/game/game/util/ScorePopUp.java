package com.mygdx.game.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.assets.GameAssets;
import com.mygdx.game.screen.GameScreen;

/**
 * Created by Adrien on 04-09-17.
 */

public class ScorePopUp {
    //private static Skin skin = new Skin(Gdx.files.internal("UI/arcade-ui.json"));

    private  BitmapFont text;// = new BitmapFont(Gdx.files.internal("UI/rdmFont.fnt"));
    private static final float MOVE_UP_SPEED = 2 * GameScreen.PIXEL_TO_METER;
    private static final float DURATION = 1;
    private float x;
    private float y;
    private int value;
    float timer = 0;
    public boolean isDead;

    private static final float FONT_SCALE = 0.5f * GameScreen.PIXEL_TO_METER;

    public ScorePopUp(int score, float argX, float argY, GameAssets assets){
        text = assets.manager.get("floatingTextFont.ttf", BitmapFont.class);
        text.getData().setScale(FONT_SCALE);
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
