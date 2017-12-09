package com.emelwerx.world.services.updaters;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.emelwerx.world.databags.components.CharacterComponent;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.databags.systemstates.CreatureSystemState;

public class CreatureChaseUpdater {

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
                                                    ModelComponent targetModelComponent,
                                                    Entity entity,
                                                    ModelComponent hunterModelComponent,
                                                    CreatureSystemState creatureSystemState) {

        float theta = getTheta(targetModelComponent, hunterModelComponent);

        Quaternion rot = creatureSystemState.getQuaternion().setFromAxis(0, 1, 0, (float) Math.toDegrees(theta) + 90);

        CharacterComponent characterComponent = creatureSystemState.getCharacterComponentMapper().get(entity);
        characterComponent.getCharacterDirection().set(-1, 0, 0).rot(hunterModelComponent.getInstance().transform);

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
                                  ModelComponent hunter) {
        Vector3 targetPosition = target.getPosition();
        target.getInstance().transform.getTranslation(targetPosition);

        Vector3 hunterPosition = hunter.getPosition();
        hunter.getInstance().transform.getTranslation(hunterPosition);

        float dX = targetPosition.x - hunterPosition.x;
        float dZ = targetPosition.z - hunterPosition.z;
        return (float) (Math.atan2(dX, dZ));
    }

    private static Vector3 getWalkDirection(float delta, CharacterComponent characterComponent) {
        Vector3 walkDirection = characterComponent.getWalkDirection();
        walkDirection.set(0, 0, 0);
        walkDirection.add(characterComponent.getCharacterDirection());
        walkDirection.scl(10f * delta);
        return walkDirection;
    }
}