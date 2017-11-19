package com.emelwerx.world.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.CreatureSystemState;

import static java.lang.String.format;

public class CreatureSpawner {
    public static void update(CreatureSystemState creatureSystemState) {
        if (getCurrentCreatureCount(creatureSystemState) < getMaxSpawnCount(creatureSystemState)) {
            Gdx.app.log("CreatureSystem", "spawning creature...");
            spawnCreature(creatureSystemState);
        }
    }

    private static int getMaxSpawnCount(CreatureSystemState creatureSystemState) {
        return creatureSystemState.getGameWorld().getCurrentScene().getMaxSpawnCount();
    }

    private static int getCurrentCreatureCount(CreatureSystemState creatureSystemState) {
        return creatureSystemState.getCreatures().size();
    }

    private static void spawnCreature(CreatureSystemState creatureSystemState) {
        Entity creatureEntity = CreatureEntityFactory.create(
                "monster.g3dj",
                creatureSystemState.getGameWorld());
        creatureSystemState.getEntityEngine().addEntity(creatureEntity);
        Gdx.app.log("CreatureSystem", format("creatureEntity spawned: %s", creatureEntity.toString()));
    }
}

