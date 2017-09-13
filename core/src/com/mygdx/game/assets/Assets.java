package com.mygdx.game.assets;

import com.badlogic.gdx.assets.AssetManager;

/**
 * Created by Adrien on 11-09-17.
 */

public interface Assets {
    void load();
    void dispose();
    float update();
    float getSoundVolume();
    float getMusicVolume();
}
