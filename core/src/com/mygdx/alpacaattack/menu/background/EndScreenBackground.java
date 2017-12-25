package com.mygdx.alpacaattack.menu.background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.alpacaattack.assets.MenuAssets;
import com.mygdx.alpacaattack.menu.enums.ShopItem;

/**
 * Created by Adrien on 11-09-17.
 */

public class EndScreenBackground implements Background {

    Texture background;
    com.mygdx.alpacaattack.menu.background.JumpingBackgroundAlpaca leftAlpace;
    com.mygdx.alpacaattack.menu.background.JumpingBackgroundAlpaca rightAlpaca;

    private final static float ALPACAS_HEIGHT = 10;

    public EndScreenBackground(MenuAssets mAssets){
        String equippedColor = getEquippedColor();

        TextureAtlas lookRightAtlas = mAssets.manager.get(MenuAssets.spriteAtlasPath, TextureAtlas.class);
        TextureRegion landRegion = lookRightAtlas.findRegion("run2_"+equippedColor);
        TextureRegion airRegion = lookRightAtlas.findRegion("run1_"+equippedColor);
        Sound jumpingSound = mAssets.manager.get(MenuAssets.jumpSoundPath, Sound.class);

        leftAlpace = new com.mygdx.alpacaattack.menu.background.JumpingBackgroundAlpaca(Gdx.graphics.getWidth() / 4 - com.mygdx.alpacaattack.menu.background.JumpingBackgroundAlpaca.getWidth(), ALPACAS_HEIGHT
                , landRegion, airRegion, false, jumpingSound, mAssets.getSoundVolume());

        rightAlpaca = new com.mygdx.alpacaattack.menu.background.JumpingBackgroundAlpaca(Gdx.graphics.getWidth() *3/ 4, ALPACAS_HEIGHT
                , landRegion, airRegion, true, jumpingSound, mAssets.getSoundVolume());

        background = mAssets.manager.get(MenuAssets.menuBackgroundPath, Texture.class);
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

    @Override
    public void render(SpriteBatch batch){
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        leftAlpace.draw(batch);
        rightAlpaca.draw(batch);
    }

    @Override
    public void dispose(){}
}
