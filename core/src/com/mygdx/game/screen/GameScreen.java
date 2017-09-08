package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.*;
import com.mygdx.game.screen.MenuScreen;
import com.mygdx.game.game.TiledMapProcessor;
import com.mygdx.game.game.UserInterface;
import com.mygdx.game.game.World;
import com.mygdx.game.entities.Player;

/**
 * Created by Adrien on 02-09-17.
 */

public class GameScreen implements Screen, InputProcessor{
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;

    private Player player;
    private World world;
    private TiledMapProcessor background;
    private UserInterface ui;

    public static final float PIXEL_TO_METER = 0.5f;
    private static final float PLAYER_INITIAL_LOC = 600 * PIXEL_TO_METER;

    private float elapsedTime = 0;
    private Game game;
    private MenuScreen menuScreen;

    public GameScreen(Game g, MenuScreen mScreen){
        game = g;
        menuScreen = mScreen;

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        player = new Player(0,PLAYER_INITIAL_LOC);
        camera = new OrthographicCamera(700*PIXEL_TO_METER*(w/h), 700*PIXEL_TO_METER);
        //camera.position.set(camera.viewportWidth/2 , player.getY() + player.getHitbox().getHeight()/2, 0);
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2,0);
        camera.update();


        ui = new UserInterface(camera, player, world);

        world = new World(player, 10 * PIXEL_TO_METER);

        /*RectangleMapObject rectO = (RectangleMapObject)plats.get(0);
        Rectangle rect = rectO.getRectangle();
        System.out.println(rect.getX()*PIXEL_TO_METER+ " "+rect.getHeight()*PIXEL_TO_METER+ " "+ rect.getY()*PIXEL_TO_METER);*/



        background = new TiledMapProcessor(world);

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

    @Override
    public void render(float delta) {

        if(player.getY() + player.getHitbox().height < 0)
            player.kill();

        if(player.isDead){
            this.reset();
            game.setScreen(menuScreen);
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
