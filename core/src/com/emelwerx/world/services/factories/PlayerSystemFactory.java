package com.emelwerx.world.services.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.emelwerx.world.systems.WorldUiSystem;
import com.emelwerx.world.databags.systemstates.PlayerSystemState;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.systems.PlayerSystem;

import static java.lang.String.format;

public class PlayerSystemFactory {
    public static PlayerSystem create(World gameWorld, WorldUiSystem worldUiSystem, Camera camera) {
        Gdx.app.log("PlayerSystemFactory", format("creating player system %s, %s, %s", gameWorld.toString(), worldUiSystem.toString(), camera.toString()));

        PlayerSystemState playerSystemState = new PlayerSystemState();
        playerSystemState.setCamera(camera);
        playerSystemState.setGameWorld(gameWorld);
        playerSystemState.setWorldUiSystem(worldUiSystem);
        playerSystemState.setRayTestCB(new ClosestRayResultCallback(Vector3.Zero, Vector3.Z));

        return new PlayerSystem(playerSystemState);
    }

}
