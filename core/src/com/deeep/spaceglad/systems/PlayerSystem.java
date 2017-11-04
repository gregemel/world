package com.deeep.spaceglad.systems;

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
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.deeep.spaceglad.Settings;
import com.deeep.spaceglad.UI.ControllerWidget;
import com.deeep.spaceglad.UI.GameUI;
import com.deeep.spaceglad.databags.AnimationComponent;
import com.deeep.spaceglad.databags.CharacterComponent;
import com.deeep.spaceglad.databags.MonsterComponent;
import com.deeep.spaceglad.databags.ModelComponent;
import com.deeep.spaceglad.databags.PlayerComponent;
import com.deeep.spaceglad.databags.PlayerSystemState;
import com.deeep.spaceglad.databags.StatusComponent;

public class PlayerSystem extends EntitySystem implements EntityListener, InputProcessor {

    private PlayerSystemState playerSystemState;

    public PlayerSystemState getPlayerSystemState() {
        return playerSystemState;
    }

    public void setPlayerSystemState(PlayerSystemState playerSystemState) {
        this.playerSystemState = playerSystemState;
    }

    @Override
    public void addedToEngine(Engine engine) {
        Gdx.app.log("PlayerSystem", String.format("addedToEngine: %s", engine.toString()));
        engine.addEntityListener(Family.all(PlayerComponent.class).get(), this);
    }

    @Override
    public void update(float delta) {
        if (getPlayerSystemState().getPlayer() == null) return;
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
        characterComponent.getWalkDirection().set(0, 0, 0);

        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            if (ControllerWidget.getMovementVector().y > 0) characterComponent.getWalkDirection().add(camera.direction);
            if (ControllerWidget.getMovementVector().y < 0) characterComponent.getWalkDirection().sub(camera.direction);
            if (ControllerWidget.getMovementVector().x < 0) tmp.set(camera.direction).crs(camera.up).scl(-1);
            if (ControllerWidget.getMovementVector().x > 0) tmp.set(camera.direction).crs(camera.up);
            characterComponent.getWalkDirection().add(tmp);
            characterComponent.getWalkDirection().scl(10f * delta);
            characterComponent.getCharacterController().setWalkDirection(characterComponent.getWalkDirection());
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) characterComponent.getWalkDirection().add(camera.direction);
            if (Gdx.input.isKeyPressed(Input.Keys.S)) characterComponent.getWalkDirection().sub(camera.direction);
            if (Gdx.input.isKeyPressed(Input.Keys.A)) tmp.set(camera.direction).crs(camera.up).scl(-1);
            if (Gdx.input.isKeyPressed(Input.Keys.D)) tmp.set(camera.direction).crs(camera.up);
            characterComponent.getWalkDirection().add(tmp);
            characterComponent.getWalkDirection().scl(10f * delta);
            characterComponent.getCharacterController().setWalkDirection(characterComponent.getWalkDirection());
        }

        Matrix4 ghost = getPlayerSystemState().getGhost();
        Vector3 translation = getPlayerSystemState().getTranslation();

        ghost.set(0, 0, 0, 0);
        translation.set(0, 0, 0);
        translation = new Vector3();
        characterComponent.getGhostObject().getWorldTransform(ghost);   //TODO export this
        ghost.getTranslation(translation);
        modelComponent.getInstance().transform.set(translation.x, translation.y, translation.z, camera.direction.x, camera.direction.y, camera.direction.z, 0);
        camera.position.set(translation.x, translation.y, translation.z);
        camera.update(true);

        playerSystemState.getDome().getComponent(ModelComponent.class).getInstance()
                .transform.setToTranslation(translation.x, translation.y, translation.z);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            // TODO change this back to 25
            characterComponent.getCharacterController().setJumpSpeed(25);
            characterComponent.getCharacterController().jump();
        }

        if (Gdx.input.justTouched()) {
            fire();
        }
    }

    private void updateStatus() {
        GameUI gameUI = playerSystemState.getGameUI();
        PlayerComponent playerComponent = playerSystemState.getPlayerComponent();
        gameUI.healthWidget.setValue(playerComponent.health);
    }


    private void fire() {
        Gdx.app.log("PlayerSystem", "fire");
        Ray ray = playerSystemState.getCamera().getPickRay(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        Vector3 rayFrom = playerSystemState.getRayFrom();
        Vector3 rayTo = playerSystemState.getRayTo();
        ClosestRayResultCallback rayTestCB = playerSystemState.getRayTestCB();

        rayFrom.set(ray.origin);
        rayTo.set(ray.direction).scl(50f).add(rayFrom);
        rayTestCB.setCollisionObject(null);
        rayTestCB.setClosestHitFraction(1f);
        rayTestCB.setRayFromWorld(rayFrom);
        rayTestCB.setRayToWorld(rayTo);

        playerSystemState.getGameWorld().getPhysicsSystem().getPhysicsSystemState()
                .getCollisionWorld().rayTest(rayFrom, rayTo, rayTestCB);

        if (rayTestCB.hasHit()) {
            final btCollisionObject obj = rayTestCB.getCollisionObject();
            if (((Entity) obj.userData).getComponent(MonsterComponent.class) != null) {
                if(((Entity) obj.userData).getComponent(StatusComponent.class).alive) {
                    ((Entity) obj.userData).getComponent(StatusComponent.class).setAlive(false);
                    PlayerComponent.score += 100;
                }
            }
        }

        playerSystemState.getAnimationService().animate(
                playerSystemState.getGun().getComponent(AnimationComponent.class), "Armature|shoot", 1, 3);

    }

    private void checkGameOver() {
        if (playerSystemState.getPlayerComponent().health <= 0 && !Settings.Paused) {
            Settings.Paused = true;
            playerSystemState.getGameUI().gameOverWidget.gameOver();
        }
    }

    @Override
    public void entityAdded(Entity entity) {
        Gdx.app.log("PlayerSystem", "entityAdded");
        playerSystemState.setPlayer(entity);
        playerSystemState.setPlayerComponent(entity.getComponent(PlayerComponent.class));
        playerSystemState.setCharacterComponent(entity.getComponent(CharacterComponent.class));
        playerSystemState.setModelComponent(entity.getComponent(ModelComponent.class));
    }

    @Override
    public void entityRemoved(Entity entity) {
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