package com.emelwerx.world.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class Assets {

    //skin is only for gui elements
    public static Skin skin;

    //asset manager is only used by the particle system
    public static AssetManager assetManager;

    private Assets() {
        skin = new Skin();
        FileHandle fileHandle = Gdx.files.internal("data/uiskin.json");
        FileHandle atlasFile = fileHandle.sibling("uiskin.atlas");
        if (atlasFile.exists()) {
            skin.addRegions(new TextureAtlas(atlasFile));
        }
        skin.load(fileHandle);

        assetManager = new AssetManager();
    }

    public static Assets init() {
        Assets assets = new Assets();

        return assets;
    }

    public static void dispose() {
        skin.dispose();
        assetManager.dispose();
    }
}
