package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.menu.shop.ShopItem;
import com.mygdx.game.screen.LoadingScreen;

import java.util.Locale;

public class AlpacaAttack extends Game {

	public AssetManager manager = new AssetManager();

	@Override
	public void create () {
		Preferences prefs = Gdx.app.getPreferences("prefs");
		prefs.putBoolean("own"+ ShopItem.ALPACA_WHITE.getName(), true).flush();prefs.putInteger("money", 99999).flush();
		this.setScreen(new LoadingScreen(this, LoadingScreen.MENU_SCREEN));
	}

	@Override
	public void render () {
		//Gdx.gl.glClearColor(0.63137f, 00.94901f, 0.92549f, 1);
		Gdx.gl.glClearColor(0,0,0,1);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		super.render();
	}

	@Override
	public void dispose () {
	}

	public static BitmapFont generateFont(FileHandle file , int size){
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(file);
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = size;
		BitmapFont font = generator.generateFont(parameter);
		generator.dispose();

		return font;
	}
}

