package com.emelwerx.world.services.updaters;

import com.badlogic.gdx.graphics.Camera;
import com.emelwerx.world.databags.components.CharacterComponent;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.databags.systemstates.PlayerSystemState;

public class PlayerMoveUpdater {

    public static void update(float delta, PlayerSystemState state) {
        CharacterComponent characterComponent = state.getCharacterComponent();
        ModelComponent modelComponent = state.getModelComponent();
        Camera camera = state.getWorldPerspectiveCamera();

        CameraRotationUpdater.update(state, camera);
        PlayerDirectionUpdater.update(delta, state, characterComponent, modelComponent, camera);
        PlayerTranslationUpdater.update(state, characterComponent, modelComponent, camera);
        PlayerExtraInputUpdater.update(state, characterComponent);
    }
}