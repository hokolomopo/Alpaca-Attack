package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

    public static final int WORLD_HEIGHT = 30;
    public static final int WORLD_WIDHT = 100;

    public static final float PIXEL_TO_METER = 1/35f;

    private float elapsedTime = 0;
    private Game game;

    public GameScreen(Game g){
        game = g;
        Gdx.input.setInputProcessor(this);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        player = new Player(0,16);

        camera = new OrthographicCamera(40, 40 * (h / w));
        camera.position.set(camera.viewportWidth/2 , player.getY() + player.getHitbox().getHeight()/2, 0);
        camera.update();


        ui = new UserInterface(camera);

        world = new World(player, 0.5f);

        /*RectangleMapObject rectO = (RectangleMapObject)plats.get(0);
        Rectangle rect = rectO.getRectangle();
        System.out.println(rect.getX()*PIXEL_TO_METER+ " "+rect.getHeight()*PIXEL_TO_METER+ " "+ rect.getY()*PIXEL_TO_METER);*/



        background = new TiledMapProcessor(world);


    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        background.render(camera, player);

        world.update();
        ui.update(camera);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        world.render(batch);
        ui.render(batch);
        batch.end();


        /*shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        world.renderHitboxes(shapeRenderer);
        shapeRenderer.rect(ui.getDashHitbox().x,ui.getDashHitbox().y,ui.getDashHitbox().width,ui.getDashHitbox().height);
        shapeRenderer.rect(ui.getJumpHitbox().x,ui.getJumpHitbox().y,ui.getJumpHitbox().width,ui.getJumpHitbox().height);
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

        if(x < Gdx.graphics.getWidth()/3 && y < Gdx.graphics.getHeight()/3)
            player.jump();
        else if(x > Gdx.graphics.getWidth()*(2/3) && y < Gdx.graphics.getHeight()/3)
            player.dash();
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
