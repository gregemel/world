package com.emelwerx.world.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.databags.systemstates.CreatureSystemState;
import com.emelwerx.world.services.factories.CreatureEntityFactory;

import static java.lang.String.format;

public class CreatureSpawner {
    public static void update(CreatureSystemState state) {
        if (getCount(state) >= getMaxCount(state)) {
            return;
        }

        spawn(state.getWorld());
    }

    private static int getMaxCount(CreatureSystemState state) {
        return state.getWorld().getCurrentScene().getMaxSpawnCount();
    }

    private static int getCount(CreatureSystemState state) {
        return state.getCreatures().size();
    }

    private static void spawn(World world) {
        Entity creatureEntity = CreatureEntityFactory.create("monster.g3dj", world);
        world.getEntityEngine().addEntity(creatureEntity);
        Gdx.app.log("CreatureSpawner", format("spawned: %s", creatureEntity.toString()));
    }
}