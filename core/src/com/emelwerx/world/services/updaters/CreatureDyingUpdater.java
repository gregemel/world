package com.emelwerx.world.services.updaters;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.databags.components.CreatureComponent;
import com.emelwerx.world.databags.components.ModelComponent;

import static java.lang.String.format;

public class CreatureDyingUpdater {
    private static final float deathDuration = 3.4f;

    public static void update(float delta, Entity creatureEntity, World world) {
        ModelComponent creatureModelComponent = creatureEntity.getComponent(ModelComponent.class);
        updateOpacity(delta, creatureModelComponent);
        ParticleUpdater.update(creatureEntity, creatureModelComponent, world);
        checkForDone(delta, creatureEntity, world);
    }

    private static void updateOpacity(float delta, ModelComponent modelComponent) {
        BlendingAttribute blendingAttribute = modelComponent.getBlendingAttribute();
        if (blendingAttribute != null) {
            blendingAttribute.opacity = blendingAttribute.opacity - delta / 3;
        }
    }

    private static void checkForDone(float delta, Entity creatureEntity, World world) {
        CreatureComponent creatureComponent = creatureEntity.getComponent(CreatureComponent.class);
        float timeSinceDeath = creatureComponent.getTimeSinceDeath() + delta;
        if (timeSinceDeath >= deathDuration) {
            Gdx.app.log("CreatureDyingUpdater", format("times up for %s", creatureEntity.toString()));
            world.getEntityEngine().removeEntity(creatureEntity);
            world.getPhysicsSystem().removeBody(creatureEntity);
        } else {
            creatureComponent.setTimeSinceDeath(timeSinceDeath);
        }
    }
}
