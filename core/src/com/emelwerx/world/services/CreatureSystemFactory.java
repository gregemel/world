package com.emelwerx.world.services;

import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.databags.CreatureSystemState;
import com.emelwerx.world.systems.CreatureSystem;

import static java.lang.String.format;

public class CreatureSystemFactory {
    public static CreatureSystem create(World gameWorld) {
        Gdx.app.log("CreatureSystemFactory", format("creating creature system for %s", gameWorld));
        CreatureSystemState creatureSystemState = new CreatureSystemState();
        creatureSystemState.setGameWorld(gameWorld);
        return new CreatureSystem(creatureSystemState);
    }

}
