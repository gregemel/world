package com.emelwerx.world.services;

import com.emelwerx.world.databags.World;

public class WorldDrawer {
    public static void draw(World gameWorld, float delta) {
        gameWorld.getEntityEngine().update(delta);
        if (gameWorld.isDebug()) {
            gameWorld.getDebugDrawer().begin(gameWorld.getRenderSystem().getRenderSystemState().getPerspectiveCamera());
            gameWorld.getPhysicsSystem().getPhysicsSystemState().getCollisionWorld().debugDrawWorld();
            gameWorld.getDebugDrawer().end();
        }
    }
}
