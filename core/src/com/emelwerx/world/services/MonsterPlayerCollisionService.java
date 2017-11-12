package com.emelwerx.world.services;

import com.emelwerx.world.databags.MonsterComponent;
import com.emelwerx.world.databags.PlayerComponent;

public class MonsterPlayerCollisionService {
    public static void collide(PlayerComponent playerComponent, MonsterComponent monsterComponent) {
        playerComponent.setHealth(playerComponent.getHealth() - 10);
        monsterComponent.setMonsterState(MonsterComponent.MONSTER_STATE.DYING);
    }
}
