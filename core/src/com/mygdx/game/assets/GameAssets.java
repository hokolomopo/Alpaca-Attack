package com.mygdx.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Created by Adrien on 11-09-17.
 */

public class GameAssets implements  Assets {
    public AssetManager manager = new AssetManager();

    private static final AssetDescriptor<TextureAtlas> spritesAtlas =
            new AssetDescriptor<TextureAtlas>("sprites.txt", TextureAtlas.class);

    private static final AssetDescriptor<TextureAtlas> explosionAtlas =
            new AssetDescriptor<TextureAtlas>("explosion.txt", TextureAtlas.class);

    private static final AssetDescriptor<Music> music =
            new AssetDescriptor<Music>("sound/music/Funky-Chiptune.mp3", Music.class);

    private static final AssetDescriptor<Sound> explosion =
            new AssetDescriptor<Sound>("sound/sfx/explosion.wav", Sound.class);

    private static final AssetDescriptor<Sound> jump =
            new AssetDescriptor<Sound>("sound/sfx/jump.wav", Sound.class);

    private static final AssetDescriptor<Sound> pickup =
            new AssetDescriptor<Sound>("sound/sfx/pickup.wav", Sound.class);





    FreetypeFontLoader.FreeTypeFontLoaderParameter floatingTextFontParameter;
    FreetypeFontLoader.FreeTypeFontLoaderParameter uiScoreTextParameter;


    public GameAssets() {
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        floatingTextFontParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        floatingTextFontParameter.fontFileName = "ttf/BeTrueToYourSchool-Regular.ttf";
        floatingTextFontParameter.fontParameters.size = (Gdx.graphics.getHeight()/10);
        floatingTextFontParameter.fontParameters.color = Color.BLACK;

        uiScoreTextParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        uiScoreTextParameter.fontFileName = "ttf/BeTrueToYourSchool-Regular.ttf";
        uiScoreTextParameter.fontParameters.size = (Gdx.graphics.getHeight()/10);


    }
    @Override
    public void load() {
        manager.load(spritesAtlas);
        manager.load(explosionAtlas);
        manager.load(music);
        manager.load(explosion);
        manager.load(jump);
        manager.load(pickup);
        manager.load("floatingTextFont.ttf", BitmapFont.class, floatingTextFontParameter);
        manager.load("uiScoreFont.ttf", BitmapFont.class, uiScoreTextParameter);
        manager.load("ok.tmx", TiledMap.class);
        //manager.finishLoading();
    }

    @Override
    public void dispose() {
        manager.dispose();
    }

    @Override
    public float update(){
        manager.update();
        return manager.getProgress();
    }

}
