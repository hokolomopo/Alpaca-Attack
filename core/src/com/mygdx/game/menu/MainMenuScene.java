package com.mygdx.game.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.screen.GameScreen;

/**
 * Created by Adrien on 08-09-17.
 */

public class MainMenuScene extends MenuScene {

    public MainMenuScene(Game g, GameScreen gScreen){
        Gdx.input.setInputProcessor(stage);

        game = g;
        gameScreen = gScreen;

        TextButton play = new TextButton("Play", skin);
        play.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        play.setPosition(Gdx.graphics.getWidth()/2 - play.getWidth()/2, Gdx.graphics.getHeight()/2);
        play.getLabel().setFontScale(TEXT_SCALE, TEXT_SCALE);
        play.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.setInputProcessor(gameScreen);
                game.setScreen(gameScreen);
            }

        });
        stage.addActor(play);


        TextButton close = new TextButton("Exit", skin);
        close.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        close.setPosition(Gdx.graphics.getWidth()/2 - play.getWidth()/2, play.getY() - close.getHeight() - 40);
        close.getLabel().setFontScale(TEXT_SCALE, TEXT_SCALE);
        close.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }

        });
        stage.addActor(close);

    }

}
