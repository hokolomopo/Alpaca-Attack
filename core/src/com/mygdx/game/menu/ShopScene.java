package com.mygdx.game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.AlpacaAttack;
import com.mygdx.game.screen.MenuScreen;

/**
 * Created by Adrien on 08-09-17.
 */

public class ShopScene extends MenuScene{

    private static final float BUTTON_WIDTH = Gdx.graphics.getWidth()/6;
    private static final float BUTTON_HEIGHT = Gdx.graphics.getHeight()/10;
    private static final float BACK_BUTTON_PADDING = 30;
    private static final float PADDING = 15;

    private static final float CELL_Y_POSITION = Gdx.graphics.getHeight()*0.06f;

    private BitmapFont font = AlpacaAttack.generateFont(Gdx.files.internal("ttf/BeTrueToYourSchool-Regular.ttf"), (int)(BUTTON_HEIGHT - BUTTON_HEIGHT/8));

    private Table table;

    private final TextureRegion background = MenuScreen.atlas.findRegion("shopBackground");

    public ShopScene(MenuScreen mScreen){
        menuScreen = mScreen;

        TextButton back = new TextButton("Back", MenuScene.skin);
        back.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        back.getLabel().setStyle(new Label.LabelStyle(font, Color.WHITE));
        back.setPosition(BACK_BUTTON_PADDING, Gdx.graphics.getHeight() - BUTTON_HEIGHT - BACK_BUTTON_PADDING);
        back.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("yolo");
                menuScreen.setScene(MenuScreen.MAIN_MENU);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        stage.addActor(back);

        table = new Table();
        table.setHeight(ShopCell.CELL_HEIGHT + 2*PADDING);
        table.setWidth(Gdx.graphics.getWidth() *3/4f);
        table.setPosition(Gdx.graphics.getWidth()*1/8f, CELL_Y_POSITION);
        table.align(Align.bottom);
        //stage.addActor(table);

        ScrollPane pane = new ScrollPane(table, MenuScene.skin);
        pane.setHeight(ShopCell.CELL_HEIGHT + 2*PADDING);
        pane.setWidth(Gdx.graphics.getWidth() *3/4f);
        pane.setPosition(Gdx.graphics.getWidth()*1/8f, CELL_Y_POSITION);

        stage.addActor(pane);

        setUpTable();

    }

    private void setUpTable(){
        ShopCell white = new ShopCell("white", 4000);
        table.add(white).pad(PADDING);

        ShopCell red = new ShopCell("red", 5000);
        table.add(red).pad(PADDING);

        ShopCell blue = new ShopCell("blue", 4000);
        table.add(blue).pad(PADDING);

        ShopCell black = new ShopCell("black", 4000);
        table.add(black).pad(PADDING);

        ShopCell pink = new ShopCell("pink", 4000);
        table.add(pink).pad(PADDING);

        ShopCell yellow = new ShopCell("yellow", 4000);
        table.add(yellow).pad(PADDING);

        ShopCell brown = new ShopCell("brown", 4000);
        table.add(brown).pad(PADDING);
    }

    @Override
    public void render(SpriteBatch batch) {
        if(Gdx.input.getInputProcessor() != stage)
            Gdx.input.setInputProcessor(stage);

        stage.act();

        batch.begin();
        batch.draw(background,0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.draw();
    }

}
