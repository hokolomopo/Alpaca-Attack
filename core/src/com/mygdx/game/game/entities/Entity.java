package com.mygdx.game.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.assets.GameAssets;

/**
 * Created by Adrien on 31-08-17.
 */

public abstract class Entity {

    protected Sprite sprite;
    protected Rectangle hitbox;
    protected TextureAtlas textureAtlas;

    //Only usable when there's no texture needed
    public Entity(){

    }
    public Entity(GameAssets assets){
        textureAtlas = assets.manager.get(GameAssets.spriteAtlasPath, TextureAtlas.class);
    }

    public Entity(Rectangle rect, String atlasRegion, GameAssets assets){
        this(assets);
        Vector2 size = new Vector2(textureAtlas.findRegion(atlasRegion).getRegionWidth(), textureAtlas.findRegion(atlasRegion).getRegionHeight());

        hitbox = new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getWidth() * (size.y/size.x));

        sprite = new Sprite(textureAtlas.findRegion(atlasRegion));
        sprite.setPosition(hitbox.getX(),hitbox.getY());
        sprite.setSize(hitbox.getWidth(),hitbox.getHeight());

    }


    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }

    public Rectangle getHitbox(){
        return  hitbox;
    }

    public void setPosition(float x, float y){
        sprite.setPosition(x,y);
        hitbox.setPosition(sprite.getX(),sprite.getY());

    }


    public float getX(){
        return hitbox.getX();
    }
    public float getY(){
        return hitbox.getY();
    }

    public void drawHitbox(ShapeRenderer r){
        r.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

}
