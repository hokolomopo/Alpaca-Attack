package com.mygdx.game.menu.enums;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.assets.MenuAssets;

/**
 * Created by Adrien on 14-09-17.
 */

public enum Levels {
    TUTORIAL, GRASSLAND, CANDYLAND, FOREST;

    public String getName(){
        switch(this){
            case TUTORIAL :
                return "tutorial";
            case GRASSLAND :
                return "grasslands";
            case CANDYLAND :
                return "candyland";
            case FOREST:
                return "forest";
        }
        return "what";
    }

    public float getGoldToPointRatio(){
        switch(this){
            case TUTORIAL :
                return 500;
            case GRASSLAND :
                return 100;
            case CANDYLAND :
                return 50;
            case FOREST:
                return 30;
        }
        return 0;
    }

    public Levels unlockedBy(){
        switch(this){
            case GRASSLAND :
                return TUTORIAL;
            case CANDYLAND :
                return GRASSLAND;
            case FOREST:
                return CANDYLAND;
        }
        return TUTORIAL;
    }

    public int pointsToUnlock(){
        switch(this){
            case TUTORIAL :
                return 0;
            case GRASSLAND :
                return 10000;
            case CANDYLAND :
                return 40000;
            case FOREST :
                return 40000;
        }
        return 0;
    }

    public String getMusicPath(){
        switch(this){
            case TUTORIAL :
                return "sound/music/grasslands.mp3";
            case GRASSLAND :
                return "sound/music/grasslands.mp3";
            case CANDYLAND :
                return "sound/music/candyland.mp3";
            case FOREST:
                return "sound/music/forest.mp3";
        }
        return "what";
    }

    public String getFilePath(){
        switch(this){
            case TUTORIAL :
                return "levels/tutorial.tmx";
            case GRASSLAND :
                return "levels/grasslands.tmx";
            case CANDYLAND :
                return "levels/candyland.tmx";
            case FOREST:
                return "levels/forest.tmx";
        }
        return "what";
    }



    public TextureRegion getTextureRegion(MenuAssets assets){
        TextureAtlas atlas = assets.manager.get("menu/levels.txt", TextureAtlas.class);
        switch(this){
            case TUTORIAL :
                return atlas.findRegion("tutorial");
            case GRASSLAND :
                return atlas.findRegion("grasslands");
            case CANDYLAND :
                return atlas.findRegion("candyland");
            case FOREST:
                return atlas.findRegion("forest");
        }

        return atlas.findRegion("tutorial");

    }

}
