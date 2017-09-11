package com.mygdx.game.menu.background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.assets.MenuAssets;
import com.mygdx.game.menu.shop.ShopItem;

/**
 * Created by Adrien on 11-09-17.
 */

public class EndScreenBackground {

    Texture background;
    JumpingBackgroundAlpaca leftAlpace;
    JumpingBackgroundAlpaca rightAlpaca;

    private final static float ALPACAS_HEIGHT = 10;

    public EndScreenBackground(MenuAssets mAssets){
        String equippedColor = getEquippedColor();

        TextureAtlas lookRightAtlas = mAssets.manager.get("sprites.txt", TextureAtlas.class);
        TextureRegion landRegion = lookRightAtlas.findRegion("run2_"+equippedColor);
        TextureRegion airRegion = lookRightAtlas.findRegion("run1_"+equippedColor);

        leftAlpace = new JumpingBackgroundAlpaca(Gdx.graphics.getWidth() / 4 - JumpingBackgroundAlpaca.getWidth(), ALPACAS_HEIGHT
                , landRegion, airRegion, false);

        rightAlpaca = new JumpingBackgroundAlpaca(Gdx.graphics.getWidth() *3/ 4, ALPACAS_HEIGHT
                , landRegion, airRegion, true);

        background = mAssets.manager.get("background.png", Texture.class);
    }

    public String getEquippedColor(){
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

    public void draw(SpriteBatch batch){
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        leftAlpace.draw(batch);
        rightAlpaca.draw(batch);
    }
}
