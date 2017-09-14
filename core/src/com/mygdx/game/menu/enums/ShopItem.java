package com.mygdx.game.menu.enums;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.game.assets.MenuAssets;

/**
 * Created by Adrien on 10-09-17.
 */

public enum ShopItem {
    ALPACA_WHITE, ALPACA_RED, ALPACA_BLUE, ALPACA_YELLOW, ALPACA_PINK, ALPACA_BROWN, ALPACA_BLACK,
    DRAG_WHITE, DRAG_PINK, DRAG_YELLOW, DRAG_RED, DRAG_BLUE,
    DASH_WHITE, DASH_RED, DASH_YELLOW, DASH_BLUE, DASH_PINK, DASH_RAINBOW;

    public static final int SKIN = 1, DRAG = 2, DASH = 3;

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
            case DRAG_BLUE:
                return 1000;
            case DRAG_RED:
                return 1000;
            case DRAG_PINK:
                return 1000;
            case DRAG_WHITE:
                return 500;
            case DRAG_YELLOW:
                return 1000;
            case DASH_RAINBOW:
                return 3000;
            case DASH_BLUE:
                return 1500;
            case DASH_RED:
                return 1500;
            case DASH_PINK:
                return 1500;
            case DASH_WHITE:
                return 1000;
            case DASH_YELLOW:
                return 1500;

        }
        return 0;
    }

    public String getName(){
        switch(this){
            case ALPACA_RED :
                return "Alpaca\nRed";
            case ALPACA_BLUE :
                return "Alpaca\nBlue";
            case ALPACA_WHITE :
                return "Alpaca\nWhite";
            case ALPACA_YELLOW :
                return "Alpaca\nYellow";
            case ALPACA_PINK :
                return "Alpaca\nPink";
            case ALPACA_BROWN :
                return "Alpaca\nBrown";
            case ALPACA_BLACK :
                return "Alpaca\nBlack";
            case DRAG_BLUE:
                return "dust\nBlue";
            case DRAG_RED:
                return "dust\nRed";
            case DRAG_PINK:
                return "dust\nPink";
            case DRAG_WHITE:
                return "dust\nWhite";
            case DRAG_YELLOW:
                return "dust\nYellow";
            case DASH_RAINBOW:
                return "Dash\nRainbow";
            case DASH_BLUE:
                return "Dash\nblue";
            case DASH_RED:
                return "dash\nred";
            case DASH_PINK:
                return "dash\npink";
            case DASH_WHITE:
                return "dash\nwhite";
            case DASH_YELLOW:
                return "dash\nyellow";

        }
        return "what";
    }

    public String getShopName(I18NBundle bundle){
        switch(this){
            case ALPACA_RED :
                return bundle.get("alpacaRed");
            case ALPACA_BLUE :
                return bundle.get("alpacaBlue");
            case ALPACA_WHITE :
                return bundle.get("alpacaWhite");
            case ALPACA_YELLOW :
                return bundle.get("alpacaYellow");
            case ALPACA_PINK :
                return bundle.get("alpacaPink");
            case ALPACA_BROWN :
                return bundle.get("alpacaBrown");
            case ALPACA_BLACK :
                return bundle.get("alpacaBlack");
            case DRAG_BLUE:
                return bundle.get("dustBlue");
            case DRAG_RED:
                return bundle.get("dustRed");
            case DRAG_PINK:
                return bundle.get("dustPink");
            case DRAG_WHITE:
                return bundle.get("dustWhite");
            case DRAG_YELLOW:
                return bundle.get("dustYellow");
            case DASH_RAINBOW:
                return bundle.get("dashRainbow");
            case DASH_BLUE:
                return bundle.get("dashBlue");
            case DASH_RED:
                return bundle.get("dashRed");
            case DASH_PINK:
                return bundle.get("dashPink");
            case DASH_WHITE:
                return bundle.get("dashWhite");
            case DASH_YELLOW:
                return bundle.get("dashYellow");

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
            case DRAG_BLUE:
                return Color.BLUE;
            case DRAG_RED:
                return Color.RED;
            case DRAG_PINK:
                return Color.PINK;
            case DRAG_WHITE:
                return Color.WHITE;
            case DRAG_YELLOW:
                return Color.YELLOW;
            case DASH_RAINBOW:
                return Color.GOLD;
            case DASH_BLUE:
                return Color.BLUE;
            case DASH_RED:
                return Color.RED;
            case DASH_PINK:
                return Color.PINK;
            case DASH_WHITE:
                return Color.WHITE;
            case DASH_YELLOW:
                return Color.YELLOW;


        }
        return Color.BLACK;
    }


    public TextureRegion getTextureRegion(MenuAssets assets){
         TextureAtlas atlas = assets.manager.get(MenuAssets.menuAtlasPath, TextureAtlas.class);
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
            case DRAG_BLUE:
                return atlas.findRegion("stars_blue");
            case DRAG_RED:
                return atlas.findRegion("stars_red");
            case DRAG_PINK:
                return atlas.findRegion("stars_pink");
            case DRAG_WHITE:
                return atlas.findRegion("stars_white");
            case DRAG_YELLOW:
                return atlas.findRegion("stars_yellow");
            case DASH_RAINBOW:
                return atlas.findRegion("rainbow");
            case DASH_BLUE:
                return atlas.findRegion("dash_blue");
            case DASH_RED:
                return atlas.findRegion("dash_red");
            case DASH_PINK:
                return atlas.findRegion("dash_pink");
            case DASH_WHITE:
                return atlas.findRegion("dash_white");
            case DASH_YELLOW:
                return atlas.findRegion("dash_yellow");

        }

        return atlas.findRegion("run2_white");
    }

    public int getItemType(){
        switch(this){
            case ALPACA_RED :
                return SKIN;
            case ALPACA_BLUE :
                return SKIN;
            case ALPACA_WHITE :
                return SKIN;
            case ALPACA_YELLOW :
                return SKIN;
            case ALPACA_PINK :
                return SKIN;
            case ALPACA_BROWN :
                return SKIN;
            case ALPACA_BLACK :
                return SKIN;
            case DRAG_BLUE:
                return DRAG;
            case DRAG_RED:
                return DRAG;
            case DRAG_PINK:
                return DRAG;
            case DRAG_WHITE:
                return DRAG;
            case DRAG_YELLOW:
                return DRAG;
            case DASH_RAINBOW:
                return DASH;
            case DASH_RED:
                return DASH;
            case DASH_BLUE:
                return DASH;
            case DASH_WHITE:
                return DASH;
            case DASH_YELLOW:
                return DASH;
            case DASH_PINK:
                return DASH;
        }
        return SKIN;

    }
}
