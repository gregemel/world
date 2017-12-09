package com.emelwerx.world.services.loaders;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.utils.JsonValue;
import com.emelwerx.world.databags.Scene;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.databags.components.SceneComponent;
import com.emelwerx.world.databags.systemstates.MotionState;
import com.emelwerx.world.services.factories.ModelComponentFactory;

import java.util.Locale;

import static java.lang.String.format;

public class GroundLoader {

    public static void load(int x, int y, int z, Scene scene, JsonValue jsonScene) {
        JsonValue ground = jsonScene.get("ground");
        String groundModelFilename = ground.getString("modelFile");
        Gdx.app.log("SceneLoader", format(Locale.US,"loadGround %s, %d, %d, %d", groundModelFilename, x, y, z));

        Entity groundEntity = new Entity();
        ModelComponent modelComponent = attachModelComponent(groundModelFilename, x, y, z, groundEntity);
        attachSceneComponent(groundEntity, modelComponent);
        scene.setGround(groundEntity);
    }

    private static ModelComponent attachModelComponent(String name, int x, int y, int z, Entity groundEntity) {
        Model model = ModelLoader.load(name);
        ModelComponent modelComponent = ModelComponentFactory.create(model, x, y, z);
        groundEntity.add(modelComponent);
        return modelComponent;
    }

    private static void attachSceneComponent(Entity groundEntity, ModelComponent modelComponent) {
        SceneComponent sceneComponent = new SceneComponent();
        btCollisionShape shape = Bullet.obtainStaticNodeShape(modelComponent.getModel().nodes);
        btRigidBody rigidBody = createStaticRigidBody(sceneComponent, shape);
        sceneComponent.setBody(rigidBody);
        sceneComponent.getBody().userData = groundEntity;
        attachMotionState(modelComponent, sceneComponent, rigidBody);
        groundEntity.add(sceneComponent);
    }

    private static void attachMotionState(ModelComponent modelComponent, SceneComponent sceneComponent, btRigidBody rigidBody) {
        MotionState motionState = new MotionState(modelComponent.getInstance().transform);
        sceneComponent.setMotionState(motionState);
        rigidBody.setMotionState(motionState);
    }

    private static btRigidBody createStaticRigidBody(SceneComponent sceneComponent, btCollisionShape shape) {
        btRigidBody.btRigidBodyConstructionInfo bodyInfo =
                new btRigidBody.btRigidBodyConstructionInfo(0, null, shape, Vector3.Zero);
        sceneComponent.setBodyInfo(bodyInfo);
        return new btRigidBody(bodyInfo);
    }
}