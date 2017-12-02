package com.mygdx.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.game.menu.enums.Levels;
import com.mygdx.game.menu.enums.ShopItem;
import com.mygdx.game.screen.GameScreen;

/**
 * Created by Adrien on 11-09-17.
 */

public class GameAssets implements  Assets {
    public AssetManager manager = new AssetManager();
    private Array<ParticleEffect> particleEffects = new Array<ParticleEffect>();

    public final static String explosionPath = "sound/sfx/explosion.mp3";
    public final static String explosionAtlasPath = "sprites/explosion.txt";
    public final static String spriteAtlasPath = "sprites/sprites.txt";
    public final static String jumpPath = "sound/sfx/jump.mp3";
    public final static String pickupPath = "sound/sfx/pickup.mp3";
    public final static String dashPath = "sound/sfx/dash.mp3";

    public final static String floatingTextFont = "floatingTextFont.ttf";
    public final static String uiScoreFont = "uiScoreFont.ttf";

    public static final String bundlePath = "properties/Game";

    FreetypeFontLoader.FreeTypeFontLoaderParameter floatingTextFontParameter;
    FreetypeFontLoader.FreeTypeFontLoaderParameter uiScoreTextParameter;


    public GameAssets() {
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        floatingTextFontParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        floatingTextFontParameter.fontFileName = "ttf/BeTrueToYourSchool-Regular.ttf";
        floatingTextFontParameter.fontParameters.size = ((int)(60 * GameScreen.PIXEL_TO_METER));

        uiScoreTextParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        uiScoreTextParameter.fontFileName = "ttf/BeTrueToYourSchool-Regular.ttf";
        uiScoreTextParameter.fontParameters.size = (Gdx.graphics.getHeight()/10);


    }
    @Override
    public void load(){
        manager.load(spriteAtlasPath, TextureAtlas.class);
        manager.load(explosionAtlasPath, TextureAtlas.class);
        manager.load(explosionPath, Sound.class);
        manager.load(jumpPath, Sound.class);
        manager.load(pickupPath, Sound.class);
        manager.load(dashPath, Sound.class);
        manager.load(floatingTextFont, BitmapFont.class, floatingTextFontParameter);
        manager.load(uiScoreFont , BitmapFont.class, uiScoreTextParameter);
        manager.load(bundlePath, I18NBundle.class);

        this.loadPlayerDash();
        this.loadPlayerDrag();
    }
    public void load(Levels level) {
        this.load();
        manager.load(level.getFilePath(), TiledMap.class);
        manager.load(level.getMusicPath(), Music.class);
    }

    @Override
    public void dispose() {
        for(ParticleEffect p : particleEffects)
            p.dispose();
        manager.dispose();
    }

    @Override
    public float update(){
        manager.update();
        return manager.getProgress();
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

    public float getSoundVolume(){
        return Gdx.app.getPreferences("prefs").getFloat("soundVolume", 1f);
    }

    public float getMusicVolume(){
        return Gdx.app.getPreferences("prefs").getFloat("musicVolume", 1f);
    }



}
