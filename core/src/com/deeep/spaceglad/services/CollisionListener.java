package com.deeep.spaceglad.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.deeep.spaceglad.databags.CharacterComponent;
import com.deeep.spaceglad.databags.MonsterComponent;
import com.deeep.spaceglad.databags.PlayerComponent;
import com.deeep.spaceglad.databags.StatusComponent;

public class CollisionListener extends ContactListener {
    @Override
    public void onContactStarted(btCollisionObject colObj0, btCollisionObject colObj1) {
        if (colObj0.userData instanceof Entity && colObj0.userData instanceof Entity) {
            Entity entity0 = (Entity) colObj0.userData;
            Entity entity1 = (Entity) colObj1.userData;
            if (entity0.getComponent(CharacterComponent.class) != null && entity1.getComponent(CharacterComponent.class) != null) {
                if (entity0.getComponent(MonsterComponent.class) != null) {
                    if (entity0.getComponent(StatusComponent.class).alive)
                        entity1.getComponent(PlayerComponent.class).health -= 10;
                    entity0.getComponent(StatusComponent.class).alive = false;
                } else {
                    if (entity1.getComponent(StatusComponent.class).alive)
                        entity0.getComponent(PlayerComponent.class).health -= 10;
                    entity1.getComponent(StatusComponent.class).alive = false;
                }
            }
        }
    }
}
