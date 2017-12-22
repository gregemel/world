package com.emelwerx.world.services.updaters;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.emelwerx.world.databags.components.CharacterComponent;
import com.emelwerx.world.databags.systemstates.PlayerSystemState;
import com.emelwerx.world.services.Shooter;

public class PlayerExtraInputUpdater {
    public static void update(PlayerSystemState state, CharacterComponent characterComponent) {
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
