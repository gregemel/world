package com.emelwerx.world.services.loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.emelwerx.world.databags.Scene;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.services.factories.EntityEngineFactory;
import com.emelwerx.world.services.factories.PlayerFactory;
import com.emelwerx.world.systems.UserInterfaceSystem;

import static java.lang.String.format;

public class WorldLoader {

    public static World load(String name, UserInterfaceSystem ui) {
        Gdx.app.log("WorldLoader", format("creating world: %s", name));
        Bullet.init();
        String startFilename = "worlds/start.json";
        World world = loadWorldFile(startFilename);
        setDebug(world);
        EntityEngineFactory.create(world, ui);
        Scene firstScene = SceneLoader.load(world, 0, 0, 0);
        PlayerFactory.create(world, firstScene.getPlayerStartLocation());
        return world;
    }

    private static World loadWorldFile(String startFilename) {
        World world = new World();
        String name = getFirstWorldName(startFilename);
        world.setName(name);
        String firstWorld = name + "/world.json";
        Gdx.app.log("WorldLoader", format("loading first world : %s", firstWorld));
        String firstScene = getFirstSceneName(firstWorld);
        world.setFirstSceneName(firstScene);
        return world;
    }

    private static String getFirstWorldName(String startFilename) {
        Gdx.app.log("WorldLoader", format("loading start file : %s", startFilename));
        FileHandle fileHandle = Gdx.files.internal(startFilename);
        JsonReader reader = new JsonReader();
        JsonValue value = reader.parse(fileHandle);
        return value.getString("firstWorld");
    }

    private static String getFirstSceneName(String firstWorld) {
        FileHandle fileHandle = Gdx.files.internal("worlds/" + firstWorld);
        JsonReader reader = new JsonReader();
        JsonValue value = reader.parse(fileHandle);
        return value.getString("firstScene");
    }

    private static void setDebug(World world) {
        if (world.isDebug()) {
            DebugDrawer debugDrawer = new DebugDrawer();
            debugDrawer.setDebugMode(btIDebugDraw.DebugDrawModes.DBG_MAX_DEBUG_DRAW_MODE);
            world.setDebugDrawer(debugDrawer);
        }
    }
}