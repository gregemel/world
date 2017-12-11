package com.emelwerx.world.services.updaters;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.databags.components.ParticleComponent;
import com.emelwerx.world.services.factories.ParticleComponentFactory;

public class ParticleUpdater {

    public static void update(Entity entity, ModelComponent creatureModelComponent, World world) {
        ParticleComponent particleComponent = entity.getComponent(ParticleComponent.class);
        boolean isParticleStartNeeded = !particleComponent.isUsed();
        if (isParticleStartNeeded) {
            particleComponent.setUsed(true);
            ParticleEffect particleEffect = ParticleComponentFactory.clone(creatureModelComponent, particleComponent);
            ParticleSystem particleSystem = world.getRenderSystem().getState().getParticleSystem();
            particleSystem.add(particleEffect);
        }
    }
}