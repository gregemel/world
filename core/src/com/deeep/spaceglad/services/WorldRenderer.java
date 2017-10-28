package com.deeep.spaceglad.services;

import com.deeep.spaceglad.Settings;
import com.deeep.spaceglad.databags.GameWorld;
import com.deeep.spaceglad.systems.PhysicsSystem;
import com.deeep.spaceglad.systems.MonsterSystem;
import com.deeep.spaceglad.systems.PlayerSystem;
import com.deeep.spaceglad.systems.StatusSystem;

public class WorldRenderer {
    public void render(GameWorld gameWorld, float delta) {
        renderWorld(gameWorld, delta);
        checkPause(gameWorld);
    }

    public void resize(GameWorld gameWorld, int width, int height) {
        gameWorld.getRenderSystem().resize(width, height);
    }

    private void checkPause(GameWorld gameWorld) {
        if (Settings.Paused) {
            gameWorld.getEngine().getSystem(PlayerSystem.class).setProcessing(false);
            gameWorld.getEngine().getSystem(MonsterSystem.class).setProcessing(false);
            gameWorld.getEngine().getSystem(StatusSystem.class).setProcessing(false);
            gameWorld.getEngine().getSystem(PhysicsSystem.class).setProcessing(false);
        } else {
            gameWorld.getEngine().getSystem(PlayerSystem.class).setProcessing(true);
            gameWorld.getEngine().getSystem(MonsterSystem.class).setProcessing(true);
            gameWorld.getEngine().getSystem(StatusSystem.class).setProcessing(true);
            gameWorld.getEngine().getSystem(PhysicsSystem.class).setProcessing(true);
        }
    }

    private void renderWorld(GameWorld gameWorld, float delta) {
        gameWorld.getEngine().update(delta);
        if (gameWorld.isDebug()) {
            gameWorld.getDebugDrawer().begin(gameWorld.getRenderSystem().perspectiveCamera);
            gameWorld.getPhysicsSystem().collisionWorld.debugDrawWorld();
            gameWorld.getDebugDrawer().end();
        }
    }
}
