package com.emelwerx.world.services.updaters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.emelwerx.world.databags.components.CharacterComponent;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.databags.systemstates.PlayerSystemState;

import java.util.Locale;

import static java.lang.String.format;

public class PlayerDirectionUpdater {

    private static float lastLog = 5.1f;

    public static void update(
            float delta,
            PlayerSystemState playerSystemState,
            CharacterComponent characterComponent,
            ModelComponent modelComponent,
            Camera worldPerspectiveCamera) {
        characterComponent.getCharacterDirection().set(-1, 0, 0).rot(modelComponent.getInstance().transform).nor();
        Vector3 playerVector = playerSystemState.getTmp();
        playerVector.set(0, 0, 0);
        Vector3 walkDirection = getWalkDirection(playerVector, worldPerspectiveCamera, characterComponent);
        walkDirection.add(playerVector);
        walkDirection.scl(10f * delta);
        characterComponent.getCharacterController().setWalkDirection(walkDirection);

        log(delta, modelComponent);
    }

    private static Vector3 getWalkDirection(Vector3 playerVector, Camera camera, CharacterComponent characterComponent) {
        Vector3 walkDirection = characterComponent.getWalkDirection();
        walkDirection.set(0, 0, 0);
        PlayerInputUpdater.update(playerVector, camera, walkDirection);
        return walkDirection;
    }

    private static void log(float delta, ModelComponent modelComponent) {
        lastLog+=delta;
        if(lastLog>5f) {
            lastLog=0f;
            Matrix4 matrix4 = modelComponent.getInstance().transform;
            Gdx.app.log("**player location**", format(Locale.US,"(%f, %f, %f)",
                    matrix4.getValues()[12], matrix4.getValues()[13], matrix4.getValues()[14]));
        }
    }
}