package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.menu.MainMenuScene;
import com.mygdx.game.menu.MenuBackground;
import com.mygdx.game.menu.MenuScene;


/**
 * Created by Adrien on 02-09-17.
 */

public class MenuScreen implements Screen {
    private Game game;
    private GameScreen gameScreen;
    private SpriteBatch batch = new SpriteBatch();


    private MenuScene scene;
    private MainMenuScene mainMenu;
    private MenuBackground background;

    public  MenuScreen(Game g){
        game = g;
        gameScreen = new GameScreen(game, this);

        mainMenu = new MainMenuScene(game, gameScreen);
        scene = mainMenu;
        background = new MenuBackground();

    }
    @Override
    public void render(float delta) {

        background.render(batch);
        scene.render();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void show() {

    }

}
