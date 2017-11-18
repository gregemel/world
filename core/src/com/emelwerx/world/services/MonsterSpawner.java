package com.emelwerx.world.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.MonsterSystemState;

import static java.lang.String.format;

public class MonsterSpawner {
    public static void update(MonsterSystemState monsterSystemState) {
        if (getCurrentMonsterCount(monsterSystemState) < getMaxSpawnCount(monsterSystemState)) {
            Gdx.app.log("MonsterSystem", "spawning monster...");
            spawnMonster(monsterSystemState);
        }
    }

    private static int getMaxSpawnCount(MonsterSystemState monsterSystemState) {
        return monsterSystemState.getGameWorld().getCurrentScene().getMaxSpawnCount();
    }

    private static int getCurrentMonsterCount(MonsterSystemState monsterSystemState) {
        return monsterSystemState.getMonsters().size();
    }

    private static void spawnMonster(MonsterSystemState monsterSystemState) {
        Entity monster = MonsterFactory.create(
                "monster",
                monsterSystemState.getGameWorld());
        monsterSystemState.getEntityEngine().addEntity(monster);
        Gdx.app.log("MonsterSystem", format("monster spawned: %s", monster.toString()));
    }
}

