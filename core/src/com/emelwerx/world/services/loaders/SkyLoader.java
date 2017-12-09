package com.emelwerx.world.services.loaders;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.utils.JsonValue;
import com.emelwerx.world.databags.Scene;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.services.factories.ModelComponentFactory;

import java.util.Locale;

import static java.lang.String.format;

public class SkyLoader {

    public static void load(int x, int y, int z, Scene scene, JsonValue jsonScene) {
        JsonValue sky = jsonScene.get("sky");
        String skyModelFilename = sky.getString("modelFile");
        Gdx.app.log("SceneLoader", format(Locale.US,"load sky %s, %d, %d, %d", skyModelFilename, x, y, z));
        Model model = ModelLoader.loadSky(skyModelFilename);
        ModelComponent modelComponent = ModelComponentFactory.create(model, x, y, z);

        Entity entity = new Entity();
        entity.add(modelComponent);

        scene.setSky(entity);
    }
}