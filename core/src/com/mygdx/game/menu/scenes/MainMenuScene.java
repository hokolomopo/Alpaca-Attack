package com.mygdx.game.menu.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.assets.MenuAssets;
import com.mygdx.game.screen.MenuScreen;

/**
 * Created by Adrien on 08-09-17.
 */

public class MainMenuScene extends com.mygdx.game.menu.scenes.MenuScene {
    public static final float BUTTON_WIDTH = Gdx.graphics.getWidth()/3;
    public static final float BUTTON_HEIGHT = Gdx.graphics.getHeight()/8;
    private static final float BUTTON_PADDING = BUTTON_HEIGHT/7;

    private BitmapFont font;


    public MainMenuScene(MenuScreen mScreen){
        super(mScreen);
        scene = this;
        menuScreen = mScreen;

        Gdx.input.setInputProcessor(stage);

        font = menuScreen.assets.manager.get(MenuAssets.mainMenuButtonFont, BitmapFont.class);

        new SoundButtonsScene(menuScreen);
        this.createButtons();

    }

    private void createButtons(){
        TextButton play = new TextButton(menuScreen.bundle.get("play"), skin);
        play.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        play.setPosition(Gdx.graphics.getWidth()/2 - BUTTON_WIDTH/2, Gdx.graphics.getHeight()/2);
        play.getLabel().setStyle(new Label.LabelStyle(font, Color.WHITE));
        play.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                menuScreen.getValidateSound().play(1f * menuScreen.assets.getSoundVolume());
                menuScreen.setScene(MenuScreen.LEVEL_SELECT);
            }

        });
        stage.addActor(play);

        TextButton shop = new TextButton(menuScreen.bundle.get("shop"), skin);
        shop.setSize(BUTTON_WIDTH,BUTTON_HEIGHT);
        shop.setPosition(Gdx.graphics.getWidth()/2 - play.getWidth()/2
                , play.getY() - BUTTON_HEIGHT - BUTTON_PADDING);
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


        TextButton close = new TextButton(menuScreen.bundle.get("exit"), skin);
        close.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        close.setPosition(Gdx.graphics.getWidth()/2 - BUTTON_WIDTH/2
                , shop.getY() - BUTTON_HEIGHT - BUTTON_PADDING);
        close.getLabel().setStyle(new Label.LabelStyle(font, Color.WHITE));
        close.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                menuScreen.getValidateSound().play(1f* menuScreen.assets.getSoundVolume());
                Gdx.app.exit();
            }

        });
        stage.addActor(close);
    }

    @Override
    public void render(){
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    @Override
    public void dispose(){
        super.dispose();
    }

}
