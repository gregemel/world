package com.deeep.spaceglad.services;

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
import com.deeep.spaceglad.databags.CharacterComponent;
import com.deeep.spaceglad.databags.ModelComponent;
import com.deeep.spaceglad.databags.PlayerComponent;
import com.deeep.spaceglad.systems.PhysicsSystem;

import static java.lang.String.format;

public class PlayerFactory {

    public Entity create(PhysicsSystem physicsSystem, float x, float y, float z) {
        Gdx.app.log("PlayerFactory", format("creating entity %s, %f, %f, %f", physicsSystem.toString(), x, y, z));

        Entity entity = createCharacter(physicsSystem, x, y, z);
        entity.add(new PlayerComponent());
        return entity;
    }

    private Entity createCharacter(PhysicsSystem physicsSystem, float x, float y, float z) {
        Entity entity = new Entity();

        ModelComponent modelComponent = getModelComponent(x, y, z);
        entity.add(modelComponent);

        CharacterComponent characterComponent = getCharacterComponent(entity, modelComponent);
        entity.add(characterComponent);

        setPhysicsSystem(physicsSystem, entity);

        return entity;
    }

    private void setPhysicsSystem(PhysicsSystem physicsSystem, Entity entity) {
        physicsSystem.getPhysicsSystemState().getCollisionWorld().addCollisionObject(entity.getComponent(CharacterComponent.class).getGhostObject(),
                (short) btBroadphaseProxy.CollisionFilterGroups.CharacterFilter,
                (short) (btBroadphaseProxy.CollisionFilterGroups.AllFilter));

        physicsSystem.getPhysicsSystemState().getCollisionWorld().addAction(entity.getComponent(CharacterComponent.class).getCharacterController());
    }

    private ModelComponent getModelComponent(float x, float y, float z) {
        Model playerModel = getModel();
        ModelService modelService = new ModelService();
        return modelService.create(playerModel, x, y, z);
    }

    private CharacterComponent getCharacterComponent(Entity entity, ModelComponent modelComponent) {
        CharacterComponent characterComponent = new CharacterComponent();
        characterComponent.setGhostObject(
                new btPairCachingGhostObject());

        characterComponent.getGhostObject().setWorldTransform(modelComponent.getInstance().transform);
        characterComponent.setGhostShape(
                new btCapsuleShape(2f, 2f));

        characterComponent.getGhostObject().setCollisionShape(characterComponent.getGhostShape());
        characterComponent.getGhostObject().setCollisionFlags(btCollisionObject.CollisionFlags.CF_CHARACTER_OBJECT);

        characterComponent.setCharacterController(
                new btKinematicCharacterController(
                        characterComponent.getGhostObject(),
                        characterComponent.getGhostShape(),
                        .35f));

        characterComponent.getGhostObject().userData = entity;
        return characterComponent;
    }

    private Model getModel() {
        ModelBuilder modelBuilder = new ModelBuilder();
        Texture playerTexture = new Texture(Gdx.files.internal("data/badlogic.jpg"));
        Material material = new Material(TextureAttribute.createDiffuse(playerTexture),
                ColorAttribute.createSpecular(1, 1, 1, 1), FloatAttribute.createShininess(8f));

        return modelBuilder.createCapsule(2f, 6f, 16, material, VertexAttributes.Usage.Position |
                VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
    }
}
