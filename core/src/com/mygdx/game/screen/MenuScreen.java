package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.menu.MainMenuScene;
import com.mygdx.game.menu.MenuScene;
import com.mygdx.game.menu.shop.ShopScene;


/**
 * Created by Adrien on 02-09-17.
 */

public class MenuScreen implements Screen {

    public static final TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("menu/menu.txt"));
    public static final BitmapFont font = new BitmapFont(Gdx.files.internal("UI/rdmFont.fnt"));
    public static final Skin ARCADE_SKIN = new Skin(Gdx.files.internal("UI/arcade-ui.json"));
    private Game game;
    private GameScreen gameScreen;
    private SpriteBatch batch = new SpriteBatch();

    public static final int MAIN_MENU = 0;
    public static final int SHOP = 1;

    private MenuScene scene;
    private MenuScene shop;
    private MainMenuScene mainMenu;

    public  MenuScreen(Game g, GameScreen gmScreen){
        game = g;
        gameScreen = gmScreen;

        mainMenu = new MainMenuScene(game, gameScreen, this);

        shop = new ShopScene(this);
        scene = shop;

    }

    public void setScene(int a){
        switch(a){
            case MAIN_MENU :
                scene = mainMenu;
                break;
            case SHOP :
                scene = shop;
                break;
        }
    }
    @Override
    public void render(float delta) {

        //background.render(batch);
        scene.render(batch);

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
