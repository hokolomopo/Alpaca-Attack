package com.mygdx.alpacaattack.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.alpacaattack.assets.GameAssets;
import com.mygdx.alpacaattack.game.entities.Player;
import com.mygdx.alpacaattack.game.util.TiledMapProcessor;
import com.mygdx.alpacaattack.game.util.UserInterface;
import com.mygdx.alpacaattack.game.util.World;
import com.mygdx.alpacaattack.menu.enums.Levels;

/**
 * Created by Adrien on 02-09-17.
 */

public class GameScreen implements Screen, InputProcessor{
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;

    private Player player;
    public World world;
    public TiledMapProcessor background;
    public UserInterface ui;
    private Music music;

    public static final float PIXEL_TO_METER = 0.5f;
    private static final float PLAYER_INITIAL_LOC = 600 * PIXEL_TO_METER;

    private Game game;
    public GameAssets assets;
    private Levels level;


    public GameScreen(Game g, Levels level, GameAssets a){
        Gdx.input.setInputProcessor(this);

        game = g;
        this.level = level;
        assets = a;

        music = assets.manager.get(level.getMusicPath(), Music.class);
        music.setLooping(true);
        music.setVolume(assets.getMusicVolume());

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        player = new Player(0,PLAYER_INITIAL_LOC, assets);
        camera = new OrthographicCamera(700*PIXEL_TO_METER*(w/h), 700*PIXEL_TO_METER);
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2,0);
        camera.update();


        ui = new UserInterface(player, this);

        world = new World(player, 10 * PIXEL_TO_METER, this);

        background = new TiledMapProcessor(this);

    }

    public void reset(){
        player.reset();
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2,0);
        camera.update();
        world.reset(true);
    }
    @Override
    public void show() {

    }

    public Levels getLevel(){ return level;}

    @Override
    public void render(float delta) {


        if(!music.isPlaying())
            music.play();

        if(player.getY() + player.getHitbox().height < 0)
            player.kill();

        if(player.isDead){
            int score = (int)(player.getScore());
            this.reset();
            music.stop();
            game.setScreen(new LoadingScreen(game, score, this));
        }

        background.render(camera, player);

        world.update();

        batch.setProjectionMatrix(camera.combined);
        world.render(batch, camera);

        ui.render();

        /*shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        world.renderHitboxes(shapeRenderer);
        shapeRenderer.end();*/

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        ui.dispose();
        background.dispose();
        world.dispose();
        assets.dispose();
        batch.dispose();
        shapeRenderer.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(Input.Keys.Z == keycode)
            player.dash();
        if(Input.Keys.SPACE == keycode) {
            player.jump();
            world.reduceGravity();
        }


        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        if(Input.Keys.SPACE == keycode) {
            world.resetGravity();
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float x = screenX;
        float y = (Gdx.graphics.getHeight() - screenY );

        if(x < Gdx.graphics.getWidth()/3 && y < Gdx.graphics.getHeight()/3) {
            player.jump();
            world.reduceGravity();
        }
        else if(x > ((float)Gdx.graphics.getWidth())*(2/3f) && y < Gdx.graphics.getHeight()/3)
            player.dash();
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        world.resetGravity();
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
