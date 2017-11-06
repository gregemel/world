package com.emelwerx.world.services;


import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.databags.MonsterSystemState;
import com.emelwerx.world.systems.MonsterSystem;

public class MonsterSystemFactory {
    public static MonsterSystem create(World gameWorld) {
        Gdx.app.log("MonsterSystemFactory", "creating monster system");
        MonsterSystemState monsterSystemState = getMonsterSystemState();
        MonsterSystem monsterSystem = new MonsterSystem(monsterSystemState);
        monsterSystemState.setGameWorld(gameWorld);
//        monsterSystem.setMonsterSystemState(monsterSystemState);
        return monsterSystem;
    }

    private static MonsterSystemState getMonsterSystemState() {
        return new MonsterSystemState();
    }
}
