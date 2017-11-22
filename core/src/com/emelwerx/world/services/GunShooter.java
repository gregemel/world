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
import com.emelwerx.world.databags.components.PlayerComponent;
import com.emelwerx.world.databags.systemstates.PlayerSystemState;

import static java.lang.String.format;

public class GunShooter {

    public static void fire(PlayerSystemState playerSystemState) {
        Gdx.app.log("GunShooter", "fire!!!");
        Ray ray = playerSystemState.getCamera().getPickRay(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        Vector3 rayFrom = playerSystemState.getRayFrom();
        Vector3 rayTo = playerSystemState.getRayTo();

        ClosestRayResultCallback rayTestCB = getClosestRayResultCallback(playerSystemState, ray, rayFrom, rayTo);

        btDiscreteDynamicsWorld collisionWorld = playerSystemState.getGameWorld().getPhysicsSystem().getPhysicsSystemState()
                .getCollisionWorld();
        collisionWorld.rayTest(rayFrom, rayTo, rayTestCB);

        checkForDamage(rayTestCB);

        animate(playerSystemState.getPlayerItemEntity());
    }

    private static void animate(Entity entity) {
        AnimationComponent animationComponent = entity.getComponent(AnimationComponent.class);
        animationComponent.getAnimationController().animate("Armature|shoot", 1, 3, null, 0);
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
            CreatureComponent creatureComponent = entity.getComponent(CreatureComponent.class);
            if (creatureComponent != null) {
                if(creatureComponent.getCreatureState() != CreatureComponent.CREATURE_STATE.DYING) {
                    Gdx.app.log("GunShooter", format("HIT creature %s", entity.toString()));
                    creatureComponent.setCreatureState(CreatureComponent.CREATURE_STATE.DYING);
                    PlayerComponent.setScore(PlayerComponent.getScore() + 100);
                } else {
                    Gdx.app.log("GunShooter", format("you hit a dying creature %s", entity.toString()));
                }
            } else {
                Gdx.app.log("GunShooter", format("hit not a creature %s", entity.toString()));
            }
        } else {
            Gdx.app.log("GunShooter", "miss");
        }
    }

}
