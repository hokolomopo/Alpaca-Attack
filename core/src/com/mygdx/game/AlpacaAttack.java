package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.mygdx.game.menu.shop.ShopItem;
import com.mygdx.game.screen.EndScreen;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.LoadingScreen;
import com.mygdx.game.screen.MenuScreen;

/*
TODO : assetmanager on skin(MEuScene), mainMenuBackground, Game and EndScreen
 */

public class AlpacaAttack extends Game {

	public AssetManager manager = new AssetManager();

	@Override
	public void create () {
		Preferences prefs = Gdx.app.getPreferences("prefs");
		prefs.putBoolean("own"+ ShopItem.ALPACA_WHITE.getName(), true).flush();

		//this.setScreen(new MenuScreen(this));
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
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = size;
		BitmapFont font = generator.generateFont(parameter);
		generator.dispose();

		return font;
	}
}
