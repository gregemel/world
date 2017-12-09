package com.emelwerx.world.services.factories;


import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch;
import com.emelwerx.world.databags.systemstates.RenderSystemState;

public class ParticleSystemFactory {

    public static void create(RenderSystemState renderSystemState, PerspectiveCamera perspectiveCamera) {
        ParticleSystem particleSystem = ParticleSystem.get();
        BillboardParticleBatch billboardParticleBatch = new BillboardParticleBatch();
        billboardParticleBatch.setCamera(perspectiveCamera);
        particleSystem.add(billboardParticleBatch);
        renderSystemState.setParticleSystem(particleSystem);
    }
}