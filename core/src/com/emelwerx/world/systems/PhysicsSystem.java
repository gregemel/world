package com.emelwerx.world.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.emelwerx.world.databags.components.CharacterComponent;
import com.emelwerx.world.databags.components.SceneComponent;
import com.emelwerx.world.databags.systemstates.PhysicsSystemState;

import static java.lang.String.format;

public class PhysicsSystem extends EntitySystem implements EntityListener {

    private PhysicsSystemState physicsSystemState;

    public PhysicsSystem(PhysicsSystemState physicsSystemState) {
        this.physicsSystemState = physicsSystemState;
    }

    public PhysicsSystemState getPhysicsSystemState() {
        return physicsSystemState;
    }

    @Override
    public void addedToEngine(Engine engine) {
        Gdx.app.log("PhysicsSystem", format("addedToEngine: %s", engine.toString()));
        engine.addEntityListener(Family.all(SceneComponent.class).get(), this);
    }

    @Override
    public void update(float deltaTime) {
        physicsSystemState.getCollisionWorld().stepSimulation(deltaTime);
    }

    public void dispose() {
        cleanup();
    }

    @Override
    public void entityAdded(Entity entity) {
        Gdx.app.log("PhysicsSystem", format("entity added: %s", entity.toString()));
        SceneComponent sceneComponent = entity.getComponent(SceneComponent.class);
        if (sceneComponent != null && sceneComponent.getBody() != null) {
            physicsSystemState.getCollisionWorld().addRigidBody((btRigidBody) sceneComponent.getBody());
        }
    }

    public void removeBody(Entity entity) {
        Gdx.app.log("PhysicsSystem", format("entity removed: %s", entity.toString()));
        btDiscreteDynamicsWorld collisionWorld = physicsSystemState.getCollisionWorld();
        removeSceneComponent(entity, collisionWorld);
        removeCharacterComponent(entity, collisionWorld);
    }

    private void removeCharacterComponent(Entity entity, btDiscreteDynamicsWorld collisionWorld) {
        CharacterComponent character = entity.getComponent(CharacterComponent.class);
        if (character != null) {
            collisionWorld.removeAction(character.getCharacterController());
            collisionWorld.removeCollisionObject(character.getGhostObject());
        }
    }

    private void removeSceneComponent(Entity entity, btDiscreteDynamicsWorld collisionWorld) {
        SceneComponent sceneComponent = entity.getComponent(SceneComponent.class);
        if (sceneComponent != null) {
            collisionWorld.removeCollisionObject(sceneComponent.getBody());
        }
    }

    @Override
    public void entityRemoved(Entity entity) {
        Gdx.app.log("PhysicsSystem", format("entity removed: %s", entity.toString()));
    }

    private void cleanup() {
        physicsSystemState.getCollisionWorld().dispose();

        if (physicsSystemState.getSolver() != null) {
            physicsSystemState.getSolver().dispose();
        }

        if (physicsSystemState.getBroadphaseInterface() != null) {
            physicsSystemState.getBroadphaseInterface().dispose();
        }

        if (physicsSystemState.getDispatcher() != null) {
            physicsSystemState.getDispatcher().dispose();
        }

        if (physicsSystemState.getCollisionConfiguration() != null) {
            physicsSystemState.getCollisionConfiguration().dispose();
        }

        physicsSystemState.getGhostPairCallback().dispose();
    }

}
