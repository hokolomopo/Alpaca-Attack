package com.mygdx.game.game.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.game.assets.GameAssets;
import com.mygdx.game.game.entities.Bonus;
import com.mygdx.game.game.entities.Platform;
import com.mygdx.game.game.entities.Player;
import com.mygdx.game.game.entities.TextEvent;
import com.mygdx.game.game.entities.WallEnemy;
import com.mygdx.game.menu.enums.Levels;
import com.mygdx.game.screen.GameScreen;

/**
 * Created by Adrien on 31-08-17.
 */

public class TiledMapProcessor {

    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;

    public final float MAP_WIDHT;
    public final float MAP_HEIGHT;

    private World world;
    GameAssets assets;
    GameScreen gameScreen;

    public TiledMapProcessor(GameScreen gmScreen){
        world = gmScreen.world;
        assets =gmScreen.assets;
        gameScreen = gmScreen;

        tiledMap = assets.manager.get(gmScreen.getLevel().getFilePath(), TiledMap.class);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, GameScreen.PIXEL_TO_METER);

        MAP_WIDHT = tiledMap.getProperties().get("width", Integer.class) * tiledMap.getProperties().get("tilewidth", Integer.class) * GameScreen.PIXEL_TO_METER;
        MAP_HEIGHT = tiledMap.getProperties().get("height", Integer.class) * tiledMap.getProperties().get("tileheight", Integer.class) * GameScreen.PIXEL_TO_METER;

        this.loadPlatforms();
        this.loadEnemies();
        this.loadBonuses();

        if(gmScreen.getLevel() == Levels.TUTORIAL)
            this.loadTextEvents();


        world.setSize(new Vector2(MAP_WIDHT, MAP_HEIGHT));

    }

    public void render(OrthographicCamera camera, Player player){

        //Dont move the camera at the very start
        if(player.getX()  >= camera.viewportWidth/4)
            camera.position.set(player.getX() + camera.viewportWidth/4, camera.position.y, 0);
        /*if(player.getX() + player.getHitbox().getWidth()/2 >= camera.viewportWidth/2)
            camera.position.set(player.getX() + player.getHitbox().getWidth()/2, camera.position.y, 0);*/

        //Teleport back when you are at half of the map to give the impression of infinite scrolling
        if(camera.position.x > MAP_WIDHT/2 + camera.viewportWidth/2 ){
            world.reset(false);
            camera.translate(-MAP_WIDHT/2 , 0);
            player.setPosition(player.getX()- MAP_WIDHT/2, player.getY());
        }

        //Make sure that the camera doesn't display anything out-of-map
        if(player.getY() + player.getHitbox().getHeight()/2 + camera.viewportHeight/2 <= MAP_HEIGHT && player.getY() + player.getHitbox().getHeight()/2 > camera.viewportHeight/2){
            camera.position.set(camera.position.x, player.getY()+ player.getHitbox().getHeight()/2, 0);
        }

        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

    private void loadPlatforms(){
        MapObjects plats = tiledMap.getLayers().get("Objects_Platforms").getObjects();
        for(MapObject o : plats) {
            Rectangle rect = ((RectangleMapObject)o).getRectangle();
            rect.set(rect.getX()* GameScreen.PIXEL_TO_METER, rect.getY()* GameScreen.PIXEL_TO_METER,
                    rect.getWidth()* GameScreen.PIXEL_TO_METER, rect.getHeight()* GameScreen.PIXEL_TO_METER);
            world.addStaticEntity(new Platform(rect));
        }
    }

    private void loadEnemies(){
        MapObjects enemies = tiledMap.getLayers().get("Enemies").getObjects();
        for(MapObject o : enemies) {
            Rectangle rect = ((RectangleMapObject)o).getRectangle();
            rect.set(rect.getX()* GameScreen.PIXEL_TO_METER, rect.getY()* GameScreen.PIXEL_TO_METER,
                    rect.getWidth()* GameScreen.PIXEL_TO_METER, rect.getHeight()* GameScreen.PIXEL_TO_METER);
            WallEnemy tmp = new WallEnemy(rect, assets, gameScreen.getLevel() );
            world.addMovableEntity(tmp);
        }
        world.sortEnemies();
    }

    public void loadBonuses(){
        MapObjects bonuses = tiledMap.getLayers().get("Bonuses").getObjects();
        for(MapObject o : bonuses) {
            Rectangle rect = ((RectangleMapObject)o).getRectangle();
            rect.setPosition(rect.getX()* GameScreen.PIXEL_TO_METER, rect.getY()* GameScreen.PIXEL_TO_METER);
            world.addMovableEntity(new Bonus(rect.getX(), rect.getY(), assets));
        }

    }

    public void loadTextEvents(){
        MapObjects textEvents = tiledMap.getLayers().get("TextEvents").getObjects();
        int i = 0;
        for(MapObject o : textEvents) {
            Rectangle rect = ((RectangleMapObject)o).getRectangle();
            rect.setPosition(rect.getX()* GameScreen.PIXEL_TO_METER, rect.getY()* GameScreen.PIXEL_TO_METER);
            String s = assets.manager.get(GameAssets.bundlePath, I18NBundle.class).get("tutorial"+i++);
            world.addTextEvent(new TextEvent(rect.getX(), MAP_HEIGHT, s));
        }

    }

    public void dispose(){
    }

}
