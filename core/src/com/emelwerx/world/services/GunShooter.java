package com.emelwerx.world.services;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.emelwerx.world.databags.AnimationComponent;
import com.emelwerx.world.databags.MonsterComponent;
import com.emelwerx.world.databags.PlayerComponent;
import com.emelwerx.world.databags.PlayerSystemState;
import com.emelwerx.world.databags.ThoughtComponent;

import static java.lang.String.format;

public class GunShooter {

    public static void fire(PlayerSystemState playerSystemState) {
        Gdx.app.log("PlayerSystem", "fire");
        Ray ray = playerSystemState.getCamera().getPickRay(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        Vector3 rayFrom = playerSystemState.getRayFrom();
        Vector3 rayTo = playerSystemState.getRayTo();

        ClosestRayResultCallback rayTestCB = getClosestRayResultCallback(playerSystemState, ray, rayFrom, rayTo);

        btDiscreteDynamicsWorld collisionWorld = playerSystemState.getGameWorld().getPhysicsSystem().getPhysicsSystemState()
                .getCollisionWorld();
        collisionWorld.rayTest(rayFrom, rayTo, rayTestCB);

        checkForDamage(rayTestCB);

        AnimationComponent animationComponent = playerSystemState.getItem().getComponent(AnimationComponent.class);
        playerSystemState.getAnimationService().animate(
                animationComponent, "Armature|shoot", 1, 3);
    }

    private static ClosestRayResultCallback getClosestRayResultCallback(
            PlayerSystemState playerSystemState, Ray ray, Vector3 rayFrom, Vector3 rayTo) {
        ClosestRayResultCallback rayTestCB = playerSystemState.getRayTestCB();

        rayFrom.set(ray.origin);
        rayTo.set(ray.direction).scl(50f).add(rayFrom);
        rayTestCB.setCollisionObject(null);
        rayTestCB.setClosestHitFraction(1f);
        rayTestCB.setRayFromWorld(rayFrom);
        rayTestCB.setRayToWorld(rayTo);
        return rayTestCB;
    }

    private static void checkForDamage(ClosestRayResultCallback rayTestCB) {
        if (rayTestCB.hasHit()) {
            btCollisionObject obj = rayTestCB.getCollisionObject();
            Entity entity = (Entity)obj.userData;
            if (entity.getComponent(MonsterComponent.class) != null) {
                ThoughtComponent component = entity.getComponent(ThoughtComponent.class);
                if(component.isAlive()) {
                    Gdx.app.log("PlayerSystem", format("HIT monster %s", entity.toString()));
                    component.setAlive(false);
                    PlayerComponent.setScore(PlayerComponent.getScore() + 100);
                }
            } else {
                Gdx.app.log("PlayerSystem", format("hit not a monster %s", entity.toString()));
            }
        } else {
            Gdx.app.log("PlayerSystem", "miss");
        }
    }

}
