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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.menu.ui.ShopCell;
import com.mygdx.game.menu.shop.ShopItem;
import com.mygdx.game.menu.ui.MoneyTextBox;
import com.mygdx.game.screen.MenuScreen;

import java.util.ArrayList;

/**
 * Created by Adrien on 08-09-17.
 */

public class ShopScene extends com.mygdx.game.menu.scenes.MenuScene {

    private Preferences prefs = Gdx.app.getPreferences("prefs");

    /*private static final float BUTTON_WIDTH = Gdx.graphics.getWidth()/6;
    private static final float BUTTON_HEIGHT = Gdx.graphics.getHeight()/10;
    private static final float BACK_BUTTON_PADDING = 30;
    private static final float PADDING = 15;

    private static final float CELL_Y_POSITION = Gdx.graphics.getHeight()*0.06f;*/

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
        table.setHeight(menuScreen.assets.CELL_HEIGHT + 2*menuScreen.assets.SHOP_PADDING);
        table.setWidth(Gdx.graphics.getWidth() *3/4f);
        table.setPosition(Gdx.graphics.getWidth()*1/8f, menuScreen.assets.SHOP_CELL_Y_POSITION);
        table.align(Align.bottom);

        ScrollPane pane = new ScrollPane(table, skin);
        pane.setHeight(menuScreen.assets.CELL_HEIGHT + 2*menuScreen.assets.SHOP_PADDING);
        pane.setWidth(Gdx.graphics.getWidth() *3/4f);
        pane.setPosition(Gdx.graphics.getWidth()*1/8f, menuScreen.assets.SHOP_CELL_Y_POSITION);

        stage.addActor(pane);

        setUpTable();
    }


    private void setUpMoneyBox(){
        moneyCell = new MoneyTextBox(prefs.getInteger("money", 0), menuScreen.assets);
        moneyCell.scale(0.7f);
        moneyCell.setPosition(Gdx.graphics.getWidth() - moneyCell.getTotalWidth() - menuScreen.assets.SHOP_BACK_BUTTON_PADDING,
                Gdx.graphics.getHeight() - moneyCell.getHeight() - menuScreen.assets.SHOP_BACK_BUTTON_PADDING);
        stage.addActor(moneyCell);
    }

    private void createBackButton(){
        TextButton back = new TextButton("Back", skin);
        back.setSize(menuScreen.assets.SHOP_BUTTON_WIDTH, menuScreen.assets.SHOP_BUTTON_HEIGHT);
        back.getLabel().setStyle(new Label.LabelStyle(font, Color.WHITE));
        back.setPosition(menuScreen.assets.SHOP_BACK_BUTTON_PADDING
                , Gdx.graphics.getHeight() - menuScreen.assets.SHOP_BUTTON_HEIGHT - menuScreen.assets.SHOP_BACK_BUTTON_PADDING);
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
        ShopCell white = new ShopCell(ShopItem.ALPACA_WHITE, menuScreen.assets);
        table.add(white).pad(menuScreen.assets.SHOP_PADDING);
        shopCells.add(white);

        ShopCell red = new ShopCell(ShopItem.ALPACA_RED, menuScreen.assets);
        table.add(red).pad(menuScreen.assets.SHOP_PADDING);
        shopCells.add(red);

        ShopCell blue = new ShopCell(ShopItem.ALPACA_BLUE, menuScreen.assets);
        table.add(blue).pad(menuScreen.assets.SHOP_PADDING);
        shopCells.add(blue);

        ShopCell black = new ShopCell(ShopItem.ALPACA_BLACK, menuScreen.assets);
        table.add(black).pad(menuScreen.assets.SHOP_PADDING);
        shopCells.add(black);

        ShopCell pink = new ShopCell(ShopItem.ALPACA_PINK, menuScreen.assets);
        table.add(pink).pad(menuScreen.assets.SHOP_PADDING);
        shopCells.add(pink);

        ShopCell yellow = new ShopCell(ShopItem.ALPACA_YELLOW, menuScreen.assets);
        table.add(yellow).pad(menuScreen.assets.SHOP_PADDING);
        shopCells.add(yellow);

        ShopCell brown = new ShopCell(ShopItem.ALPACA_BROWN, menuScreen.assets);
        table.add(brown).pad(menuScreen.assets.SHOP_PADDING);
        shopCells.add(brown);
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
        moneyCell.dispose();
        super.dispose();
    }

}
