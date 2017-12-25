package com.mygdx.alpacaattack.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.alpacaattack.menu.scenes.MainMenuScene;
import com.mygdx.alpacaattack.menu.ui.LevelCell;
import com.mygdx.alpacaattack.menu.ui.ShopCell;

/**
 * Created by Adrien on 11-09-17.
 */

public class MenuAssets implements Assets {

    public AssetManager manager = new AssetManager();

    public static final String menuAtlasPath = "menu/menu.txt";
    public static final String levelSelectAtlasPath = "menu/levels.txt";
    public static final String spriteAtlasPath = "sprites/sprites.txt";
    public static final String menuBackgroundPath = "menu/background.png";
    public static final String menuSkinPath = "menu/flat-earth-ui.json";
    public static final String menuScreenMusicPath = "sound/music/menu.mp3";
    public static final String endScreenMusicPath = "sound/music/endScreen.mp3";
    public static final String jumpSoundPath = "sound/sfx/jump.mp3";
    public static final String goldSoundPath = "sound/sfx/gold.mp3";
    public static final String validateSoundPath = "sound/menu/validate.wav";

    public static final String titleScreenTitleFont = "titleScreenTitle.ttf";
    public static final String mainMenuButtonFont = "mainMenuButtonFont.ttf";
    public static final String shopCellButtonFont = "shopCellButtonFont.ttf";
    public static final String shopCellFont = "shopCellFont.ttf";
    public static final String levelCellButtonFont = "levelCellButtonFont.ttf";
    public static final String levelCellTitleFont = "levelCellTitleFont.ttf";
    public static final String levelInfoFont = "levelInfoFont.ttf";

    public static final String bundlePath = "properties/Menu";


    FreeTypeFontLoaderParameter titleScreenTitleParameter;
    FreeTypeFontLoaderParameter bigButtonFontParameter;
    FreeTypeFontLoaderParameter shopCellFontParameter;
    FreeTypeFontLoaderParameter shopCellButtonFontParameter;
    FreeTypeFontLoaderParameter levelCellButtonFontParameter;
    FreeTypeFontLoaderParameter levelCellTitleFontParameter;
    FreeTypeFontLoaderParameter levelInfoFontParameter;

    public MenuAssets(){
        //Set no maximum size for fonts, otherwise there could be problem of fonts not displaying
        FreeTypeFontGenerator.setMaxTextureSize(FreeTypeFontGenerator.NO_MAXIMUM);

        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        titleScreenTitleParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        titleScreenTitleParameter.fontFileName = "ttf/Bonnie.otf";
        titleScreenTitleParameter.fontParameters.size = (int)(MainMenuScene.TITLE_FONT_SIZE);

        bigButtonFontParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        bigButtonFontParameter.fontFileName = "ttf/BeTrueToYourSchool-Regular.ttf";
        bigButtonFontParameter.fontParameters.size = (int)(MainMenuScene.BUTTON_HEIGHT*7/8);

        shopCellButtonFontParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        shopCellButtonFontParameter.fontFileName = "ttf/BeTrueToYourSchool-Regular.ttf";
        shopCellButtonFontParameter.fontParameters.size = (int)(ShopCell.TITLE_FONT_SIZE*0.8f);

        shopCellFontParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        shopCellFontParameter.fontFileName = "ttf/BeTrueToYourSchool-Regular.ttf";
        shopCellFontParameter.fontParameters.size = (int)(ShopCell.TITLE_FONT_SIZE);

        levelCellButtonFontParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        levelCellButtonFontParameter.fontFileName = "ttf/BeTrueToYourSchool-Regular.ttf";
        levelCellButtonFontParameter.fontParameters.size = (int)(LevelCell.BUTTON_HEIGHT*0.9f);

        levelCellTitleFontParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        levelCellTitleFontParameter.fontFileName = "ttf/BeTrueToYourSchool-Regular.ttf";
        levelCellTitleFontParameter.fontParameters.size = (int)(LevelCell.CELL_WIDTH*0.17f);

        levelInfoFontParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        levelInfoFontParameter.fontFileName = "ttf/BeTrueToYourSchool-Regular.ttf";
        levelInfoFontParameter.fontParameters.size = (int)(LevelCell.CELL_WIDTH*0.10f);

    }
    @Override
    public void load() {
        manager.load(spriteAtlasPath, TextureAtlas.class);
        manager.load(menuAtlasPath, TextureAtlas.class);
        manager.load(levelSelectAtlasPath, TextureAtlas.class);
        manager.load(menuBackgroundPath, Texture.class);
        manager.load(menuSkinPath, Skin.class);
        manager.load(menuScreenMusicPath, Music.class);
        manager.load(endScreenMusicPath, Music.class);
        manager.load(jumpSoundPath, Sound.class);
        manager.load(goldSoundPath, Sound.class);
        manager.load(validateSoundPath, Sound.class);
        manager.load(titleScreenTitleFont, BitmapFont.class, titleScreenTitleParameter);
        manager.load(mainMenuButtonFont, BitmapFont.class, bigButtonFontParameter);
        manager.load(shopCellButtonFont, BitmapFont.class, shopCellButtonFontParameter);
        manager.load(shopCellFont, BitmapFont.class, shopCellFontParameter);
        manager.load(levelCellButtonFont, BitmapFont.class, levelCellButtonFontParameter);
        manager.load(levelCellTitleFont, BitmapFont.class, levelCellTitleFontParameter);
        manager.load(levelInfoFont, BitmapFont.class, levelInfoFontParameter);
        manager.load(bundlePath, I18NBundle.class);
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
