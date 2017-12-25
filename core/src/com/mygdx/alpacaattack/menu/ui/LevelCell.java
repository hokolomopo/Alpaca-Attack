package com.mygdx.alpacaattack.menu.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.alpacaattack.menu.enums.Levels;

/**
 * Created by Adrien on 14-09-17.
 */

public class LevelCell extends Actor {

    public static final float CELL_WIDTH = Gdx.graphics.getWidth()/3.5f;
    public static final float CELL_HEIGHT = Gdx.graphics.getHeight()*0.65f;

    private static final float IMAGE_HEIGHT = CELL_HEIGHT*0.4f;
    private static final float IMAGE_WIDTH = CELL_WIDTH*0.95f;
    private static final float IMAGE_Y = CELL_HEIGHT *0.4f;

    private static final float MARGIN = CELL_WIDTH /20f;
    public static final float BUTTON_WIDTH = CELL_WIDTH - 8*MARGIN;
    public static final float BUTTON_HEIGHT = CELL_HEIGHT /10f;
    private static final float INFOLABEL_Y = CELL_HEIGHT*0.17f;

    private Skin skin;
    private I18NBundle bundle;
    Preferences prefs = Gdx.app.getPreferences("prefs");

    private Label levelTitle;
    private Label infoLabel;
    private Image cardImage;
    private TextButton button;

    private com.mygdx.alpacaattack.screen.MenuScreen menuScreen;

    private Levels level;
    private boolean owned;

    public LevelCell(Levels argLevel, com.mygdx.alpacaattack.screen.MenuScreen mScreen){
        menuScreen = mScreen;
        level = argLevel;

        this.setSize(CELL_WIDTH, CELL_HEIGHT);

        skin = mScreen.assets.manager.get(com.mygdx.alpacaattack.assets.MenuAssets.menuSkinPath, Skin.class);
        bundle = mScreen.assets.manager.get(com.mygdx.alpacaattack.assets.MenuAssets.bundlePath, I18NBundle.class);

        cardImage = new Image(level.getTextureRegion(mScreen.assets));

        cardImage.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);

        owned = prefs.getBoolean("own"+level.getName(), false);
        //Verify that you don't meet the condition to unlock it
        if(!owned)
            if (prefs.getInteger("highScore" + level.unlockedBy().getName(), 0) >= level.pointsToUnlock())
                owned = true;

        if(owned)
            this.setUpButton();

        this.setUpTitle();
        this.setUpListener();
        this.setUpInfoLabel();
    }

    private void setUpListener(){
        this.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(owned) {
                    menuScreen.setSelectedLevel(level);
                    menuScreen.setScene(com.mygdx.alpacaattack.screen.MenuScreen.GAME);
                }
            }
        });
    }


    private void setUpTitle(){

        BitmapFont font = menuScreen.assets.manager.get(com.mygdx.alpacaattack.assets.MenuAssets.levelCellTitleFont, BitmapFont.class);

        levelTitle = new Label(bundle.get(level.getName()), new Label.LabelStyle(font, Color.BLACK));
        levelTitle.setSize(CELL_WIDTH, CELL_HEIGHT*0.2f);
        levelTitle.setAlignment(Align.center);

    }

    private void setUpInfoLabel(){
        BitmapFont font = menuScreen.assets.manager.get(com.mygdx.alpacaattack.assets.MenuAssets.levelCellButtonFont, BitmapFont.class);

        String labelString;
        if(owned)
            labelString = "Highscore:\n"+prefs.getInteger("highScore"+level.getName())+
                    "\n"+(int)level.getGoldToPointRatio()+" points = 1$";
        else
            labelString = bundle.get("condition")+level.pointsToUnlock()+" points\n"+
                    bundle.get("in")+" "+bundle.get(level.unlockedBy().getName());
            //labelString = bundle.get("locked")+"\n"+level.getPrice()+"G";
        infoLabel = new Label(labelString, new Label.LabelStyle(font, Color.BLACK));
        infoLabel.setWidth(CELL_WIDTH);
        infoLabel.setAlignment(Align.center);
    }

    private void setUpButton(){

        BitmapFont font = menuScreen.assets.manager.get(com.mygdx.alpacaattack.assets.MenuAssets.levelCellButtonFont, BitmapFont.class);

        button = new TextButton(bundle.get("play"), skin);
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
        cardImage.setPosition(x + (CELL_WIDTH - IMAGE_WIDTH)/2, y + IMAGE_Y);
        levelTitle.setPosition(x ,y + CELL_HEIGHT - levelTitle.getHeight());
        infoLabel.setPosition(x, y + INFOLABEL_Y);

        if(button != null)
            button.setPosition(x + 4*MARGIN, y + MARGIN);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        this.setPosition(this.getX(), this.getY());

        cardImage.draw(batch, parentAlpha);
        levelTitle.draw(batch, parentAlpha);
        infoLabel.draw(batch, parentAlpha);

        if(button != null)
            button.draw(batch, parentAlpha);
    }


}
