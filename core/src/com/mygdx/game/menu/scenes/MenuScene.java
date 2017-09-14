package com.mygdx.game.menu.scenes;

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
import com.mygdx.game.assets.MenuAssets;
import com.mygdx.game.screen.MenuScreen;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.LoadingScreen;

/**
 * Created by Adrien on 06-09-17.
 */

public abstract class MenuScene{
    public final Skin skin;
    public MenuScene scene;

    protected Stage stage;

    protected MenuScreen menuScreen;

    public MenuScene(MenuScreen menuScreen){
        skin = menuScreen.assets.manager.get("menu/flat-earth-ui.json", Skin.class);
        stage = menuScreen.stage;
    }

    public void dispose(){

    }

    public abstract void render();
}
