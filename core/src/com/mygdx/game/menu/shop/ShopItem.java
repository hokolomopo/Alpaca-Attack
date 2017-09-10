package com.mygdx.game.menu.shop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Color;

/**
 * Created by Adrien on 10-09-17.
 */

public enum ShopItem {
    ALPACA_RED, ALPACA_BLUE, ALPACA_WHITE, ALPACA_YELLOW, ALPACA_PINK, ALPACA_BROWN, ALPACA_BLACK;

    private static TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("menu/menu.txt"));

    public int getPrice(){
        switch(this){
            case ALPACA_RED :
                return 2000;
            case ALPACA_BLUE :
                return 1500;
            case ALPACA_WHITE :
                return 0;
            case ALPACA_YELLOW :
                return 2000;
            case ALPACA_PINK :
                return 3000;
            case ALPACA_BROWN :
                return 1500;
            case ALPACA_BLACK :
                return 2500;
        }
        return 0;
    }

    public String getName(){
        switch(this){
            case ALPACA_RED :
                return "Alpaca \nRed";
            case ALPACA_BLUE :
                return "Alpaca \nBlue";
            case ALPACA_WHITE :
                return "Alpaca \nWhite";
            case ALPACA_YELLOW :
                return "Alpaca \nYellow";
            case ALPACA_PINK :
                return "Alpaca \nPink";
            case ALPACA_BROWN :
                return "Alpaca \nBrown";
            case ALPACA_BLACK :
                return "Alpaca \nBlack";
        }
        return "what";
    }

    public Color getTextColor(){
        switch(this){
            case ALPACA_RED :
                return Color.RED;
            case ALPACA_BLUE :
                return Color.BLUE;
            case ALPACA_WHITE :
                return Color.WHITE;
            case ALPACA_YELLOW :
                return Color.YELLOW;
            case ALPACA_PINK :
                return Color.PINK;
            case ALPACA_BROWN :
                return Color.BROWN;
            case ALPACA_BLACK :
                return Color.BLACK;
        }
        return Color.BLACK;
    }


    public TextureRegion getTextureRegion(){
        switch(this){
            case ALPACA_RED :
                return  atlas.findRegion("run2_red");
            case ALPACA_BLUE :
                return  atlas.findRegion("run2_blue");
            case ALPACA_WHITE :
                return  atlas.findRegion("run2_white");
            case ALPACA_YELLOW :
                return atlas.findRegion("run2_yellow");
            case ALPACA_PINK :
                return atlas.findRegion("run2_pink");
            case ALPACA_BROWN :
                return atlas.findRegion("run2_brown");
            case ALPACA_BLACK :
                return atlas.findRegion("run2_black");
        }
        return atlas.findRegion("run2_white");
    }
}
