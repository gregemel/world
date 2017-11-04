package com.deeep.spaceglad.services;

import com.deeep.spaceglad.databags.CharacterComponent;
import com.deeep.spaceglad.databags.World;

public class WorldDisposer {
    public void dispose(World gameWorld) {
        gameWorld.getPhysicsSystem().getPhysicsSystemState().getCollisionWorld().removeAction(
                gameWorld.getCharacter().getComponent(CharacterComponent.class).getCharacterController());

        gameWorld.getPhysicsSystem().getPhysicsSystemState().getCollisionWorld().removeCollisionObject(
                gameWorld.getCharacter().getComponent(CharacterComponent.class).getGhostObject());

        gameWorld.getPhysicsSystem().dispose();

        gameWorld.setPhysicsSystem(null);
        gameWorld.getRenderSystem().dispose();

        gameWorld.getCharacter().getComponent(CharacterComponent.class).getCharacterController().dispose();
        gameWorld.getCharacter().getComponent(CharacterComponent.class).getGhostObject().dispose();
        gameWorld.getCharacter().getComponent(CharacterComponent.class).getGhostShape().dispose();
    }
}
