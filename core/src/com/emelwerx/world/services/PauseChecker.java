package com.emelwerx.world.services;

import com.badlogic.ashley.core.Engine;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.systems.CreatureSystem;
import com.emelwerx.world.systems.PhysicsSystem;
import com.emelwerx.world.systems.PlayerSystem;

public class PauseChecker {
    public static void checkPause(World gameWorld) {
        boolean run = !Settings.isPaused();
        Engine entityEngine = gameWorld.getEntityEngine();
        entityEngine.getSystem(PlayerSystem.class).setProcessing(run);
        entityEngine.getSystem(CreatureSystem.class).setProcessing(run);
        entityEngine.getSystem(PhysicsSystem.class).setProcessing(run);
    }
}
