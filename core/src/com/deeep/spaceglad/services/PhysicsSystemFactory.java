package com.deeep.spaceglad.services;

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
        PhysicsSystem physicsSystem = new PhysicsSystem();
        PhysicsSystemState physicsSystemState = new PhysicsSystemState();

        CollisionListener collisionListener = new CollisionListener();
        collisionListener.enable();
        physicsSystemState.setCollisionListener(collisionListener);

        btDefaultCollisionConfiguration collisionConfiguration = new btDefaultCollisionConfiguration();
        physicsSystemState.setCollisionConfiguration(collisionConfiguration);

        btCollisionDispatcher dispatcher = new btCollisionDispatcher(collisionConfiguration);
        physicsSystemState.setDispatcher(dispatcher);

        btAxisSweep3 broadphase = new btAxisSweep3(
                new Vector3(-1000, -1000, -1000),
                new Vector3(1000, 1000, 1000));
        physicsSystemState.setBroadphase(broadphase);

        btSequentialImpulseConstraintSolver solver = new btSequentialImpulseConstraintSolver();
        physicsSystemState.setSolver(solver);

        btGhostPairCallback ghostPairCallback = new btGhostPairCallback();
        physicsSystemState.setGhostPairCallback(ghostPairCallback);

        broadphase.getOverlappingPairCache().setInternalGhostPairCallback(ghostPairCallback);

        btDiscreteDynamicsWorld collisionWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
        collisionWorld.setGravity(new Vector3(0, -0.5f, 0));
        physicsSystemState.setCollisionWorld(collisionWorld);

        physicsSystem.setPhysicsSystemState(physicsSystemState);
        return physicsSystem;
    }

}
