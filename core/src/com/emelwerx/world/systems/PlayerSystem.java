package com.emelwerx.world.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.emelwerx.world.Settings;
import com.emelwerx.world.UI.ControllerWidget;
import com.emelwerx.world.UI.GameUI;
import com.emelwerx.world.databags.CharacterComponent;
import com.emelwerx.world.databags.ModelComponent;
import com.emelwerx.world.databags.PlayerComponent;
import com.emelwerx.world.databags.PlayerSystemState;
import com.emelwerx.world.services.GunShooter;

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
        updateMovement(delta);
        updateStatus();
        checkGameOver();
    }

    private void updateMovement(float delta) {
        float deltaX;
        float deltaY;

        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            deltaX = -ControllerWidget.getWatchVector().x * 1.5f;
            deltaY = ControllerWidget.getWatchVector().y * 1.5f;
        } else {
            deltaX = -Gdx.input.getDeltaX() * 0.5f;
            deltaY = -Gdx.input.getDeltaY() * 0.5f;
        }

        Vector3 tmp = getPlayerSystemState().getTmp();
        tmp.set(0, 0, 0);
        Camera camera = getPlayerSystemState().getCamera();
        camera.rotate(camera.up, deltaX);
        tmp.set(camera.direction).crs(camera.up).nor();
        camera.direction.rotate(tmp, deltaY);
        tmp.set(0, 0, 0);

        CharacterComponent characterComponent = getPlayerSystemState().getCharacterComponent();
        ModelComponent modelComponent = getPlayerSystemState().getModelComponent();

        characterComponent.getCharacterDirection().set(-1, 0, 0).rot(modelComponent.getInstance().transform).nor();

        Vector3 walkDirection = getWalkDirection(tmp, camera, characterComponent);

        walkDirection.add(tmp);
        walkDirection.scl(10f * delta);
        characterComponent.getCharacterController().setWalkDirection(walkDirection);

        Matrix4 ghost = getPlayerSystemState().getGhost();
        Vector3 translation = getPlayerSystemState().getTranslation();

        ghost.set(0, 0, 0, 0);
        translation.set(0, 0, 0);
        translation = new Vector3();
        characterComponent.getGhostObject().getWorldTransform(ghost);
        ghost.getTranslation(translation);
        modelComponent.getInstance().transform.set(
                translation.x, translation.y, translation.z,
                camera.direction.x, camera.direction.y, camera.direction.z,
                0);
        camera.position.set(translation.x, translation.y, translation.z);
        camera.update(true);

        playerSystemState.getSkyEntity().getComponent(ModelComponent.class).getInstance()
                .transform.setToTranslation(translation.x, translation.y, translation.z);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            characterComponent.getCharacterController().setJumpSpeed(playerSystemState.getJumpForce());
            characterComponent.getCharacterController().jump();
        }

        if (Gdx.input.justTouched()) {
            GunShooter.fire(playerSystemState);
        }
    }

    private Vector3 getWalkDirection(Vector3 tmp, Camera camera, CharacterComponent characterComponent) {
        Vector3 walkDirection = characterComponent.getWalkDirection();
        walkDirection.set(0, 0, 0);

        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            getAndroidPlayerControl(tmp, camera, walkDirection);

        } else {
            getDesktopPlayerControl(tmp, camera, walkDirection);
        }
        return walkDirection;
    }

    private void getDesktopPlayerControl(Vector3 tmp, Camera camera, Vector3 walkDirection) {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) walkDirection.add(camera.direction);
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) walkDirection.sub(camera.direction);
        if (Gdx.input.isKeyPressed(Input.Keys.A)) tmp.set(camera.direction).crs(camera.up).scl(-1);
        if (Gdx.input.isKeyPressed(Input.Keys.S)) tmp.set(camera.direction).crs(camera.up);
    }

    private void getAndroidPlayerControl(Vector3 tmp, Camera camera, Vector3 walkDirection) {
        if (ControllerWidget.getMovementVector().y > 0) walkDirection.add(camera.direction);
        if (ControllerWidget.getMovementVector().y < 0) walkDirection.sub(camera.direction);
        if (ControllerWidget.getMovementVector().x < 0) tmp.set(camera.direction).crs(camera.up).scl(-1);
        if (ControllerWidget.getMovementVector().x > 0) tmp.set(camera.direction).crs(camera.up);
    }

    private void updateStatus() {
        GameUI gameUI = playerSystemState.getGameUI();
        PlayerComponent playerComponent = playerSystemState.getPlayerComponent();
        gameUI.getHealthWidget().setValue(playerComponent.getHealth());
    }

    private void checkGameOver() {
        if (playerSystemState.getPlayerComponent().getHealth() <= 0 && !Settings.Paused) {
            Settings.Paused = true;
            playerSystemState.getGameUI().getGameOverWidget().gameOver();
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