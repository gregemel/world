package com.deeep.spaceglad.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.deeep.spaceglad.databags.CharacterComponent;
import com.deeep.spaceglad.databags.World;

import static java.lang.String.format;

public class WorldDisposer {
    public void dispose(World gameWorld) {
        Gdx.app.log("WorldDisposer", format("dispose %s", gameWorld.toString()));
        CharacterComponent characterComponent = gameWorld.getEntityCharacter().getComponent(CharacterComponent.class);
        btDiscreteDynamicsWorld collisionWorld = gameWorld.getPhysicsSystem().getPhysicsSystemState().getCollisionWorld();

        collisionWorld.removeAction(characterComponent.getCharacterController());
        collisionWorld.removeCollisionObject(characterComponent.getGhostObject());

        gameWorld.getPhysicsSystem().dispose();
        gameWorld.setPhysicsSystem(null);
        gameWorld.getRenderSystem().dispose();

        characterComponent.getCharacterController().dispose();
        characterComponent.getGhostObject().dispose();
        characterComponent.getGhostShape().dispose();
    }
}
