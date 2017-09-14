package com.mygdx.game.menu.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.assets.MenuAssets;
import com.mygdx.game.screen.MenuScreen;

/**
 * Created by Adrien on 14-09-17.
 */

public class BackToMainMenuButton {
    private MenuScreen menuScreen;

    public static final float BUTTON_WIDTH = Gdx.graphics.getWidth()/4;
    public static final float BUTTON_HEIGHT = Gdx.graphics.getHeight()/10;
    private static final float BACK_BUTTON_PADDING = Gdx.graphics.getHeight()/36f;


    public BackToMainMenuButton(MenuScreen mScreen){
        menuScreen = mScreen;

        BitmapFont font = menuScreen.assets.manager.get(MenuAssets.mainMenuButtonFont, BitmapFont.class);
        Skin skin = menuScreen.assets.manager.get(MenuAssets.menuSkinPath, Skin.class);

        TextButton back = new TextButton(menuScreen.bundle.get("back"), skin);
        back.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        back.getLabel().setStyle(new Label.LabelStyle(font, Color.WHITE));
        back.setPosition(BACK_BUTTON_PADDING
                , Gdx.graphics.getHeight() - BUTTON_HEIGHT - BACK_BUTTON_PADDING);
        back.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                menuScreen.setScene(MenuScreen.MAIN_MENU);
                super.touchUp(event, x, y, pointer, button);
            }
        });
    menuScreen.stage.addActor(back);

    }

}
