package com.emelwerx.world.services.updaters;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.emelwerx.world.databags.systemstates.PlayerSystemState;
import com.emelwerx.world.services.ui.widgets.ControllerWidget;

public class CameraRotationUpdater {
    public static void update(PlayerSystemState playerSystemState, Camera worldPerspectiveCamera) {
        float deltaX;
        float deltaY;

        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            deltaX = -ControllerWidget.getWatchVector().x * 1.5f;
            deltaY = ControllerWidget.getWatchVector().y * 1.5f;
        } else {
            deltaX = -Gdx.input.getDeltaX() * 0.5f;
            deltaY = -Gdx.input.getDeltaY() * 0.5f;
        }

        Vector3 cameraRotation = playerSystemState.getTmp();
        cameraRotation.set(0, 0, 0);
        worldPerspectiveCamera.rotate(worldPerspectiveCamera.up, deltaX);
        cameraRotation.set(worldPerspectiveCamera.direction).crs(worldPerspectiveCamera.up).nor();
        worldPerspectiveCamera.direction.rotate(cameraRotation, deltaY);
    }
}