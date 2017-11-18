package com.emelwerx.world.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.emelwerx.world.databags.CharacterComponent;
import com.emelwerx.world.databags.ModelComponent;
import com.emelwerx.world.databags.CreatureSystemState;

public class CreatureAttackUpdater {

    public static void update(
            float delta,
            ModelComponent playerModelComponent,
            Entity creatureEntity,
            CreatureSystemState creatureSystemState) {
        ModelComponent creatureModelComponent = creatureEntity.getComponent(ModelComponent.class);
        Quaternion rotationToFacePlayer = getRotationToFaceTarget(delta,
                playerModelComponent, creatureEntity, creatureModelComponent, creatureSystemState);

        Vector3 translation = creatureSystemState.getTranslation();

        creatureModelComponent.getInstance().transform.set(
                translation.x, translation.y, translation.z,
                rotationToFacePlayer.x, rotationToFacePlayer.y, rotationToFacePlayer.z,
                rotationToFacePlayer.w);
    }

    private static Quaternion getRotationToFaceTarget(float delta,
                                                    ModelComponent target,
                                                    Entity entity,
                                                    ModelComponent creatureModelComponent,
                                                    CreatureSystemState creatureSystemState) {

        float theta = getTheta(target, creatureModelComponent, creatureSystemState);

        Quaternion rot = creatureSystemState.getQuaternion().setFromAxis(0, 1, 0, (float) Math.toDegrees(theta) + 90);

        CharacterComponent characterComponent = creatureSystemState.getCm().get(entity);
        characterComponent.getCharacterDirection().set(-1, 0, 0).rot(creatureModelComponent.getInstance().transform);

        Vector3 walkDirection = getWalkDirection(delta, characterComponent);
        characterComponent.getCharacterController().setWalkDirection(walkDirection);

        Matrix4 ghostMatrix = creatureSystemState.getGhostMatrix();
        ghostMatrix.set(0, 0, 0, 0);
        Vector3 translation = creatureSystemState.getTranslation();
        translation.set(0, 0, 0);
        characterComponent.getGhostObject().getWorldTransform(ghostMatrix);
        ghostMatrix.getTranslation(translation);

        return rot;
    }

    private static float getTheta(ModelComponent target,
                                  ModelComponent creatureModelComponent,
                                  CreatureSystemState creatureSystemState) {
        Vector3 targetPosition = creatureSystemState.getPlayerPosition();
        target.getInstance().transform.getTranslation(targetPosition);

        Vector3 creaturePosition = creatureSystemState.getCurrentCreaturePosition();
        creatureModelComponent.getInstance().transform.getTranslation(creaturePosition);

        float dX = targetPosition.x - creaturePosition.x;
        float dZ = targetPosition.z - creaturePosition.z;
        return (float) (Math.atan2(dX, dZ));
    }

    private static Vector3 getWalkDirection(float delta, CharacterComponent characterComponent) {
        Vector3 walkDirection = characterComponent.getWalkDirection();
        walkDirection.set(0, 0, 0);
        walkDirection.add(characterComponent.getCharacterDirection());
        walkDirection.scl(10f * delta);   //TODO make this change on difficulty
        return walkDirection;
    }
}


