package com.emelwerx.world.services.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.databags.components.SceneComponent;
import com.emelwerx.world.databags.systemstates.MotionState;

public class SceneComponentFactory {

    public static void create(Entity groundEntity, ModelComponent modelComponent) {
        SceneComponent sceneComponent = new SceneComponent();
        btCollisionShape shape = Bullet.obtainStaticNodeShape(modelComponent.getModel().nodes);
        btRigidBody rigidBody = createRigidBody(sceneComponent, shape);
        sceneComponent.setBody(rigidBody);
        sceneComponent.getBody().userData = groundEntity;
        attachNewMotionState(modelComponent, sceneComponent, rigidBody);
        groundEntity.add(sceneComponent);
    }

    private static btRigidBody createRigidBody(SceneComponent sceneComponent, btCollisionShape shape) {
        btRigidBody.btRigidBodyConstructionInfo bodyInfo =
                new btRigidBody.btRigidBodyConstructionInfo(0, null, shape, Vector3.Zero);
        sceneComponent.setBodyInfo(bodyInfo);
        return new btRigidBody(bodyInfo);
    }

    private static void attachNewMotionState(ModelComponent modelComponent, SceneComponent sceneComponent, btRigidBody rigidBody) {
        MotionState motionState = new MotionState(modelComponent.getInstance().transform);
        sceneComponent.setMotionState(motionState);
        rigidBody.setMotionState(motionState);
    }
}