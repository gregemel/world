package com.emelwerx.world.services;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.emelwerx.world.databags.ModelComponent;
import com.emelwerx.world.databags.CreatureComponent;
import com.emelwerx.world.databags.CreatureSystemState;
import com.emelwerx.world.databags.ParticleComponent;

import static java.lang.String.format;

public class CreatureDyingUpdater {
    private static final float deathDuration = 3.4f;

    public static void update(float delta, Entity creatureEntity, CreatureSystemState creatureSystemState) {
        ModelComponent creatureModelComponent = creatureEntity.getComponent(ModelComponent.class);
        updateOpacity(delta, creatureModelComponent);
        updateParticles(creatureEntity, creatureModelComponent, creatureSystemState);

        CreatureComponent creatureComponent = creatureEntity.getComponent(CreatureComponent.class);
        float timeSinceDeath = creatureComponent.getTimeSinceDeath() + delta;
        if (timeSinceDeath >= deathDuration) {
            Gdx.app.log("CreatureDyingUpdater", format("times up for %s", creatureEntity.toString()));
            creatureSystemState.getGameWorld().getEntityEngine().removeEntity(creatureEntity);
            creatureSystemState.getGameWorld().getPhysicsSystem().removeBody(creatureEntity);
        } else {
            creatureComponent.setTimeSinceDeath(timeSinceDeath);
        }
    }

    private static void updateOpacity(float delta, ModelComponent modelComponent) {
        BlendingAttribute blendingAttribute = modelComponent.getBlendingAttribute();
        if (blendingAttribute != null) {
            blendingAttribute.opacity = blendingAttribute.opacity - delta / 3;
        }
    }

    private static void updateParticles(Entity entity, ModelComponent creatureModelComponent, CreatureSystemState creatureSystemState) {
        ParticleComponent particleComponent = entity.getComponent(ParticleComponent.class);

        boolean isParticleStartNeeded = !particleComponent.isUsed();
        if (isParticleStartNeeded) {
            particleComponent.setUsed(true);
            ParticleEffect particleEffect = ParticleFactory.createParticleEffect(creatureModelComponent, particleComponent);
            ParticleSystem particleSystem = creatureSystemState.getGameWorld().getRenderSystem().getRenderSystemState().getParticleSystem();
            particleSystem.add(particleEffect);
        }
    }
}
