package com.emelwerx.world.services;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.emelwerx.world.databags.ModelComponent;
import com.emelwerx.world.databags.MonsterComponent;
import com.emelwerx.world.databags.MonsterSystemState;
import com.emelwerx.world.databags.ParticleComponent;

import static java.lang.String.format;

public class DeadMonsterService {
    private static final float deathDuration = 3.4f;

    public static void update(float delta, Entity monsterEntity, MonsterSystemState monsterSystemState) {
        ModelComponent monsterModelComponent = monsterEntity.getComponent(ModelComponent.class);
        updateOpacity(delta, monsterModelComponent);
        updateParticles(monsterEntity, monsterModelComponent, monsterSystemState);

        MonsterComponent monsterComponent = monsterEntity.getComponent(MonsterComponent.class);
        float timeSinceDeath = monsterComponent.getTimeSinceDeath() + delta;
        if (timeSinceDeath >= deathDuration) {
            Gdx.app.log("ThinkingSystem", format("times up for %s", monsterEntity.toString()));
            monsterSystemState.getGameWorld().getEntityEngine().removeEntity(monsterEntity);
            monsterSystemState.getGameWorld().getPhysicsSystem().removeBody(monsterEntity);
        } else {
            monsterComponent.setTimeSinceDeath(timeSinceDeath);
        }
    }

    private static void updateOpacity(float delta, ModelComponent modelComponent) {
        BlendingAttribute blendingAttribute = modelComponent.getBlendingAttribute();
        if (blendingAttribute != null) {
            blendingAttribute.opacity = blendingAttribute.opacity - delta / 3;
        }
    }

    private static void updateParticles(Entity entity, ModelComponent modelComponent, MonsterSystemState monsterSystemState) {
        ParticleComponent particleComponent = entity.getComponent(ParticleComponent.class);

        boolean isParticleStartNeeded = !particleComponent.isUsed();
        if (isParticleStartNeeded) {
            particleComponent.setUsed(true);
            ParticleEffect particleEffect = ParticleFactory.createParticleEffect(modelComponent, particleComponent);
            ParticleSystem particleSystem = monsterSystemState.getGameWorld().getRenderSystem().getRenderSystemState().getParticleSystem();
            particleSystem.add(particleEffect);
        }
    }
}
