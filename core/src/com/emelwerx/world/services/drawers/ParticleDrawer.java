package com.emelwerx.world.services.drawers;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.emelwerx.world.databags.systemstates.RenderSystemState;

public class ParticleDrawer {

    public static void draw(RenderSystemState state) {
        ModelBatch batch = state.getBatch();
        batch.begin(state.getWorldPerspectiveCamera());
        ParticleSystem particleSystem = state.getParticleSystem();
        particleSystem.update(); // not necessary
        particleSystem.begin();
        particleSystem.draw();
        particleSystem.end();
        batch.render(particleSystem);
        batch.end();
    }
}