package com.emelwerx.world.services.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch;
import com.badlogic.gdx.math.Vector3;
import com.emelwerx.world.WorldCore;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.databags.systemstates.RenderSystemState;
import com.emelwerx.world.systems.RenderSystem;

public class RenderSystemFactory {

    public static RenderSystem create(World world) {
        Gdx.app.log("RenderSystemFactory", "creating");

        RenderSystemState renderSystemState = new RenderSystemState();
        PerspectiveCamera worldCamera = createWorldPerspectiveCamera(world, renderSystemState);
        attachEnvironment(renderSystemState);
        attachModelBatch(renderSystemState);
        attachPlayerItemCamera(renderSystemState);
        renderSystemState.setPosition(new Vector3());
        attachParticleSystem(renderSystemState, worldCamera);

        return new RenderSystem(renderSystemState);
    }

    private static PerspectiveCamera createWorldPerspectiveCamera(World world, RenderSystemState renderSystemState) {
        PerspectiveCamera perspectiveCamera = new PerspectiveCamera(
                renderSystemState.getFOV(),
                WorldCore.VIRTUAL_WIDTH,
                WorldCore.VIRTUAL_HEIGHT);
        perspectiveCamera.far = 10000f;
        renderSystemState.setWorldPerspectiveCamera(perspectiveCamera);
        world.setPerspectiveCamera(perspectiveCamera);
        return perspectiveCamera;
    }

    private static void attachEnvironment(RenderSystemState renderSystemState) {
        Environment environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1f));
        DirectionalShadowLight shadowLight = new DirectionalShadowLight(
                1024 * 5,
                1024 * 5,
                200f,
                200f,
                1f,
                300f);
        shadowLight.set(0.8f, 0.8f, 0.8f, 0, -0.1f, 0.1f);
        environment.add(shadowLight);
        environment.shadowMap = shadowLight;

        renderSystemState.setShadowLight(shadowLight);
        renderSystemState.setEnvironment(environment);
    }

    private static void attachModelBatch(RenderSystemState renderSystemState) {
        ModelBatch batch = new ModelBatch();
        renderSystemState.setBatch(batch);
    }

    private static void attachPlayerItemCamera(RenderSystemState renderSystemState) {
        PerspectiveCamera itemCamera = new PerspectiveCamera(renderSystemState.getFOV(),
                WorldCore.VIRTUAL_WIDTH, WorldCore.VIRTUAL_HEIGHT);
        itemCamera.far = 100f;
        renderSystemState.setPlayerItemCamera(itemCamera);
    }

    private static void attachParticleSystem(RenderSystemState renderSystemState, PerspectiveCamera perspectiveCamera) {
        ParticleSystem particleSystem = ParticleSystem.get();
        BillboardParticleBatch billboardParticleBatch = new BillboardParticleBatch();
        billboardParticleBatch.setCamera(perspectiveCamera);
        particleSystem.add(billboardParticleBatch);
        renderSystemState.setParticleSystem(particleSystem);
    }
}
