package com.mygdx.alpacaattack.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.alpacaattack.AlpacaAttack;
import com.mygdx.alpacaattack.menu.background.Background;
import com.mygdx.alpacaattack.menu.background.MainMenuBackground;
import com.mygdx.alpacaattack.menu.background.ShopBackground;
import com.mygdx.alpacaattack.menu.enums.Levels;
import com.mygdx.alpacaattack.menu.scenes.MainMenuScene;
import com.mygdx.alpacaattack.menu.scenes.MenuScene;


/**
 * Created by Adrien on 02-09-17.
 */

public class MenuScreen implements Screen {
    private AlpacaAttack game;
    public Music music;

    public com.mygdx.alpacaattack.assets.MenuAssets assets;
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
    private Levels selectedLevel;

    /*public  MenuScreen(AlpacaAttack g){
        this(g, new MenuAssets());
    }*/

    public MenuScreen(AlpacaAttack g){

        game = g;
        assets = g.assets;

        music = assets.manager.get(com.mygdx.alpacaattack.assets.MenuAssets.menuScreenMusicPath, Music.class);
        music.play();
        music.setVolume(assets.getMusicVolume());
        music.setLooping(true);

        validateSound = assets.manager.get(com.mygdx.alpacaattack.assets.MenuAssets.validateSoundPath, Sound.class);
        bundle = assets.manager.get(com.mygdx.alpacaattack.assets.MenuAssets.bundlePath, I18NBundle.class);

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
                scene = new com.mygdx.alpacaattack.menu.scenes.ShopScene(this);
                if(currentBackground != SHOP_BACKGROUND) {
                    background.dispose();
                    background = new ShopBackground(assets);
                }
                currentBackground = SHOP_BACKGROUND;
                break;
            case LEVEL_SELECT :
                stage.clear();
                scene = new com.mygdx.alpacaattack.menu.scenes.SelectLevelScene(this);
                break;
            case GAME :
                this.dispose();
                game.setScreen(new LoadingScreen(game, selectedLevel));
                break;
        }
    }

    public void setSelectedLevel(Levels level){
        selectedLevel = level;
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
