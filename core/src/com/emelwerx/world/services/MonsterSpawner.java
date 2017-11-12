package com.emelwerx.world.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.MonsterSystemState;

import static java.lang.String.format;

public class MonsterSpawner {
    public static void update(MonsterSystemState monsterSystemState) {
        int numberOfMonsters = monsterSystemState.getMonsters().size();
        if (numberOfMonsters < 1) {
            Gdx.app.log("MonsterSystem", "spawning monster...");
            spawnMonster(monsterSystemState);
        }
    }

    private static void spawnMonster(MonsterSystemState monsterSystemState) {
        Entity monster = MonsterFactory.create(
                "monster",
                monsterSystemState.getGameWorld());

        Gdx.app.log("MonsterSystem", format("monster spawned: %s", monster.toString()));

        monsterSystemState.getEntityEngine().addEntity(monster);
    }
}

