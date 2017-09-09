package com.mygdx.game.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.AlpacaAttack;
import com.mygdx.game.screen.MenuScreen;
import com.mygdx.game.screen.GameScreen;

/**
 * Created by Adrien on 08-09-17.
 */

public class MainMenuScene extends MenuScene {
    public static final float BUTTON_WIDTH = Gdx.graphics.getWidth()/3;
    public static final float BUTTON_HEIGHT = Gdx.graphics.getHeight()/8;
    private static final float BUTTON_PADDING = 30;

    private BitmapFont font = AlpacaAttack.generateFont(Gdx.files.internal("ttf/BeTrueToYourSchool-Regular.ttf"), (int)(BUTTON_HEIGHT - BUTTON_HEIGHT/8));


    private Game game;
    private GameScreen gameScreen;

    private MainMenuBackground background;

    public MainMenuScene(Game g, GameScreen gmScreen, MenuScreen mScreen){
        Gdx.input.setInputProcessor(stage);

        game = g;
        menuScreen = mScreen;
        gameScreen = gmScreen;

        background = new MainMenuBackground();

        TextButton play = new TextButton("Play", skin);
        play.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        play.setPosition(Gdx.graphics.getWidth()/2 - BUTTON_WIDTH/2, Gdx.graphics.getHeight()/2);
        play.getLabel().setStyle(new Label.LabelStyle(font, Color.WHITE));
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

        TextButton shop = new TextButton("Shop", skin);
        shop.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        shop.setPosition(Gdx.graphics.getWidth()/2 - play.getWidth()/2, play.getY() - BUTTON_HEIGHT - BUTTON_PADDING);
        shop.getLabel().setStyle(new Label.LabelStyle(font, Color.WHITE));
        shop.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                menuScreen.setScene(MenuScreen.SHOP);
            }

        });
        stage.addActor(shop);


        TextButton close = new TextButton("Exit", skin);
        close.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        close.setPosition(Gdx.graphics.getWidth()/2 - BUTTON_WIDTH/2, shop.getY() - BUTTON_HEIGHT - BUTTON_PADDING);
        close.getLabel().setStyle(new Label.LabelStyle(font, Color.WHITE));
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

    @Override
    public void render(SpriteBatch batch){
        //Make sure the inputProcessor is the stage
        if(Gdx.input.getInputProcessor() != stage)
            Gdx.input.setInputProcessor(stage);
        stage.act(Gdx.graphics.getDeltaTime());

        background.render(batch);
        stage.draw();

    }

}
