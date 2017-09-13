package com.mygdx.game.menu.background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.assets.MenuAssets;

import java.sql.Time;
import java.util.Random;

/**
 * Created by Adrien on 06-09-17.
 */

public class MainMenuBackground {
    private Texture background;
    private Animation animation;
    private TextureAtlas textureAtlas;
    private float alpacaX = 0;
    private float alpacaWidth;
    private float alpacaHeight;
    private static final float ALPACA_SPEED = Gdx.graphics.getWidth() / 300f;
    private static final float ALPACA_WIDHT = Gdx.graphics.getWidth() / 10f;
    private float elapsedTime = 0;

    private String color = "red";

    public MainMenuBackground(MenuAssets assets) {
        textureAtlas = assets.manager.get(MenuAssets.spriteAtlasPath, TextureAtlas.class);
        background = assets.manager.get(MenuAssets.menuBackgroundPath, Texture.class);

        animation = new Animation(1 / 7f, textureAtlas.findRegion("run1" + "_" + color), textureAtlas.findRegion("run2" + "_" + color), textureAtlas.findRegion("run3" + "_" + color), textureAtlas.findRegion("run2" + "_" + color));
        TextureRegion region = textureAtlas.findRegion("run1" + "_" + color);
        alpacaWidth = ALPACA_WIDHT;
        alpacaHeight = alpacaWidth * ((float) region.getRegionHeight() / (float) region.getRegionWidth());

        this.changeColor();
    }

    public void render(SpriteBatch batch) {
        alpacaX += ALPACA_SPEED;
        if (alpacaX >= Gdx.graphics.getWidth()) {
            alpacaX = -alpacaWidth;
            changeColor();
        }

        elapsedTime += Gdx.graphics.getDeltaTime();

        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(animation.getKeyFrame(elapsedTime, true), alpacaX, 0, alpacaWidth, alpacaHeight);
    }

    public void changeColor() {
        Random r = new Random();
        switch (r.nextInt(7)) {
            case 0:
                color = "black";
                break;
            case 1:
                color = "blue";
                break;
            case 2:
                color = "brown";
                break;
            case 3:
                color = "pink";
                break;
            case 4:
                color = "red";
                break;
            case 5:
                color = "white";
                break;
            case 6:
                color = "yellow";
                break;
        }
        animation = new Animation(1 / 7f, textureAtlas.findRegion("run1" + "_" + color), textureAtlas.findRegion("run2" + "_" + color), textureAtlas.findRegion("run3" + "_" + color), textureAtlas.findRegion("run2" + "_" + color));

    }

    public void dispose() {
    }
}
