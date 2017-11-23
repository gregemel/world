package com.emelwerx.world.databags;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.emelwerx.world.systems.PhysicsSystem;
import com.emelwerx.world.systems.PlayerSystem;
import com.emelwerx.world.systems.RenderSystem;

public class World {
    private String name;
    private boolean debug = false;
    private DebugDrawer debugDrawer;
    private Engine entityEngine;
    private Entity entityCharacter;
    private Entity entityPlayerItem;
    private PhysicsSystem physicsSystem;
    private PlayerSystem playerSystem;
    private RenderSystem renderSystem;
    private PerspectiveCamera perspectiveCamera;
    private Scene currentScene;
    private String firstSceneName;

    public PerspectiveCamera getPerspectiveCamera() {
        return perspectiveCamera;
    }

    public void setPerspectiveCamera(PerspectiveCamera perspectiveCamera) {
        this.perspectiveCamera = perspectiveCamera;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstSceneName() {
        return firstSceneName;
    }

    public void setFirstSceneName(String firstSceneName) {
        this.firstSceneName = firstSceneName;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public DebugDrawer getDebugDrawer() {
        return debugDrawer;
    }

    public void setDebugDrawer(DebugDrawer debugDrawer) {
        this.debugDrawer = debugDrawer;
    }

    public Engine getEntityEngine() {
        return entityEngine;
    }

    public void setEntityEngine(Engine entityEngine) {
        this.entityEngine = entityEngine;
    }

    public Entity getEntityCharacter() {
        return entityCharacter;
    }

    public void setEntityCharacter(Entity entityCharacter) {
        this.entityCharacter = entityCharacter;
    }

    public void setPlayer(Entity character) {
        this.entityCharacter = character;
    }

    public Entity getEntityPlayerItem() {
        return entityPlayerItem;
    }

    public void setEntityPlayerItem(Entity entityPlayerItem) {
        this.entityPlayerItem = entityPlayerItem;
    }

    public PhysicsSystem getPhysicsSystem() {
        return physicsSystem;
    }

    public void setPhysicsSystem(PhysicsSystem physicsSystem) {
        this.physicsSystem = physicsSystem;
    }

    public PlayerSystem getPlayerSystem() {
        return playerSystem;
    }

    public void setPlayerSystem(PlayerSystem playerSystem) {
        this.playerSystem = playerSystem;
    }

    public RenderSystem getRenderSystem() {
        return renderSystem;
    }

    public void setRenderSystem(RenderSystem renderSystem) {
        this.renderSystem = renderSystem;
    }
}