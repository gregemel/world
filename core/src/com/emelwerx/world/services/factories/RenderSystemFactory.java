package com.emelwerx.world.services.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.emelwerx.world.WorldAdapter;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.databags.systemstates.RenderSystemState;
import com.emelwerx.world.systems.RenderSystem;

public class RenderSystemFactory {

    public static RenderSystem create(World world) {
        Gdx.app.log("RenderSystemFactory", "creating");

        RenderSystemState renderSystemState = new RenderSystemState();
        PerspectiveCamera worldCamera = PerspectiveCameraFactory.create(world, renderSystemState);
        EnvironmentFactory.create(renderSystemState);
        attachModelBatch(renderSystemState);
        attachPlayerItemCamera(renderSystemState);
        renderSystemState.setPosition(new Vector3());
        ParticleSystemFactory.create(renderSystemState, worldCamera);

        return new RenderSystem(renderSystemState);
    }

    private static void attachModelBatch(RenderSystemState renderSystemState) {
        ModelBatch batch = new ModelBatch();
        renderSystemState.setBatch(batch);
    }

    private static void attachPlayerItemCamera(RenderSystemState renderSystemState) {
        PerspectiveCamera itemCamera = new PerspectiveCamera(renderSystemState.getFOV(),
                WorldAdapter.VIRTUAL_WIDTH, WorldAdapter.VIRTUAL_HEIGHT);
        itemCamera.far = 100f;
        renderSystemState.setPlayerItemCamera(itemCamera);
    }
}
