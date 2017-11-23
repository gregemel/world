package com.emelwerx.world.services.factories;

import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.databags.systemstates.CreatureSystemState;
import com.emelwerx.world.systems.CreatureSystem;

import static java.lang.String.format;

public class CreatureSystemFactory {
    public static CreatureSystem create(World world) {
        Gdx.app.log("CreatureSystemFactory", format("creating creature system for %s", world));
        CreatureSystemState creatureSystemState = new CreatureSystemState();
        creatureSystemState.setWorld(world);
        return new CreatureSystem(creatureSystemState);
    }

}
