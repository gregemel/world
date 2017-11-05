package com.emelwerx.world.services;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.emelwerx.world.Settings;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.systems.PhysicsSystem;
import com.emelwerx.world.systems.MonsterSystem;
import com.emelwerx.world.systems.PlayerSystem;
import com.emelwerx.world.systems.StatusSystem;

import static java.lang.String.format;

public class WorldRenderer {
    public void render(World gameWorld, float delta) {
        renderWorld(gameWorld, delta);
        checkPause(gameWorld);
    }

    public void resize(World gameWorld, int width, int height) {
        Gdx.app.log("resize", format("load %s, %d, %d", gameWorld.toString(), width, height));
        gameWorld.getRenderSystem().resize(width, height);
    }

    private void checkPause(World gameWorld) {
        Engine entityEngine = gameWorld.getEntityEngine();
        if (Settings.Paused) {
            entityEngine.getSystem(PlayerSystem.class).setProcessing(false);
            entityEngine.getSystem(MonsterSystem.class).setProcessing(false);
            entityEngine.getSystem(StatusSystem.class).setProcessing(false);
            entityEngine.getSystem(PhysicsSystem.class).setProcessing(false);
        } else {
            entityEngine.getSystem(PlayerSystem.class).setProcessing(true);
            entityEngine.getSystem(MonsterSystem.class).setProcessing(true);
            entityEngine.getSystem(StatusSystem.class).setProcessing(true);
            entityEngine.getSystem(PhysicsSystem.class).setProcessing(true);
        }
    }

    private void renderWorld(World gameWorld, float delta) {
        gameWorld.getEntityEngine().update(delta);
        if (gameWorld.isDebug()) {
            gameWorld.getDebugDrawer().begin(gameWorld.getRenderSystem().getRenderSystemState().getPerspectiveCamera());
            gameWorld.getPhysicsSystem().getPhysicsSystemState().getCollisionWorld().debugDrawWorld();
            gameWorld.getDebugDrawer().end();
        }
    }
}
