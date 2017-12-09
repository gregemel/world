package com.emelwerx.world.services.factories;


import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.emelwerx.world.WorldAdapter;
import com.emelwerx.world.databags.systemstates.RenderSystemState;

public class PlayerItemCameraFactory {

    public static void create(RenderSystemState renderSystemState) {
        PerspectiveCamera itemCamera = new PerspectiveCamera(renderSystemState.getFOV(),
                WorldAdapter.VIRTUAL_WIDTH, WorldAdapter.VIRTUAL_HEIGHT);
        itemCamera.far = 100f;
        renderSystemState.setPlayerItemCamera(itemCamera);
    }
}