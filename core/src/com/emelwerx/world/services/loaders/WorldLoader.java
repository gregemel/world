package com.emelwerx.world.services.loaders;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.services.factories.CreatureSystemFactory;
import com.emelwerx.world.services.factories.PhysicsSystemFactory;
import com.emelwerx.world.services.factories.PlayerEntityFactory;
import com.emelwerx.world.services.factories.PlayerItemFactory;
import com.emelwerx.world.services.factories.PlayerSystemFactory;
import com.emelwerx.world.services.factories.RenderSystemFactory;
import com.emelwerx.world.systems.CreatureSystem;
import com.emelwerx.world.systems.WorldUiSystem;
import com.emelwerx.world.databags.Scene;
import com.emelwerx.world.systems.PhysicsSystem;
import com.emelwerx.world.systems.PlayerSystem;
import com.emelwerx.world.systems.RenderSystem;

import static java.lang.String.format;

public class WorldLoader {

    private static World world;
    private static WorldUiSystem worldUiSystem;

    public static World create(String name, WorldUiSystem ui) {

        Gdx.app.log("WorldLoader", format("creating world: %s", name));
        Bullet.init();

        world = new World();
        worldUiSystem = ui;

        loadWorldFile();
        setDebug();
        addSystems();
        Scene firstScene = SceneLoader.load(world, 0, 0, 0);
        createPlayer(firstScene);

        return world;
    }

    private static void loadWorldFile() {
        String name = getFirstWorldName();
        world.setName(name);
        String firstWorld = name + "/world.json";
        Gdx.app.log("WorldLoader", format("loading first world : %s", firstWorld));

        String firstScene = getFirstSceneName(firstWorld);

        world.setFirstSceneName(firstScene);
    }

    private static String getFirstSceneName(String firstWorld) {
        FileHandle fileHandle = Gdx.files.internal("worlds/" + firstWorld);

        JsonReader reader = new JsonReader();
        JsonValue value = reader.parse(fileHandle);

        return value.getString("firstScene");
    }

    private static String getFirstWorldName() {
        String firstWorld = "worlds/start.json";
        Gdx.app.log("WorldLoader", format("loading start file : %s", firstWorld));
        FileHandle fileHandle = Gdx.files.internal(firstWorld);

        JsonReader reader = new JsonReader();
        JsonValue value = reader.parse(fileHandle);

        return value.getString("firstWorld");
    }

    private static void setDebug() {
        if (world.isDebug()) {
            DebugDrawer debugDrawer = new DebugDrawer();
            debugDrawer.setDebugMode(btIDebugDraw.DebugDrawModes.DBG_MAX_DEBUG_DRAW_MODE);
            world.setDebugDrawer(debugDrawer);
        }
    }

    private static void addSystems() {
        Engine engine = createEntityEngine();
        RenderSystem renderSystem = createRenderSystem(engine);
        createPhysicsSystem(engine);
        createPlayerSystem(engine, renderSystem);
        createCreatureSystem(engine);
        world.setEntityEngine(engine);
    }

    private static Engine createEntityEngine() {
        Gdx.app.log("WorldLoader", "createEntityEngine");
        return new Engine();
    }

    private static RenderSystem createRenderSystem(Engine engine) {
        RenderSystem renderSystem = RenderSystemFactory.create();
        world.setRenderSystem(renderSystem);
        engine.addSystem(renderSystem);
        return renderSystem;
    }

    private static void createPhysicsSystem(Engine engine) {
        PhysicsSystem physicsSystem = PhysicsSystemFactory.create();
        world.setPhysicsSystem(physicsSystem);
        engine.addSystem(physicsSystem);
        if (world.isDebug()) {
            physicsSystem.getPhysicsSystemState().getCollisionWorld().setDebugDrawer(world.getDebugDrawer());
        }
    }

    private static void createPlayerSystem(Engine engine, RenderSystem renderSystem) {
        PlayerSystem playerSystem = PlayerSystemFactory.create(
                world, worldUiSystem, renderSystem.getRenderSystemState().getPerspectiveCamera());
        world.setPlayerSystem(playerSystem);
        engine.addSystem(playerSystem);
    }

    private static void createCreatureSystem(Engine engine) {
        CreatureSystem creatureSystem = CreatureSystemFactory.create(world);
        engine.addSystem(creatureSystem);
    }

    private static void createPlayer(Scene arena) {

        Vector3 start = arena.getPlayerStartLocation();

        Entity player = PlayerEntityFactory.create(world.getPhysicsSystem(), start.x, start.y, start.z);
        world.setPlayer(player);
        world.getEntityEngine().addEntity(player);

        Entity itemEntity = PlayerItemFactory.create("GUNMODEL", 2.5f, -1.9f, -4);
        addItemToWorld(itemEntity);
    }

    private static void addItemToWorld(Entity itemEntity) {
        world.setEntityPlayerItem(itemEntity);
        world.getEntityEngine().addEntity(itemEntity);
        world.getPlayerSystem().getPlayerSystemState().setVisibleItem(itemEntity);
        world.getRenderSystem().getRenderSystemState().setPlayersVisibleItem(itemEntity);
    }
}
