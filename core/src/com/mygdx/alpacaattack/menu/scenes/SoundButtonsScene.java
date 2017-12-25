package com.mygdx.alpacaattack.menu.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.alpacaattack.screen.MenuScreen;

/**
 * Created by Adrien on 14-09-17.
 */

public class SoundButtonsScene {
    private static final float VOLUME_BUTTON_SIZE = Gdx.graphics.getHeight()/10;
    private static final float BUTTON_PADDING = Gdx.graphics.getHeight()/50;

    private Stage stage;

    private MenuScreen menuScreen;

    private TextureAtlas menuAtlas;

    private ImageButton soundButton;
    private ImageButton musicButton;

    public SoundButtonsScene(MenuScreen mScreen){
        menuScreen = mScreen;
        stage = mScreen.stage;

        menuAtlas = menuScreen.assets.manager.get(com.mygdx.alpacaattack.assets.MenuAssets.menuAtlasPath, TextureAtlas.class);
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


    public void render() {
    }

    public void dispose(){
    }
}
