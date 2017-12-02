package com.mygdx.game.menu.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.assets.MenuAssets;
import com.mygdx.game.menu.enums.ShopItem;
import com.mygdx.game.menu.ui.BackToMainMenuButton;
import com.mygdx.game.menu.ui.MoneyTextBox;
import com.mygdx.game.menu.ui.ShopCell;
import com.mygdx.game.screen.MenuScreen;

import java.util.ArrayList;
import java.util.EnumSet;

/**
 * Created by Adrien on 08-09-17.
 */

public class ShopScene extends com.mygdx.game.menu.scenes.MenuScene {

    private Preferences prefs = Gdx.app.getPreferences("prefs");

    private static final float BACK_BUTTON_PADDING = Gdx.graphics.getHeight()/36f;

    private static final float MONEYBOX_WIDTH = Gdx.graphics.getWidth()/4;
    private static final float MONEYBOX_HEIGHT = Gdx.graphics.getHeight()/10;

    private static final float PADDING = Gdx.graphics.getHeight()/90f;

    private static final float CELL_Y_POSITION = Gdx.graphics.getHeight()*0.12f;

    private Table table;
    private MoneyTextBox moneyCell;

    private TextureAtlas atlasFrame;

    public ShopScene(MenuScreen mScreen){
        super(mScreen);
        scene = this;

        menuScreen = mScreen;

        atlasFrame = menuScreen.assets.manager.get(MenuAssets.levelSelectAtlasPath, TextureAtlas.class);

        new BackToMainMenuButton(menuScreen);
        new SoundButtonsScene(menuScreen);

        setUpMoneyBox();
        setUpScrollPane();
    }


    //Create a scrollable interface for the shop containing Shopcells
    private void setUpScrollPane(){
        table = new Table();
        /*table.setHeight(ShopCell.CELL_HEIGHT + 2*PADDING);
        table.setWidth(Gdx.graphics.getWidth() *3/4f);
        table.setPosition(Gdx.graphics.getWidth()*1/8f, CELL_Y_POSITION);*/
        table.align(Align.bottom);

        ScrollPane pane = new ScrollPane(table, skin);
        pane.setHeight(ShopCell.CELL_HEIGHT + 2*PADDING);
        pane.setWidth(Gdx.graphics.getWidth() *3/4f);
        pane.setPosition(Gdx.graphics.getWidth()*1/8f, CELL_Y_POSITION);

        Image frame = new Image(atlasFrame.findRegion("woodenFrame"));
        frame.setSize(pane.getWidth() + PADDING*8, pane.getHeight() + PADDING*4);
        frame.setPosition(pane.getX() - PADDING*4, pane.getY() - PADDING*2);
        stage.addActor(frame);


        stage.addActor(pane);

        setUpTable();
    }


    private void setUpMoneyBox(){
        moneyCell = new MoneyTextBox(prefs.getInteger("money", 0), menuScreen.assets);
        moneyCell.setSize(MONEYBOX_WIDTH, MONEYBOX_HEIGHT);
        moneyCell.setPosition(Gdx.graphics.getWidth() - moneyCell.getTotalWidth() - BACK_BUTTON_PADDING,
                Gdx.graphics.getHeight() - moneyCell.getHeight() - BACK_BUTTON_PADDING);
        stage.addActor(moneyCell);
    }

    //Add all the shop elements in the table
    private void setUpTable(){
        ArrayList<ShopItem> items = new ArrayList<ShopItem>(EnumSet.allOf(ShopItem.class));
        for(ShopItem z : items){
            ShopCell a = new ShopCell(z, menuScreen.assets);
            table.add(a).pad(PADDING);
        }

    }

    @Override
    public void render() {
        //Check if we've bought something
        if(prefs.getInteger("money",0) != moneyCell.getAmount()){
            moneyCell.setAmount(prefs.getInteger("money",0));
        }

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose(){
        moneyCell.dispose();
    }

}
