package com.mygdx.game.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.assets.GameAssets;
import com.mygdx.game.game.entities.Player;


/**
 * Created by Adrien on 01-09-17.
 */

public class UserInterface implements InputProcessor{
    private SpriteBatch batch;
    private OrthographicCamera cam;
    TextureAtlas textureAtlas;

    private Stage stage;
    private BitmapFont font;
    private Label score;

    private Player player;


    private static final float BUTTON_HEIGHT = Gdx.graphics.getHeight()/5;
    private static final float BUTTON_WIDTH = BUTTON_HEIGHT*16/9f;
    private static final float FONT_SCALE = 3;

    public UserInterface(OrthographicCamera camera, Player p, GameAssets a){
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        batch = new SpriteBatch();
        textureAtlas = a.manager.get("sprites.txt", TextureAtlas.class);
        font = a.manager.get("uiScoreFont.ttf", BitmapFont.class);

        player = p;

        cam = new OrthographicCamera();
        cam.position.set(camera.position.x, camera.position.y,0);
        cam.update();


        stage = new Stage(new ScreenViewport(cam));
        //skin = new Skin(Gdx.files.internal("UI/arcade-ui.json"));

        ImageButton jButton = new ImageButton(new TextureRegionDrawable(textureAtlas.findRegion("jumpButton")));
        jButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        jButton.setPosition(0,0);
        stage.addActor(jButton);

        ImageButton dButton = new ImageButton(new TextureRegionDrawable(textureAtlas.findRegion("dashButton")));
        dButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        dButton.setPosition(Gdx.graphics.getWidth()-BUTTON_WIDTH,0);
        stage.addActor(dButton);


        score = new Label(Integer.toString(player.getScore()), new Label.LabelStyle(font, Color.WHITE));
        //score.setWrap(true);
        score.setWidth(Gdx.graphics.getWidth());
        score.setHeight( Gdx.graphics.getHeight()/10);
        score.setAlignment(Align.center);
        score.setPosition(0,((float)Gdx.graphics.getHeight())*(9/10f));
        stage.addActor(score);

    }

    public void render(){
        score.setText(Integer.toString((int)player.getScore()));
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        stage.draw();
        batch.end();
    }

    public void dispose(){
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
