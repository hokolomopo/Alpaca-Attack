package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
import com.mygdx.game.menu.MoneyTextBox;

/**
 * Created by Adrien on 09-09-17.
 */

public class EndScreen implements Screen {

    private Preferences prefs = Gdx.app.getPreferences("prefs");

    private final float TIME_FOR_ANIMATION = 3; //Time to wait before teh animation score => money starts
    private final float TIME_FOR_ANIMATION_SPEEDUP = 2; //Time to wait before teh animation score => money speed up
    private final float POINT_TO_MONEY_RATIO = 1/50f; //Ratio to convert points to money
    private final float MARGIN = Gdx.graphics.getHeight()/12;

    private Stage stage;
    private Game game;
    private GameScreen gameScreen;
    private BitmapFont font = AlpacaAttack.generateFont(Gdx.files.internal("ttf/BeTrueToYourSchool-Regular.ttf"), (int)(MainMenuScene.BUTTON_HEIGHT - MainMenuScene.BUTTON_HEIGHT/8));
    private int finalScore;
    private int highScore;
    private int moneyAmount;

    private float screenTimer = 0; //Duration since the screen was launched
    private float animationTimer = 0; //Duration since the animation score => money started
    private boolean animationStart = false; //True = start animation score => money
    private boolean endAnimationNow = false; //True = force animation end and convert everything

    private Label score;
    private MoneyTextBox money;


    public EndScreen(Game g, final GameScreen gmScreen, int argScore){
        game = g;
        gameScreen = gmScreen;
        finalScore = argScore;
        highScore = prefs.getInteger("highScore", 0);

        //Update the highScore
        if(highScore < finalScore){
            highScore = finalScore;
            prefs.putInteger("highScore", finalScore).flush();
        }

        stage = new Stage(new ScreenViewport());
        stage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!animationStart)
                    animationStart = true;
                else
                    endAnimationNow = true;
                return true;
            }
        });
        Gdx.input.setInputProcessor(stage);

        moneyAmount = prefs.getInteger("money", 0);

        money= new MoneyTextBox(moneyAmount);
        money.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/10);
        money.setPosition(Gdx.graphics.getWidth() - money.getTotalWidth() - MARGIN, Gdx.graphics.getHeight() - money.getHeight() - MARGIN);
        stage.addActor(money);

        Label HScore = new Label("Highscore: \n" +Integer.toString(highScore), new Label.LabelStyle(font, Color.BLACK));
        HScore.setSize(500, 200);
        HScore.setPosition(Gdx.graphics.getWidth()/2 - HScore.getWidth()/2, Gdx.graphics.getHeight()*3/4);
        HScore.setAlignment(Align.center);
        stage.addActor(HScore);

        score = new Label("Score: \n" +Integer.toString(finalScore), new Label.LabelStyle(font, Color.BLACK));
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
                //Makes sur to convert all the score to money before quitting the screen
                if(!endAnimationNow) {
                    endAnimationNow = true;
                    return false;
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.setInputProcessor(gameScreen);
                prefs.putInteger("money", moneyAmount).flush();
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
                //Makes sur to convert all the score to money before quitting the screen
                if(!endAnimationNow) {
                    endAnimationNow = true;
                    return false;
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                prefs.putInteger("money", moneyAmount).flush();
                game.setScreen(new MenuScreen(game, gameScreen));
            }
        });
        stage.addActor(quit);


    }

    public void scoreToMoneyAnim(){

        //Initialize the points to deduct this frame
        int deductedPoints = 50;
        if (animationTimer > TIME_FOR_ANIMATION_SPEEDUP)
            deductedPoints = 500;
        if (animationTimer > TIME_FOR_ANIMATION_SPEEDUP * 3)
            deductedPoints = 5000;

        //Deduct score points and add money
        if (finalScore >= deductedPoints) {
            finalScore -= deductedPoints;
            moneyAmount += deductedPoints * POINT_TO_MONEY_RATIO;
            updateTexts();
        }
        else {
            endAnimationNow = true;
        }

        if(endAnimationNow){
            moneyAmount += finalScore * POINT_TO_MONEY_RATIO;
            finalScore = 0;
            updateTexts();
        }
    }

    //Update the score and money labels with the current values of score and money
    public void updateTexts(){
        score.setText("Score:\n" + Integer.toString(finalScore));
        money.setAmount(moneyAmount);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        screenTimer += Gdx.graphics.getDeltaTime();
        if(animationStart ||screenTimer > TIME_FOR_ANIMATION) {
            animationTimer += Gdx.graphics.getDeltaTime();
            scoreToMoneyAnim();
        }
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
