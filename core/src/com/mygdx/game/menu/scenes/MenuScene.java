package com.mygdx.game.menu.scenes;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.screen.MenuScreen;

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
