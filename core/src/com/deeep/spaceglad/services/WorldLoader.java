package com.deeep.spaceglad.services;


import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.deeep.spaceglad.databags.GameWorld;
import com.deeep.spaceglad.UI.GameUI;
import com.deeep.spaceglad.databags.Scene;
import com.deeep.spaceglad.databags.StatusSystemState;
import com.deeep.spaceglad.systems.PhysicsSystem;
import com.deeep.spaceglad.systems.MonsterSystem;
import com.deeep.spaceglad.systems.PlayerSystem;
import com.deeep.spaceglad.systems.RenderSystem;
import com.deeep.spaceglad.systems.StatusSystem;

public class WorldLoader {

    private GameWorld gameWorld;
    private GameUI gameUI;

    public GameWorld create(GameUI ui) {
        Bullet.init();

        gameWorld = new GameWorld();
        gameUI = ui;

        setDebug();
        addSystems();
        loadLevel();
        createPlayer(0, 6, 0);

        return gameWorld;
    }

    private void setDebug() {
        if (gameWorld.isDebug()) {
            DebugDrawer debugDrawer = new DebugDrawer();
            debugDrawer.setDebugMode(btIDebugDraw.DebugDrawModes.DBG_MAX_DEBUG_DRAW_MODE);
            gameWorld.setDebugDrawer(debugDrawer);
        }
    }

    private void addSystems() {
        Engine engine = createEntitySystem();

        RenderSystem renderSystem = createRenderSystem(engine);

        createPhysicsSystem(engine);

        createPlayerSystem(engine, renderSystem);

        createMonsterSystem(engine);

        createStatusSystem(engine);

        gameWorld.setEngine(engine);
    }

    private Engine createEntitySystem() {
        return new Engine();
    }

    private RenderSystem createRenderSystem(Engine engine) {
        RenderSystemFactory renderSystemFactory = new RenderSystemFactory();
        RenderSystem renderSystem = renderSystemFactory.create();
        gameWorld.setRenderSystem(renderSystem);
        engine.addSystem(renderSystem);
        return renderSystem;
    }

    private void createPhysicsSystem(Engine engine) {
        PhysicsSystemFactory physicsSystemFactory = new PhysicsSystemFactory();
        PhysicsSystem physicsSystem = physicsSystemFactory.create();
        gameWorld.setPhysicsSystem(physicsSystem);
        engine.addSystem(physicsSystem);
        if (gameWorld.isDebug()) {
            physicsSystem.getPhysicsSystemState().getCollisionWorld().setDebugDrawer(gameWorld.getDebugDrawer());
        }
    }

    private void createPlayerSystem(Engine engine, RenderSystem renderSystem) {
        PlayerSystemFactory playerSystemFactory = new PlayerSystemFactory();
        PlayerSystem playerSystem = playerSystemFactory.create(
                gameWorld, gameUI, renderSystem.getRenderSystemState().getPerspectiveCamera());
        gameWorld.setPlayerSystem(playerSystem);
        engine.addSystem(playerSystem);
    }

    private void createStatusSystem(Engine engine) {
        StatusSystem statusSystem = new StatusSystem();
        StatusSystemState statusSystemState = new StatusSystemState();

        statusSystemState.setGameWorld(gameWorld);
        statusSystemState.setStatusService(new StatusService());
        statusSystem.setStatusSystemState(statusSystemState);
        statusSystemState.setWorldService(this);

        engine.addSystem(statusSystem);
    }

    private void createMonsterSystem(Engine engine) {
        MonsterSystemFactory monsterSystemFactory = new MonsterSystemFactory();
        MonsterSystem monsterSystem = monsterSystemFactory.create(gameWorld);
        engine.addSystem(monsterSystem);
    }


    private void loadLevel() {
        Scene area = SceneLoader.load("area", 0, 0, 0);
        gameWorld.setCurrentScene(area);
        gameWorld.getEngine().addEntity(area.getGround());
        gameWorld.getEngine().addEntity(area.getSky());
        gameWorld.getPlayerSystem().getPlayerSystemState().setDome(area.getSky());
    }

    private void createPlayer(float x, float y, float z) {
        PlayerFactory playerFactory = new PlayerFactory();
        Entity player = playerFactory.create(gameWorld.getPhysicsSystem(), x, y, z);
        gameWorld.setPlayer(player);
        gameWorld.getEngine().addEntity(player);

        PlayerItemFactory playerItemFactory = new PlayerItemFactory();
        Entity gun = playerItemFactory.create("GUNMODEL", 2.5f, -1.9f, -4);
        gameWorld.setGun(gun);
        gameWorld.getEngine().addEntity(gun);
        gameWorld.getPlayerSystem().getPlayerSystemState().setGun(gun);
        gameWorld.getRenderSystem().getRenderSystemState().setGun(gun);
    }


    public void remove(GameWorld gameWorld, Entity entity) {
        gameWorld.getEngine().removeEntity(entity);
        gameWorld.getPhysicsSystem().removeBody(entity);
    }
}
