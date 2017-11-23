package com.emelwerx.world.services;

import com.badlogic.ashley.core.Engine;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.systems.CreatureSystem;
import com.emelwerx.world.systems.PhysicsSystem;
import com.emelwerx.world.systems.PlayerSystem;

public class Pauser {
    public static void check(World world) {
        boolean run = !Settings.isPaused();
        Engine entityEngine = world.getEntityEngine();
        entityEngine.getSystem(PlayerSystem.class).setProcessing(run);
        entityEngine.getSystem(CreatureSystem.class).setProcessing(run);
        entityEngine.getSystem(PhysicsSystem.class).setProcessing(run);
    }
}
