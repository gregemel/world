package com.emelwerx.world.services;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.emelwerx.world.databags.ModelComponent;
import com.emelwerx.world.databags.MonsterSystemState;
import com.emelwerx.world.databags.ParticleComponent;

public class DeadMonsterService {

    public static void update(float delta, Entity monsterEntity, MonsterSystemState monsterSystemState) {
        ModelComponent monsterModelComponent = monsterEntity.getComponent(ModelComponent.class);
        updateOpacity(delta, monsterModelComponent);
        updateParticles(monsterEntity, monsterModelComponent, monsterSystemState);
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
