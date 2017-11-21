package com.emelwerx.world.services;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.emelwerx.world.ui.widgets.ControllerWidget;
import com.emelwerx.world.databags.CharacterComponent;
import com.emelwerx.world.databags.ModelComponent;
import com.emelwerx.world.databags.PlayerSystemState;

import static java.lang.String.format;

public class PlayerMovementService {

    private static final int forward = Input.Keys.W;
    private static final int backwards = Input.Keys.Z;
    private static final int left = Input.Keys.A;
    private static final int right = Input.Keys.S;

    private static float lastLog = 5.1f;

    public static void update(float delta, PlayerSystemState playerSystemState) {
        CharacterComponent characterComponent = playerSystemState.getCharacterComponent();
        ModelComponent modelComponent = playerSystemState.getModelComponent();
        Camera camera = playerSystemState.getCamera();

        updatePlayerDirection(delta, playerSystemState, characterComponent, modelComponent, camera);
        updateTranslation(playerSystemState, characterComponent, modelComponent, camera);
        checkAttack(playerSystemState);
        checkJump(playerSystemState, characterComponent);
    }

    private static void updatePlayerDirection(float delta, PlayerSystemState playerSystemState, CharacterComponent characterComponent, ModelComponent modelComponent, Camera camera) {

        float deltaX;
        float deltaY;

        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            deltaX = -ControllerWidget.getWatchVector().x * 1.5f;
            deltaY = ControllerWidget.getWatchVector().y * 1.5f;
        } else {
            deltaX = -Gdx.input.getDeltaX() * 0.5f;
            deltaY = -Gdx.input.getDeltaY() * 0.5f;
        }


        Vector3 tmp = playerSystemState.getTmp();
        tmp.set(0, 0, 0);
        camera.rotate(camera.up, deltaX);
        tmp.set(camera.direction).crs(camera.up).nor();
        camera.direction.rotate(tmp, deltaY);
        tmp.set(0, 0, 0);

        characterComponent.getCharacterDirection().set(-1, 0, 0).rot(modelComponent.getInstance().transform).nor();

        Vector3 walkDirection = getWalkDirection(tmp, camera, characterComponent);

        lastLog+=delta;
        if(lastLog>5f) {
            lastLog=0f;

            Gdx.app.log("PlayerMovementService", format("walkDirection(%s), tmp(%s)",
                    walkDirection.toString(), tmp.toString()));

            Gdx.app.log("**player location**", format("modelComponent.getInstance().transform\n%s", modelComponent.getInstance().transform));

        }

        walkDirection.add(tmp);
        walkDirection.scl(10f * delta);
        characterComponent.getCharacterController().setWalkDirection(walkDirection);
    }

    private static Vector3 getWalkDirection(Vector3 tmp, Camera camera, CharacterComponent characterComponent) {
        Vector3 walkDirection = characterComponent.getWalkDirection();
        walkDirection.set(0, 0, 0);

        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            getAndroidPlayerControl(tmp, camera, walkDirection);

        } else {
            getDesktopPlayerControl(tmp, camera, walkDirection);
        }
        return walkDirection;
    }

    private static void getDesktopPlayerControl(Vector3 tmp, Camera camera, Vector3 walkDirection) {
        if (Gdx.input.isKeyPressed(forward)) walkDirection.add(camera.direction);
        if (Gdx.input.isKeyPressed(backwards)) walkDirection.sub(camera.direction);
        if (Gdx.input.isKeyPressed(left)) tmp.set(camera.direction).crs(camera.up).scl(-1);
        if (Gdx.input.isKeyPressed(right)) tmp.set(camera.direction).crs(camera.up);
    }

    private static void getAndroidPlayerControl(Vector3 tmp, Camera camera, Vector3 walkDirection) {
        if (ControllerWidget.getMovementVector().y > 0) walkDirection.add(camera.direction);
        if (ControllerWidget.getMovementVector().y < 0) walkDirection.sub(camera.direction);
        if (ControllerWidget.getMovementVector().x < 0) tmp.set(camera.direction).crs(camera.up).scl(-1);
        if (ControllerWidget.getMovementVector().x > 0) tmp.set(camera.direction).crs(camera.up);
    }

    private static void updateTranslation(PlayerSystemState playerSystemState, CharacterComponent characterComponent, ModelComponent modelComponent, Camera camera) {
        Vector3 translation = playerSystemState.getTranslation();
        translation.set(0, 0, 0);

        setPlayerGhostTranslation(playerSystemState, characterComponent, translation);

        modelComponent.getInstance().transform.set(
                translation.x, translation.y, translation.z,
                camera.direction.x, camera.direction.y, camera.direction.z,
                0);
        camera.position.set(translation.x, translation.y, translation.z);
        camera.update(true);

        playerSystemState.getSkyEntity().getComponent(ModelComponent.class).getInstance()
                .transform.setToTranslation(translation.x, translation.y, translation.z);
    }

    private static void setPlayerGhostTranslation(PlayerSystemState playerSystemState, CharacterComponent characterComponent, Vector3 translation) {
        Matrix4 ghost = playerSystemState.getGhost();
        ghost.set(0, 0, 0, 0);
        characterComponent.getGhostObject().getWorldTransform(ghost);
        ghost.getTranslation(translation);
    }

    private static void checkAttack(PlayerSystemState playerSystemState) {
        if (Gdx.input.justTouched()) {
            GunShooter.fire(playerSystemState);
        }
    }

    private static void checkJump(PlayerSystemState playerSystemState, CharacterComponent characterComponent) {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            characterComponent.getCharacterController().setJumpSpeed(playerSystemState.getJumpForce());
            characterComponent.getCharacterController().jump();
        }
    }

}
