package com.deeep.spaceglad.services;

import com.deeep.spaceglad.databags.CharacterComponent;
import com.deeep.spaceglad.databags.GameWorld;

public class WorldDisposer {
    public void dispose(GameWorld gameWorld) {
        gameWorld.getPhysicsSystem().collisionWorld.removeAction(
                gameWorld.getCharacter().getComponent(CharacterComponent.class).getCharacterController());

        gameWorld.getPhysicsSystem().collisionWorld.removeCollisionObject(
                gameWorld.getCharacter().getComponent(CharacterComponent.class).getGhostObject());

        gameWorld.getPhysicsSystem().dispose();

        gameWorld.setPhysicsSystem(null);
        gameWorld.getRenderSystem().dispose();

        gameWorld.getCharacter().getComponent(CharacterComponent.class).getCharacterController().dispose();
        gameWorld.getCharacter().getComponent(CharacterComponent.class).getGhostObject().dispose();
        gameWorld.getCharacter().getComponent(CharacterComponent.class).getGhostShape().dispose();
    }
}
