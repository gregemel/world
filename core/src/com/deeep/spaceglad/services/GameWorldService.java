package com.deeep.spaceglad.services;


import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.deeep.spaceglad.GameWorld;
import com.deeep.spaceglad.Settings;
import com.deeep.spaceglad.UI.GameUI;
import com.deeep.spaceglad.components.CharacterComponent;
import com.deeep.spaceglad.databags.Scene;
import com.deeep.spaceglad.systems.BulletSystem;
import com.deeep.spaceglad.systems.EnemySystem;
import com.deeep.spaceglad.systems.PlayerSystem;
import com.deeep.spaceglad.systems.RenderSystem;
import com.deeep.spaceglad.systems.StatusSystem;

public class GameWorldService {

    private GameWorld gameWorld;
    private GameUI gameUI;

    public GameWorld create(GameUI ui) {
        Bullet.init();

        gameWorld = new GameWorld();
        gameUI = ui;

        setDebug();
        addSystems();
        addEntities();

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
        Engine engine = new Engine();

        RenderSystem renderSystem = new RenderSystem();
        gameWorld.setRenderSystem(renderSystem);
        engine.addSystem(renderSystem);
        EntityFactory.renderSystem = renderSystem;

        BulletSystem bulletSystem = new BulletSystem();
        gameWorld.setBulletSystem(bulletSystem);
        engine.addSystem(bulletSystem);

        PlayerSystem playerSystem = new PlayerSystem(gameWorld, gameUI, renderSystem.perspectiveCamera);
        gameWorld.setPlayerSystem(playerSystem);
        engine.addSystem(playerSystem);
        engine.addSystem(new EnemySystem(gameWorld));
        engine.addSystem(new StatusSystem(gameWorld));

        gameWorld.setEngine(engine);

        if (gameWorld.isDebug()) {
            bulletSystem.collisionWorld.setDebugDrawer(gameWorld.getDebugDrawer());
        }
    }

    private void addEntities() {
        loadLevel();
        createPlayer(0, 6, 0);
    }

    private void loadLevel() {
        Scene area = SceneLoader.load("area", 0, 0, 0);
        gameWorld.getEngine().addEntity(area.getGround());
        gameWorld.setDome(area.getSky());
        gameWorld.getEngine().addEntity(area.getSky());
        gameWorld.getPlayerSystem().dome = area.getSky();
    }

    private void createPlayer(float x, float y, float z) {
        Entity character = EntityFactory.createPlayer(gameWorld.getBulletSystem(), x, y, z);
        gameWorld.setCharacter(character);
        gameWorld.getEngine().addEntity(character);

        Entity gun = EntityFactory.loadGun(2.5f, -1.9f, -4);
        gameWorld.setGun(gun);
        gameWorld.getEngine().addEntity(gun);
        gameWorld.getPlayerSystem().gun = gun;
        gameWorld.getRenderSystem().gun = gun;
    }

    public void render(float delta) {
        renderWorld(delta);
        checkPause();
    }

    private void checkPause() {
        if (Settings.Paused) {
            gameWorld.getEngine().getSystem(PlayerSystem.class).setProcessing(false);
            gameWorld.getEngine().getSystem(EnemySystem.class).setProcessing(false);
            gameWorld.getEngine().getSystem(StatusSystem.class).setProcessing(false);
            gameWorld.getEngine().getSystem(BulletSystem.class).setProcessing(false);
        } else {
            gameWorld.getEngine().getSystem(PlayerSystem.class).setProcessing(true);
            gameWorld.getEngine().getSystem(EnemySystem.class).setProcessing(true);
            gameWorld.getEngine().getSystem(StatusSystem.class).setProcessing(true);
            gameWorld.getEngine().getSystem(BulletSystem.class).setProcessing(true);
        }
    }

    private void renderWorld(float delta) {
        gameWorld.getEngine().update(delta);
        if (gameWorld.isDebug()) {
            gameWorld.getDebugDrawer().begin(gameWorld.getRenderSystem().perspectiveCamera);
            gameWorld.getBulletSystem().collisionWorld.debugDrawWorld();
            gameWorld.getDebugDrawer().end();
        }
    }

    public void resize(int width, int height) {
        gameWorld.getRenderSystem().resize(width, height);
    }

    public void dispose() {
        gameWorld.getBulletSystem().collisionWorld.removeAction(gameWorld.getCharacter().getComponent(CharacterComponent.class).characterController);
        gameWorld.getBulletSystem().collisionWorld.removeCollisionObject(gameWorld.getCharacter().getComponent(CharacterComponent.class).ghostObject);
        gameWorld.getBulletSystem().dispose();

        gameWorld.setBulletSystem(null);
        gameWorld.getRenderSystem().dispose();

        gameWorld.getCharacter().getComponent(CharacterComponent.class).characterController.dispose();
        gameWorld.getCharacter().getComponent(CharacterComponent.class).ghostObject.dispose();
        gameWorld.getCharacter().getComponent(CharacterComponent.class).ghostShape.dispose();
    }

    public static void remove(GameWorld gameWorld, Entity entity) {
        gameWorld.getEngine().removeEntity(entity);
        gameWorld.getBulletSystem().removeBody(entity);
    }
}
