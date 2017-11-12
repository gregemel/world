package com.emelwerx.world.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.emelwerx.world.databags.CharacterComponent;
import com.emelwerx.world.databags.ModelComponent;
import com.emelwerx.world.databags.MonsterSystemState;

public class MonsterAttachingUpdater {

    public static void update(
            float delta,
            ModelComponent playerModelComponent,
            Entity monsterEntity,
            MonsterSystemState monsterSystemState) {
        ModelComponent monsterModelComponent = monsterEntity.getComponent(ModelComponent.class);
        Quaternion rotationToFacePlayer = getRotationToFaceTarget(delta,
                playerModelComponent, monsterEntity, monsterModelComponent, monsterSystemState);

        Vector3 translation = monsterSystemState.getTranslation();

        monsterModelComponent.getInstance().transform.set(
                translation.x, translation.y, translation.z,
                rotationToFacePlayer.x, rotationToFacePlayer.y, rotationToFacePlayer.z,
                rotationToFacePlayer.w);
    }

    private static Quaternion getRotationToFaceTarget(float delta,
                                                    ModelComponent target,
                                                    Entity entity,
                                                    ModelComponent monsterModelComponent,
                                                    MonsterSystemState monsterSystemState) {

        float theta = getTheta(target, monsterModelComponent, monsterSystemState);

        Quaternion rot = monsterSystemState.getQuaternion().setFromAxis(0, 1, 0, (float) Math.toDegrees(theta) + 90);

        CharacterComponent characterComponent = monsterSystemState.getCm().get(entity);
        characterComponent.getCharacterDirection().set(-1, 0, 0).rot(monsterModelComponent.getInstance().transform);

        Vector3 walkDirection = getWalkDirection(delta, characterComponent);
        characterComponent.getCharacterController().setWalkDirection(walkDirection);

        Matrix4 ghostMatrix = monsterSystemState.getGhostMatrix();
        ghostMatrix.set(0, 0, 0, 0);
        Vector3 translation = monsterSystemState.getTranslation();
        translation.set(0, 0, 0);
        characterComponent.getGhostObject().getWorldTransform(ghostMatrix);
        ghostMatrix.getTranslation(translation);

        return rot;
    }

    private static float getTheta(ModelComponent target,
                                  ModelComponent monsterModelComponent,
                                  MonsterSystemState monsterSystemState) {
        Vector3 targetPosition = monsterSystemState.getPlayerPosition();
        target.getInstance().transform.getTranslation(targetPosition);

        Vector3 monsterPosition = monsterSystemState.getCurrentMonsterPosition();
        monsterModelComponent.getInstance().transform.getTranslation(monsterPosition);

        float dX = targetPosition.x - monsterPosition.x;
        float dZ = targetPosition.z - monsterPosition.z;
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


