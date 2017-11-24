package com.emelwerx.world.databags.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;
import com.badlogic.gdx.physics.bullet.collision.btPairCachingGhostObject;
import com.badlogic.gdx.physics.bullet.dynamics.btKinematicCharacterController;

public class CharacterComponent extends Component {
    private btPairCachingGhostObject ghostObject;
    private btConvexShape ghostShape;
    private btKinematicCharacterController characterController;
    private Vector3 characterDirection;
    private Vector3 walkDirection;

    public btPairCachingGhostObject getGhostObject() {
        return ghostObject;
    }

    public void setGhostObject(btPairCachingGhostObject ghostObject) {
        this.ghostObject = ghostObject;
    }

    public btConvexShape getGhostShape() {
        return ghostShape;
    }

    public void setGhostShape(btConvexShape ghostShape) {
        this.ghostShape = ghostShape;
    }

    public btKinematicCharacterController getCharacterController() {
        return characterController;
    }

    public void setCharacterController(btKinematicCharacterController characterController) {
        this.characterController = characterController;
    }

    public Vector3 getCharacterDirection() {
        return characterDirection;
    }

    public void setCharacterDirection(Vector3 characterDirection) {
        this.characterDirection = characterDirection;
    }

    public Vector3 getWalkDirection() {
        return walkDirection;
    }

    public void setWalkDirection(Vector3 walkDirection) {
        this.walkDirection = walkDirection;
    }
}
