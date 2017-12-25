package com.mygdx.alpacaattack.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Sort;
import com.mygdx.alpacaattack.assets.GameAssets;
import com.mygdx.alpacaattack.game.entities.Bonus;
import com.mygdx.alpacaattack.game.entities.Enemy;
import com.mygdx.alpacaattack.game.entities.Player;
import com.mygdx.alpacaattack.game.entities.TextEvent;
import com.mygdx.alpacaattack.game.entities.WallEnemy;
import com.mygdx.alpacaattack.menu.enums.Levels;

import java.util.Comparator;

/**
 * Created by Adrien on 31-08-17.
 */

public class World {
    private com.mygdx.alpacaattack.screen.GameScreen gameScreen;
    private GameAssets assets;

    private Vector2 worldSize;
    private Player player;
    private Array<com.mygdx.alpacaattack.game.entities.MovableEntity> movableEntities;
    private Array<Enemy> enemies;
    private Array<com.mygdx.alpacaattack.game.entities.Entity> staticEntities;
    private Array<com.mygdx.alpacaattack.game.util.ScorePopUp> scorePopUps;
    private Array<Explosion> explosions;
    private Array<TextEvent> textEvents;

    private SpriteBatch textBatch;
    private Sound pickup;

    private float g;
    private float normalG;

    private int lastKilled = -2;
    private float travelledDistance = 0;

    //distance that the player has to move before accelerationg
    private static final float DISTANCE_BETWEEN_PLAYER_ACCELERATION = 1000* com.mygdx.alpacaattack.screen.GameScreen.PIXEL_TO_METER;

    /*
    TODO : Reset : Rectangles instead of enemies
     */

    public World(Player p, float gravity, com.mygdx.alpacaattack.screen.GameScreen gmScreen){
        gameScreen = gmScreen;
        assets = gmScreen.assets;
        player = p;
        normalG = 2*gravity;
        g = normalG;
        movableEntities = new Array<com.mygdx.alpacaattack.game.entities.MovableEntity>();
        staticEntities = new Array<com.mygdx.alpacaattack.game.entities.Entity>();
        scorePopUps = new Array<com.mygdx.alpacaattack.game.util.ScorePopUp>();
        enemies = new Array<Enemy>();
        explosions = new Array<Explosion>();
        textEvents = new Array<TextEvent>();
        worldSize = new Vector2(1000,1000);
        pickup = assets.manager.get(GameAssets.pickupPath, Sound.class);

        textBatch = new SpriteBatch();


    }

    public void addTextEvent(TextEvent e){
        textEvents.add(e);
    }

    public void addMovableEntity(com.mygdx.alpacaattack.game.entities.MovableEntity e){
        movableEntities.add(e);
        if(e instanceof Enemy)
            enemies.add((Enemy)e);
    }
    public void addStaticEntity(com.mygdx.alpacaattack.game.entities.Entity e){
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

        for(com.mygdx.alpacaattack.game.entities.MovableEntity e : movableEntities){
            e.update();
        }
        for(int i = 0;i < explosions.size;i++)
            if(explosions.get(i).isDead())
                explosions.removeIndex(i);

        for(int i = 0;i < scorePopUps.size;i++) {
            scorePopUps.get(i).update();
            if(scorePopUps.get(i).isDead){
                scorePopUps.removeIndex(i);
            }

        }
        player.update();

    }

    private void detectCollisions() {
        for(com.mygdx.alpacaattack.game.entities.MovableEntity e : movableEntities){
            if(player.getHitbox().overlaps(e.getHitbox())){
                if(!player.isDashing && e instanceof Enemy) {
                    player.kill();
                    return;
                }
                else {
                    if(e instanceof Enemy){
                        explosions.add(new Explosion(e.getX() + e.getHitbox().width/2, e.getY()+e.getHitbox().height/2, e.getHitbox().getWidth()*1.5f, assets));
                        if(((Enemy) e).number == lastKilled+1)
                            ((Enemy)e).addKillScore();
                        else
                            ((Enemy)e).resetKillScore();
                        lastKilled = ((Enemy)e).number;
                    }
                    else if(e instanceof Bonus)
                        pickup.play(0.2f * assets.getSoundVolume());
                    scorePopUps.add(new com.mygdx.alpacaattack.game.util.ScorePopUp(e.getKillScore(), player.getHitbox().getX() + player.getHitbox().getWidth()
                            , player.getHitbox().getY() + player.getHitbox().getHeight() / 3 * 2, assets));
                    player.addScore(e.getKillScore());
                    e.kill();
                }
            }
        }
        for(TextEvent event : textEvents){
            if(!event.isActivated()){
                if(event.getHitbox().overlaps(player.getHitbox())){
                    gameScreen.ui.activateEvent(event.getString());
                }
            }
        }
    }

    private void applyGravity(com.mygdx.alpacaattack.game.entities.MovableEntity e){
            e.setYSpeed(e.getSpeed().y - g*Gdx.graphics.getDeltaTime()*e.getWeight()) ;
    }

    private void playerMove() {
        if(player.getSpeed().x != 0){
            //Increment player speed
            travelledDistance += player.getSpeed().x;
            if(travelledDistance > DISTANCE_BETWEEN_PLAYER_ACCELERATION && gameScreen.getLevel() != Levels.TUTORIAL){
                player.incrementSpeed();
                travelledDistance = 0;
            }
            player.addScore(player.getSpeed().x);
            player.move(player.getSpeed().x, 0);
            for(com.mygdx.alpacaattack.game.entities.Entity e : staticEntities){
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

            for(com.mygdx.alpacaattack.game.entities.Entity e : staticEntities){
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
        for(com.mygdx.alpacaattack.game.entities.MovableEntity o : movableEntities){
            o.draw(batch);
        }
        for(Explosion e : explosions)
            e.render(batch);
        player.draw(batch);
        for(com.mygdx.alpacaattack.game.util.ScorePopUp s : scorePopUps)
            s.draw(batch);
        batch.end();


        /*textCam.position.set(camera.position.x, camera.position.y,0);
        textCam.viewportHeight = camera.viewportHeight/2;
        textCam.viewportWidth = camera.viewportWidth;
        textCam.update();
        textBatch.setProjectionMatrix(camera.combined);
        textBatch.begin();*/
        //textBatch.end();
    }

    public void renderHitboxes(ShapeRenderer r){
        for(com.mygdx.alpacaattack.game.entities.MovableEntity o : movableEntities){
            o.drawHitbox(r);
        }
        for(com.mygdx.alpacaattack.game.entities.Entity o : staticEntities){
            o.drawHitbox(r);
        }
        player.drawHitbox(r);
    }

    public void reset(boolean quitGame){
        if(quitGame) {
            travelledDistance = 0;
            enemies.get(0).resetKillScore();
            lastKilled = -2;
            if(gameScreen.getLevel() == Levels.TUTORIAL)
                for(TextEvent e : textEvents)
                    e.reset();
        }
        else{
            //Find the closest enemy behind the player
            Enemy tmp = new WallEnemy(new Rectangle(0,0,0,0), assets, gameScreen.getLevel());
            for(Enemy e : enemies){
                if(e.getX() < player.getX() && e.getX() > tmp.getX())
                    tmp = e;
            }
            //If the last killed enemy is the closest behind the player, keep the score combo going
            if(lastKilled == tmp.number)
                lastKilled = -1;
            else
                lastKilled = -2;
        }
        for(com.mygdx.alpacaattack.game.entities.MovableEntity e : movableEntities)
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
        Sort s = new Sort();
        s.sort(enemies, new Comparator<Enemy>() {
            @Override
            public int compare(Enemy a, Enemy b) {
                return (int)(a.getHitbox().getX() - b.getHitbox().getX());
            }
        });
        for(int i = 0;i < enemies.size;i++)
            enemies.get(i).number = i;
    }

    public void dispose(){
        enemies.clear();
        explosions.clear();
        staticEntities.clear();
        scorePopUps.clear();
        movableEntities.clear();
        textBatch.dispose();}
}
