package com.emelwerx.world.services.updaters;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.emelwerx.world.databags.components.CharacterComponent;
import com.emelwerx.world.databags.systemstates.CreatureSystemState;

public class CreatureTransationUpdater {

    public static Vector3 update(CreatureSystemState creatureSystemState, CharacterComponent characterComponent) {
        Matrix4 ghostMatrix = creatureSystemState.getGhostMatrix();
        ghostMatrix.set(0, 0, 0, 0);
        Vector3 translation = creatureSystemState.getTranslation();
        translation.set(0, 0, 0);
        characterComponent.getGhostObject().getWorldTransform(ghostMatrix);
        ghostMatrix.getTranslation(translation);
        return translation;
    }
}