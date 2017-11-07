package com.emelwerx.world.services;

import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.emelwerx.world.databags.RenderSystemState;

public class ParticleDrawer {

    public static void draw(RenderSystemState renderSystemState) {
        renderSystemState.getBatch().begin(renderSystemState.getPerspectiveCamera());
        ParticleSystem particleSystem = renderSystemState.getParticleSystem();
        particleSystem.update(); // not necessary
        particleSystem.begin();
        particleSystem.draw();
        particleSystem.end();
        renderSystemState.getBatch().render(particleSystem);
        renderSystemState.getBatch().end();
    }

}
