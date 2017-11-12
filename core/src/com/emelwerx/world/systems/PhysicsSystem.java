package com.emelwerx.world.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.emelwerx.world.databags.CharacterComponent;
import com.emelwerx.world.databags.PhysicsComponent;
import com.emelwerx.world.databags.PhysicsSystemState;

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
        engine.addEntityListener(Family.all(PhysicsComponent.class).get(), this);
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
        PhysicsComponent physicsComponent = entity.getComponent(PhysicsComponent.class);

        if (physicsComponent.getBody() != null) {
            physicsSystemState.getCollisionWorld().addRigidBody((btRigidBody)physicsComponent.getBody());
        }
    }

    public void removeBody(Entity entity) {
        Gdx.app.log("PhysicsSystem", format("entity removed: %s", entity.toString()));
        PhysicsComponent comp = entity.getComponent(PhysicsComponent.class);

        if (comp != null) {
            physicsSystemState.getCollisionWorld().removeCollisionObject(comp.getBody());
        }

        CharacterComponent character = entity.getComponent(CharacterComponent.class);

        if (character != null) {
            physicsSystemState.getCollisionWorld().removeAction(character.getCharacterController());
            physicsSystemState.getCollisionWorld().removeCollisionObject(character.getGhostObject());
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
