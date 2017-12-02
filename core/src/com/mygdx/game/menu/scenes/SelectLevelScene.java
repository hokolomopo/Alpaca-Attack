package com.mygdx.game.menu.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.assets.MenuAssets;
import com.mygdx.game.menu.enums.Levels;
import com.mygdx.game.menu.ui.BackToMainMenuButton;
import com.mygdx.game.menu.ui.LevelCell;
import com.mygdx.game.screen.MenuScreen;

import java.util.ArrayList;
import java.util.EnumSet;

/**
 * Created by Adrien on 14-09-17.
 */

public class SelectLevelScene extends MenuScene {

    private final static float PANE_PADDING = Gdx.graphics.getWidth()/15;
    private final static float PADDING = Gdx.graphics.getWidth()/100f;
    private final static float PANE_Y = Gdx.graphics.getHeight()*0.13f;

    private ScrollPane pane;

    public SelectLevelScene(MenuScreen mScreen){
        super(mScreen);
        menuScreen = mScreen;

        new BackToMainMenuButton(menuScreen);
        new SoundButtonsScene(menuScreen);

        pane = new ScrollPane(this.setUpTable());
        pane.setSize(Gdx.graphics.getWidth() - 2*PANE_PADDING, LevelCell.CELL_HEIGHT + 2*PADDING);
        pane.setPosition(PANE_PADDING, PANE_Y);
        this.setFrame();

        stage.addActor(pane);
    }

    private void setFrame(){
        TextureAtlas atlas = menuScreen.assets.manager.get(MenuAssets.levelSelectAtlasPath);
        Image frame = new Image(atlas.findRegion("woodenFrame"));
        frame.setSize(pane.getWidth() + PADDING*4, pane.getHeight() + PADDING*2);
        frame.setPosition(pane.getX() - PADDING*2f, pane.getY() - PADDING);
        stage.addActor(frame);

    }

    private Table setUpTable(){
        Table table = new Table();
        ArrayList<Levels> levels = new ArrayList<Levels>(EnumSet.allOf(Levels.class));
        for(Levels z : levels){
            LevelCell a = new LevelCell(z,  menuScreen);
            table.add(a).pad(PADDING);
        }
        return table;
    }

    @Override
    public void render() {
        stage.act();
        stage.draw();
    }
}
