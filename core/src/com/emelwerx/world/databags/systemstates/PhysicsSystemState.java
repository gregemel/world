package com.emelwerx.world.databags.systemstates;

import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btGhostPairCallback;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.emelwerx.world.systems.CollisionSystem;

public class PhysicsSystemState {
    private static final int maxSubSteps = 5;
    private static final float fixedTimeStep = 1f / 60f;

    private btCollisionConfiguration collisionConfiguration;
    private btCollisionDispatcher dispatcher;
    private btBroadphaseInterface broadphaseInterface;
    private btConstraintSolver solver;
    private btDiscreteDynamicsWorld collisionWorld;
    private btGhostPairCallback ghostPairCallback;

    public btCollisionConfiguration getCollisionConfiguration() {
        return collisionConfiguration;
    }

    public void setCollisionConfiguration(btCollisionConfiguration collisionConfiguration) {
        this.collisionConfiguration = collisionConfiguration;
    }

    public btCollisionDispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(btCollisionDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public btBroadphaseInterface getBroadphaseInterface() {
        return broadphaseInterface;
    }

    public void setBroadphaseInterface(btBroadphaseInterface broadphaseInterface) {
        this.broadphaseInterface = broadphaseInterface;
    }

    public btConstraintSolver getSolver() {
        return solver;
    }

    public void setSolver(btConstraintSolver solver) {
        this.solver = solver;
    }

    public btDiscreteDynamicsWorld getCollisionWorld() {
        return collisionWorld;
    }

    public void setCollisionWorld(btDiscreteDynamicsWorld collisionWorld) {
        this.collisionWorld = collisionWorld;
    }

    public btGhostPairCallback getGhostPairCallback() {
        return ghostPairCallback;
    }

    public void setGhostPairCallback(btGhostPairCallback ghostPairCallback) {
        this.ghostPairCallback = ghostPairCallback;
    }

    public int getMaxSubSteps() {
        return maxSubSteps;
    }

    public float getFixedTimeStep() {
        return fixedTimeStep;
    }
}
