package com.emelwerx.world.services.updaters;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.emelwerx.world.services.ui.widgets.ControllerWidget;

public class PlayerInputUpdater {

    private static final int forward = Input.Keys.W;
    private static final int backwards = Input.Keys.Z;
    private static final int left = Input.Keys.A;
    private static final int right = Input.Keys.S;

    public static void update(Vector3 playerVector, Camera camera, Vector3 walkDirection) {
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            getAndroidPlayerControl(playerVector, camera, walkDirection);

        } else {
            getDesktopPlayerControl(playerVector, camera, walkDirection);
        }
    }

    private static void getDesktopPlayerControl(Vector3 playerVector, Camera camera, Vector3 walkDirection) {
        if (Gdx.input.isKeyPressed(forward)) walkDirection.add(camera.direction);
        if (Gdx.input.isKeyPressed(backwards)) walkDirection.sub(camera.direction);
        if (Gdx.input.isKeyPressed(left)) playerVector.set(camera.direction).crs(camera.up).scl(-1);
        if (Gdx.input.isKeyPressed(right)) playerVector.set(camera.direction).crs(camera.up);
    }

    private static void getAndroidPlayerControl(Vector3 tmp, Camera camera, Vector3 walkDirection) {
        if (ControllerWidget.getMovementVector().y > 0) walkDirection.add(camera.direction);
        if (ControllerWidget.getMovementVector().y < 0) walkDirection.sub(camera.direction);
        if (ControllerWidget.getMovementVector().x < 0) tmp.set(camera.direction).crs(camera.up).scl(-1);
        if (ControllerWidget.getMovementVector().x > 0) tmp.set(camera.direction).crs(camera.up);
    }
}