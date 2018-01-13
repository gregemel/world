package com.emelwerx.world.services.loaders;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.JsonValue;
import com.emelwerx.world.databags.Scene;
import com.emelwerx.world.databags.World;

public class ScenePropertiesLoader {

    public static void load(World world, Scene scene, JsonValue jsonScene) {
        setPlayerStartLocation(jsonScene, scene);
        scene.setMaxSpawnCount(3);
        CreaturesLoader.load(world, jsonScene.get("creatures"));
        ItemsLoader.load(world, jsonScene.get("items"));
        world.setCurrentScene(scene);
        attachToEntityEngine(world.getEntityEngine(), scene);
        world.getPlayerSystem().getState().setSkyEntity(scene.getSky());
    }

    private static void attachToEntityEngine(Engine entityEngine, Scene scene) {
        entityEngine.addEntity(scene.getGround());
        entityEngine.addEntity(scene.getSky());
    }

    private static void setPlayerStartLocation(JsonValue jsonScene, Scene scene) {
        JsonValue location = jsonScene.get("player").get("startLocation");
        Vector3 vector3 = new Vector3(location.getFloat("x"), location.getFloat("y"), location.getFloat("z"));
        scene.setPlayerStartLocation(vector3);
    }
}