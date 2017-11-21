package com.emelwerx.world.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.emelwerx.world.databags.ModelComponent;
import com.emelwerx.world.databags.MotionState;
import com.emelwerx.world.databags.PhysicsComponent;
import com.emelwerx.world.databags.Scene;
import com.emelwerx.world.databags.World;

import java.util.Locale;

import static java.lang.String.format;

public class SceneLoader {

    public static Scene load(World world, int x, int y, int z) {

        String worldName = world.getName();
        String sceneName = world.getFirstSceneName();

        String sceneFilename = "worlds/" + worldName + "/" + sceneName + "/scene.json";
        Gdx.app.log("SceneLoader", format(Locale.US,"load %s, %d, %d, %d", sceneFilename, x, y, z));

        FileHandle fileHandle = Gdx.files.internal(sceneFilename);

        JsonReader reader = new JsonReader();
        JsonValue jsonScene = reader.parse(fileHandle);

        JsonValue sky = jsonScene.get("sky");
        String skyModelFilename = sky.getString("modelFile");

        JsonValue ground = jsonScene.get("ground");
        String groundModelFilename = ground.getString("modelFile");

        Scene scene = new Scene();

        scene.setName(sceneName);
        scene.setSky(loadSky(skyModelFilename, x, y, z));
        scene.setGround(loadGround(groundModelFilename, x, y, z));

        JsonValue location = jsonScene.get("player").get("startLocation");
        scene.getPlayerStartLocation().x = location.getFloat("x");
        scene.getPlayerStartLocation().y = location.getFloat("y");
        scene.getPlayerStartLocation().z = location.getFloat("z");

        scene.setMaxSpawnCount(3);

        loadCreatures(world, jsonScene.get("creatures"));

        return scene;
    }

    private static void loadCreatures(World world, JsonValue value) {
        for(JsonValue item: value.iterator()) {
            JsonValue goblin = item.get("goblin");
            if(goblin != null) {
                loadGoblin(world, goblin);
            }
        }
    }

    private static void loadGoblin(World world, JsonValue goblin) {
        String modelFilename = goblin.getString("modelFile");
        JsonValue location = goblin.get("startLocation");
        float x = location.getFloat("x");
        float y = location.getFloat("y");
        float z = location.getFloat("z");

        Gdx.app.log("SceneLoader", format(Locale.US, "load goblin %s, %.2f, %.2f, %.2f", modelFilename, x, y, z));
        CreatureEntityFactory.create(world, modelFilename, x, y, z);
    }

    private static Entity loadSky(String name, int x, int y, int z) {
        Gdx.app.log("SceneLoader", format(Locale.US,"load sky %s, %d, %d, %d", name, x, y, z));
        Model model = ModelLoader.getSkyModel(name);
        ModelComponent modelComponent = ModelComponentFactory.create(model, x, y, z);

        Entity entity = new Entity();
        entity.add(modelComponent);
        return entity;
    }

    private static Entity loadGround(String name, int x, int y, int z) {
        Gdx.app.log("SceneLoader", format(Locale.US,"loadGround %s, %d, %d, %d", name, x, y, z));
        Entity entity = new Entity();

        Model model = ModelLoader.loadModel(name);
        ModelComponent modelComponent = ModelComponentFactory.create(model, x, y, z);
        entity.add(modelComponent);

        PhysicsComponent physicsComponent = getPhysicsComponent(entity, model, modelComponent);
        entity.add(physicsComponent);

        return entity;
    }

    private static PhysicsComponent getPhysicsComponent(Entity entity, Model model, ModelComponent modelComponent) {
        PhysicsComponent physicsComponent = new PhysicsComponent();

        btCollisionShape shape = Bullet.obtainStaticNodeShape(model.nodes);

        btRigidBody.btRigidBodyConstructionInfo bodyInfo =
                new btRigidBody.btRigidBodyConstructionInfo(0, null, shape, Vector3.Zero);
        physicsComponent.setBodyInfo(bodyInfo);
        physicsComponent.setBody(new btRigidBody(physicsComponent.getBodyInfo()));
        physicsComponent.getBody().userData = entity;
        physicsComponent.setMotionState(new MotionState(modelComponent.getInstance().transform));

        ((btRigidBody) physicsComponent.getBody()).setMotionState(physicsComponent.getMotionState());
        return physicsComponent;
    }

}
