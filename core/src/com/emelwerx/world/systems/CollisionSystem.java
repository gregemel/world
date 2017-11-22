package com.emelwerx.world.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.emelwerx.world.databags.components.CreatureComponent;
import com.emelwerx.world.databags.components.PlayerComponent;
import com.emelwerx.world.services.CreaturePlayerHitDamager;

import static java.lang.String.format;

public class CollisionSystem extends ContactListener {
    @Override
    public void onContactStarted(btCollisionObject colObj0, btCollisionObject colObj1) {
        if (colObj0.userData instanceof Entity && colObj1.userData instanceof Entity) {
            twoEntityCollision(colObj0, colObj1);
        }
    }

    private void twoEntityCollision(btCollisionObject colObj0, btCollisionObject colObj1) {
        Entity entity0 = (Entity) colObj0.userData;
        Entity entity1 = (Entity) colObj1.userData;

        Gdx.app.log("CollisionSystem", format("two entities in contact %s, %s",
                entity0.toString(), entity1.toString()));

        PlayerComponent playerComponent = getPlayerComponent(entity0, entity1);
        CreatureComponent creatureComponent = getCreatureComponent(entity0, entity1);

        boolean isCreatureInvolved = creatureComponent != null
                && creatureComponent.getCreatureState() != CreatureComponent.CREATURE_STATE.DYING;
        boolean isTransitionToPlayerAndCreatureCollision = isCreatureInvolved
                && playerComponent != null;

        if(isTransitionToPlayerAndCreatureCollision) {
            CreaturePlayerHitDamager.collide(playerComponent, creatureComponent);
        } else {
            collideOther(playerComponent, creatureComponent);
        }
    }

    private void collideOther(PlayerComponent playerComponent, CreatureComponent creatureComponent) {
        if(creatureComponent != null
                && creatureComponent.getCreatureState() != CreatureComponent.CREATURE_STATE.DYING) {
            Gdx.app.log("CollisionSystem", format("creature hit something %s",
                    creatureComponent.toString()));
        } else {
            if(playerComponent != null && creatureComponent == null) {
                Gdx.app.log("CollisionSystem", format("player hit something %s",
                        playerComponent.toString()));
            }
        }
    }

    private CreatureComponent getCreatureComponent(Entity entity0, Entity entity1) {
        CreatureComponent creatureComponent = entity0.getComponent(CreatureComponent.class);
        if(creatureComponent == null) {
            creatureComponent = entity1.getComponent(CreatureComponent.class);
        }
        return creatureComponent;
    }

    private PlayerComponent getPlayerComponent(Entity entity0, Entity entity1) {
        PlayerComponent playerComponent = entity0.getComponent(PlayerComponent.class);
        if(playerComponent == null) {
            playerComponent = entity1.getComponent(PlayerComponent.class);
        }
        return playerComponent;
    }
}
