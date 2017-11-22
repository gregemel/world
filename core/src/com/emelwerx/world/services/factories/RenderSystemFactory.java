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
import com.emelwerx.world.databags.systemstates.RenderSystemState;
import com.emelwerx.world.systems.RenderSystem;

public class RenderSystemFactory {

    public static RenderSystem create() {
        Gdx.app.log("RenderSystemFactory", "creating");
        RenderSystemState renderSystemState = new RenderSystemState();

        PerspectiveCamera perspectiveCamera = getPerspectiveCamera(renderSystemState);
        renderSystemState.setPerspectiveCamera(perspectiveCamera);

        Environment environment = getAttributes(renderSystemState);
        renderSystemState.setEnvironment(environment);

        ModelBatch batch = new ModelBatch();
        renderSystemState.setBatch(batch);

        PerspectiveCamera itemCamera = getItemPerspectiveCamera(renderSystemState);
        renderSystemState.setPlayerItemCamera(itemCamera);

        renderSystemState.setPosition(new Vector3());

        ParticleSystem particleSystem = ParticleSystem.get();

        BillboardParticleBatch billboardParticleBatch = getBillboardParticleBatch(perspectiveCamera);

        particleSystem.add(billboardParticleBatch);
        renderSystemState.setParticleSystem(particleSystem);

        return new RenderSystem(renderSystemState);
    }

    private static BillboardParticleBatch getBillboardParticleBatch(PerspectiveCamera perspectiveCamera) {
        BillboardParticleBatch billboardParticleBatch = new BillboardParticleBatch();
        billboardParticleBatch.setCamera(perspectiveCamera);
        return billboardParticleBatch;
    }

    private static PerspectiveCamera getItemPerspectiveCamera(RenderSystemState renderSystemState) {
        PerspectiveCamera gunCamera = new PerspectiveCamera(renderSystemState.getFOV(), WorldCore.VIRTUAL_WIDTH, WorldCore.VIRTUAL_HEIGHT);
        gunCamera.far = 100f;
        return gunCamera;
    }

    private static PerspectiveCamera getPerspectiveCamera(RenderSystemState renderSystemState) {
        PerspectiveCamera perspectiveCamera = new PerspectiveCamera(
                renderSystemState.getFOV(),
                WorldCore.VIRTUAL_WIDTH,
                WorldCore.VIRTUAL_HEIGHT);

        perspectiveCamera.far = 10000f;
        return perspectiveCamera;
    }

    private static Environment getAttributes(RenderSystemState renderSystemState) {
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
        return environment;
    }

}
