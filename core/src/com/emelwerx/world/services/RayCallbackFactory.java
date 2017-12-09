package com.emelwerx.world.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.emelwerx.world.databags.systemstates.PhysicsSystemState;
import com.emelwerx.world.databags.systemstates.PlayerSystemState;

public class RayCallbackFactory {
    public static ClosestRayResultCallback create(PlayerSystemState state) {
        Ray ray = state.getWorldPerspectiveCamera()
                .getPickRay(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        Vector3 rayFrom = state.getRayFrom();
        Vector3 rayTo = state.getRayTo();
        ClosestRayResultCallback callback = getClosestRayResultCallback(state, ray, rayFrom, rayTo);
        PhysicsSystemState physicsState = state.getWorld().getPhysicsSystem().getPhysicsSystemState();
        btDiscreteDynamicsWorld collisionWorld = physicsState.getCollisionWorld();
        collisionWorld.rayTest(rayFrom, rayTo, callback);
        return callback;
    }

    private static ClosestRayResultCallback getClosestRayResultCallback(
            PlayerSystemState state, Ray ray, Vector3 rayFrom, Vector3 rayTo) {

        ClosestRayResultCallback resultCallback = state.getWeaponRayResultCallback();
        rayFrom.set(ray.origin);
        rayTo.set(ray.direction).scl(50f).add(rayFrom);
        resultCallback.setCollisionObject(null);
        resultCallback.setClosestHitFraction(1f);
        resultCallback.setRayFromWorld(rayFrom);
        resultCallback.setRayToWorld(rayTo);
        return resultCallback;
    }
}