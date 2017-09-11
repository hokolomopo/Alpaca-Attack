package com.mygdx.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.screen.MenuScreen;

/**
 * Created by Adrien on 11-09-17.
 */

public class MenuAssets implements Assets {
    //Main Menu
    public static final float MAIN_MENU_BUTTON_WIDTH = Gdx.graphics.getWidth()/3;
    public static final float MAIN_MENU_BUTTON_HEIGHT = Gdx.graphics.getHeight()/8;
    public static final float MAIN_MENU_BUTTON_PADDING = 30;

    //Shop
    public static final float SHOP_BUTTON_WIDTH = Gdx.graphics.getWidth()/6;
    public static final float SHOP_BUTTON_HEIGHT = Gdx.graphics.getHeight()/10;
    public static final float SHOP_BACK_BUTTON_PADDING = 30;
    public static final float SHOP_PADDING = 15;
    public static final float SHOP_CELL_Y_POSITION = Gdx.graphics.getHeight()*0.06f;


    //ShopCell
    public final static float CELL_HEIGHT = 0.65f*Gdx.graphics.getHeight();
    public final static float CELL_WIDTH = 0.15f*Gdx.graphics.getWidth();
    public final static float OBJECT_CELL_RATIO = 0.4f;
    public final static float CELL_MARGIN = CELL_WIDTH*0.05f;
    public final static float CELL_BUTTON_HEIGHT = 0.1f*CELL_HEIGHT;
    public final static float CELL_BUTTON_WIDTH = CELL_WIDTH -2*CELL_MARGIN;
    public final static float CELL_FONT_SIZE = 60;


    public AssetManager manager = new AssetManager();

    private static final AssetDescriptor<TextureAtlas> shopAtlas =
            new AssetDescriptor<TextureAtlas>("menu/menu.txt", TextureAtlas.class);

    private static final AssetDescriptor<TextureAtlas> spritesAtlas =
            new AssetDescriptor<TextureAtlas>("sprites.txt", TextureAtlas.class);

    private static final AssetDescriptor<Texture> mainMenuBackground =
            new AssetDescriptor<Texture>("background.png", Texture.class);

    private static final AssetDescriptor<Skin> skin =
            new AssetDescriptor<Skin>("menu/flat-earth-ui.json", Skin.class);

    private static final AssetDescriptor<Music> music =
            new AssetDescriptor<Music>("sound/music/Intro Theme.mp3", Music.class);


    FreeTypeFontLoaderParameter bigButtonFontParameter;
    FreeTypeFontLoaderParameter shopCellFontParameter;
    FreeTypeFontLoaderParameter shopButtonFontParameter;

    public MenuAssets(){
        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        bigButtonFontParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        bigButtonFontParameter.fontFileName = "ttf/BeTrueToYourSchool-Regular.ttf";
        bigButtonFontParameter.fontParameters.size = (int)(MAIN_MENU_BUTTON_HEIGHT*7/8);

        shopButtonFontParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        shopButtonFontParameter.fontFileName = "ttf/BeTrueToYourSchool-Regular.ttf";
        shopButtonFontParameter.fontParameters.size = (int)(SHOP_BUTTON_HEIGHT*7/8);

        shopCellFontParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        shopCellFontParameter.fontFileName = "ttf/BeTrueToYourSchool-Regular.ttf";
        shopCellFontParameter.fontParameters.size = (int)(CELL_FONT_SIZE);

    }
    @Override
    public void load() {
        manager.load(shopAtlas);
        manager.load(spritesAtlas);
        manager.load(mainMenuBackground);
        manager.load(skin);
        manager.load(music);
        manager.load("mainMenuButtonFont.ttf", BitmapFont.class, bigButtonFontParameter);
        manager.load("shopButtonFont.ttf", BitmapFont.class, shopButtonFontParameter);
        manager.load("shopCellFont.ttf", BitmapFont.class, shopCellFontParameter);
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
}
