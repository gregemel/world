package com.emelwerx.world.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.databags.systemstates.CreatureSystemState;
import com.emelwerx.world.services.factories.CreatureEntityFactory;

import static java.lang.String.format;

public class Spawner {
    public static void update(CreatureSystemState creatureSystemState) {
        if (getCurrentCreatureCount(creatureSystemState) < getMaxSpawnCount(creatureSystemState.getWorld())) {
            spawnCreature(creatureSystemState.getWorld());
        }
    }

    private static int getMaxSpawnCount(World world) {
        return world.getCurrentScene().getMaxSpawnCount();
    }

    private static int getCurrentCreatureCount(CreatureSystemState creatureSystemState) {
        return creatureSystemState.getCreatures().size();
    }

    private static void spawnCreature(World world) {
        Entity creatureEntity = CreatureEntityFactory.create("monster.g3dj", world);
        world.getEntityEngine().addEntity(creatureEntity);
        Gdx.app.log("Spawner", format("spawned: %s", creatureEntity.toString()));
    }
}

