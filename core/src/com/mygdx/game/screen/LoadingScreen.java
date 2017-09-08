package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/**
 * Created by Adrien on 04-09-17.
 */

public class LoadingScreen implements Screen {
    private Game game;

    public LoadingScreen(Game g){
        game = g;
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //game.setScreen(new GameScreen(game));
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
}
