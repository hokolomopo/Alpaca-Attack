package com.mygdx.game.menu.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.AlpacaAttack;
import com.mygdx.game.assets.MenuAssets;
import com.mygdx.game.menu.scenes.MenuScene;
import com.mygdx.game.menu.shop.ShopItem;
import com.mygdx.game.screen.MenuScreen;

/**
 * Created by Adrien on 08-09-17.
 */

public class ShopCell extends Actor {
    private final TextureRegion card;// = MenuScreen.atlas.findRegion("cell");
    private SpriteBatch b = new SpriteBatch();
    private static final Preferences prefs = Gdx.app.getPreferences("prefs");

    private final ShopItem item;

    private Skin skin;
    private TextureRegion itemTexture;
    private TextureRegion frame;// = MenuScreen.atlas.findRegion("frame");
    private TextButton button;
    private Label labelPrice;
    private Label title;

    public final static float CELL_HEIGHT = 0.65f*Gdx.graphics.getHeight();
    public final static float CELL_WIDTH = 0.15f*Gdx.graphics.getWidth();

    private final static float OBJECT_CELL_RATIO = 0.4f;
    private final static float MARGIN = CELL_WIDTH*0.05f;
    private final static float BUTTON_HEIGHT = 0.1f*CELL_HEIGHT;
    private final static float BUTTON_WIDTH = CELL_WIDTH -2*MARGIN;
    private final static float TITLE_FONT_SIZE = 60;

    //private static final BitmapFont title_font = AlpacaAttack.generateFont(Gdx.files.internal("ttf/BeTrueToYourSchool-Regular.ttf"), (int)(TITLE_FONT_SIZE));
    //private BitmapFont button_font = AlpacaAttack.generateFont(Gdx.files.internal("ttf/BeTrueToYourSchool-Regular.ttf"), (int)(BUTTON_HEIGHT - BUTTON_HEIGHT/8));
    private BitmapFont font;

    private final float alpacaHeight;
    private final float alpacaWidth;

    private boolean equipped = false;
    private boolean owned = false;

    private MenuAssets assets;

    public ShopCell(ShopItem i, MenuAssets a){
        item = i;
        assets = a;

        skin = assets.manager.get("menu/flat-earth-ui.json", Skin.class);
        font = assets.manager.get("shopCellFont.ttf", BitmapFont.class);
        frame = assets.manager.get("menu/menu.txt", TextureAtlas.class).findRegion("frame");
        card = assets.manager.get("menu/menu.txt", TextureAtlas.class).findRegion("cell");

        itemTexture = item.getTextureRegion(assets);

        //Size = clickable size, so we take the size of the button
        this.setWidth(BUTTON_WIDTH + 2* MARGIN);
        this.setHeight(BUTTON_HEIGHT + 2* MARGIN);

        alpacaHeight = CELL_HEIGHT*OBJECT_CELL_RATIO;
        alpacaWidth = alpacaHeight *((float)itemTexture.getRegionWidth()/(float)itemTexture.getRegionHeight());

        owned = prefs.getBoolean("own"+item.getName(), false);

        String equippedSkin = prefs.getString("equippedSkin", ShopItem.ALPACA_WHITE.getName());
        equipped = (equippedSkin.equals(item.getName()));

        setUpBuyButton();
        setUpPriceLabel();
        setUpCellTitle();

        this.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //If you don't own it
                if(!prefs.getBoolean("own"+item.getName(), false)){
                    int money = prefs.getInteger("money", 0);
                    if(money >= item.getPrice()){
                        //Buy it
                        prefs.putBoolean("own" + item.getName(), true).flush();
                        //Equip it
                        prefs.putString("equippedSkin", item.getName()).flush();
                        owned = equipped = true;
                        money -= item.getPrice();
                        prefs.putInteger("money", money).flush();
                        setUpBuyButton();
                        setUpPriceLabel();
                    }
                }
                //If you own it
                else{
                    //Equip it
                    prefs.putString("equippedSkin", item.getName()).flush();
                    equipped = true;
                    setUpBuyButton();
                }
            }
        });
    }

    public TextButton getButton(){
        return button;
    }

    private void setUpCellTitle(){
        title = new Label(item.getName(), new Label.LabelStyle(font, item.getTextColor()));
        title.setSize(CELL_WIDTH - 2*MARGIN, 0);
        title.setAlignment(Align.center);
        title.setPosition(this.getX() + MARGIN, this.getY() +CELL_HEIGHT - TITLE_FONT_SIZE*2 -200);
    }

    private void setUpBuyButton(){
        String buttonString;
        String buttonStyle;

        if(owned)
            buttonString = "Use";
        else
            buttonString = "Buy";

        if(equipped)
            buttonStyle = "pushed";
        else
            buttonStyle = "default";

        button = new TextButton(buttonString, skin, buttonStyle);
        button.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.getLabel().setStyle(new Label.LabelStyle(font, Color.WHITE));
        button.setPosition(this.getX(), this.getY());

        //Add a empty listener jut to have the button animation
        button.addListener(new InputListener());
        this.addListener(button.getClickListener());

    }

    private void setUpPriceLabel(){

        String labelString;
        if(owned)
            labelString = "Owned";
        else
            labelString = item.getPrice()+"G";

        labelPrice = new Label(labelString, new Label.LabelStyle(font, Color.BLACK));
        labelPrice.setSize(CELL_WIDTH - 2*MARGIN, TITLE_FONT_SIZE);
        labelPrice.setAlignment(Align.center);
    }

    @Override
    public void setPosition(float x, float y){
        super.setPosition(x,y);
        title.setPosition(super.getX() + x + MARGIN,super.getY() + y + CELL_HEIGHT - TITLE_FONT_SIZE*2 -200);
        button.setPosition(super.getX() + x + MARGIN, super.getY() + y + MARGIN);
        labelPrice.setPosition(super.getX() + x + MARGIN, super.getY() + y + 2* MARGIN + BUTTON_HEIGHT);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //Check if the skin is unequipped
        if(equipped && !prefs.getString("equippedSkin", ShopItem.ALPACA_WHITE.getName()).equals(item.getName())) {
            equipped = false;
            setUpBuyButton();
        }

        title.setPosition(this.getX() + MARGIN, this.getY() + CELL_HEIGHT - CELL_HEIGHT/10);
        button.setPosition(this.getX() + MARGIN, this.getY() + MARGIN);
        labelPrice.setPosition(this.getX() + MARGIN, this.getY() + 2* MARGIN + BUTTON_HEIGHT);

        batch.end();

        b.setTransformMatrix(batch.getTransformMatrix());
        b.begin();
        b.draw(card, this.getX(), this.getY(), CELL_WIDTH, CELL_HEIGHT);
        b.draw(frame, this.getX() + MARGIN, this.getY() + CELL_HEIGHT*0.28f, CELL_WIDTH - 2*MARGIN, alpacaHeight + CELL_HEIGHT*0.12f);
        b.draw(itemTexture, this.getX() + CELL_WIDTH/2 - alpacaWidth/2, this.getY() + CELL_HEIGHT*0.35f, alpacaWidth, alpacaHeight);
        b.end();
        batch.begin();
        button.draw(batch, parentAlpha);
        title.draw(batch, parentAlpha);
        labelPrice.draw(batch, parentAlpha);

    }

    public void dispose(){

        b.dispose();
    }



}
