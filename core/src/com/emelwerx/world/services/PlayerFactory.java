package com.emelwerx.world.services;

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
import com.badlogic.gdx.physics.bullet.collision.btCapsuleShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btPairCachingGhostObject;
import com.badlogic.gdx.physics.bullet.dynamics.btKinematicCharacterController;
import com.emelwerx.world.databags.CharacterComponent;
import com.emelwerx.world.databags.ModelComponent;
import com.emelwerx.world.databags.PlayerComponent;
import com.emelwerx.world.systems.PhysicsSystem;

import static java.lang.String.format;

public class PlayerFactory {

    public static Entity create(PhysicsSystem physicsSystem, float x, float y, float z) {
        Gdx.app.log("PlayerFactory", format("creating entity %s, %f, %f, %f", physicsSystem.toString(), x, y, z));

        Entity entity = createCharacter(physicsSystem, x, y, z);
        entity.add(new PlayerComponent());
        return entity;
    }

    private static Entity createCharacter(PhysicsSystem physicsSystem, float x, float y, float z) {
        Entity entity = new Entity();

        ModelComponent modelComponent = getModelComponent(x, y, z);
        entity.add(modelComponent);

        CharacterComponent characterComponent = CharacterComponentFactory.create(entity, modelComponent);
        entity.add(characterComponent);

        setPhysicsSystem(physicsSystem, entity);

        return entity;
    }

    private static void setPhysicsSystem(PhysicsSystem physicsSystem, Entity entity) {
        physicsSystem.getPhysicsSystemState().getCollisionWorld().addCollisionObject(entity.getComponent(CharacterComponent.class).getGhostObject(),
                (short) btBroadphaseProxy.CollisionFilterGroups.CharacterFilter,
                (short) (btBroadphaseProxy.CollisionFilterGroups.AllFilter));

        physicsSystem.getPhysicsSystemState().getCollisionWorld().addAction(entity.getComponent(CharacterComponent.class).getCharacterController());
    }

    private static ModelComponent getModelComponent(float x, float y, float z) {
        Model playerModel = getModel();
        ModelService modelService = new ModelService();
        return modelService.create(playerModel, x, y, z);
    }

    private static Model getModel() {
        ModelBuilder modelBuilder = new ModelBuilder();
        Texture playerTexture = new Texture(Gdx.files.internal("data/badlogic.jpg"));
        Material material = new Material(TextureAttribute.createDiffuse(playerTexture),
                ColorAttribute.createSpecular(1, 1, 1, 1), FloatAttribute.createShininess(8f));

        return modelBuilder.createCapsule(2f, 6f, 16, material, VertexAttributes.Usage.Position |
                VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
    }
}
