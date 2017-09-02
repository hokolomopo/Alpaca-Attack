package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Enemy;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.MovableEntity;
import com.mygdx.game.entities.Player;

import java.util.ArrayList;

/**
 * Created by Adrien on 31-08-17.
 */

public class World {
    private Vector2 worldSize;
    private Player player;
    private ArrayList<MovableEntity> movableEntities;
    private ArrayList<Entity> staticEntities;

    private float g;

    public World(Player p, float gravity){
        player = p;
        g = gravity;
        movableEntities = new ArrayList<MovableEntity>();
        staticEntities = new ArrayList<Entity>();
        worldSize = new Vector2(1000,1000);

    }
    public void addMovableEntity(MovableEntity e){
        movableEntities.add(e);
    }
    public void addStaticEntity(Entity e){
        staticEntities.add(e);
    }


    public void update(){
        applyGravity(player);

        playerMove();

        for(MovableEntity e : movableEntities){
            e.update();
        }
        player.update();

        detectCollisions();
        //for(Entity e: staticEntities) System.out.println("Hitbox" + e.getHitbox().getX()+ " "+ e.getHitbox().getHeight() +"  "+ e.getHitbox().getWidth());
    }

    private void detectCollisions() {
        for(MovableEntity e : movableEntities){
            if(player.getHitbox().overlaps(e.getHitbox())){
                e.kill();
            }
        }
    }

    private void applyGravity(MovableEntity e){
            e.setYSpeed(e.getSpeed().y - g*Gdx.graphics.getDeltaTime()*e.getWeight()) ;
    }

    private void playerMove() {

        if(player.getSpeed().x != 0){
            player.move(player.getSpeed().x, 0);
            for(Entity e : staticEntities){
                if(e.getHitbox().overlaps(player.getHitbox())){
                    if(player.getSpeed().x > 0)
                        player.setPosition(e.getHitbox().getX() - player.getHitbox().width, player.getY());
                    else
                        player.setPosition(e.getX(), player.getY());
                    //player.setXSpeed(0);
                }
            }
        }

        if(player.getSpeed().y != 0){
            player.move(0, player.getSpeed().y);

            //Prevent the player to go beyond the top of the screen
            if(player.getY() + player.getHitbox().height > worldSize.y) {
                player.setPosition(player.getX(), worldSize.y - player.getHitbox().getHeight());
                player.setYSpeed(0);
            }

            for(Entity e : staticEntities){
                if(e.getHitbox().overlaps(player.getHitbox())){
                    if(player.getSpeed().y > 0)
                        player.setPosition(player.getX(), e.getY() - player.getHitbox().getHeight());
                    else {
                        player.setPosition(player.getX(), e.getY() + e.getHitbox().height);
                        player.land();
                    }
                    player.setYSpeed(0);
                }
            }
        }
    }

    public void render(SpriteBatch batch){
        for(MovableEntity o : movableEntities){
            o.draw(batch);
        }
        player.draw(batch);
    }

    public void renderHitboxes(ShapeRenderer r){
        for(MovableEntity o : movableEntities){
            o.drawHitbox(r);
        }
        for(Entity o : staticEntities){
            o.drawHitbox(r);
        }
        player.drawHitbox(r);
    }
    public void reset(){
        for(MovableEntity e : movableEntities)
            e.revive();
    }
    public void setSize(Vector2 s){
        worldSize = s;
    }


}
