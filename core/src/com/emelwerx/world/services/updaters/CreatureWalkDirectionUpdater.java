package com.emelwerx.world.services.updaters;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.dynamics.btKinematicCharacterController;
import com.emelwerx.world.databags.components.CharacterComponent;

public class CreatureWalkDirectionUpdater {

    public static void update(float delta, CharacterComponent chasingCharacter) {
        Vector3 walkDirection = chasingCharacter.getWalkDirection();
        walkDirection.set(0, 0, 0);
        walkDirection.add(chasingCharacter.getCharacterDirection());
        walkDirection.scl(10f * delta);
        btKinematicCharacterController chasingController = chasingCharacter.getCharacterController();
        chasingController.setWalkDirection(walkDirection);
    }
}