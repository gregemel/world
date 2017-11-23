package com.emelwerx.world.services.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.databags.systemstates.CreatureSystemState;
import com.emelwerx.world.systems.CreatureSystem;

import static java.lang.String.format;

public class CreatureSystemFactory {
    public static CreatureSystem create(World world) {
        Gdx.app.log("CreatureSystemFactory", format("creating creature system for %s", world));
        CreatureSystemState creatureSystemState = new CreatureSystemState();
        creatureSystemState.setWorld(world);
        creatureSystemState.setPlayerPosition(new Vector3());
        creatureSystemState.setCurrentCreaturePosition(new Vector3());
        creatureSystemState.setGhost(new Matrix4());
        creatureSystemState.setTranslation(new Vector3());
        creatureSystemState.setQuaternion(new Quaternion());
        return new CreatureSystem(creatureSystemState);
    }
}
