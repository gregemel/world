package com.emelwerx.world.services;


import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.databags.MonsterSystemState;
import com.emelwerx.world.systems.MonsterSystem;

public class MonsterSystemFactory {
    public static MonsterSystem create(World gameWorld) {
        Gdx.app.log("MonsterSystemFactory", "creating monster system");
        MonsterSystem monsterSystem = new MonsterSystem();
        MonsterSystemState monsterSystemState = new MonsterSystemState();
        monsterSystemState.setGameWorld(gameWorld);
        monsterSystem.setMonsterSystemState(monsterSystemState);
        return monsterSystem;
    }
}
