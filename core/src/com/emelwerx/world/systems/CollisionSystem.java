package com.emelwerx.world.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.emelwerx.world.databags.CharacterComponent;
import com.emelwerx.world.databags.MonsterComponent;
import com.emelwerx.world.databags.PlayerComponent;
import com.emelwerx.world.databags.ThoughtComponent;

import static java.lang.String.format;

public class CollisionSystem extends ContactListener {
    @Override
    public void onContactStarted(btCollisionObject colObj0, btCollisionObject colObj1) {
        Gdx.app.log("CollisionSystem", format("contact between %s, %s", colObj0.toString(), colObj1.toString()));

        if (colObj0.userData instanceof Entity && colObj1.userData instanceof Entity) {

            Entity entity0 = (Entity) colObj0.userData;
            Entity entity1 = (Entity) colObj1.userData;

            Gdx.app.log("CollisionSystem", format("two entities in contact %s, %s", entity0.toString(), entity1.toString()));

            if (entity0.getComponent(CharacterComponent.class) != null
                    && entity1.getComponent(CharacterComponent.class) != null) {
                if (entity0.getComponent(MonsterComponent.class) != null) {
                    if (entity0.getComponent(ThoughtComponent.class).isAlive()) {
                        entity1.getComponent(PlayerComponent.class).health -= 10;
                        Gdx.app.log("CollisionSystem", "1 ouch!");
                    }
                    entity0.getComponent(ThoughtComponent.class).setAlive(false);
                } else {
                    if (entity1.getComponent(ThoughtComponent.class).isAlive()) {
                                            entity0.getComponent(PlayerComponent.class).health -= 10;
                        Gdx.app.log("CollisionSystem", "0 ouch!");
                    }
                    entity1.getComponent(ThoughtComponent.class).setAlive(false);
                }
            }
        } else {
            //TODO - ge[2017-11-05]
            //what else could collide?
            //what does monster hitting ground look like?
            //what does player hitting ground look like?
            //these events should trigger sounds
        }
    }
}
