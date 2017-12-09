package com.emelwerx.world.services.updaters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.emelwerx.world.databags.components.CharacterComponent;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.databags.systemstates.PlayerSystemState;
import com.emelwerx.world.services.Shooter;

public class PlayerMoveUpdater {

    public static void update(float delta, PlayerSystemState playerSystemState) {
        CharacterComponent characterComponent = playerSystemState.getCharacterComponent();
        ModelComponent modelComponent = playerSystemState.getModelComponent();
        Camera camera = playerSystemState.getWorldPerspectiveCamera();

        CameraRotationUpdater.update(playerSystemState, camera);
        PlayerDirectionUpdater.update(delta, playerSystemState, characterComponent, modelComponent, camera);
        TranslationUpdater.update(playerSystemState, characterComponent, modelComponent, camera);
        checkAttack(playerSystemState);
        checkJump(playerSystemState, characterComponent);
    }

    private static void checkAttack(PlayerSystemState playerSystemState) {
        if (Gdx.input.justTouched()) {
            Shooter.fire(playerSystemState);
        }
    }

    private static void checkJump(PlayerSystemState playerSystemState, CharacterComponent characterComponent) {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            characterComponent.getCharacterController().setJumpSpeed(playerSystemState.getJumpForce());
            characterComponent.getCharacterController().jump();
        }
    }
}