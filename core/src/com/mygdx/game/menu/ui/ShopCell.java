package com.mygdx.game.menu.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.game.assets.MenuAssets;
import com.mygdx.game.menu.enums.ShopItem;

/**
 * Created by Adrien on 08-09-17.
 */

public class ShopCell extends Actor {

    private static final Preferences prefs = Gdx.app.getPreferences("prefs");

    private final ShopItem item;

    private TextureRegion itemTexture;

    private Skin skin;
    private TextButton button;
    private Label labelPrice;
    private Label title;
    private Image itemImage;
    private Image card;
    private Image frame;

    public final static float CELL_HEIGHT = 0.55f*Gdx.graphics.getHeight();
    public final static float CELL_WIDTH = 0.15f*Gdx.graphics.getWidth();

    private final static float OBJECT_FRAME_Y_RATIO = 0.8f;
    private final static float MARGIN = CELL_WIDTH*0.05f;
    private final static float BUTTON_HEIGHT = 0.1f*CELL_HEIGHT;
    private final static float BUTTON_WIDTH = CELL_WIDTH -2*MARGIN;
    public final static float TITLE_FONT_SIZE = Gdx.graphics.getHeight()/18f;
    private final static float FRAME_HEIGHT = CELL_HEIGHT*0.52f;
    private  static  final  float FRAME_WIDTH = CELL_WIDTH -2*MARGIN;

    //private static final BitmapFont title_font = AlpacaAttack.generateFont(Gdx.files.internal("ttf/BeTrueToYourSchool-Regular.ttf"), (int)(TITLE_FONT_SIZE));
    //private BitmapFont button_font = AlpacaAttack.generateFont(Gdx.files.internal("ttf/BeTrueToYourSchool-Regular.ttf"), (int)(BUTTON_HEIGHT - BUTTON_HEIGHT/8));
    private BitmapFont font;

    private float itemHeight;
    private float itemWidth;

    private float frameY;

    private boolean equipped = false;
    private boolean owned = false;

    private MenuAssets assets;
    private I18NBundle bundle;

    public ShopCell(ShopItem i, MenuAssets a){
        item = i;
        assets = a;
        bundle = assets.manager.get(MenuAssets.bundlePath, I18NBundle.class);

        skin = assets.manager.get(MenuAssets.menuSkinPath, Skin.class);
        font = assets.manager.get(MenuAssets.shopCellFont, BitmapFont.class);
        itemTexture = item.getTextureRegion(assets);


        this.setWidth(CELL_WIDTH);
        this.setHeight(CELL_HEIGHT);

        //Base the size of the item on the shop from it's largest dimension
        if(itemTexture.getRegionHeight() > itemTexture.getRegionWidth()) {
            itemHeight = FRAME_HEIGHT * OBJECT_FRAME_Y_RATIO;
            itemWidth = itemHeight * ((float) itemTexture.getRegionWidth() / (float) itemTexture.getRegionHeight());
            while(itemWidth >= FRAME_WIDTH){
                itemHeight -= MARGIN*2;
                itemWidth = itemHeight * ((float) itemTexture.getRegionWidth() / (float) itemTexture.getRegionHeight());
            }
        }
        else{
            itemWidth =  FRAME_WIDTH - 2*MARGIN;
            itemHeight = itemWidth * ((float) itemTexture.getRegionHeight() / (float) itemTexture.getRegionWidth());
            while(itemHeight >= FRAME_HEIGHT) {
                itemWidth -= MARGIN * 2;
                itemHeight = itemWidth * ((float) itemTexture.getRegionWidth() / (float) itemTexture.getRegionHeight());

            }
        }

        this.getOwned();
        this.getEquipped();

        this.createImages();
        this.setUpBuyButton();
        this.setUpPriceLabel();
        this.setUpCellTitle();

        this.createListener();

    }

    private void createImages(){

        TextureRegion frameTexture = assets.manager.get(MenuAssets.menuAtlasPath, TextureAtlas.class).findRegion("frame");
        TextureRegion cardTexture = assets.manager.get(MenuAssets.menuAtlasPath, TextureAtlas.class).findRegion("cell");

        itemImage = new Image(new TextureRegionDrawable(itemTexture));
        itemImage.setSize(itemWidth, itemHeight);

        card = new Image(new TextureRegionDrawable(cardTexture));
        card.setSize(CELL_WIDTH, CELL_HEIGHT);

        frame = new Image(new TextureRegionDrawable(frameTexture));
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);


    }
    private void createListener(){
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
                        buyItem(money);
                    }
                }
                //If you own it
                else{
                    //Equip it
                    if(!equipped)
                        equipItem();
                    //Unequip it
                    else{
                        switch (item.getItemType()) {
                            case ShopItem.DASH:
                                prefs.putString("equippedDash", "").flush();
                                break;
                            case ShopItem.DRAG:
                                prefs.putString("equippedDrag", "").flush();
                                break;
                        }
                    }
                }
            }
        });

    }
    private void getOwned(){
        owned = prefs.getBoolean("own"+item.getName(), false);
    }

    //Detect hit only on the button
    @Override
    public Actor hit(float x, float y, boolean touchable){
        if (touchable && getTouchable() != Touchable.enabled) return null;
        return x >= MARGIN && x < BUTTON_WIDTH + MARGIN && y >= MARGIN && y < BUTTON_HEIGHT + MARGIN ? this : null;
    }

    private void getEquipped(){
        if(item.getItemType() == ShopItem.SKIN) {
            String equippedSkin = prefs.getString("equippedSkin", ShopItem.ALPACA_WHITE.getName());
            equipped = (equippedSkin.equals(item.getName()));
        }
        else if(item.getItemType() == ShopItem.DRAG) {
            String equippedDrag = prefs.getString("equippedDrag", "");
            equipped = (equippedDrag.equals(item.getName()));
        }
        else if(item.getItemType() == ShopItem.DASH) {
            String equippedDash = prefs.getString("equippedDash", "");
            equipped = (equippedDash.equals(item.getName()));
        }
    }

    private void equipItem(){
        if(item.getItemType() == ShopItem.SKIN)
            prefs.putString("equippedSkin", item.getName()).flush();
        if(item.getItemType() == ShopItem.DRAG)
            prefs.putString("equippedDrag", item.getName()).flush();
        if(item.getItemType() == ShopItem.DASH)
            prefs.putString("equippedDash", item.getName()).flush();

        equipped = true;
        setUpBuyButton();
    }

    private void unEquip(){
        equipped = false;
        setUpBuyButton();
    }

    //Check if the skin is unequipped
    private void isUnequipped(){
        String pref ="nope";
        switch(item.getItemType()){
            case ShopItem.SKIN:
                pref = prefs.getString("equippedSkin", ShopItem.ALPACA_WHITE.getName());
                break;
            case ShopItem.DRAG:
                pref = prefs.getString("equippedDrag", "");
                break;
            case ShopItem.DASH:
                pref = prefs.getString("equippedDash", "");
                break;
        }
        if(!pref.equals(item.getName()))
            this.unEquip();
    }

    private void buyItem(int money){
        //Buy it
        prefs.putBoolean("own" + item.getName(), true);
        owned = true;
        money -= item.getPrice();
        prefs.putInteger("money", money).flush();
        setUpPriceLabel();

        //Equip it
        this.equipItem();

    }

    public TextButton getButton(){
        return button;
    }

    private void setUpCellTitle(){
        title = new Label(item.getShopName(assets.manager.get(MenuAssets.bundlePath, I18NBundle.class)), new Label.LabelStyle(font, item.getTextColor()));
        title.setSize(CELL_WIDTH - 2*MARGIN, 0);
        title.setAlignment(Align.center);
        title.setPosition(this.getX() + MARGIN, this.getY() +CELL_HEIGHT - TITLE_FONT_SIZE*2 -200);
    }

    private void setUpBuyButton(){
        String buttonString;
        String buttonStyle;

        if(owned)
            buttonString = bundle.get("use");
        else
            buttonString = bundle.get("buy");

        if(equipped)
            buttonStyle = "pushed";
        else
            buttonStyle = "default";


        button = new TextButton(buttonString, skin, buttonStyle);
        button.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setPosition(this.getX(), this.getY());

        button.getLabel().setStyle(new Label.LabelStyle(assets.manager.get(MenuAssets.shopCellButtonFont, BitmapFont.class), Color.WHITE));

        //Add a empty listener jut to have the button animation
        button.addListener(new InputListener());
        this.addListener(button.getClickListener());

    }

    private void setUpPriceLabel(){

        String labelString;
        if(owned)
            labelString = bundle.get("owned");
        else
            labelString = item.getPrice()+"$";

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

        //Check if item is still equipped
        if(equipped)
            this.isUnequipped();

        title.setPosition(this.getX() + MARGIN, this.getY() + CELL_HEIGHT - CELL_HEIGHT/10);
        button.setPosition(this.getX() + MARGIN, this.getY() + MARGIN);
        labelPrice.setPosition(this.getX() + MARGIN, this.getY() + 2* MARGIN + BUTTON_HEIGHT);
        frameY = this.getY() + CELL_HEIGHT*0.28f;
        frame.setPosition(this.getX() + MARGIN, frameY);
        itemImage.setPosition(this.getX() + CELL_WIDTH/2 - itemWidth/2, frameY + FRAME_HEIGHT/2 - itemHeight/2);
        card.setPosition(this.getX(), this.getY());


        card.draw(batch, parentAlpha);
        frame.draw(batch, parentAlpha);
        button.draw(batch, parentAlpha);
        title.draw(batch, parentAlpha);
        labelPrice.draw(batch, parentAlpha);
        itemImage.draw(batch, parentAlpha);
    }


}
