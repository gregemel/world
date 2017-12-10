package com.emelwerx.world.services.drawers;

import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.emelwerx.world.databags.systemstates.RenderSystemState;

public class ParticleDrawer {

    public static void draw(RenderSystemState state) {
        state.getBatch().begin(state.getWorldPerspectiveCamera());
        ParticleSystem particleSystem = state.getParticleSystem();
        particleSystem.update(); // not necessary
        particleSystem.begin();
        particleSystem.draw();
        particleSystem.end();
        state.getBatch().render(particleSystem);
        state.getBatch().end();
    }
}