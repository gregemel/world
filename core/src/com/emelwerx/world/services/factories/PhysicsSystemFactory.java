package com.emelwerx.world.services.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btAxisSweep3;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btGhostPairCallback;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
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

    private static void attachCollisionWorld(PhysicsSystemState physicsSystemState) {
        physicsSystemState.setCollisionWorld(getCollisionWorld(
                physicsSystemState.getCollisionConfiguration(),
                physicsSystemState.getDispatcher(),
                physicsSystemState.getBroadphaseInterface(),
                physicsSystemState.getSolver()));
    }

    private static btDiscreteDynamicsWorld getCollisionWorld(
            btCollisionConfiguration collisionConfiguration,
            btCollisionDispatcher dispatcher,
            btBroadphaseInterface broadphase,
            btConstraintSolver solver) {

        btDiscreteDynamicsWorld collisionWorld = new btDiscreteDynamicsWorld(
                dispatcher, broadphase, solver, collisionConfiguration);
        collisionWorld.setGravity(new Vector3(0, -0.5f, 0));

        return collisionWorld;
    }

    private static btGhostPairCallback attachGhostPairCallback(PhysicsSystemState physicsSystemState) {
        btGhostPairCallback btGhostPairCallback = new btGhostPairCallback();
        physicsSystemState.setGhostPairCallback(btGhostPairCallback);
        physicsSystemState.getBroadphaseInterface().getOverlappingPairCache().setInternalGhostPairCallback(btGhostPairCallback);

        return btGhostPairCallback;
    }

    private static btSequentialImpulseConstraintSolver attachSolver(PhysicsSystemState physicsSystemState) {
        btSequentialImpulseConstraintSolver btSequentialImpulseConstraintSolver = new btSequentialImpulseConstraintSolver();
        physicsSystemState.setSolver(btSequentialImpulseConstraintSolver);
        return btSequentialImpulseConstraintSolver;
    }

    private static btAxisSweep3 attachBroadphase(PhysicsSystemState physicsSystemState) {
        btAxisSweep3 btAxisSweep3 = new btAxisSweep3(
                new Vector3(-1000, -1000, -1000),
                new Vector3(1000, 1000, 1000));
        physicsSystemState.setBroadphaseInterface(btAxisSweep3);
        return btAxisSweep3;
    }

    private static btCollisionDispatcher attachCollisionDispatcher(PhysicsSystemState physicsSystemState) {
        btCollisionConfiguration collisionConfiguration = physicsSystemState.getCollisionConfiguration();
        btCollisionDispatcher btCollisionDispatcher = new btCollisionDispatcher(collisionConfiguration);
        physicsSystemState.setDispatcher(btCollisionDispatcher);
        return btCollisionDispatcher;
    }

    private static btDefaultCollisionConfiguration attachCollisionConfiguration(PhysicsSystemState physicsSystemState) {
        btDefaultCollisionConfiguration btDefaultCollisionConfiguration = new btDefaultCollisionConfiguration();
        physicsSystemState.setCollisionConfiguration(btDefaultCollisionConfiguration);
        return btDefaultCollisionConfiguration;
    }

    private static CollisionSystem initializeCollisionSystem() {
        CollisionSystem collisionListener = new CollisionSystem();
        collisionListener.enable();
        return collisionListener;
    }

}
