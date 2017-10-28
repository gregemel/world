package com.deeep.spaceglad.databags;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.deeep.spaceglad.systems.*;

public class GameWorld {
    private boolean debug = false;
    private DebugDrawer debugDrawer;
    private Engine engine;
    private Entity character;
    private Entity gun;
    private Entity dome;
    private PhysicsSystem physicsSystem;
    private PlayerSystem playerSystem;
    private RenderSystem renderSystem;
    private Scene currentScene;

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

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Entity getCharacter() {
        return character;
    }

    public void setPlayer(Entity character) {
        this.character = character;
    }

    public Entity getGun() {
        return gun;
    }

    public void setGun(Entity gun) {
        this.gun = gun;
    }

    public Entity getDome() {
        return dome;
    }

    public void setDome(Entity dome) {
        this.dome = dome;
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