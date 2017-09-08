package com.mygdx.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Enemy;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.MovableEntity;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.WallEnemy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Adrien on 31-08-17.
 */

public class World {
    private Vector2 worldSize;
    private Player player;
    private ArrayList<MovableEntity> movableEntities;
    private ArrayList<Enemy> enemies;
    private ArrayList<Entity> staticEntities;
    private ArrayList<ScorePopUp> scorePopUps;

    private OrthographicCamera textCam;
    private SpriteBatch textBatch;

    private float g;
    private float normalG;

    private int lastKilled = -1;

    public World(Player p, float gravity){
        player = p;
        normalG = 2*gravity;
        g = normalG;
        movableEntities = new ArrayList<MovableEntity>();
        staticEntities = new ArrayList<Entity>();
        scorePopUps = new ArrayList<ScorePopUp>();
        enemies = new ArrayList<Enemy>();
        worldSize = new Vector2(1000,1000);

        textCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        textBatch = new SpriteBatch();


    }
    public void addMovableEntity(MovableEntity e){
        movableEntities.add(e);
        if(e instanceof Enemy)
            enemies.add((Enemy)e);
    }
    public void addStaticEntity(Entity e){
        staticEntities.add(e);
    }


    public void update(){
        applyGravity(player);

        if(!player.isDying) {
            playerMove();
            detectCollisions();
        }
        else
            player.move();

        for(MovableEntity e : movableEntities){
            e.update();
        }

        for(int i = 0;i < scorePopUps.size();i++) {
            scorePopUps.get(i).update();
            if(scorePopUps.get(i).isDead){
                scorePopUps.remove(i);
            }

        }
        player.update();


        //for(Entity e: staticEntities) System.out.println("Hitbox" + e.getHitbox().getX()+ " "+ e.getHitbox().getHeight() +"  "+ e.getHitbox().getWidth());
    }

    private void detectCollisions() {
        for(MovableEntity e : movableEntities){
            if(player.getHitbox().overlaps(e.getHitbox())){
                if(!player.isDashing && e instanceof Enemy) {
                    player.kill();
                    return;
                }
                else {
                    if(e instanceof Enemy){
                        if(((Enemy) e).number == lastKilled+1)
                            ((Enemy)e).addKillScore();
                        else
                            ((Enemy)e).resetKillScore();
                        lastKilled = ((Enemy)e).number;
                    }
                    scorePopUps.add(new ScorePopUp(e.getKillScore(), player.getHitbox().getX(), player.getHitbox().getY() + player.getHitbox().getHeight() / 3 * 2));
                    player.addScore(e.getKillScore());
                    e.kill();
                }
            }
        }
    }

    private void applyGravity(MovableEntity e){
            e.setYSpeed(e.getSpeed().y - g*Gdx.graphics.getDeltaTime()*e.getWeight()) ;
    }

    private void playerMove() {

        if(player.getSpeed().x != 0){
            player.addScore(player.getSpeed().x);
            player.move(player.getSpeed().x, 0);
            for(Entity e : staticEntities){
                if(e.getHitbox().overlaps(player.getHitbox())){
                    player.kill();
                    return;
                    //Real collisions, not used there because the player dies if he hits something
                    /*if(player.getSpeed().x > 0)
                        player.setPosition(e.getHitbox().getX() - player.getHitbox().width, player.getY());
                    else if(player.getSpeed().x < 0)
                        player.setPosition(e.getX(), player.getY());*/
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
                    else if(player.getSpeed().y < 0){
                        player.setPosition(player.getX(), e.getY() + e.getHitbox().height);
                        player.land();
                    }
                    player.setYSpeed(0);
                }
            }
        }
    }

    public void render(SpriteBatch batch, OrthographicCamera camera){
        batch.begin();
        for(MovableEntity o : movableEntities){
            o.draw(batch);
        }
        player.draw(batch);
        batch.end();

        //textCam.position.set(camera.position.x, camera.position.y,0);
        textBatch.setProjectionMatrix(camera.combined);
        textBatch.begin();
        for(ScorePopUp s : scorePopUps)
            s.draw(textBatch);
        textBatch.end();

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
    public void reset(boolean quitGame){
        if(quitGame) {
            enemies.get(0).resetKillScore();
            lastKilled = -1;
        }
        else{
            //Find the closest enemy behind the player
            Enemy tmp = new WallEnemy(new Rectangle(0,0,0,0));
            for(Enemy e : enemies){
                if(e.getX() < player.getX() && e.getX() > tmp.getX())
                    tmp = e;
            }
            //If the last killed enemy is the closest behind the player, keep the score combo going
            if(lastKilled == tmp.number)
                lastKilled = -1;
            else
                lastKilled = 0;
        }
        for(MovableEntity e : movableEntities)
            e.revive();
    }
    public void setSize(Vector2 s){
        worldSize = s;
    }

    public void reduceGravity(){
        g = normalG*0.5f;
    }

    public void resetGravity(){
        g = normalG;
    }

    public  void sortEnemies(){
        Collections.sort(enemies, new Comparator<Enemy>() {
            @Override
            public int compare(Enemy a, Enemy b) {
                return (int)(a.getHitbox().getX() - b.getHitbox().getX());
            }
        });
        for(int i = 0;i < enemies.size();i++)
            enemies.get(i).number = i;
    }
}
