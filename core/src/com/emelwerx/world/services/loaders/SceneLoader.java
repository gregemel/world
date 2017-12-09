package com.emelwerx.world.services.loaders;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.emelwerx.world.databags.Scene;
import com.emelwerx.world.databags.World;

import java.util.Locale;

import static java.lang.String.format;

public class SceneLoader {

    public static Scene load(World world, int x, int y, int z) {

        String worldName = world.getName();
        String sceneName = world.getFirstSceneName();

        String sceneFilename = "worlds/" + worldName + "/" + sceneName + "/scene.json";
        Gdx.app.log("SceneLoader", format(Locale.US,"load %s, %d, %d, %d", sceneFilename, x, y, z));

        FileHandle fileHandle = Gdx.files.internal(sceneFilename);

        return createScene(world, x, y, z, sceneName, fileHandle);
    }

    private static Scene createScene(World world, int x, int y, int z,
                                    String sceneName, FileHandle fileHandle) {

        Scene scene = new Scene();
        scene.setName(sceneName);

        JsonReader reader = new JsonReader();
        JsonValue jsonScene = reader.parse(fileHandle);

        SkyLoader.load(x, y, z, scene, jsonScene);
        GroundLoader.load(x, y, z, scene, jsonScene);
        loadProperties(world, scene, jsonScene);
        return scene;
    }

    private static void loadProperties(World world, Scene scene, JsonValue jsonScene) {
        setPlayerStartLocation(jsonScene, scene);
        scene.setMaxSpawnCount(3);
        CreatureLoader.load(world, jsonScene.get("creatures"));
        world.setCurrentScene(scene);
        attachToEntityEngine(world.getEntityEngine(), scene);
        world.getPlayerSystem().getPlayerSystemState().setSkyEntity(scene.getSky());
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