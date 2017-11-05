package com.deeep.spaceglad.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btAxisSweep3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btGhostPairCallback;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.deeep.spaceglad.databags.PhysicsSystemState;
import com.deeep.spaceglad.systems.PhysicsSystem;

public class PhysicsSystemFactory {
    public PhysicsSystem create() {
        Gdx.app.log("PhysicsSystemFactory", "creating physics system");
        PhysicsSystem physicsSystem = new PhysicsSystem();
        PhysicsSystemState physicsSystemState = new PhysicsSystemState();

        physicsSystemState.setCollisionListener(getCollisionListener());

        btDefaultCollisionConfiguration collisionConfiguration = getCollisionConfiguration();
        physicsSystemState.setCollisionConfiguration(collisionConfiguration);

        btCollisionDispatcher dispatcher = getCollisionDispatcher(collisionConfiguration);
        physicsSystemState.setDispatcher(dispatcher);

        btAxisSweep3 broadPhase = getBroadphase();
        physicsSystemState.setBroadphase(broadPhase);

        btSequentialImpulseConstraintSolver solver = getSolver();
        physicsSystemState.setSolver(solver);

        btGhostPairCallback ghostPairCallback = getGhostPairCallback();
        physicsSystemState.setGhostPairCallback(ghostPairCallback);

        broadPhase.getOverlappingPairCache().setInternalGhostPairCallback(ghostPairCallback);

        physicsSystemState.setCollisionWorld(
                getCollisionWorld(collisionConfiguration, dispatcher, broadPhase, solver));

        physicsSystem.setPhysicsSystemState(physicsSystemState);
        return physicsSystem;
    }

    private btDiscreteDynamicsWorld getCollisionWorld(btDefaultCollisionConfiguration collisionConfiguration, btCollisionDispatcher dispatcher, btAxisSweep3 broadphase, btSequentialImpulseConstraintSolver solver) {
        btDiscreteDynamicsWorld collisionWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
        collisionWorld.setGravity(new Vector3(0, -0.5f, 0));
        return collisionWorld;
    }

    private btGhostPairCallback getGhostPairCallback() {
        return new btGhostPairCallback();
    }

    private btSequentialImpulseConstraintSolver getSolver() {
        return new btSequentialImpulseConstraintSolver();
    }

    private btAxisSweep3 getBroadphase() {
        return new btAxisSweep3(
                    new Vector3(-1000, -1000, -1000),
                    new Vector3(1000, 1000, 1000));
    }

    private btCollisionDispatcher getCollisionDispatcher(btDefaultCollisionConfiguration collisionConfiguration) {
        return new btCollisionDispatcher(collisionConfiguration);
    }

    private btDefaultCollisionConfiguration getCollisionConfiguration() {
        return new btDefaultCollisionConfiguration();
    }

    private CollisionListener getCollisionListener() {
        CollisionListener collisionListener = new CollisionListener();
        collisionListener.enable();
        return collisionListener;
    }

}
