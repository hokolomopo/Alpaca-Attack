package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.assets.Assets;
import com.mygdx.game.assets.GameAssets;
import com.mygdx.game.assets.MenuAssets;

/**
 * Created by Adrien on 04-09-17.
 */

public class LoadingScreen implements Screen {
    private Game game;
    Assets assets;

    private BitmapFont font;
    private int FONT_SIZE = Gdx.graphics.getHeight()/8;
    private SpriteBatch batch = new SpriteBatch();
    private Skin skin = new Skin(Gdx.files.internal("menu/flat-earth-ui.json"));
    private Stage stage = new Stage();

    private ProgressBar loadingBar;
    private float progress;

    public final static int GAME_SCREEN = 0;
    public final static int MENU_SCREEN = 1;
    private final static int END_SCREEN = 2;

    private int screenType;

    //For EndScreen
    private int score;
    private GameScreen gameScreen;

    public LoadingScreen(Game g, int screenType){
        this.createLoadingBar();
        this.createText();

        this.screenType = screenType;
        game = g;
        switch(screenType){
            case GAME_SCREEN :
                assets = new GameAssets();
                break;
            case MENU_SCREEN :
                assets = new MenuAssets();
                break;
        }
        assets.load();
    }

    public LoadingScreen(Game g, int argScore, GameScreen gmScreen){
        this.createLoadingBar();
        this.createText();

        this.screenType = END_SCREEN;
        game = g;
        score = argScore;
        gameScreen = gmScreen;

        assets = new MenuAssets();
        assets.load();
    }

    private void createText(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ttf/BeTrueToYourSchool-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = FONT_SIZE;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);
        generator.dispose();

    }

    private void createLoadingBar(){
        loadingBar = new ProgressBar(0f, 1f, 0.1f, false, skin, "fancy");
        loadingBar.setSize(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/8);
        loadingBar.setPosition(Gdx.graphics.getWidth()/2 - loadingBar.getWidth()/2, Gdx.graphics.getHeight()/4);
        stage.addActor(loadingBar);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        progress = assets.update();
        loadingBar.setValue(progress);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        batch.begin();
        font.draw(batch, "Loading ...", 0, loadingBar.getY()+ loadingBar.getHeight() +40, Gdx.graphics.getWidth(), Align.center, true);
        batch.end();
        if(progress == 1){
            this.dispose();
            switch(screenType){
                case GAME_SCREEN :
                    game.setScreen(new GameScreen(game, (GameAssets)assets));
                    break;
                case MENU_SCREEN :
                    game.setScreen(new MenuScreen(game, (MenuAssets)assets));
                    break;
                case END_SCREEN :
                    game.setScreen(new EndScreen(game, score, gameScreen, (MenuAssets)assets));
                    break;
            }
        }

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
        batch.dispose();
        font.dispose();
        skin.dispose();
        stage.clear();
        stage.dispose();
    }
}
