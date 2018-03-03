package com.mygdx.alpacaattack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.alpacaattack.assets.Assets;
import com.mygdx.alpacaattack.assets.MenuAssets;
import com.mygdx.alpacaattack.menu.enums.ShopItem;

public class AlpacaAttack extends Game {

	public MenuAssets assets;

	@Override
	public void create () {
		Preferences prefs = Gdx.app.getPreferences("prefs");
		prefs.putBoolean("own"+ ShopItem.ALPACA_WHITE.getName(), true).flush();
		this.setScreen(new com.mygdx.alpacaattack.screen.LoadingScreen(this));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		super.render();
	}

	@Override
	public void dispose () {
		screen.dispose();
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

