package com.emelwerx.world.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.emelwerx.world.databags.MonsterComponent;
import com.emelwerx.world.databags.PlayerComponent;
import com.emelwerx.world.services.MonsterPlayerCollisionService;

import static java.lang.String.format;

public class CollisionSystem extends ContactListener {
    @Override
    public void onContactStarted(btCollisionObject colObj0, btCollisionObject colObj1) {
        if (colObj0.userData instanceof Entity && colObj1.userData instanceof Entity) {

            Entity entity0 = (Entity) colObj0.userData;
            Entity entity1 = (Entity) colObj1.userData;

            Gdx.app.log("CollisionSystem", format("two entities in contact %s, %s",
                    entity0.toString(), entity1.toString()));

            PlayerComponent playerComponent = getPlayerComponent(entity0, entity1);
            MonsterComponent monsterComponent = getMonsterComponent(entity0, entity1);

            checkCollision(playerComponent, monsterComponent);
        }
    }

    private void checkCollision(PlayerComponent playerComponent, MonsterComponent monsterComponent) {
        boolean isTransitionToPlayerMonsterCollision = monsterComponent != null
                && monsterComponent.getMonsterState() != MonsterComponent.MONSTER_STATE.DYING
                && playerComponent != null;
        if(isTransitionToPlayerMonsterCollision) {
            Gdx.app.log("CollisionSystem", "OUCH!");
            MonsterPlayerCollisionService.collide(playerComponent, monsterComponent);
        }
    }


    private MonsterComponent getMonsterComponent(Entity entity0, Entity entity1) {
        MonsterComponent monster = entity0.getComponent(MonsterComponent.class);
        if(monster == null) {
            monster = entity1.getComponent(MonsterComponent.class);
        }
        return monster;
    }

    private PlayerComponent getPlayerComponent(Entity entity0, Entity entity1) {
        PlayerComponent playerComponent = entity0.getComponent(PlayerComponent.class);
        if(playerComponent == null) {
            playerComponent = entity1.getComponent(PlayerComponent.class);
        }
        return playerComponent;
    }
}
