package com.deeep.spaceglad.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.deeep.spaceglad.databags.CharacterComponent;
import com.deeep.spaceglad.databags.MonsterComponent;
import com.deeep.spaceglad.databags.PlayerComponent;
import com.deeep.spaceglad.databags.StatusComponent;

import static java.lang.String.format;

public class CollisionListener extends ContactListener {
    @Override
    public void onContactStarted(btCollisionObject colObj0, btCollisionObject colObj1) {
        Gdx.app.log("CollisionListener", format("contact between %s, %s", colObj0.toString(), colObj1.toString()));

        if (colObj0.userData instanceof Entity && colObj1.userData instanceof Entity) {

            Entity entity0 = (Entity) colObj0.userData;
            Entity entity1 = (Entity) colObj1.userData;

            Gdx.app.log("CollisionListener", format("two entities in contact %s, %s", entity0.toString(), entity1.toString()));


            if (entity0.getComponent(CharacterComponent.class) != null
                    && entity1.getComponent(CharacterComponent.class) != null) {
                if (entity0.getComponent(MonsterComponent.class) != null) {
                    if (entity0.getComponent(StatusComponent.class).isAlive()) {
                        entity1.getComponent(PlayerComponent.class).health -= 10;
                        Gdx.app.log("CollisionListener", "1 ouch!");
                    }
                    entity0.getComponent(StatusComponent.class).setAlive(false);
                } else {
                    if (entity1.getComponent(StatusComponent.class).isAlive()) {
                                            entity0.getComponent(PlayerComponent.class).health -= 10;
                        Gdx.app.log("CollisionListener", "0 ouch!");
                    }
                    entity1.getComponent(StatusComponent.class).setAlive(false);
                }
            }
        }
    }
}
