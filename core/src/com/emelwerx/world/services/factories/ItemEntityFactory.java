package com.emelwerx.world.services.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.databags.components.CharacterComponent;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.services.loaders.ModelCache;
import com.emelwerx.world.systems.PhysicsSystem;

import java.util.Locale;

public class ItemEntityFactory {
    private static final float modelScalar = 0.025f;

    public static Entity create(World world, String name, float x, float y, float z) {
        Gdx.app.log("ItemEntityFactory", String.format(Locale.US,
                "creating item %s, %s", name, world.toString()));

        Entity entity = new Entity();
        attachComponents(entity, world, name, x, y, z);
        return entity;
    }

    private static void attachComponents(Entity entity, World world, String name, float x, float y, float z) {
        ModelComponent modelComponent = createModelComponent(entity, name, x, y, z);
        attachCaracterComponent(entity, modelComponent);
        attachPhysicsSystem(world, entity);
    }

    private static ModelComponent createModelComponent(Entity entity, String name, float x, float y, float z) {
        ModelComponent modelComponent = getModelComponent(name, x, y, z);
        entity.add(modelComponent);
        return modelComponent;
    }

    private static void attachPhysicsSystem(World world, Entity entity) {
        PhysicsSystem physicsSystem = world.getPhysicsSystem();
        setPhysics(physicsSystem, entity);
    }

    private static void attachCaracterComponent(Entity entity, ModelComponent modelComponent) {
        CharacterComponent characterComponent = CharacterComponentFactory.create(entity, modelComponent);
        entity.add(characterComponent);
    }

    private static ModelComponent getModelComponent(String name, float x, float y, float z) {
        Model model = ModelCache.get(name, modelScalar);
        ModelComponent creatureModelComponent = ModelComponentFactory.create(model, x, y, z);

        Material material = creatureModelComponent.getInstance().materials.get(0);
        BlendingAttribute blendingAttribute = new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        material.set(blendingAttribute);
        creatureModelComponent.setBlendingAttribute(blendingAttribute);

        Matrix4 matrix4 = creatureModelComponent.getMatrix4();
        creatureModelComponent.getInstance().transform.set(matrix4.setTranslation(x, y, z));
        return creatureModelComponent;
    }

    private static void setPhysics(PhysicsSystem physicsSystem, Entity entity) {
        btDiscreteDynamicsWorld collisionWorld = physicsSystem.getState().getCollisionWorld();
        CharacterComponent characterComponent = entity.getComponent(CharacterComponent.class);
        collisionWorld.addCollisionObject(
                characterComponent.getGhostObject(),
                (short) btBroadphaseProxy.CollisionFilterGroups.CharacterFilter,
                (short) (btBroadphaseProxy.CollisionFilterGroups.AllFilter));
        collisionWorld.addAction(characterComponent.getCharacterController());
    }
}