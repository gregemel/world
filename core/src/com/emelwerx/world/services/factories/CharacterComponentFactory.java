package com.emelwerx.world.services.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
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
        btKinematicCharacterController characterController = getBtKinematicCharacterController(
                entity, modelComponent, characterComponent);
        characterComponent.setCharacterController(characterController);

        return characterComponent;
    }

    private static btKinematicCharacterController getBtKinematicCharacterController(
            Entity entity,ModelComponent modelComponent, CharacterComponent characterComponent) {

        btPairCachingGhostObject ghostObject = new btPairCachingGhostObject();
        ghostObject.userData = entity;
        ghostObject.setWorldTransform(modelComponent.getInstance().transform);
        ghostObject.setCollisionFlags(btCollisionObject.CollisionFlags.CF_CHARACTER_OBJECT);

        btCapsuleShape capsuleShape = new btCapsuleShape(2f, 2f);
        ghostObject.setCollisionShape(capsuleShape);

        characterComponent.setGhostObject(ghostObject);
        characterComponent.setGhostShape(capsuleShape);

        return new btKinematicCharacterController(
                ghostObject,
                capsuleShape,
                .35f);
    }

}
