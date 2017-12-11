package com.emelwerx.world.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.emelwerx.world.databags.components.CharacterComponent;
import com.emelwerx.world.databags.World;

import static java.lang.String.format;

public class Disposer {
    public static void dispose(World world) {
        Gdx.app.log("Disposer", format("dispose %s", world.toString()));
        CharacterComponent characterComponent = world.getEntityCharacter().getComponent(CharacterComponent.class);
        btDiscreteDynamicsWorld collisionWorld = world.getPhysicsSystem().getState().getCollisionWorld();

        collisionWorld.removeAction(characterComponent.getCharacterController());
        collisionWorld.removeCollisionObject(characterComponent.getGhostObject());

        world.getPhysicsSystem().dispose();
        world.setPhysicsSystem(null);
        world.getRenderSystem().dispose();

        characterComponent.getCharacterController().dispose();
        characterComponent.getGhostObject().dispose();
        characterComponent.getGhostShape().dispose();
    }
}