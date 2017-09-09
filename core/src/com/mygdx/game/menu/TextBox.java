package com.mygdx.game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.AlpacaAttack;
import com.mygdx.game.screen.MenuScreen;


/**
 * Created by Adrien on 09-09-17.
 */

public class TextBox extends Actor {
    private final static int FONT_SIZE = 80;

    private static final TextureRegion box = MenuScreen.atlas.findRegion("textBox");
    private static final SpriteBatch b = new SpriteBatch();
    private BitmapFont font = AlpacaAttack.generateFont(Gdx.files.internal("ttf/BeTrueToYourSchool-Regular.ttf"), box.getRegionHeight() - box.getRegionHeight()/10);

    private Label text;



    public TextBox(String t){

        text = new Label(t, new Label.LabelStyle(font, Color.WHITE));
        text.setSize(box.getRegionWidth(), box.getRegionHeight());
        text.setAlignment(Align.center);

        this.setSize(box.getRegionWidth(), box.getRegionHeight());
        this.setPosition(0,0);

    }

    @Override
    public void setPosition(float x, float y){
        super.setPosition(x,y);
        text.setPosition(x,y);
    }

    @Override
    public void setSize(float width, float height){
        super.setSize(width, height);
        text.setSize(width, height);
        font = AlpacaAttack.generateFont(Gdx.files.internal("ttf/BeTrueToYourSchool-Regular.ttf"), (int)(height - height/6));
    }

    public void setText(String s){
        text.setText(s);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.end();
        b.setTransformMatrix(batch.getTransformMatrix());
        b.begin();
        b.draw(box, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        b.end();
        batch.begin();
        text.draw(batch, parentAlpha);
    }

}
