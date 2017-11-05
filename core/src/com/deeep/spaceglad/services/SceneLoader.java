package com.deeep.spaceglad.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.utils.TextureProvider;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.UBJsonReader;
import com.deeep.spaceglad.physics.MotionState;
import com.deeep.spaceglad.databags.PhysicsComponent;
import com.deeep.spaceglad.databags.ModelComponent;
import com.deeep.spaceglad.databags.Scene;

import static java.lang.String.format;

public class SceneLoader {

    public static Scene load(String name, int x, int y, int z) {
        Gdx.app.log("SceneLoader", format("load %s, %d, %d, %d", name, x, y, z));

        Scene scene = new Scene();
        scene.setName(name);
        scene.setSky(loadSky("spacedome", x, y, z));
        scene.setGround(loadGround("arena", x, y, z));

        return scene;
    }

    private static Entity loadSky(String name, int x, int y, int z) {
        Gdx.app.log("loadSky", format("load %s, %d, %d, %d", name, x, y, z));
        ModelService modelService = new ModelService();
        Model model = modelService.getSkyModel(name);
        ModelComponent modelComponent = modelService.create(model, x, y, z);

        Entity entity = new Entity();
        entity.add(modelComponent);
        return entity;
    }

    private static Entity loadGround(String name, int x, int y, int z) {
        Gdx.app.log("loadGround", format("load %s, %d, %d, %d", name, x, y, z));
        Entity entity = new Entity();

        ModelService modelService = new ModelService();
        Model model = modelService.loadModel(name);
        ModelComponent modelComponent = modelService.create(model, x, y, z);
        entity.add(modelComponent);

        PhysicsComponent physicsComponent = new PhysicsComponent();
        btCollisionShape shape = Bullet.obtainStaticNodeShape(model.nodes);

        physicsComponent.setBodyInfo(
                new btRigidBody.btRigidBodyConstructionInfo(
                        0, null, shape, Vector3.Zero));

        physicsComponent.setBody(
                new btRigidBody(physicsComponent.getBodyInfo()));

        physicsComponent.getBody().userData = entity;
        physicsComponent.setMotionState(
                new MotionState(modelComponent.getInstance().transform));

        ((btRigidBody) physicsComponent.getBody()).setMotionState(physicsComponent.getMotionState());

        entity.add(physicsComponent);

        return entity;
    }

}
