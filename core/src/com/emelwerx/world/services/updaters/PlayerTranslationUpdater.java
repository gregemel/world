package com.emelwerx.world.services.updaters;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.emelwerx.world.databags.components.CharacterComponent;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.databags.systemstates.PlayerSystemState;

public class PlayerTranslationUpdater {
    public static void update(
            PlayerSystemState playerSystemState,
            CharacterComponent characterComponent,
            ModelComponent modelComponent,
            Camera worldPerspectiveCamera) {
        Vector3 translation = playerSystemState.getTranslation();
        translation.set(0, 0, 0);

        setPlayerGhostTranslation(playerSystemState, characterComponent, translation);

        modelComponent.getInstance().transform.set(
                translation.x, translation.y, translation.z,
                worldPerspectiveCamera.direction.x, worldPerspectiveCamera.direction.y, worldPerspectiveCamera.direction.z,
                0);
        worldPerspectiveCamera.position.set(translation.x, translation.y, translation.z);
        worldPerspectiveCamera.update(true);
    }

    private static void setPlayerGhostTranslation(
            PlayerSystemState playerSystemState, CharacterComponent characterComponent, Vector3 translation) {
        Matrix4 ghost = playerSystemState.getGhost();
        ghost.set(0, 0, 0, 0);
        characterComponent.getGhostObject().getWorldTransform(ghost);
        ghost.getTranslation(translation);
    }
}