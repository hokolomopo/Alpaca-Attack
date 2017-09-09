package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.AlpacaAttack;
import com.mygdx.game.menu.MainMenuScene;
import com.mygdx.game.menu.MenuScene;
import com.mygdx.game.menu.TextBox;

/**
 * Created by Adrien on 09-09-17.
 */

public class EndScreen implements Screen {

    private final static int FONT_SIZE = 110;
    private static final float MARGIN = Gdx.graphics.getHeight()/12;

    private Stage stage;
    private Game game;
    private GameScreen gameScreen;
    private BitmapFont font = AlpacaAttack.generateFont(Gdx.files.internal("ttf/BeTrueToYourSchool-Regular.ttf"), (int)(MainMenuScene.BUTTON_HEIGHT - MainMenuScene.BUTTON_HEIGHT/8));
    private int finalScore;
    private int highScore;

    private Label hScore;
    private Label score;
    private TextBox money;


    public EndScreen(Game g, final GameScreen gmScreen, int argScore){
        game = g;
        gameScreen = gmScreen;
        finalScore = argScore;
        highScore = 1000;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        money= new TextBox(Integer.toString(1000));
        money.setSize(Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight()/10);
        money.setPosition(Gdx.graphics.getWidth() - money.getWidth() - MARGIN, Gdx.graphics.getHeight() - money.getHeight() - MARGIN);
        stage.addActor(money);

        score = new Label("Score : \n" +Integer.toString(finalScore), new Label.LabelStyle(font, Color.BLACK));
        score.setSize(500, 200);
        score.setPosition(Gdx.graphics.getWidth()/2 - score.getWidth()/2, Gdx.graphics.getHeight()/2);
        score.setAlignment(Align.center);
        stage.addActor(score);

        TextButton play = new TextButton("Retry", MenuScene.skin);
        play.getLabel().setStyle(new Label.LabelStyle(font, Color.WHITE));
        play.setSize(MainMenuScene.BUTTON_WIDTH, MainMenuScene.BUTTON_HEIGHT);
        play.setPosition(Gdx.graphics.getWidth()/2 - play.getWidth()/2, score.getY() - MainMenuScene.BUTTON_HEIGHT -10);
        play.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.setInputProcessor(gameScreen);
                game.setScreen(gameScreen);
            }
        });
        stage.addActor(play);


        TextButton quit = new TextButton("Quit", MenuScene.skin);
        quit.getLabel().setStyle(new Label.LabelStyle(font, Color.WHITE));
        quit.setSize(MainMenuScene.BUTTON_WIDTH, MainMenuScene.BUTTON_HEIGHT);
        quit.setPosition(Gdx.graphics.getWidth()/2 - quit.getWidth()/2, play.getY() - MainMenuScene.BUTTON_HEIGHT -10);
        quit.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MenuScreen(game));
            }
        });
        stage.addActor(quit);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.draw();
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
