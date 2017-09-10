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

public class MoneyTextBox extends Actor {
    private final static int FONT_SIZE = 80;

    private static final TextureRegion box = MenuScreen.atlas.findRegion("textBox");
    private static final TextureRegion coin = MenuScreen.atlas.findRegion("coin");

    private static final SpriteBatch b = new SpriteBatch();
    private BitmapFont font = AlpacaAttack.generateFont(Gdx.files.internal("ttf/BeTrueToYourSchool-Regular.ttf"), box.getRegionHeight()*8/10);

    private Label text;
    private int amount;


    public MoneyTextBox(int money){
        amount = money;
        text = new Label(Integer.toString(money)+"G", new Label.LabelStyle(font, Color.WHITE));
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

    //Return Button + Coins widht
    public float getTotalWidth(){
        return super.getWidth()*8/9 +  this.getHeight()*10/8;
    }

    @Override
    public void setSize(float width, float height){
        super.setSize(width, height);
        text.setSize(width, height);
        font = AlpacaAttack.generateFont(Gdx.files.internal("ttf/BeTrueToYourSchool-Regular.ttf"), (int)(height*8/10));
        text.setStyle(new Label.LabelStyle(font, Color.WHITE));
    }

    public void scale(float scale){
        this.setSize(this.getWidth()*scale, this.getHeight()*scale);
    }

    public void setAmount(int s){
        text.setText(Integer.toString(s)+"G");
        amount = s;
    }

    public int getAmount(){return amount;}

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.end();
        b.setTransformMatrix(batch.getTransformMatrix());
        b.begin();
        b.draw(box, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        b.draw(coin, this.getX() + this.getWidth()*8/9, this.getY() - this.getHeight()/8, this.getHeight()*10/8, this.getHeight()*10/8);
        b.end();
        batch.begin();
        text.draw(batch, parentAlpha);
    }

}
