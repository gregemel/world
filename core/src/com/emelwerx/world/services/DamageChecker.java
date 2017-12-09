package com.emelwerx.world.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.emelwerx.world.databags.components.CreatureComponent;

import static java.lang.String.format;

public class DamageChecker {
    public static void check(ClosestRayResultCallback resultCallback) {
        if (resultCallback.hasHit()) {
            btCollisionObject obj = resultCallback.getCollisionObject();
            Entity entity = (Entity)obj.userData;
            CreatureComponent creatureComponent = entity.getComponent(CreatureComponent.class);
            if (creatureComponent != null) {
                CreatureDamager.shoot(creatureComponent);
            } else {
                shootNonCreature(entity);
            }
        } else {
            Gdx.app.log("Shooter", "miss");
        }
    }

    private static void shootNonCreature(Entity entity) {
        Gdx.app.log("Shooter", format("hit not a creature %s", entity.toString()));
    }
}