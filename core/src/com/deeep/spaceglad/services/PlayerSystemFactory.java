package com.deeep.spaceglad.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.deeep.spaceglad.UI.GameUI;
import com.deeep.spaceglad.databags.PlayerSystemState;
import com.deeep.spaceglad.databags.World;
import com.deeep.spaceglad.systems.PlayerSystem;

import static java.lang.String.format;

public class PlayerSystemFactory {
    public PlayerSystem create(World gameWorld, GameUI gameUI, Camera camera) {
        Gdx.app.log("PlayerSystemFactory", format("creating player system %s, %s, %s", gameWorld.toString(), gameUI.toString(), camera.toString()));
        PlayerSystem playerSystem = new PlayerSystem();
        playerSystem.setPlayerSystemState(getPlayerSystemState(gameWorld, gameUI, camera));
        return playerSystem;
    }

    private PlayerSystemState getPlayerSystemState(World gameWorld, GameUI gameUI, Camera camera) {
        PlayerSystemState playerSystemState = new PlayerSystemState();
        playerSystemState.setCamera(camera);
        playerSystemState.setGameWorld(gameWorld);
        playerSystemState.setGameUI(gameUI);
        playerSystemState.setRayTestCB(new ClosestRayResultCallback(Vector3.Zero, Vector3.Z));
        return playerSystemState;
    }
}
