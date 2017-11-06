package com.emelwerx.world.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.bullet.collision.btCapsuleShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btPairCachingGhostObject;
import com.badlogic.gdx.physics.bullet.dynamics.btKinematicCharacterController;
import com.emelwerx.world.databags.CharacterComponent;
import com.emelwerx.world.databags.ModelComponent;

public class CharacterComponentFactory {
    public static CharacterComponent create(Entity entity, ModelComponent modelComponent) {
        CharacterComponent characterComponent = new CharacterComponent();
        characterComponent.setGhostObject(
                new btPairCachingGhostObject());

        characterComponent.getGhostObject().setWorldTransform(modelComponent.getInstance().transform);
        characterComponent.setGhostShape(
                new btCapsuleShape(2f, 2f));

        characterComponent.getGhostObject().setCollisionShape(characterComponent.getGhostShape());
        characterComponent.getGhostObject().setCollisionFlags(btCollisionObject.CollisionFlags.CF_CHARACTER_OBJECT);

        characterComponent.setCharacterController(
                new btKinematicCharacterController(
                        characterComponent.getGhostObject(),
                        characterComponent.getGhostShape(),
                        .35f));

        characterComponent.getGhostObject().userData = entity;
        return characterComponent;
    }

}
