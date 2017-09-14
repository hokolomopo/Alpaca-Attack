package com.mygdx.game.menu.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.game.assets.MenuAssets;
import com.mygdx.game.menu.enums.Levels;
import com.mygdx.game.screen.MenuScreen;

/**
 * Created by Adrien on 14-09-17.
 */

public class LevelCell extends Actor {

    public static final float CELL_WIDTH = Gdx.graphics.getWidth()/3.5f;
    public static final float CELL_HEIGHT = Gdx.graphics.getHeight()*0.65f;
    private static final float MARGIN = CELL_WIDTH /20f;
    public static final float BUTTON_WIDTH = CELL_WIDTH - 8*MARGIN;
    public static final float BUTTON_HEIGHT = CELL_HEIGHT /10f;
    public final static float TITLE_FONT_SIZE = Gdx.graphics.getHeight()/18f;


    private Skin skin;
    private I18NBundle bundle;

    private Label levelTitle;
    private Image cardImage;
    private TextButton button;

    private MenuScreen menuScreen;

    private Levels level;
    private boolean owned;

    public LevelCell(Levels argLevel, MenuScreen mScreen){
        menuScreen = mScreen;
        level = argLevel;

        this.setSize(CELL_WIDTH, CELL_HEIGHT);

        skin = mScreen.assets.manager.get(MenuAssets.menuSkinPath, Skin.class);
        bundle = mScreen.assets.manager.get(MenuAssets.bundlePath, I18NBundle.class);

        cardImage = new Image(level.getTextureRegion(mScreen.assets));

        cardImage.setSize(CELL_WIDTH, CELL_HEIGHT);

        owned = Gdx.app.getPreferences("prefs").getBoolean("own"+level.getName(), false);


        this.setUpButton();
        this.setUpLabel();
    }

    private void setUpLabel(){

        BitmapFont font = menuScreen.assets.manager.get(MenuAssets.levelCellTitleFont, BitmapFont.class);

        levelTitle = new Label(level.getName(), new Label.LabelStyle(font, Color.BLACK));
        levelTitle.setSize(CELL_WIDTH, CELL_HEIGHT*0.3f);
        levelTitle.setAlignment(Align.center);

    }

    private void setUpButton(){
        String buttonString;
        BitmapFont font = menuScreen.assets.manager.get(MenuAssets.levelCellButtonFont, BitmapFont.class);

        if(owned)
            buttonString = bundle.get("use");
        else
            buttonString = bundle.get("buy");


        button = new TextButton(buttonString, skin);
        button.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        button.getLabel().setStyle(new Label.LabelStyle(font , Color.WHITE));

        //Add a empty listener jut to have the button animation
        button.addListener(new InputListener());
        this.addListener(button.getClickListener());
    }

    //Detect hit only on the button
    @Override
    public Actor hit(float x, float y, boolean touchable){
        if (touchable && getTouchable() != Touchable.enabled) return null;
        return x >= MARGIN*4 && x < BUTTON_WIDTH + MARGIN*4 && y >= MARGIN && y < BUTTON_HEIGHT + MARGIN ? this : null;
    }
    @Override
    public void setPosition(float x, float y){
        super.setPosition(x,y);
        cardImage.setPosition(x, y);
        levelTitle.setPosition(x ,y + CELL_HEIGHT - levelTitle.getHeight());
        button.setPosition(x + 4*MARGIN, y + MARGIN);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        this.setPosition(this.getX(), this.getY());

        cardImage.draw(batch, parentAlpha);
        levelTitle.draw(batch, parentAlpha);
        button.draw(batch, parentAlpha);
    }


}
