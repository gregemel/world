package com.emelwerx.world.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.emelwerx.world.UI.GameUI;
import com.emelwerx.world.databags.PlayerSystemState;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.systems.PlayerSystem;

import static java.lang.String.format;

public class PlayerSystemFactory {
    public static PlayerSystem create(World gameWorld, GameUI gameUI, Camera camera) {
        Gdx.app.log("PlayerSystemFactory", format("creating player system %s, %s, %s", gameWorld.toString(), gameUI.toString(), camera.toString()));
        PlayerSystem playerSystem = new PlayerSystem();
        playerSystem.setPlayerSystemState(getPlayerSystemState(gameWorld, gameUI, camera));
        return playerSystem;
    }

    private static PlayerSystemState getPlayerSystemState(World gameWorld, GameUI gameUI, Camera camera) {
        PlayerSystemState playerSystemState = new PlayerSystemState();
        playerSystemState.setCamera(camera);
        playerSystemState.setGameWorld(gameWorld);
        playerSystemState.setGameUI(gameUI);
        playerSystemState.setRayTestCB(new ClosestRayResultCallback(Vector3.Zero, Vector3.Z));
        return playerSystemState;
    }
}
