package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.game.AlpacaAttack;
import com.mygdx.game.assets.MenuAssets;
import com.mygdx.game.menu.scenes.MainMenuScene;
import com.mygdx.game.menu.scenes.MenuScene;
import com.mygdx.game.menu.scenes.ShopScene;


/**
 * Created by Adrien on 02-09-17.
 */

public class MenuScreen implements Screen {
    private Game game;
    public Music music;

    public MenuAssets assets;
    public I18NBundle bundle;

    private Sound validateSound;

    //public final static TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("menu/menu.txt"));
    //public final BitmapFont font = new BitmapFont(Gdx.files.internal("UI/rdmFont.fnt"));
    private SpriteBatch batch = new SpriteBatch();

    public static final int MAIN_MENU = 0;
    public static final int SHOP = 1;
    public static final int GAME = 2;

    private MenuScene scene;

    /*public  MenuScreen(Game g){
        this(g, new MenuAssets());
    }*/

    public MenuScreen(Game g, MenuAssets a){

        game = g;
        assets = a;

        music = assets.manager.get(MenuAssets.menuScreenMusicPath, Music.class);
        music.setLooping(true);
        music.play();
        music.setVolume(assets.getMusicVolume());

        validateSound = assets.manager.get(MenuAssets.validateSoundPath, Sound.class);
        bundle = assets.manager.get(MenuAssets.bundlePath, I18NBundle.class);

        scene = new MainMenuScene(this);
        Gdx.input.setInputProcessor(scene.getStage());

    }

    public void setScene(MenuScene old, int a){
        old.dispose();
        switch(a){
            case MAIN_MENU :
                scene = new MainMenuScene(this);
                Gdx.input.setInputProcessor(scene.getStage());
                break;
            case SHOP :
                scene = new ShopScene(this);
                Gdx.input.setInputProcessor(scene.getStage());
                break;
            case GAME :
                this.dispose();
                game.setScreen(new LoadingScreen(game, LoadingScreen.GAME_SCREEN));
                break;
        }
    }
    @Override
    public void render(float delta) {

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
        assets.dispose();
        batch.dispose();
    }

    @Override
    public void show() {

    }

    public Sound getValidateSound(){
        return validateSound;
    }


}
