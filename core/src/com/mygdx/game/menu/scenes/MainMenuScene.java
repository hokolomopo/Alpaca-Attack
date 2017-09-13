package com.mygdx.game.menu.scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.AlpacaAttack;
import com.mygdx.game.assets.MenuAssets;
import com.mygdx.game.menu.background.MainMenuBackground;
import com.mygdx.game.menu.shop.ShopItem;
import com.mygdx.game.screen.EndScreen;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.MenuScreen;

/**
 * Created by Adrien on 08-09-17.
 */

public class MainMenuScene extends com.mygdx.game.menu.scenes.MenuScene {
    public static final float BUTTON_WIDTH = Gdx.graphics.getWidth()/3;
    public static final float BUTTON_HEIGHT = Gdx.graphics.getHeight()/8;
    private static final float BUTTON_PADDING = BUTTON_HEIGHT/7;
    private static final float VOLUME_BUTTON_SIZE = Gdx.graphics.getHeight()/10;

    private BitmapFont font;

    private Game game;

    private MainMenuBackground background;
    private TextureAtlas menuAtlas;

    private ImageButton soundButton;
    private ImageButton musicButton;


    public MainMenuScene(MenuScreen mScreen){
        super(mScreen.assets);
        scene = this;
        menuScreen = mScreen;

        Gdx.input.setInputProcessor(stage);

        font = menuScreen.assets.manager.get(MenuAssets.mainMenuButtonFont, BitmapFont.class);
        background = new MainMenuBackground(mScreen.assets);

        menuAtlas = menuScreen.assets.manager.get(MenuAssets.menuAtlasPath, TextureAtlas.class);

        this.createButtons();

    }

    private void createButtons(){
        TextButton play = new TextButton(menuScreen.bundle.get("play"), skin);
        play.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        play.setPosition(Gdx.graphics.getWidth()/2 - BUTTON_WIDTH/2, Gdx.graphics.getHeight()/2);
        play.getLabel().setStyle(new Label.LabelStyle(font, Color.WHITE));
        play.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                menuScreen.getValidateSound().play(1f * menuScreen.assets.getSoundVolume());
                menuScreen.setScene(scene, MenuScreen.GAME);
            }

        });
        stage.addActor(play);

        TextButton shop = new TextButton(menuScreen.bundle.get("shop"), skin);
        shop.setSize(BUTTON_WIDTH,BUTTON_HEIGHT);
        shop.setPosition(Gdx.graphics.getWidth()/2 - play.getWidth()/2
                , play.getY() - BUTTON_HEIGHT - BUTTON_PADDING);
        shop.getLabel().setStyle(new Label.LabelStyle(font, Color.WHITE));
        shop.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                menuScreen.setScene(scene, MenuScreen.SHOP);
            }

        });
        stage.addActor(shop);


        TextButton close = new TextButton(menuScreen.bundle.get("exit"), skin);
        close.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        close.setPosition(Gdx.graphics.getWidth()/2 - BUTTON_WIDTH/2
                , shop.getY() - BUTTON_HEIGHT - BUTTON_PADDING);
        close.getLabel().setStyle(new Label.LabelStyle(font, Color.WHITE));
        close.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                menuScreen.getValidateSound().play(1f* menuScreen.assets.getSoundVolume());
                Gdx.app.exit();
            }

        });
        stage.addActor(close);

        this.createSoundButtons();
    }

    private void createSoundButtons(){

        soundButton = new ImageButton(this.selectSoundButtonTexture());
        soundButton.setSize(VOLUME_BUTTON_SIZE,VOLUME_BUTTON_SIZE);
        soundButton.setPosition(BUTTON_PADDING, BUTTON_PADDING);
        soundButton.setChecked(true);
        soundButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                changeSoundVolume();
                soundButton.setStyle(selectSoundButtonTexture());
                return true;
            }
        });
        stage.addActor(soundButton);

        musicButton = new ImageButton(this.selectMusicButtonTexture());
        musicButton.setSize(VOLUME_BUTTON_SIZE,VOLUME_BUTTON_SIZE);
        musicButton.setPosition(Gdx.graphics.getWidth() - VOLUME_BUTTON_SIZE - BUTTON_PADDING, BUTTON_PADDING);
        musicButton.setChecked(true);
        musicButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                changeMusicVolume();
                musicButton.setStyle(selectMusicButtonTexture());
                return true;
            }
        });
        stage.addActor(musicButton);

    }

    private void changeSoundVolume(){
        if(Gdx.app.getPreferences("prefs").getFloat("soundVolume", 1f) == 1)
            Gdx.app.getPreferences("prefs").putFloat("soundVolume", 0f).flush();
        else
            Gdx.app.getPreferences("prefs").putFloat("soundVolume", 1f).flush();

    }

    private void changeMusicVolume(){
        if(Gdx.app.getPreferences("prefs").getFloat("musicVolume", 1f) == 1) {
            Gdx.app.getPreferences("prefs").putFloat("musicVolume", 0f).flush();
            menuScreen.music.setVolume(0f);
        }

        else {
            Gdx.app.getPreferences("prefs").putFloat("musicVolume", 1f).flush();
            menuScreen.music.setVolume(1f);
            menuScreen.music.play();
        }

    }

    private ImageButton.ImageButtonStyle selectSoundButtonTexture(){
        TextureRegion texture;
        if(Gdx.app.getPreferences("prefs").getFloat("soundVolume", 1f) == 1)
            texture = menuAtlas.findRegion("sound_on");
        else
            texture = menuAtlas.findRegion("sound_off");

        ImageButton.ImageButtonStyle soundStyle = new ImageButton.ImageButtonStyle();
        soundStyle.imageUp = new TextureRegionDrawable(texture);

        return soundStyle;
    }

    private ImageButton.ImageButtonStyle selectMusicButtonTexture(){
        TextureRegion texture;
        if(Gdx.app.getPreferences("prefs").getFloat("musicVolume", 1f) == 1)
            texture = menuAtlas.findRegion("music_on");
        else
            texture = menuAtlas.findRegion("music_off");

        ImageButton.ImageButtonStyle soundStyle = new ImageButton.ImageButtonStyle();
        soundStyle.imageUp = new TextureRegionDrawable(texture);

        return soundStyle;
    }


    private TextureRegionDrawable selectSoundMusicButtonTexture(){

        TextureRegion musicTexture;
        if(Gdx.app.getPreferences("prefs").getFloat("musicVolume", 1f) == 1)
            musicTexture = menuAtlas.findRegion("sound_on");
        else
            musicTexture = menuAtlas.findRegion("sound_off");
        return new TextureRegionDrawable(musicTexture);
    }


    @Override
    public void render(SpriteBatch batch){
        stage.act(Gdx.graphics.getDeltaTime());

        batch.begin();
        background.render(batch);
        batch.end();
        stage.draw();

    }

    @Override
    public void dispose(){
        super.dispose();
        background.dispose();
    }

}
