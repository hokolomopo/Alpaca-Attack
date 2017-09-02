package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Adrien on 01-09-17.
 */

public class UserInterface {
    private Button jumpButton;
    private Button dashButton;

    private static final float BUTTON_HEIGHT = 7;
    private static final float BUTTON_WIDHT = 9.33f;

    public UserInterface(OrthographicCamera camera){

        jumpButton = new Button(new Rectangle(camera.position.x, camera.position.y,BUTTON_WIDHT, BUTTON_HEIGHT), "jumpButton");
        dashButton = new Button(new Rectangle(Gdx.graphics.getWidth()*GameScreen.PIXEL_TO_METER - BUTTON_WIDHT,0,BUTTON_WIDHT, BUTTON_HEIGHT), "dashButton");
    }

    public void update(OrthographicCamera camera){
        jumpButton.setPosition(camera.position.x - camera.viewportWidth/2, camera.position.y - camera.viewportHeight/2);
        dashButton.setPosition(camera.position.x + camera.viewportWidth/2 - jumpButton.getHitbox().width, camera.position.y - camera.viewportHeight/2);
    }

    public void render(SpriteBatch batch){
        dashButton.draw(batch);
        jumpButton.draw(batch);
    }

    public Rectangle getJumpHitbox(){
        return jumpButton.getHitbox();
    }

    public Rectangle getDashHitbox(){
        return dashButton.getHitbox();
    }

}
