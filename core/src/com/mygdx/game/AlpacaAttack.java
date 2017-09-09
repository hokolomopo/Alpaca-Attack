package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.mygdx.game.screen.EndScreen;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.MenuScreen;

public class AlpacaAttack extends Game {

	@Override
	public void create () {
		//this.setScreen(new MenuScreen(this));
		this.setScreen(new EndScreen(this, new GameScreen(this), 1000));
	}

	@Override
	public void render () {
		//Gdx.gl.glClearColor(0.63137f, 00.94901f, 0.92549f, 1);
		Gdx.gl.glClearColor(1,1,1,1);

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
