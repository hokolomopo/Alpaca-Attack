package com.mygdx.game.menu.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.AlpacaAttack;
import com.mygdx.game.assets.Assets;
import com.mygdx.game.assets.MenuAssets;
import com.mygdx.game.screen.MenuScreen;


/**
 * Created by Adrien on 09-09-17.
 */

public class MoneyTextBox extends Actor {
    private final static int FONT_SIZE = 80;


    private BitmapFont font;// = AlpacaAttack.generateFont(Gdx.files.internal("ttf/BeTrueToYourSchool-Regular.ttf"), box.getRegionHeight()*8/10);

    private Label text;
    private Image coin;
    private Image box;
    private int amount;

    private MenuAssets assets;

    public MoneyTextBox(int money, MenuAssets a){
        assets = a;
        amount = money;
        font = assets.manager.get(MenuAssets.shopCellFont, BitmapFont.class);
        TextureAtlas atlas = assets.manager.get(MenuAssets.menuAtlasPath, TextureAtlas.class);
        TextureRegion boxTexture = atlas.findRegion("textBox");
        TextureRegion coinTexture = atlas.findRegion("coin");

        coin = new Image(new TextureRegionDrawable(coinTexture));
        box = new Image(new TextureRegionDrawable(boxTexture));

        text = new Label(Integer.toString(money)+" G", new Label.LabelStyle(font, Color.WHITE));
        text.setSize(boxTexture.getRegionWidth(), boxTexture.getRegionHeight());
        text.setAlignment(Align.center);

        this.setSize(boxTexture.getRegionWidth(), boxTexture.getRegionHeight());
        this.setPosition(0,0);

    }

    @Override
    public void setPosition(float x, float y){
        super.setPosition(x,y);
        text.setPosition(x,y);
        box.setPosition(x,y);
        coin.setPosition(x + this.getWidth()*8/9, y - this.getHeight()/8);
    }

    //Return Button + Coins widht
    public float getTotalWidth(){
        return super.getWidth()*8/9 +  this.getHeight()*10/8;
    }

    @Override
    public void setSize(float width, float height){
        super.setSize(width, height);
        coin.setSize(height*10/8f, height*10/8f);
        box.setSize(width, height);
        text.setSize(width, height);
        font = AlpacaAttack.generateFont(Gdx.files.internal("ttf/BeTrueToYourSchool-Regular.ttf"), (int)(height*8/10));
        text.setStyle(new Label.LabelStyle(font, Color.WHITE));
    }

    public void setAmount(int s){
        text.setText(Integer.toString(s)+" G");
        amount = s;
    }

    public int getAmount(){return amount;}

    @Override
    public void draw(Batch batch, float parentAlpha){

        box.draw(batch, parentAlpha);
        coin.draw(batch, parentAlpha);
        text.draw(batch, parentAlpha);
    }

    public void dispose(){
        font.dispose();
    }
}
