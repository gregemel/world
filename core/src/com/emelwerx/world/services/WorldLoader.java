package com.emelwerx.world.services;


import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.UI.GameUI;
import com.emelwerx.world.databags.Scene;
import com.emelwerx.world.databags.StatusSystemState;
import com.emelwerx.world.systems.PhysicsSystem;
import com.emelwerx.world.systems.MonsterSystem;
import com.emelwerx.world.systems.PlayerSystem;
import com.emelwerx.world.systems.RenderSystem;
import com.emelwerx.world.systems.StatusSystem;

public class WorldLoader {

    private World gameWorld;
    private GameUI gameUI;

    public World create(String name, GameUI ui) {

        Gdx.app.log("WorldLoader", String.format("creating world: %s", name));
        Bullet.init();

        gameWorld = new World();
        gameUI = ui;

        setDebug();
        addSystems();
        loadFirstScene();
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
        gameWorld.setEntityEngine(engine);
    }

    private Engine createEntitySystem() {
        Gdx.app.log("WorldLoader", "createEntitySystem");
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

    private void loadFirstScene() {
        Scene area = SceneLoader.load("arena", 0, 0, 0);
        gameWorld.setCurrentScene(area);
        gameWorld.getEntityEngine().addEntity(area.getGround());
        gameWorld.getEntityEngine().addEntity(area.getSky());
        gameWorld.getPlayerSystem().getPlayerSystemState().setDome(area.getSky());
    }

    private void createPlayer(float x, float y, float z) {
        PlayerFactory playerFactory = new PlayerFactory();
        Entity player = playerFactory.create(gameWorld.getPhysicsSystem(), x, y, z);
        gameWorld.setPlayer(player);
        gameWorld.getEntityEngine().addEntity(player);

        PlayerItemFactory playerItemFactory = new PlayerItemFactory();
        Entity gun = playerItemFactory.create("GUNMODEL", 2.5f, -1.9f, -4);
        gameWorld.setEntityPlayerItem(gun);
        gameWorld.getEntityEngine().addEntity(gun);
        gameWorld.getPlayerSystem().getPlayerSystemState().setGun(gun);
        gameWorld.getRenderSystem().getRenderSystemState().setGun(gun);
    }

    public void remove(World gameWorld, Entity entity) {
        Gdx.app.log("WorldLoader", "remove");
        gameWorld.getEntityEngine().removeEntity(entity);
        gameWorld.getPhysicsSystem().removeBody(entity);
    }
}
