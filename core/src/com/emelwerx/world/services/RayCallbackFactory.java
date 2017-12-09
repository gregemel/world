package com.emelwerx.world.services;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.emelwerx.world.databags.systemstates.PhysicsSystemState;
import com.emelwerx.world.databags.systemstates.PlayerSystemState;

public class RayCallbackFactory {
    public static ClosestRayResultCallback create(PlayerSystemState playerSystemState) {
        Ray ray = playerSystemState.getWorldPerspectiveCamera()
                .getPickRay(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        Vector3 rayFrom = playerSystemState.getRayFrom();
        Vector3 rayTo = playerSystemState.getRayTo();
        ClosestRayResultCallback rayTestCB = getClosestRayResultCallback(playerSystemState, ray, rayFrom, rayTo);
        PhysicsSystemState physicsSystemState = playerSystemState.getWorld().getPhysicsSystem().getPhysicsSystemState();
        btDiscreteDynamicsWorld collisionWorld = physicsSystemState.getCollisionWorld();
        collisionWorld.rayTest(rayFrom, rayTo, rayTestCB);
        return rayTestCB;
    }

    private static ClosestRayResultCallback getClosestRayResultCallback(
            PlayerSystemState playerSystemState, Ray ray, Vector3 rayFrom, Vector3 rayTo) {

        ClosestRayResultCallback resultCallback = playerSystemState.getWeaponRayResultCallback();
        rayFrom.set(ray.origin);
        rayTo.set(ray.direction).scl(50f).add(rayFrom);
        resultCallback.setCollisionObject(null);
        resultCallback.setClosestHitFraction(1f);
        resultCallback.setRayFromWorld(rayFrom);
        resultCallback.setRayToWorld(rayTo);
        return resultCallback;
    }
}
