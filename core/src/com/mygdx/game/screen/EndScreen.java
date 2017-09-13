package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.assets.MenuAssets;
import com.mygdx.game.menu.background.EndScreenBackground;
import com.mygdx.game.menu.scenes.MainMenuScene;
import com.mygdx.game.menu.ui.MoneyTextBox;

/**
 * Created by Adrien on 09-09-17.
 */

public class EndScreen implements Screen {

    private Preferences prefs = Gdx.app.getPreferences("prefs");

    private final float TIME_FOR_ANIMATION = 3; //Time to wait before teh animation score => money starts
    private final float TIME_FOR_ANIMATION_SPEEDUP = 2; //Time to wait before teh animation score => money speed up
    private final float POINT_TO_MONEY_RATIO = 1/50f; //Ratio to convert points to money
    private final float MARGIN = Gdx.graphics.getHeight()/12;
    private float BUTTON_HEIGHT = Gdx.graphics.getHeight()/8;
    private float BUTTON_WIDTH = Gdx.graphics.getWidth()/3;
    private Stage stage;
    private Game game;
    private BitmapFont font;
    private int finalScore;
    private int highScore;
    private int moneyAmount;

    private float screenTimer = 0; //Duration since the screen was launched
    private float animationTimer = 0; //Duration since the animation score => money started
    private boolean animationStart = false; //True = start animation score => money
    private boolean endAnimationNow = false; //True = force animation end and convert everything
    private boolean animationEnded = false;//True when animation is ended
    private boolean animationJustStarted = false;

    private Label score;
    private MoneyTextBox money;
    private Sound gold;
    private Sound validateSound;

    private MenuAssets assets;
    private Skin skin;
    private Music music;
    private GameScreen gameScreen;
    private EndScreen thisScreen;

    private EndScreenBackground background;
    private SpriteBatch batch;

    /*public EndScreen(Game g, int argScore, GameScreen gmScreen) {
        this(g, argScore, gmScreen, new MenuAssets());
    }*/

    public EndScreen(Game g, int argScore, GameScreen gmScreen, MenuAssets a){

        thisScreen = this;
        game = g;
        finalScore = argScore;
        gameScreen = gmScreen;
        highScore = prefs.getInteger("highScore", 0);
        batch = new SpriteBatch();

        assets = a;
        //assets.load();


        this.loadAssets();

        background = new EndScreenBackground(assets);

        //Update the highScore
        if(highScore < finalScore){
            highScore = finalScore;
            prefs.putInteger("highScore", finalScore).flush();
        }

        stage = new Stage(new ScreenViewport());
        stage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!animationStart) {
                    animationStart = true;
                }
                else
                    endAnimationNow = true;
                return true;
            }
        });
        Gdx.input.setInputProcessor(stage);

        this.initializeMoneyBox();
        this.initializeLabels();

        this.createButtons();

        Gdx.input.setInputProcessor(stage);

        music.setLooping(true);
        music.play();
        music.setVolume(assets.getMusicVolume());
    }

    private void loadAssets(){
        font = assets.manager.get(MenuAssets.mainMenuButtonFont);
        skin = assets.manager.get(MenuAssets.menuSkinPath, Skin.class);
        music = assets.manager.get(MenuAssets.endScreenMusicPath, Music.class);
        gold = assets.manager.get(MenuAssets.goldSoundPath, Sound.class);
        validateSound = assets.manager.get(MenuAssets.validateSoundPath, Sound.class);

    }

    private void initializeLabels(){

        Label HScore = new Label("Highscore: \n" +Integer.toString(highScore), new Label.LabelStyle(font, Color.BLACK));
        HScore.setSize(0,0);
        HScore.setPosition(Gdx.graphics.getWidth()/2 - HScore.getWidth()/2, Gdx.graphics.getHeight() - MainMenuScene.BUTTON_HEIGHT);
        HScore.setAlignment(Align.center);
        stage.addActor(HScore);

        score = new Label("Score: \n" +Integer.toString(finalScore), new Label.LabelStyle(font, Color.BLACK));
        score.setSize(0, 0);
        score.setPosition(Gdx.graphics.getWidth()/2 - score.getWidth()/2, HScore.getY() - MainMenuScene.BUTTON_HEIGHT*2);
        score.setAlignment(Align.center);
        stage.addActor(score);

    }

    private void initializeMoneyBox(){
        moneyAmount = prefs.getInteger("money", 0);

        money= new MoneyTextBox(moneyAmount, assets);
        money.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/10);
        money.setPosition(Gdx.graphics.getWidth() - money.getTotalWidth() - MARGIN, Gdx.graphics.getHeight() - money.getHeight() - MARGIN);
        stage.addActor(money);
    }

    private void createButtons(){
        TextButton play = new TextButton("Retry", skin);
        play.getLabel().setStyle(new Label.LabelStyle(font, Color.WHITE));
        play.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        play.setPosition(Gdx.graphics.getWidth()/2 - play.getWidth()/2, score.getY() - BUTTON_HEIGHT*2);
        play.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //Makes sur to convert all the score to money before quitting the screen
                if(!endAnimationNow) {
                    endAnimationNow = true;
                    return false;
                }
                validateSound.play(assets.getSoundVolume());
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.setInputProcessor(gameScreen);
                prefs.putInteger("money", moneyAmount).flush();
                thisScreen.dispose();
                game.setScreen(gameScreen);
            }
        });

        stage.addActor(play);

        TextButton quit = new TextButton("Quit", skin);
        quit.getLabel().setStyle(new Label.LabelStyle(font, Color.WHITE));
        quit.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        quit.setPosition(Gdx.graphics.getWidth()/2 - quit.getWidth()/2, play.getY() - BUTTON_HEIGHT -10);
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
                gameScreen.dispose();
                thisScreen.disposeWithoutAssets();
                game.setScreen(new MenuScreen(game, assets));
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

        if(endAnimationNow && !animationEnded){
            moneyAmount += finalScore * POINT_TO_MONEY_RATIO;
            finalScore = 0;
            updateTexts();
            animationEnded = true;
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
        if((animationStart ||screenTimer > TIME_FOR_ANIMATION) && !animationEnded) {
            if(animationJustStarted == false){
                animationJustStarted = true;
                gold.loop(0.5f * assets.getSoundVolume());
            }
            animationTimer += Gdx.graphics.getDeltaTime();
            scoreToMoneyAnim();
        }
        else if(animationEnded){
            gold.stop();
        }
        batch.begin();
        background.draw(batch);
        batch.end();
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
        assets.dispose();
        this.disposeWithoutAssets();
    }

    //Dispose everything but teh assets, use it wehn giving the assets to another screen instead of reloading everything
    public void disposeWithoutAssets(){
        money.dispose();
        stage.dispose();
        batch.dispose();
        music.stop();
    }
}
