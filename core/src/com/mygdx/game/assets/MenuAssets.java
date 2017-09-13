package com.mygdx.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.game.menu.scenes.MainMenuScene;
import com.mygdx.game.menu.scenes.ShopScene;
import com.mygdx.game.menu.ui.ShopCell;
import com.mygdx.game.screen.MenuScreen;

import java.util.Locale;

/**
 * Created by Adrien on 11-09-17.
 */

public class MenuAssets implements Assets {

    public AssetManager manager = new AssetManager();

    public static final String menuAtlasPath = "menu/menu.txt";
    public static final String spriteAtlasPath = "sprites.txt";
    public static final String menuBackgroundPath = "background.png";
    public static final String menuSkinPath = "menu/flat-earth-ui.json";
    public static final String menuScreenMusicPath = "sound/music/Intro Theme.mp3";
    public static final String endScreenMusicPath = "sound/music/Worldmap Theme.mp3";
    public static final String jumpSoundPath = "sound/sfx/jump.wav";
    public static final String goldSoundPath = "sound/sfx/gold.mp3";
    public static final String validateSoundPath = "sound/menu/validate.wav";

    public static final String mainMenuButtonFont = "mainMenuButtonFont.ttf";
    public static final String shopCellButtonFont = "shopCellButtonFont.ttf";
    public static final String shopCellFont = "shopCellFont.ttf";

    public static final String bundlePath = "properties/Menu";


    FreeTypeFontLoaderParameter bigButtonFontParameter;
    FreeTypeFontLoaderParameter shopCellFontParameter;
    FreeTypeFontLoaderParameter shopCellButtonFontParameter;


    public MenuAssets(){
        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        bigButtonFontParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        bigButtonFontParameter.fontFileName = "ttf/BeTrueToYourSchool-Regular.ttf";
        bigButtonFontParameter.fontParameters.size = (int)(MainMenuScene.BUTTON_HEIGHT*7/8);

        shopCellButtonFontParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        shopCellButtonFontParameter.fontFileName = "ttf/BeTrueToYourSchool-Regular.ttf";
        shopCellButtonFontParameter.fontParameters.size = (int)(ShopCell.TITLE_FONT_SIZE*0.8f);

        shopCellFontParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        shopCellFontParameter.fontFileName = "ttf/BeTrueToYourSchool-Regular.ttf";
        shopCellFontParameter.fontParameters.size = (int)(ShopCell.TITLE_FONT_SIZE);


    }
    @Override
    public void load() {
        manager.load(spriteAtlasPath, TextureAtlas.class);
        manager.load(menuAtlasPath, TextureAtlas.class);
        manager.load(menuBackgroundPath, Texture.class);
        manager.load(menuSkinPath, Skin.class);
        manager.load(menuScreenMusicPath, Music.class);
        manager.load(endScreenMusicPath, Music.class);
        manager.load(jumpSoundPath, Sound.class);
        manager.load(goldSoundPath, Sound.class);
        manager.load(validateSoundPath, Sound.class);
        manager.load(mainMenuButtonFont, BitmapFont.class, bigButtonFontParameter);
        manager.load(shopCellButtonFont, BitmapFont.class, shopCellButtonFontParameter);
        manager.load(shopCellFont, BitmapFont.class, shopCellFontParameter);
        manager.load(bundlePath, I18NBundle.class);


        //manager.finishLoading();
    }

    @Override
    public void dispose() {
        manager.dispose();
    }

    @Override
    public float update() {
        manager.update();
        return manager.getProgress();
    }

    public float getSoundVolume(){
        return Gdx.app.getPreferences("prefs").getFloat("soundVolume", 1f);
    }

    public float getMusicVolume(){
        return Gdx.app.getPreferences("prefs").getFloat("musicVolume", 1f);
    }

}
