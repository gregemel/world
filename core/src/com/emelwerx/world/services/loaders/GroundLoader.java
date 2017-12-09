package com.emelwerx.world.services.loaders;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.utils.JsonValue;
import com.emelwerx.world.databags.Scene;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.services.factories.ModelComponentFactory;
import com.emelwerx.world.services.factories.SceneComponentFactory;

import java.util.Locale;

import static java.lang.String.format;

public class GroundLoader {

    public static void load(int x, int y, int z, Scene scene, JsonValue jsonScene) {
        JsonValue ground = jsonScene.get("ground");
        String groundModelFilename = ground.getString("modelFile");
        Gdx.app.log("SceneLoader", format(Locale.US,"loadGround %s, %d, %d, %d", groundModelFilename, x, y, z));

        Entity groundEntity = new Entity();
        ModelComponent modelComponent = attachModelComponent(groundModelFilename, x, y, z, groundEntity);
        SceneComponentFactory.create(groundEntity, modelComponent);
        scene.setGround(groundEntity);
    }

    private static ModelComponent attachModelComponent(String name, int x, int y, int z, Entity groundEntity) {
        Model model = ModelLoader.load(name);
        ModelComponent modelComponent = ModelComponentFactory.create(model, x, y, z);
        groundEntity.add(modelComponent);
        return modelComponent;
    }
}