package com.emelwerx.world.services.updaters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.emelwerx.world.databags.components.CharacterComponent;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.databags.systemstates.PlayerSystemState;
import com.emelwerx.world.services.Shooter;

public class PlayerMoveUpdater {

    public static void update(float delta, PlayerSystemState state) {
        CharacterComponent characterComponent = state.getCharacterComponent();
        ModelComponent modelComponent = state.getModelComponent();
        Camera camera = state.getWorldPerspectiveCamera();

        CameraRotationUpdater.update(state, camera);
        PlayerDirectionUpdater.update(delta, state, characterComponent, modelComponent, camera);
        PlayerTranslationUpdater.update(state, characterComponent, modelComponent, camera);
        checkAttack(state);
        checkJump(state, characterComponent);
    }

    private static void checkAttack(PlayerSystemState playerSystemState) {
        if (Gdx.input.justTouched()) {
            Shooter.fire(playerSystemState);
        }
    }

    private static void checkJump(PlayerSystemState state, CharacterComponent player) {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            player.getCharacterController().setJumpSpeed(state.getJumpForce());
            player.getCharacterController().jump();
        }
    }
}