package com.mygdx.game.menu.scenes;

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
import com.mygdx.game.menu.background.MainMenuBackground;
import com.mygdx.game.screen.EndScreen;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.MenuScreen;

/**
 * Created by Adrien on 08-09-17.
 */

public class MainMenuScene extends com.mygdx.game.menu.scenes.MenuScene {
    /*public static final float BUTTON_WIDTH = Gdx.graphics.getWidth()/3;
    public static final float BUTTON_HEIGHT = Gdx.graphics.getHeight()/8;
    private static final float BUTTON_PADDING = 30;*/

    private BitmapFont font;


    private Game game;

    private MainMenuBackground background;

    public MainMenuScene(MenuScreen mScreen){
        super(mScreen.assets);
        scene = this;
        menuScreen = mScreen;

        Gdx.input.setInputProcessor(stage);


        font = menuScreen.assets.manager.get("mainMenuButtonFont.ttf", BitmapFont.class);
        background = new MainMenuBackground(mScreen.assets);

        this.createButtons();

    }

    private void createButtons(){
        TextButton play = new TextButton("Play", skin);
        play.setSize(menuScreen.assets.MAIN_MENU_BUTTON_WIDTH, menuScreen.assets.MAIN_MENU_BUTTON_HEIGHT);
        play.setPosition(Gdx.graphics.getWidth()/2 - menuScreen.assets.MAIN_MENU_BUTTON_WIDTH/2, Gdx.graphics.getHeight()/2);
        play.getLabel().setStyle(new Label.LabelStyle(font, Color.WHITE));
        play.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                menuScreen.setScene(scene, MenuScreen.GAME);
            }

        });
        stage.addActor(play);

        TextButton shop = new TextButton("Shop", skin);
        shop.setSize(menuScreen.assets.MAIN_MENU_BUTTON_WIDTH, menuScreen.assets.MAIN_MENU_BUTTON_HEIGHT);
        shop.setPosition(Gdx.graphics.getWidth()/2 - play.getWidth()/2
                , play.getY() - menuScreen.assets.MAIN_MENU_BUTTON_HEIGHT - menuScreen.assets.MAIN_MENU_BUTTON_PADDING);
        shop.getLabel().setStyle(new Label.LabelStyle(font, Color.WHITE));
        shop.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                menuScreen.setScene(scene, MenuScreen.SHOP);
            }

        });
        stage.addActor(shop);


        TextButton close = new TextButton("Exit", skin);
        close.setSize(menuScreen.assets.MAIN_MENU_BUTTON_WIDTH, menuScreen.assets.MAIN_MENU_BUTTON_HEIGHT);
        close.setPosition(Gdx.graphics.getWidth()/2 - menuScreen.assets.MAIN_MENU_BUTTON_WIDTH/2
                , shop.getY() - menuScreen.assets.MAIN_MENU_BUTTON_HEIGHT - menuScreen.assets.MAIN_MENU_BUTTON_PADDING);
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
        stage.act(Gdx.graphics.getDeltaTime());

        background.render(batch);
        stage.draw();

    }

    @Override
    public void dispose(){
        super.dispose();
        background.dispose();
    }

}
