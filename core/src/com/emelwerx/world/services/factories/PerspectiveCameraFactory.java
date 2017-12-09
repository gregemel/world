package com.emelwerx.world.services.factories;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.emelwerx.world.WorldAdapter;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.databags.systemstates.RenderSystemState;

public class PerspectiveCameraFactory {

    public static PerspectiveCamera create(World world, RenderSystemState renderSystemState) {
        com.badlogic.gdx.graphics.PerspectiveCamera perspectiveCamera = new com.badlogic.gdx.graphics.PerspectiveCamera(
                renderSystemState.getFOV(),
                WorldAdapter.VIRTUAL_WIDTH,
                WorldAdapter.VIRTUAL_HEIGHT);
        perspectiveCamera.far = 10000f;
        renderSystemState.setWorldPerspectiveCamera(perspectiveCamera);
        world.setWorldPerspectiveCamera(perspectiveCamera);
        return perspectiveCamera;
    }
}