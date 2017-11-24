package com.emelwerx.world.services.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCapsuleShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btPairCachingGhostObject;
import com.badlogic.gdx.physics.bullet.dynamics.btKinematicCharacterController;
import com.emelwerx.world.databags.components.CharacterComponent;
import com.emelwerx.world.databags.components.ModelComponent;

import static java.lang.String.format;

public class CharacterComponentFactory {
    public static CharacterComponent create(Entity entity, ModelComponent modelComponent) {
        Gdx.app.log("CharacterComponentFactory", format("creating character component: %s, %s",
                entity.toString(), modelComponent.toString()));

        CharacterComponent characterComponent = new CharacterComponent();
        attachController(entity, modelComponent, characterComponent);
        characterComponent.setWalkDirection(new Vector3());
        characterComponent.setCharacterDirection(new Vector3());
        return characterComponent;
    }

    private static void attachController(Entity entity, ModelComponent modelComponent, CharacterComponent characterComponent) {
        btPairCachingGhostObject ghostObject = createBtPairCachingGhostObject(entity, modelComponent);
        btCapsuleShape capsuleShape = createBtCapsuleShape(characterComponent, ghostObject);
        btKinematicCharacterController characterController = new btKinematicCharacterController(
                ghostObject,
                capsuleShape,
                .35f);
        characterComponent.setCharacterController(characterController);
    }

    private static btPairCachingGhostObject createBtPairCachingGhostObject(Entity entity, ModelComponent modelComponent) {
        btPairCachingGhostObject ghostObject = new btPairCachingGhostObject();
        ghostObject.userData = entity;
        ghostObject.setWorldTransform(modelComponent.getInstance().transform);
        ghostObject.setCollisionFlags(btCollisionObject.CollisionFlags.CF_CHARACTER_OBJECT);
        return ghostObject;
    }

    private static btCapsuleShape createBtCapsuleShape(CharacterComponent characterComponent, btPairCachingGhostObject ghostObject) {
        btCapsuleShape capsuleShape = new btCapsuleShape(2f, 2f);
        ghostObject.setCollisionShape(capsuleShape);
        characterComponent.setGhostObject(ghostObject);
        characterComponent.setGhostShape(capsuleShape);
        return capsuleShape;
    }
}
