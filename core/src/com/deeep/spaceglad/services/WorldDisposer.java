package com.deeep.spaceglad.services;

import com.deeep.spaceglad.databags.CharacterComponent;
import com.deeep.spaceglad.databags.GameWorld;

public class WorldDisposer {
    public void dispose(GameWorld gameWorld) {
        gameWorld.getBulletSystem().collisionWorld.removeAction(
                gameWorld.getCharacter().getComponent(CharacterComponent.class).getCharacterController());

        gameWorld.getBulletSystem().collisionWorld.removeCollisionObject(
                gameWorld.getCharacter().getComponent(CharacterComponent.class).getGhostObject());

        gameWorld.getBulletSystem().dispose();

        gameWorld.setBulletSystem(null);
        gameWorld.getRenderSystem().dispose();

        gameWorld.getCharacter().getComponent(CharacterComponent.class).getCharacterController().dispose();
        gameWorld.getCharacter().getComponent(CharacterComponent.class).getGhostObject().dispose();
        gameWorld.getCharacter().getComponent(CharacterComponent.class).getGhostShape().dispose();
    }
}
