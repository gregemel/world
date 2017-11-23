package com.emelwerx.world.services.drawers;

import com.emelwerx.world.databags.World;

public class WorldDrawer {
    public static void draw(World world, float delta) {
        world.getEntityEngine().update(delta);
        if (world.isDebug()) {
            world.getDebugDrawer().begin(world.getPerspectiveCamera());
            world.getPhysicsSystem().getPhysicsSystemState().getCollisionWorld().debugDrawWorld();
            world.getDebugDrawer().end();
        }
    }
}
