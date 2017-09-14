package com.mygdx.game.menu.enums;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.game.assets.MenuAssets;

/**
 * Created by Adrien on 14-09-17.
 */

public enum Levels {
    TUTORIAL, GRASSLAND, CANDYLAND, FOREST;

    public int getPrice(){
        switch(this){
            case TUTORIAL :
                return 0;
            case GRASSLAND :
                return 1000;
            case CANDYLAND :
                return 2000;
            case FOREST :
                return 2000;

        }
        return 0;
    }

    public String getName(){
        switch(this){
            case TUTORIAL :
                return "Tutorial";
            case GRASSLAND :
                return "Grassland";
            case CANDYLAND :
                return "Candyland";
            case FOREST:
                return "Forest";
        }
        return "what";
    }


    public TextureRegion getTextureRegion(MenuAssets assets){
        TextureAtlas atlas = assets.manager.get("menu/levels.txt", TextureAtlas.class);
        switch(this){
            case TUTORIAL :
                return atlas.findRegion("level0");
            case GRASSLAND :
                return atlas.findRegion("level1");
            case CANDYLAND :
                return atlas.findRegion("level2");
            case FOREST:
                return atlas.findRegion("level3");
        }

        return atlas.findRegion("level0");

    }

}
