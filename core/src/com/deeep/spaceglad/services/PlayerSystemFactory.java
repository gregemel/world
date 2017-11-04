package com.deeep.spaceglad.services;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.deeep.spaceglad.UI.GameUI;
import com.deeep.spaceglad.databags.World;
import com.deeep.spaceglad.databags.PlayerSystemState;
import com.deeep.spaceglad.systems.PlayerSystem;

public class PlayerSystemFactory {
    public PlayerSystem create(World gameWorld, GameUI gameUI, Camera camera) {
        PlayerSystem playerSystem = new PlayerSystem();
        PlayerSystemState playerSystemState = new PlayerSystemState();

        playerSystemState.setCamera(camera);
        playerSystemState.setGameWorld(gameWorld);

        playerSystemState.setGameUI(gameUI);

        playerSystemState.setRayTestCB(new ClosestRayResultCallback(Vector3.Zero, Vector3.Z));

        playerSystem.setPlayerSystemState(playerSystemState);

        return playerSystem;
    }
}
