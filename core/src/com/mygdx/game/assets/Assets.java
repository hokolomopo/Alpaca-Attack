package com.mygdx.game.assets;

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
