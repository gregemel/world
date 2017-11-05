package com.deeep.spaceglad.services;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch;
import com.badlogic.gdx.math.Vector3;
import com.deeep.spaceglad.WorldCore;
import com.deeep.spaceglad.databags.RenderSystemState;
import com.deeep.spaceglad.systems.RenderSystem;

public class RenderSystemFactory {

    public RenderSystem create() {
        Gdx.app.log("RenderSystemFactory", "creating");
        RenderSystem renderSystem = new RenderSystem();
        RenderSystemState renderSystemState = new RenderSystemState();

        PerspectiveCamera perspectiveCamera = getPerspectiveCamera(renderSystemState);
        renderSystemState.setPerspectiveCamera(perspectiveCamera);

        Environment environment = getAttributes(renderSystemState);
        renderSystemState.setEnvironment(environment);

        ModelBatch batch = new ModelBatch();
        renderSystemState.setBatch(batch);

        PerspectiveCamera itemCamera = getItemPerspectiveCamera(renderSystemState);
        renderSystemState.setGunCamera(itemCamera);

        renderSystemState.setPosition(new Vector3());

        ParticleSystem particleSystem = ParticleSystem.get();

        BillboardParticleBatch billboardParticleBatch = getBillboardParticleBatch(perspectiveCamera);

        particleSystem.add(billboardParticleBatch);
        renderSystemState.setParticleSystem(particleSystem);

        renderSystem.setRenderSystemState(renderSystemState);

        return renderSystem;
    }

    private BillboardParticleBatch getBillboardParticleBatch(PerspectiveCamera perspectiveCamera) {
        BillboardParticleBatch billboardParticleBatch = new BillboardParticleBatch();
        billboardParticleBatch.setCamera(perspectiveCamera);
        return billboardParticleBatch;
    }

    private PerspectiveCamera getItemPerspectiveCamera(RenderSystemState renderSystemState) {
        PerspectiveCamera gunCamera = new PerspectiveCamera(renderSystemState.getFOV(), WorldCore.VIRTUAL_WIDTH, WorldCore.VIRTUAL_HEIGHT);
        gunCamera.far = 100f;
        return gunCamera;
    }

    private PerspectiveCamera getPerspectiveCamera(RenderSystemState renderSystemState) {
        PerspectiveCamera perspectiveCamera = new PerspectiveCamera(
                renderSystemState.getFOV(),
                WorldCore.VIRTUAL_WIDTH,
                WorldCore.VIRTUAL_HEIGHT);

        perspectiveCamera.far = 10000f;
        return perspectiveCamera;
    }

    private Environment getAttributes(RenderSystemState renderSystemState) {
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
