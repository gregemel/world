package com.emelwerx.world.services.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btAxisSweep3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btGhostPairCallback;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.emelwerx.world.databags.systemstates.PhysicsSystemState;
import com.emelwerx.world.systems.CollisionSystem;
import com.emelwerx.world.systems.PhysicsSystem;

public class PhysicsSystemFactory {
    public static PhysicsSystem create() {
        Gdx.app.log("PhysicsSystemFactory", "creating physics system");
        PhysicsSystemState physicsSystemState = new PhysicsSystemState();

        initializeCollisionSystem();
        attachCollisionConfiguration(physicsSystemState);
        attachCollisionDispatcher(physicsSystemState);
        attachBroadphase(physicsSystemState);
        attachSolver(physicsSystemState);
        attachGhostPairCallback(physicsSystemState);
        attachCollisionWorld(physicsSystemState);

        return new PhysicsSystem(physicsSystemState);
    }

    private static void initializeCollisionSystem() {
        CollisionSystem collisionListener = new CollisionSystem();
        collisionListener.enable();
    }

    private static void attachCollisionConfiguration(PhysicsSystemState physicsSystemState) {
        btDefaultCollisionConfiguration btDefaultCollisionConfiguration = new btDefaultCollisionConfiguration();
        physicsSystemState.setCollisionConfiguration(btDefaultCollisionConfiguration);
    }

    private static void attachCollisionDispatcher(PhysicsSystemState physicsSystemState) {
        btCollisionConfiguration collisionConfiguration = physicsSystemState.getCollisionConfiguration();
        btCollisionDispatcher btCollisionDispatcher = new btCollisionDispatcher(collisionConfiguration);
        physicsSystemState.setDispatcher(btCollisionDispatcher);
    }

    private static void attachBroadphase(PhysicsSystemState physicsSystemState) {
        btAxisSweep3 btAxisSweep3 = new btAxisSweep3(
                new Vector3(-1000, -1000, -1000),
                new Vector3(1000, 1000, 1000));
        physicsSystemState.setBroadphaseInterface(btAxisSweep3);
    }

    private static void attachSolver(PhysicsSystemState physicsSystemState) {
        btSequentialImpulseConstraintSolver btSequentialImpulseConstraintSolver = new btSequentialImpulseConstraintSolver();
        physicsSystemState.setSolver(btSequentialImpulseConstraintSolver);
    }

    private static void attachGhostPairCallback(PhysicsSystemState physicsSystemState) {
        btGhostPairCallback btGhostPairCallback = new btGhostPairCallback();
        physicsSystemState.setGhostPairCallback(btGhostPairCallback);
        physicsSystemState.getBroadphaseInterface().getOverlappingPairCache().setInternalGhostPairCallback(btGhostPairCallback);
    }

    private static void attachCollisionWorld(PhysicsSystemState physicsSystemState) {
        btDiscreteDynamicsWorld collisionWorld = new btDiscreteDynamicsWorld(
                physicsSystemState.getDispatcher(),
                physicsSystemState.getBroadphaseInterface(),
                physicsSystemState.getSolver(),
                physicsSystemState.getCollisionConfiguration());
        collisionWorld.setGravity(new Vector3(0, -0.5f, 0));
        physicsSystemState.setCollisionWorld(collisionWorld);
    }
}
