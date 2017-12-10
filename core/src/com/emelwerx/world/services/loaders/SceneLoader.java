package com.emelwerx.world.services.loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.emelwerx.world.databags.Scene;
import com.emelwerx.world.databags.World;

import java.util.Locale;

import static java.lang.String.format;

public class SceneLoader {

    public static Scene load(World world, int x, int y, int z) {
        String sceneName = world.getFirstSceneName();
        String sceneFilename = "worlds/" + world.getName() + "/" + sceneName + "/scene.json";
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
        ScenePropertiesLoader.load(world, scene, jsonScene);
        return scene;
    }
}