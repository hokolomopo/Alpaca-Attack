package com.mygdx.game.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.LoadingScreen;

/**
 * Created by Adrien on 06-09-17.
 */

public abstract class MenuScene{
    protected static final float BUTTON_WIDTH = Gdx.graphics.getWidth()/3;
    protected static final float BUTTON_HEIGHT = Gdx.graphics.getHeight()/8;
    protected static final float TEXT_SCALE = 3.5f;

    protected Table table;
    protected Stage stage = new Stage(new ScreenViewport());
    protected Skin skin = new Skin(Gdx.files.internal("menu/flat-earth-ui.json"));

    protected Game game;
    protected GameScreen gameScreen;


    public void render(){
        //Make sure the inputProcessor is the stage
        if(Gdx.input.getInputProcessor() != stage)
            Gdx.input.setInputProcessor(stage);
        stage.draw();
    }
}
