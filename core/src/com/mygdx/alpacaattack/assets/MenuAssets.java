package com.mygdx.alpacaattack.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
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
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.alpacaattack.menu.enums.Levels;
import com.mygdx.alpacaattack.menu.enums.ShopItem;
import com.mygdx.alpacaattack.menu.scenes.MainMenuScene;
import com.mygdx.alpacaattack.menu.ui.LevelCell;
import com.mygdx.alpacaattack.menu.ui.ShopCell;

/**
 * Created by Adrien on 11-09-17.
 */

public class MenuAssets implements Assets {

    static public boolean loaded = false;

    public AssetManager manager = new AssetManager();
    private Array<ParticleEffect> particleEffects = new Array<ParticleEffect>();

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

    public static final String bundlePath = "properties/Game";

    public final static String explosionPath = "sound/sfx/explosion.mp3";
    public final static String explosionAtlasPath = "sprites/explosion.txt";
    public final static String jumpPath = "sound/sfx/jump.mp3";
    public final static String pickupPath = "sound/sfx/pickup.mp3";
    public final static String dashPath = "sound/sfx/dash.mp3";

    public final static String floatingTextFont = "floatingTextFont.ttf";
    public final static String uiScoreFont = "uiScoreFont.ttf";

    FreetypeFontLoader.FreeTypeFontLoaderParameter floatingTextFontParameter;
    FreetypeFontLoader.FreeTypeFontLoaderParameter uiScoreTextParameter;

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

        //Menu Resources
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

        //Game Resources
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

        floatingTextFontParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        floatingTextFontParameter.fontFileName = "ttf/BeTrueToYourSchool-Regular.ttf";
        floatingTextFontParameter.fontParameters.size = ((int)(60 * com.mygdx.alpacaattack.screen.GameScreen.PIXEL_TO_METER));

        uiScoreTextParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        uiScoreTextParameter.fontFileName = "ttf/BeTrueToYourSchool-Regular.ttf";
        uiScoreTextParameter.fontParameters.size = (Gdx.graphics.getHeight()/10);


    }
    @Override
    public void load() {
        //Menu
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

        //Game
        manager.load(explosionAtlasPath, TextureAtlas.class);
        manager.load(explosionPath, Sound.class);
        manager.load(jumpPath, Sound.class);
        manager.load(pickupPath, Sound.class);
        manager.load(dashPath, Sound.class);
        manager.load(floatingTextFont, BitmapFont.class, floatingTextFontParameter);
        manager.load(uiScoreFont , BitmapFont.class, uiScoreTextParameter);

        this.loadPlayerDash();
        this.loadPlayerDrag();

        loaded = true;

    }

    public void load(Levels level) {
        manager.load(level.getFilePath(), TiledMap.class);
        manager.load(level.getMusicPath(), Music.class);

    }

    public void unloadLevel(Levels level) {
        manager.unload(level.getFilePath());
        manager.unload(level.getMusicPath());
    }


    @Override
    public void dispose() {

        for(ParticleEffect p : particleEffects)
            p.dispose();
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
    public ParticleEffect createBonusParticleEffect(){
        ParticleEffect p = new ParticleEffect();
        p.load(Gdx.files.internal("particles/stardust"), Gdx.files.internal("particles/"));
        particleEffects.add(p);
        return p;
    }

    public ParticleEffect getPlayerDash(){
        Preferences prefs = Gdx.app.getPreferences("prefs");
        String drag = prefs.getString("equippedDash", "");
        if(drag.equals(ShopItem.DASH_RAINBOW.getName()))
            return  manager.get("particles/dash_rainbow", ParticleEffect.class);
        else if(drag.equals(ShopItem.DASH_BLUE.getName()))
            return  manager.get("particles/dash_blue", ParticleEffect.class);
        else if(drag.equals(ShopItem.DASH_RED.getName()))
            return  manager.get("particles/dash_red", ParticleEffect.class);
        else if(drag.equals(ShopItem.DASH_PINK.getName()))
            return  manager.get("particles/dash_pink", ParticleEffect.class);
        else if(drag.equals(ShopItem.DASH_YELLOW.getName()))
            return  manager.get("particles/dash_yellow", ParticleEffect.class);
        else if(drag.equals(ShopItem.DASH_WHITE.getName()))
            return  manager.get("particles/dash_white", ParticleEffect.class);

        return null;
    }

    public void loadPlayerDash(){
        Preferences prefs = Gdx.app.getPreferences("prefs");
        String drag = prefs.getString("equippedDash", "");
        if(drag.equals(ShopItem.DASH_RAINBOW.getName()))
            manager.load("particles/dash_rainbow", ParticleEffect.class);
        else if(drag.equals(ShopItem.DASH_WHITE.getName()))
            manager.load("particles/dash_white", ParticleEffect.class);
        else if(drag.equals(ShopItem.DASH_BLUE.getName()))
            manager.load("particles/dash_blue", ParticleEffect.class);
        else if(drag.equals(ShopItem.DASH_RED.getName()))
            manager.load("particles/dash_red", ParticleEffect.class);
        else if(drag.equals(ShopItem.DASH_PINK.getName()))
            manager.load("particles/dash_pink", ParticleEffect.class);
        else if(drag.equals(ShopItem.DASH_YELLOW.getName()))
            manager.load("particles/dash_yellow", ParticleEffect.class);
    }

    public ParticleEffect getPlayerDrag(){
        Preferences prefs = Gdx.app.getPreferences("prefs");
        String drag = prefs.getString("equippedDrag", "");

        if(drag.equals(ShopItem.DRAG_BLUE.getName()))
            return  manager.get("particles/drag_blue", ParticleEffect.class);
        else if(drag.equals(ShopItem.DRAG_RED.getName()))
            return  manager.get("particles/drag_red", ParticleEffect.class);
        else if(drag.equals(ShopItem.DRAG_YELLOW.getName()))
            return  manager.get("particles/drag_yellow", ParticleEffect.class);
        else if(drag.equals(ShopItem.DRAG_WHITE.getName()))
            return  manager.get("particles/drag_white", ParticleEffect.class);
        else if(drag.equals(ShopItem.DRAG_PINK.getName()))
            return  manager.get("particles/drag_pink", ParticleEffect.class);

        return  null;
    }

    public ParticleEffect loadPlayerDrag(){
        Preferences prefs = Gdx.app.getPreferences("prefs");
        String drag = prefs.getString("equippedDrag", "");

        if(drag.equals(ShopItem.DRAG_BLUE.getName()))
            manager.load("particles/drag_blue", ParticleEffect.class);
        else if(drag.equals(ShopItem.DRAG_RED.getName()))
            manager.load("particles/drag_red", ParticleEffect.class);
        else if(drag.equals(ShopItem.DRAG_YELLOW.getName()))
            manager.load("particles/drag_yellow", ParticleEffect.class);
        else if(drag.equals(ShopItem.DRAG_WHITE.getName()))
            manager.load("particles/drag_white", ParticleEffect.class);
        else if(drag.equals(ShopItem.DRAG_PINK.getName()))
            manager.load("particles/drag_pink", ParticleEffect.class);

        return  null;
    }

}
