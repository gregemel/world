package com.emelwerx.world.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.emelwerx.world.databags.components.AnimationComponent;
import com.emelwerx.world.databags.components.CreatureComponent;
import com.emelwerx.world.databags.systemstates.PhysicsSystemState;
import com.emelwerx.world.databags.systemstates.PlayerSystemState;

import static java.lang.String.format;

public class Shooter {

    public static void fire(PlayerSystemState playerSystemState) {
        Gdx.app.log("Shooter", "fire!!!");
        ClosestRayResultCallback resultCallback = createRayTestCallback(playerSystemState);
        checkForDamage(resultCallback);
        animatePlayerItem(playerSystemState.getPlayerItemEntity());
    }

    private static ClosestRayResultCallback createRayTestCallback(PlayerSystemState playerSystemState) {
        Ray ray = playerSystemState.getWorldPerspectiveCamera().getPickRay(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
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

    private static void checkForDamage(ClosestRayResultCallback resultCallback) {
        if (resultCallback.hasHit()) {
            btCollisionObject obj = resultCallback.getCollisionObject();
            Entity entity = (Entity)obj.userData;
            CreatureComponent creatureComponent = entity.getComponent(CreatureComponent.class);
            if (creatureComponent != null) {
                Damager.shootCreature(creatureComponent);
            } else {
                playerShotEntity(entity);
            }
        } else {
            Gdx.app.log("Shooter", "miss");
        }
    }

    private static void playerShotEntity(Entity entity) {
        Gdx.app.log("Shooter", format("hit not a creature %s", entity.toString()));
    }

    private static void animatePlayerItem(Entity entity) {
        AnimationComponent animationComponent = entity.getComponent(AnimationComponent.class);
        animationComponent.getAnimationController().animate("Armature|shoot", 1, 3, null, 0);
    }
}
