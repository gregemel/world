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
import com.deeep.spaceglad.components.BulletComponent;
import com.deeep.spaceglad.components.ModelComponent;
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

        Model model = modelLoader.loadModel(Gdx.files.getFileHandle("data/spacedome.g3db", Files.FileType.Internal));
        ModelComponent modelComponent = new ModelComponent(model, x, y, z);

        Entity entity = new Entity();
        entity.add(modelComponent);
        return entity;
    }


    public static Entity loadGround(String name, int x, int y, int z) {
        Entity entity = new Entity();

        ModelLoader<?> modelLoader = new G3dModelLoader(new JsonReader());
        ModelData modelData = modelLoader.loadModelData(Gdx.files.internal("data/arena.g3dj"));

        Model model = new Model(modelData, new TextureProvider.FileTextureProvider());
        ModelComponent modelComponent = new ModelComponent(model, x, y, z);
        entity.add(modelComponent);

        BulletComponent bulletComponent = new BulletComponent();
        btCollisionShape shape = Bullet.obtainStaticNodeShape(model.nodes);
        bulletComponent.bodyInfo = new btRigidBody.btRigidBodyConstructionInfo(0, null, shape, Vector3.Zero);
        bulletComponent.body = new btRigidBody(bulletComponent.bodyInfo);
        bulletComponent.body.userData = entity;
        bulletComponent.motionState = new MotionState(modelComponent.instance.transform);
        ((btRigidBody) bulletComponent.body).setMotionState(bulletComponent.motionState);

        entity.add(bulletComponent);

        return entity;
    }


}
