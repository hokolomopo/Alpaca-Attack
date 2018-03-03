package com.mygdx.alpacaattack.game.util;

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

/**
 * Created by Adrien on 31-08-17.
 */

public class TiledMapProcessor {

    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;

    public final float MAP_WIDHT;
    public final float MAP_HEIGHT;

    private World world;
    com.mygdx.alpacaattack.assets.MenuAssets assets;
    com.mygdx.alpacaattack.screen.GameScreen gameScreen;

    public TiledMapProcessor(com.mygdx.alpacaattack.screen.GameScreen gmScreen){
        world = gmScreen.world;
        assets =gmScreen.assets;
        gameScreen = gmScreen;

        tiledMap = assets.manager.get(gmScreen.getLevel().getFilePath(), TiledMap.class);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, com.mygdx.alpacaattack.screen.GameScreen.PIXEL_TO_METER);

        MAP_WIDHT = tiledMap.getProperties().get("width", Integer.class) * tiledMap.getProperties().get("tilewidth", Integer.class) * com.mygdx.alpacaattack.screen.GameScreen.PIXEL_TO_METER;
        MAP_HEIGHT = tiledMap.getProperties().get("height", Integer.class) * tiledMap.getProperties().get("tileheight", Integer.class) * com.mygdx.alpacaattack.screen.GameScreen.PIXEL_TO_METER;

        this.loadPlatforms();
        this.loadEnemies();
        this.loadBonuses();

        if(gmScreen.getLevel() == com.mygdx.alpacaattack.menu.enums.Levels.TUTORIAL)
            this.loadTextEvents();


        world.setSize(new Vector2(MAP_WIDHT, MAP_HEIGHT));

    }

    public void render(OrthographicCamera camera, com.mygdx.alpacaattack.game.entities.Player player){

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
            rect.set(rect.getX()* com.mygdx.alpacaattack.screen.GameScreen.PIXEL_TO_METER, rect.getY()* com.mygdx.alpacaattack.screen.GameScreen.PIXEL_TO_METER,
                    rect.getWidth()* com.mygdx.alpacaattack.screen.GameScreen.PIXEL_TO_METER, rect.getHeight()* com.mygdx.alpacaattack.screen.GameScreen.PIXEL_TO_METER);
            world.addStaticEntity(new com.mygdx.alpacaattack.game.entities.Platform(rect));
        }
    }

    private void loadEnemies(){
        MapObjects enemies = tiledMap.getLayers().get("Enemies").getObjects();
        for(MapObject o : enemies) {
            Rectangle rect = ((RectangleMapObject)o).getRectangle();
            rect.set(rect.getX()* com.mygdx.alpacaattack.screen.GameScreen.PIXEL_TO_METER, rect.getY()* com.mygdx.alpacaattack.screen.GameScreen.PIXEL_TO_METER,
                    rect.getWidth()* com.mygdx.alpacaattack.screen.GameScreen.PIXEL_TO_METER, rect.getHeight()* com.mygdx.alpacaattack.screen.GameScreen.PIXEL_TO_METER);
            com.mygdx.alpacaattack.game.entities.WallEnemy tmp = new com.mygdx.alpacaattack.game.entities.WallEnemy(rect, assets, gameScreen.getLevel() );
            world.addMovableEntity(tmp);
        }
        world.sortEnemies();
    }

    public void loadBonuses(){
        MapObjects bonuses = tiledMap.getLayers().get("Bonuses").getObjects();
        for(MapObject o : bonuses) {
            Rectangle rect = ((RectangleMapObject)o).getRectangle();
            rect.setPosition(rect.getX()* com.mygdx.alpacaattack.screen.GameScreen.PIXEL_TO_METER, rect.getY()* com.mygdx.alpacaattack.screen.GameScreen.PIXEL_TO_METER);
            world.addMovableEntity(new com.mygdx.alpacaattack.game.entities.Bonus(rect.getX(), rect.getY(), assets));
        }

    }

    public void loadTextEvents(){
        MapObjects textEvents = tiledMap.getLayers().get("TextEvents").getObjects();
        int i = 0;
        for(MapObject o : textEvents) {
            Rectangle rect = ((RectangleMapObject)o).getRectangle();
            rect.setPosition(rect.getX()* com.mygdx.alpacaattack.screen.GameScreen.PIXEL_TO_METER, rect.getY()* com.mygdx.alpacaattack.screen.GameScreen.PIXEL_TO_METER);
            String s = assets.manager.get(com.mygdx.alpacaattack.assets.MenuAssets.bundlePath, I18NBundle.class).get("tutorial"+i++);
            world.addTextEvent(new com.mygdx.alpacaattack.game.entities.TextEvent(rect.getX(), MAP_HEIGHT, s));
        }

    }

    public void dispose(){
    }

}
