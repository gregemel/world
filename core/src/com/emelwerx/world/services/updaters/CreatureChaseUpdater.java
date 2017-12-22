package com.emelwerx.world.services.updaters;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.dynamics.btKinematicCharacterController;
import com.emelwerx.world.databags.components.CharacterComponent;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.databags.systemstates.CreatureSystemState;

public class CreatureChaseUpdater {

    public static void update(
            float delta,
            Entity chasingEntity,
            CreatureSystemState state) {
        ModelComponent playerTarget = state.getPlayer().getComponent(ModelComponent.class);

        ModelComponent creatureModelComponent = chasingEntity.getComponent(ModelComponent.class);
        Quaternion rotation = CreatureRotationUpdater.update(playerTarget, creatureModelComponent, state);

        CharacterComponent chasingCharacter = state.getCharacterComponentMapper().get(chasingEntity);
        chasingCharacter.getCharacterDirection().set(-1, 0, 0).rot(creatureModelComponent.getInstance().transform);

        CreatureWalkDirectionUpdater.update(delta, chasingCharacter);

        Vector3 translation = CreatureTransationUpdater.update(state, chasingCharacter);

        creatureModelComponent.getInstance().transform.set(
                translation.x, translation.y, translation.z,
                rotation.x, rotation.y, rotation.z,
                rotation.w);
    }
}