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

    private PlayerSystemState state;

    public PlayerSystem (PlayerSystemState state) {
        this.state = state;
    }

    public PlayerSystemState getState() {
        return state;
    }

    @Override
    public void addedToEngine(Engine engine) {
        Gdx.app.log("PlayerSystem", format("addedToEngine: %s", engine.toString()));
        engine.addEntityListener(Family.all(PlayerComponent.class).get(), this);
    }

    @Override
    public void update(float delta) {
        if (getState().getPlayerEntity() == null)
            return;
        PlayerMoveUpdater.update(delta, state);
        updateStatus();
        checkGameOver();
    }

    private void updateStatus() {
        UserInterfaceSystem userInterfaceSystem = state.getUserInterfaceSystem();
        PlayerComponent playerComponent = state.getPlayerComponent();
        userInterfaceSystem.getState().getHealthWidget().setValue(playerComponent.getHealth());
    }

    private void checkGameOver() {
        if (state.getPlayerComponent().getHealth() <= 0 && !Settings.isPaused()) {
            Settings.setPaused(true);
            state.getUserInterfaceSystem().getState().getGameOverWidget().gameOver();
        }
    }

    @Override
    public void entityAdded(Entity entity) {
        Gdx.app.log("PlayerSystem", format("entityAdded: %s", entity.toString()));
        state.setPlayerEntity(entity);
        state.setPlayerComponent(entity.getComponent(PlayerComponent.class));
        state.setCharacterComponent(entity.getComponent(CharacterComponent.class));
        state.setModelComponent(entity.getComponent(ModelComponent.class));
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