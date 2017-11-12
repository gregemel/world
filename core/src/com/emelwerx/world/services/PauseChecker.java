package com.emelwerx.world.services;

import com.badlogic.ashley.core.Engine;
import com.emelwerx.world.Settings;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.systems.MonsterSystem;
import com.emelwerx.world.systems.PhysicsSystem;
import com.emelwerx.world.systems.PlayerSystem;

public class PauseChecker {
    public static void checkPause(World gameWorld) {
        boolean run = !Settings.Paused;
        Engine entityEngine = gameWorld.getEntityEngine();
        entityEngine.getSystem(PlayerSystem.class).setProcessing(run);
        entityEngine.getSystem(MonsterSystem.class).setProcessing(run);
        entityEngine.getSystem(PhysicsSystem.class).setProcessing(run);
    }
}
