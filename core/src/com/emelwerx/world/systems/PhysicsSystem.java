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

    private PhysicsSystemState state;

    public PhysicsSystem(PhysicsSystemState state) {
        this.state = state;
    }

    public PhysicsSystemState getState() {
        return state;
    }

    @Override
    public void addedToEngine(Engine engine) {
        Gdx.app.log("PhysicsSystem", format("addedToEngine: %s", engine.toString()));
        engine.addEntityListener(Family.all(SceneComponent.class).get(), this);
    }

    @Override
    public void update(float deltaTime) {
        state.getCollisionWorld().stepSimulation(deltaTime);
    }

    public void dispose() {
        cleanup();
    }

    @Override
    public void entityAdded(Entity entity) {
        Gdx.app.log("PhysicsSystem", format("entity added: %s", entity.toString()));
        SceneComponent sceneComponent = entity.getComponent(SceneComponent.class);
        if (sceneComponent != null && sceneComponent.getBody() != null) {
            state.getCollisionWorld().addRigidBody((btRigidBody) sceneComponent.getBody());
        }
    }

    public void removeBody(Entity entity) {
        Gdx.app.log("PhysicsSystem", format("entity removed: %s", entity.toString()));
        btDiscreteDynamicsWorld collisionWorld = state.getCollisionWorld();
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
        state.getCollisionWorld().dispose();

        if (state.getSolver() != null) {
            state.getSolver().dispose();
        }

        if (state.getBroadphaseInterface() != null) {
            state.getBroadphaseInterface().dispose();
        }

        if (state.getDispatcher() != null) {
            state.getDispatcher().dispose();
        }

        if (state.getCollisionConfiguration() != null) {
            state.getCollisionConfiguration().dispose();
        }

        state.getGhostPairCallback().dispose();
    }

}
