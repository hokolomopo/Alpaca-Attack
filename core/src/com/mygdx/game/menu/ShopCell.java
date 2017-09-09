package com.mygdx.game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.AlpacaAttack;
import com.mygdx.game.screen.MenuScreen;
import com.sun.nio.sctp.PeerAddressChangeNotification;

import javax.swing.GroupLayout;

/**
 * Created by Adrien on 08-09-17.
 */

public class ShopCell extends Actor {
    private static final TextureRegion card = MenuScreen.atlas.findRegion("cell");
    private static final SpriteBatch b = new SpriteBatch();

    private TextureRegion alpaca;
    private TextureRegion frame = MenuScreen.atlas.findRegion("frame");
    private TextButton button;
    private Label labelPrice;
    private Label title;

    public final static float CELL_HEIGHT = 0.65f*Gdx.graphics.getHeight();
    public final static float CELL_WIDTH = 0.15f*Gdx.graphics.getWidth();

    private final static float OBJECT_CELL_RATIO = 0.4f;
    private final static float MARGIN = CELL_WIDTH*0.05f;
    private final static float BUTTON_HEIGHT = 0.1f*CELL_HEIGHT;
    private final static float BUTTON_WIDTH = CELL_WIDTH -2*MARGIN;
    private final static float TITLE_SIZE = 60;

    private static final BitmapFont title_font = AlpacaAttack.generateFont(Gdx.files.internal("ttf/BeTrueToYourSchool-Regular.ttf"), (int)(TITLE_SIZE));
    private BitmapFont button_font = AlpacaAttack.generateFont(Gdx.files.internal("ttf/BeTrueToYourSchool-Regular.ttf"), (int)(BUTTON_HEIGHT - BUTTON_HEIGHT/8));

    private final float alpacaHeight;
    private final float alpacaWidth;
    private final int price;

    public ShopCell(String color, int x){
        alpaca = MenuScreen.atlas.findRegion("run2_"+color);

        //Size = clickable size, so we take the size of the button
        this.setWidth(BUTTON_WIDTH + 2* MARGIN);
        this.setHeight(BUTTON_HEIGHT + 2* MARGIN);

        alpacaHeight = CELL_HEIGHT*OBJECT_CELL_RATIO;
        alpacaWidth = alpacaHeight *((float)alpaca.getRegionWidth()/(float)alpaca.getRegionHeight());

        price = x;
        button = new TextButton("Buy", MenuScene.skin);
        button.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.getLabel().setStyle(new Label.LabelStyle(button_font, Color.WHITE));
        button.setPosition(this.getX(), this.getY());

        //Add a empty listener jut to have the button animation
        button.addListener(new InputListener());
        this.addListener(button.getClickListener());

        labelPrice = new Label(Integer.toString(price)+" G", new Label.LabelStyle(button_font, Color.BLACK));
        labelPrice.setSize(CELL_WIDTH - 2*MARGIN, TITLE_SIZE);
        labelPrice.setAlignment(Align.center);

        this.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("wolo");
                return false;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });

        title = new Label("Alpaca\n"+color, new Label.LabelStyle(title_font, getColor(color)));
        title.setSize(CELL_WIDTH - 2*MARGIN, 0);
        title.setAlignment(Align.center);
        title.setPosition(this.getX() + MARGIN, this.getY() +CELL_HEIGHT - TITLE_SIZE*2 -200);
    }

    public TextButton getButton(){
        return button;
    }

    public Color getColor(String color){
        if(color.equals("red"))
            return Color.RED;
        else if(color.equals("blue"))
            return Color.BLUE;
        else if(color.equals("yellow"))
            return Color.YELLOW;
        else if(color.equals("black"))
            return Color.BLACK;
        else if(color.equals("brown"))
            return Color.BROWN;
        else if(color.equals("pink"))
            return Color.PINK;

        return Color.WHITE;

    }
    @Override
    public void setPosition(float x, float y){
        super.setPosition(x,y);
        title.setPosition(super.getX() + x + MARGIN,super.getY() + y + CELL_HEIGHT - TITLE_SIZE*2 -200);
        button.setPosition(super.getX() + x + MARGIN, super.getY() + y + MARGIN);
        labelPrice.setPosition(super.getX() + x + MARGIN, super.getY() + y + 2* MARGIN + BUTTON_HEIGHT);
    }




    @Override
    public void draw(Batch batch, float parentAlpha) {
        title.setPosition(this.getX() + MARGIN, this.getY() + CELL_HEIGHT - CELL_HEIGHT/10);
        button.setPosition(this.getX() + MARGIN, this.getY() + MARGIN);
        labelPrice.setPosition(this.getX() + MARGIN, this.getY() + 2* MARGIN + BUTTON_HEIGHT);

        batch.end();

        b.setTransformMatrix(batch.getTransformMatrix());
        b.begin();
        b.draw(card, this.getX(), this.getY(), CELL_WIDTH, CELL_HEIGHT);
        b.draw(frame, this.getX() + MARGIN, this.getY() + CELL_HEIGHT*0.28f, CELL_WIDTH - 2*MARGIN, alpacaHeight + CELL_HEIGHT*0.12f);
        b.draw(alpaca, this.getX() + CELL_WIDTH/2 - alpacaWidth/2, this.getY() + CELL_HEIGHT*0.35f, alpacaWidth, alpacaHeight);
        b.end();
        batch.begin();
        //super.draw(batch, parentAlpha);
        button.draw(batch, parentAlpha);
        title.draw(batch, parentAlpha);
        labelPrice.draw(batch, parentAlpha);

    }


}
