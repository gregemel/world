package com.emelwerx.world.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.emelwerx.world.services.Settings;
import com.emelwerx.world.databags.components.CharacterComponent;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.databags.components.PlayerComponent;
import com.emelwerx.world.databags.systemstates.PlayerSystemState;
import com.emelwerx.world.services.updaters.PlayerMoveUpdater;

import static java.lang.String.format;

public class PlayerSystem extends EntitySystem implements EntityListener, InputProcessor {

    private PlayerSystemState playerSystemState;

    public PlayerSystem (PlayerSystemState playerSystemState) {
        this.playerSystemState = playerSystemState;
    }

    public PlayerSystemState getPlayerSystemState() {
        return playerSystemState;
    }

    @Override
    public void addedToEngine(Engine engine) {
        Gdx.app.log("PlayerSystem", format("addedToEngine: %s", engine.toString()));
        engine.addEntityListener(Family.all(PlayerComponent.class).get(), this);
    }

    @Override
    public void update(float delta) {
        if (getPlayerSystemState().getPlayerEntity() == null) return;
        PlayerMoveUpdater.update(delta, playerSystemState);
        updateStatus();
        checkGameOver();
    }

    private void updateStatus() {
        UserInterfaceSystem userInterfaceSystem = playerSystemState.getUserInterfaceSystem();
        PlayerComponent playerComponent = playerSystemState.getPlayerComponent();
        userInterfaceSystem.getUserInterfaceSystemState().getHealthWidget().setValue(playerComponent.getHealth());
    }

    private void checkGameOver() {
        if (playerSystemState.getPlayerComponent().getHealth() <= 0 && !Settings.isPaused()) {
            Settings.setPaused(true);
            playerSystemState.getUserInterfaceSystem().getUserInterfaceSystemState().getGameOverWidget().gameOver();
        }
    }

    @Override
    public void entityAdded(Entity entity) {
        Gdx.app.log("PlayerSystem", format("entityAdded: %s", entity.toString()));
        playerSystemState.setPlayerEntity(entity);
        playerSystemState.setPlayerComponent(entity.getComponent(PlayerComponent.class));
        playerSystemState.setCharacterComponent(entity.getComponent(CharacterComponent.class));
        playerSystemState.setModelComponent(entity.getComponent(ModelComponent.class));
    }

    @Override
    public void entityRemoved(Entity entity) {
        Gdx.app.log("PlayerSystem", format("entity removed: %s", entity.toString()));
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}