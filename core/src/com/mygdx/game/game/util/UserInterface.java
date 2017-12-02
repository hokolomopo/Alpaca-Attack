package com.mygdx.game.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.assets.GameAssets;
import com.mygdx.game.game.entities.Player;
import com.mygdx.game.menu.enums.Levels;
import com.mygdx.game.screen.GameScreen;


/**
 * Created by Adrien on 01-09-17.
 */

public class UserInterface implements InputProcessor{
    TextureAtlas textureAtlas;

    private Stage stage;
    private BitmapFont font;
    private Label score;
    private Label event;

    private Player player;


    private static final float BUTTON_HEIGHT = Gdx.graphics.getHeight()/5;
    private static final float BUTTON_WIDTH = BUTTON_HEIGHT*16/9f;
    private static final float EVENT_DURATION = 3;

    private float eventTimer = 0;

    public UserInterface(Player p, GameScreen gmSreen){
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        textureAtlas = gmSreen.assets.manager.get(GameAssets.spriteAtlasPath, TextureAtlas.class);
        font = gmSreen.assets.manager.get(GameAssets.uiScoreFont, BitmapFont.class);

        player = p;


        stage = new Stage(new ScreenViewport());
        //skin = new Skin(Gdx.files.internal("UI/arcade-ui.json"));

        if(gmSreen.getLevel() == Levels.TUTORIAL) {
            ImageButton jButton = new ImageButton(new TextureRegionDrawable(textureAtlas.findRegion("jumpButton")));
            jButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
            jButton.setPosition(0, 0);
            stage.addActor(jButton);

            ImageButton dButton = new ImageButton(new TextureRegionDrawable(textureAtlas.findRegion("dashButton")));
            dButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
            dButton.setPosition(Gdx.graphics.getWidth() - BUTTON_WIDTH, 0);
            stage.addActor(dButton);
        }


        score = new Label(Integer.toString((int)player.getScore()), new Label.LabelStyle(font, Color.WHITE));
        score.setWidth(Gdx.graphics.getWidth());
        score.setHeight( Gdx.graphics.getHeight()/10);
        score.setAlignment(Align.center);
        score.setPosition(0,((float)Gdx.graphics.getHeight())*(9/10f));
        stage.addActor(score);

        event = new Label("",new Label.LabelStyle(font, Color.BLACK));
        event.setWidth(Gdx.graphics.getWidth());
        event.setPosition(0, Gdx.graphics.getHeight()/2);
        event.setAlignment(Align.center);
        stage.addActor(event);


    }

    public void activateEvent(String eventText){
        eventTimer = 0;
        event.setText(eventText);
        Color color = event.getColor();
        event.setColor(color.r, color.g, color.b, 0);
        event.clearActions();
        event.addAction(Actions.fadeIn(0.2f));
    }

    public void updateEvent(){
        event.act(Gdx.graphics.getDeltaTime());
        eventTimer += Gdx.graphics.getDeltaTime();
        if(eventTimer > EVENT_DURATION){
            event.clearActions();
            event.addAction(Actions.fadeOut(0.2f));
        }
    }

    public void render(){
        this.updateEvent();
        score.setText(Integer.toString((int)player.getScore()));
        stage.draw();
    }

    public void dispose(){
        stage.dispose();
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
