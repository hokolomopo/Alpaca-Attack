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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.AlpacaAttack;
import com.mygdx.game.assets.MenuAssets;
import com.mygdx.game.menu.background.Background;
import com.mygdx.game.menu.background.MainMenuBackground;
import com.mygdx.game.menu.background.ShopBackground;
import com.mygdx.game.menu.scenes.*;
import com.mygdx.game.menu.scenes.SoundButtonsScene;


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
    public static final int LEVEL_SELECT = 3;

    private static final int MAIN_MENU_BACKGROUND = -1;
    private static final int SHOP_BACKGROUND = -2;

    private int currentBackground = -1;

    private MenuScene scene;
    private Background background;
    public Stage stage = new Stage(new ScreenViewport());

    /*public  MenuScreen(Game g){
        this(g, new MenuAssets());
    }*/

    public MenuScreen(Game g, MenuAssets a){

        game = g;
        assets = a;

        music = assets.manager.get(MenuAssets.menuScreenMusicPath, Music.class);
        music.play();
        music.setVolume(assets.getMusicVolume());
        music.setLooping(true);

        validateSound = assets.manager.get(MenuAssets.validateSoundPath, Sound.class);
        bundle = assets.manager.get(MenuAssets.bundlePath, I18NBundle.class);

        background = new MainMenuBackground(assets);
        scene = new MainMenuScene(this);

        Gdx.input.setInputProcessor(stage);

    }

    public void setScene(int a){
        scene.dispose();
        switch(a){
            case MAIN_MENU :
                stage.clear();
                scene = new MainMenuScene(this);
                if(currentBackground != MAIN_MENU_BACKGROUND) {
                    background.dispose();
                    background = new MainMenuBackground(assets);
                }
                currentBackground = MAIN_MENU_BACKGROUND;
                break;
            case SHOP :
                stage.clear();
                scene = new ShopScene(this);
                if(currentBackground != SHOP_BACKGROUND) {
                    background.dispose();
                    background = new ShopBackground(assets);
                }
                currentBackground = SHOP_BACKGROUND;
                break;
            case LEVEL_SELECT :
                stage.clear();
                scene = new SelectLevelScene(this);
                break;
            case GAME :
                this.dispose();
                game.setScreen(new LoadingScreen(game, LoadingScreen.GAME_SCREEN));
                break;
        }
    }

    @Override
    public void render(float delta) {
        batch.begin();
        background.render(batch);
        batch.end();
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
        stage.dispose();
        assets.dispose();
        batch.dispose();
        background.dispose();
    }

    @Override
    public void show() {

    }

    public Sound getValidateSound(){
        return validateSound;
    }


}
