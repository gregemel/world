package com.emelwerx.world.databags;

import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btGhostPairCallback;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.emelwerx.world.systems.CollisionSystem;

public class PhysicsSystemState {
    private btCollisionConfiguration collisionConfiguration;
    private btCollisionDispatcher dispatcher;
    private CollisionSystem collisionListener;
    private btBroadphaseInterface broadphase;
    private btConstraintSolver solver;
    private btDiscreteDynamicsWorld collisionWorld;
    private btGhostPairCallback ghostPairCallback;
    private int maxSubSteps = 5;
    private float fixedTimeStep = 1f / 60f;

    public CollisionSystem getCollisionListener() {
        return collisionListener;
    }

    public void setCollisionListener(CollisionSystem collisionListener) {
        this.collisionListener = collisionListener;
    }

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

    public btBroadphaseInterface getBroadphase() {
        return broadphase;
    }

    public void setBroadphase(btBroadphaseInterface broadphase) {
        this.broadphase = broadphase;
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
