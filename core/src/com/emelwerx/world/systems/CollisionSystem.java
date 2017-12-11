package com.emelwerx.world.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.emelwerx.world.databags.components.CreatureComponent;
import com.emelwerx.world.databags.components.PlayerComponent;
import com.emelwerx.world.services.CreatureDamager;

import static java.lang.String.format;

public class CollisionSystem extends ContactListener {
    @Override
    public void onContactStarted(btCollisionObject colObj0, btCollisionObject colObj1) {
        if (colObj0.userData instanceof Entity && colObj1.userData instanceof Entity) {
            twoEntityCollision((Entity) colObj0.userData, (Entity) colObj1.userData);
        }
    }

    private void twoEntityCollision(Entity entity0, Entity entity1) {
        Gdx.app.log("CollisionSystem", format("two entities in contact %s, %s",
                entity0.toString(), entity1.toString()));

        PlayerComponent player = getPlayerComponent(entity0, entity1);
        CreatureComponent creature = getCreatureComponent(entity0, entity1);

        if(creature == null) {
            Gdx.app.log("CollisionSystem", "not a creature...");
            return;
        }

        if(player == null) {
            Gdx.app.log("CollisionSystem", "not a player...");
            return;
        }

        if(creature.getCreatureState() == CreatureComponent.CREATURE_STATE.DYING) {
            Gdx.app.log("CollisionSystem", "creature already dying...");
            return;
        }

        CreatureDamager.collide(player, creature);
    }

    private CreatureComponent getCreatureComponent(Entity entity0, Entity entity1) {
        CreatureComponent creatureComponent = entity0.getComponent(CreatureComponent.class);
        if(creatureComponent != null) {
            return creatureComponent;
        }
        return entity1.getComponent(CreatureComponent.class);
    }

    private PlayerComponent getPlayerComponent(Entity entity0, Entity entity1) {
        PlayerComponent playerComponent = entity0.getComponent(PlayerComponent.class);
        if(playerComponent != null) {
            return playerComponent;
        }
        return entity1.getComponent(PlayerComponent.class);
    }
}