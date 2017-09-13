package com.mygdx.game.menu.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.menu.ui.ShopCell;
import com.mygdx.game.menu.shop.ShopItem;
import com.mygdx.game.menu.ui.MoneyTextBox;
import com.mygdx.game.screen.MenuScreen;

import java.util.ArrayList;
import java.util.EnumSet;

/**
 * Created by Adrien on 08-09-17.
 */

public class ShopScene extends com.mygdx.game.menu.scenes.MenuScene {

    private Preferences prefs = Gdx.app.getPreferences("prefs");

    public static final float BUTTON_WIDTH = Gdx.graphics.getWidth()/6;
    public static final float BUTTON_HEIGHT = Gdx.graphics.getHeight()/10;

    private static final float MONEYBOX_WIDTH = Gdx.graphics.getWidth()/4;
    private static final float MONEYBOX_HEIGHT = Gdx.graphics.getHeight()/10;

    private static final float BACK_BUTTON_PADDING = Gdx.graphics.getHeight()/36f;
    private static final float PADDING = Gdx.graphics.getHeight()/90f;

    private static final float CELL_Y_POSITION = Gdx.graphics.getHeight()*0.06f;

    //private BitmapFont font = AlpacaAttack.generateFont(Gdx.files.internal("ttf/BeTrueToYourSchool-Regular.ttf"), (int)(BUTTON_HEIGHT - BUTTON_HEIGHT/8));
    private ArrayList<ShopCell> shopCells = new ArrayList<ShopCell>();//Used to dispose of the shopcells

    private Table table;
    private MoneyTextBox moneyCell;

    private BitmapFont font;
    private TextureAtlas atlas;
    private TextureRegion background;

    public ShopScene(MenuScreen mScreen){
        super(mScreen.assets);
        scene = this;

        menuScreen = mScreen;

        font = menuScreen.assets.manager.get("shopButtonFont.ttf", BitmapFont.class);
        atlas = menuScreen.assets.manager.get("menu/menu.txt", TextureAtlas.class);
        background = atlas.findRegion("shopBackground");

        createBackButton();

        setUpMoneyBox();
        setUpScrollPane();
    }


    //Create a scrollable interface for the shop containing Shopcells
    private void setUpScrollPane(){
        table = new Table();
        table.setHeight(ShopCell.CELL_HEIGHT + 2*PADDING);
        table.setWidth(Gdx.graphics.getWidth() *3/4f);
        table.setPosition(Gdx.graphics.getWidth()*1/8f, CELL_Y_POSITION);
        table.align(Align.bottom);

        ScrollPane pane = new ScrollPane(table, skin);
        pane.setHeight(ShopCell.CELL_HEIGHT + 2*PADDING);
        pane.setWidth(Gdx.graphics.getWidth() *3/4f);
        pane.setPosition(Gdx.graphics.getWidth()*1/8f, CELL_Y_POSITION);

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

    private void createBackButton(){
        TextButton back = new TextButton("Back", skin);
        back.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        back.getLabel().setStyle(new Label.LabelStyle(font, Color.WHITE));
        back.setPosition(BACK_BUTTON_PADDING
                , Gdx.graphics.getHeight() - BUTTON_HEIGHT - BACK_BUTTON_PADDING);
        back.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                menuScreen.setScene(scene, MenuScreen.MAIN_MENU);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        stage.addActor(back);

    }


    //Add all the shop elements in the table
    private void setUpTable(){
        ArrayList<ShopItem> items = new ArrayList<ShopItem>(EnumSet.allOf(ShopItem.class));
        for(ShopItem z : items){
            ShopCell a = new ShopCell(z, menuScreen.assets);
            table.add(a).pad(PADDING);
            shopCells.add(a);
        }

    }

    @Override
    public void render(SpriteBatch batch) {
        //Check if we've bought something
        if(prefs.getInteger("money",0) != moneyCell.getAmount()){
            moneyCell.setAmount(prefs.getInteger("money",0));
        }

        stage.act();

        batch.begin();
        batch.draw(background,0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.draw();
    }

    @Override
    public void dispose(){
        for(ShopCell cell : shopCells)
            cell.dispose();
        shopCells.clear();
        moneyCell.dispose();
        super.dispose();
    }

}
