package com.emelwerx.world.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.bullet.collision.btCapsuleShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btPairCachingGhostObject;
import com.badlogic.gdx.physics.bullet.dynamics.btKinematicCharacterController;
import com.emelwerx.world.databags.CharacterComponent;
import com.emelwerx.world.databags.ModelComponent;

import static java.lang.String.format;

public class CharacterComponentFactory {
    public static CharacterComponent create(Entity entity, ModelComponent modelComponent) {
        Gdx.app.log("CharacterComponentFactory", format("creating character component: %s, %s",
                entity.toString(), modelComponent.toString()));

        CharacterComponent characterComponent = new CharacterComponent();

        btPairCachingGhostObject ghostObject = new btPairCachingGhostObject();
        characterComponent.setGhostObject(ghostObject);
        ghostObject.setWorldTransform(modelComponent.getInstance().transform);

        btCapsuleShape capsuleShape = new btCapsuleShape(2f, 2f);
        characterComponent.setGhostShape(capsuleShape);
        ghostObject.setCollisionShape(capsuleShape);
        ghostObject.setCollisionFlags(btCollisionObject.CollisionFlags.CF_CHARACTER_OBJECT);

        btKinematicCharacterController characterController = new btKinematicCharacterController(
                ghostObject,
                capsuleShape,
                .35f);
        characterComponent.setCharacterController(characterController);

        ghostObject.userData = entity;
        return characterComponent;
    }

}
