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
import com.deeep.spaceglad.bullet.MotionState;
import com.deeep.spaceglad.databags.BulletComponent;
import com.deeep.spaceglad.databags.ModelComponent;
import com.deeep.spaceglad.databags.Scene;

public class SceneLoader {

    public static Scene load(String name, int x, int y, int z) {

        Scene scene = new Scene();
        scene.setName(name);
        scene.setSky(loadSky("spacedome", x, y, z));
        scene.setGround(loadGround("arena", x, y, z));

        return scene;
    }

    private static Entity loadSky(String name, int x, int y, int z) {
        UBJsonReader jsonReader = new UBJsonReader();
        G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);

        Model model = modelLoader.loadModel(Gdx.files.getFileHandle("data/" + name + ".g3db", Files.FileType.Internal));

        ModelService modelService = new ModelService();
        ModelComponent modelComponent = modelService.create(model, x, y, z);

        Entity entity = new Entity();
        entity.add(modelComponent);
        return entity;
    }

    private static Entity loadGround(String name, int x, int y, int z) {
        Entity entity = new Entity();

        ModelLoader<?> modelLoader = new G3dModelLoader(new JsonReader());
        ModelData modelData = modelLoader.loadModelData(Gdx.files.internal("data/" + name + ".g3dj"));

        Model model = new Model(modelData, new TextureProvider.FileTextureProvider());
        ModelService modelService = new ModelService();
        ModelComponent modelComponent = modelService.create(model, x, y, z);
        entity.add(modelComponent);

        BulletComponent bulletComponent = new BulletComponent();
        btCollisionShape shape = Bullet.obtainStaticNodeShape(model.nodes);

        bulletComponent.setBodyInfo(
                new btRigidBody.btRigidBodyConstructionInfo(
                        0, null, shape, Vector3.Zero));

        bulletComponent.setBody(
                new btRigidBody(bulletComponent.getBodyInfo()));

        bulletComponent.getBody().userData = entity;
        bulletComponent.setMotionState(
                new MotionState(modelComponent.getInstance().transform));

        ((btRigidBody) bulletComponent.getBody()).setMotionState(bulletComponent.getMotionState());

        entity.add(bulletComponent);

        return entity;
    }
}
