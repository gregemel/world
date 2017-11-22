package com.emelwerx.world.services.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy;
import com.badlogic.gdx.physics.bullet.collision.btPairCachingGhostObject;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btKinematicCharacterController;
import com.emelwerx.world.databags.components.CharacterComponent;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.databags.components.PlayerComponent;
import com.emelwerx.world.systems.PhysicsSystem;

import java.util.Locale;

import static java.lang.String.format;

public class PlayerEntityFactory {

    public static Entity create(PhysicsSystem physicsSystem, float x, float y, float z) {
        Gdx.app.log("PlayerEntityFactory", format(Locale.US,"creating entity %s, %f, %f, %f", physicsSystem.toString(), x, y, z));

        Entity entity = new Entity();
        attachComponents(entity, x, y, z);
        setPhysicsSystem(physicsSystem, entity);
        return entity;
    }

    private static void attachComponents(Entity entity, float x, float y, float z) {
        ModelComponent modelComponent = getModelComponent(x, y, z);
        entity.add(modelComponent);

        CharacterComponent characterComponent = CharacterComponentFactory.create(entity, modelComponent);
        entity.add(characterComponent);

        entity.add(new PlayerComponent());
    }

    private static ModelComponent getModelComponent(float x, float y, float z) {
        Model playerModel = getModel();
        return ModelComponentFactory.create(playerModel, x, y, z);
    }

    private static Model getModel() {
        ModelBuilder modelBuilder = new ModelBuilder();
        Texture playerTexture = new Texture(Gdx.files.internal("data/badlogic.jpg"));
        Material material = new Material(TextureAttribute.createDiffuse(playerTexture),
                ColorAttribute.createSpecular(1, 1, 1, 1), FloatAttribute.createShininess(8f));

        return modelBuilder.createCapsule(2f, 6f, 16, material, VertexAttributes.Usage.Position |
                VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
    }

    private static void setPhysicsSystem(PhysicsSystem physicsSystem, Entity entity) {
        btDiscreteDynamicsWorld collisionWorld = physicsSystem.getPhysicsSystemState().getCollisionWorld();
        btPairCachingGhostObject ghostObject = entity.getComponent(CharacterComponent.class).getGhostObject();
        collisionWorld.addCollisionObject(ghostObject,
                (short) btBroadphaseProxy.CollisionFilterGroups.CharacterFilter,
                (short) (btBroadphaseProxy.CollisionFilterGroups.AllFilter));
        btKinematicCharacterController characterController = entity.getComponent(CharacterComponent.class).getCharacterController();
        collisionWorld.addAction(characterController);
    }
}
