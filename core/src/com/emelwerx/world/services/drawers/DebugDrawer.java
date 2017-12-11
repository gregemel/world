package com.emelwerx.world.services.drawers;

import com.emelwerx.world.databags.World;

public class DebugDrawer {
    public static void draw(World world) {
        if (world.isDebug()) {
            world.getDebugDrawer().begin(world.getWorldPerspectiveCamera());
            world.getPhysicsSystem().getState().getCollisionWorld().debugDrawWorld();
            world.getDebugDrawer().end();
        }
    }
}
