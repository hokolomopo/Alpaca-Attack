package com.mygdx.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.entities.Player;


/**
 * Created by Adrien on 01-09-17.
 */

public class UserInterface implements InputProcessor{
    private SpriteBatch batch;
    private OrthographicCamera cam;
    TextureAtlas textureAtlas;

    private Stage stage;
    private Skin skin;
    private Label score;

    private Player player;
    private World world;

    private static final float BUTTON_HEIGHT = Gdx.graphics.getHeight()/5;
    private static final float BUTTON_WIDTH = BUTTON_HEIGHT*16/9f;
    private static final float FONT_SCALE = 3;

    public UserInterface(OrthographicCamera camera, Player p, World wor){
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        world = wor;

        batch = new SpriteBatch();
        textureAtlas = new TextureAtlas("sprites.txt");

        player = p;

        cam = new OrthographicCamera();
        cam.position.set(camera.position.x, camera.position.y,0);
        cam.update();


        stage = new Stage(new ScreenViewport(cam));
        skin = new Skin(Gdx.files.internal("UI/arcade-ui.json"));

        ImageButton jButton = new ImageButton(new TextureRegionDrawable(textureAtlas.findRegion("jumpButton")));
        jButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        jButton.setPosition(0,0);
        stage.addActor(jButton);

        ImageButton dButton = new ImageButton(new TextureRegionDrawable(textureAtlas.findRegion("dashButton")));
        dButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        dButton.setPosition(Gdx.graphics.getWidth()-BUTTON_WIDTH,0);
        stage.addActor(dButton);


        score = new Label(Integer.toString((int)player.getScore()), skin);
        score.setColor(Color.WHITE);
        //score.setWrap(true);
        score.setWidth(Gdx.graphics.getWidth());
        score.setHeight( Gdx.graphics.getHeight()/10);
        score.setFontScale(FONT_SCALE,FONT_SCALE);
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
