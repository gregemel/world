package com.deeep.spaceglad.services;


import com.deeep.spaceglad.databags.World;
import com.deeep.spaceglad.databags.MonsterSystemState;
import com.deeep.spaceglad.systems.MonsterSystem;

public class MonsterSystemFactory {
    public MonsterSystem create(World gameWorld) {
        MonsterSystem monsterSystem = new MonsterSystem();
        MonsterSystemState monsterSystemState = new MonsterSystemState();
        monsterSystemState.setGameWorld(gameWorld);
        monsterSystem.setMonsterSystemState(monsterSystemState);
        return monsterSystem;
    }
}
