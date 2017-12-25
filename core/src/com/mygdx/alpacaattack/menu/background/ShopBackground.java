package com.mygdx.alpacaattack.menu.background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Adrien on 14-09-17.
 */

public class ShopBackground implements Background {
    private TextureRegion background;

    public ShopBackground(com.mygdx.alpacaattack.assets.MenuAssets assets){
        TextureAtlas atlas = assets.manager.get(com.mygdx.alpacaattack.assets.MenuAssets.menuAtlasPath, TextureAtlas.class);
        background = atlas.findRegion("shopBackground");
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(background,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void dispose() {

    }
}
