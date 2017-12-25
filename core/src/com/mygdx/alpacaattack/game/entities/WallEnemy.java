package com.mygdx.alpacaattack.game.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.alpacaattack.assets.GameAssets;
import com.mygdx.alpacaattack.menu.enums.Levels;

/**
 * Created by Adrien on 01-09-17.
 */

public class WallEnemy extends Enemy {


    private Rectangle initialLocation;
    private String aliveRegion;
    private String deadRegion;

    public WallEnemy(Rectangle rect, GameAssets assets, Levels level){
        super(rect, getTextureString(level),assets);

        this.initializeTextures(level);

        initialLocation = new Rectangle(hitbox);
        speed = new Vector2(0,0);
    }

    private static String getTextureString(Levels level){
        switch(level){
            case TUTORIAL :
                return "greenBug";
            case GRASSLAND:
                return "greenBug";
            case CANDYLAND:
                return "pinkStone";
            case FOREST:
                return "skullObstacle";
        }
        return "void";
    }

    private void initializeTextures(Levels level){

        aliveRegion = level.getAliveEnemyTextureString();
        deadRegion = level.getDyingEnemyTextureString();
        sprite.setRegion(textureAtlas.findRegion(aliveRegion));
    }
    @Override
    public void kill() {
        //Set the hitboxSize to 0 to not detect collision while it's dead
        //Set the hitboxY to -10 because libgdx detect collisions even when the size is 0
        //Keep the x to correctly detect the last enemy hit for computing the killScore when reseting the world
        hitbox.set(hitbox.x,-10,0,0);
        sprite.setRegion(textureAtlas.findRegion(deadRegion));
        isDead = true;
    }
    @Override
    public void revive(){
        if(isDead) {
            sprite.setRegion(textureAtlas.findRegion(aliveRegion));
            hitbox.set(initialLocation);
            isDead = false;
        }
    }
    @Override
    public void update(){
    }

    @Override
    public int getKillScore(){return killScore;}

}
