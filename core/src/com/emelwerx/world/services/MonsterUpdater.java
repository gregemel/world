package com.emelwerx.world.services;

import com.badlogic.ashley.core.Entity;
import com.emelwerx.world.databags.ModelComponent;
import com.emelwerx.world.databags.MonsterSystemState;
import com.emelwerx.world.databags.ThoughtComponent;

public class MonsterUpdater {
    public static void updateAll(float delta, MonsterSystemState monsterSystemState) {
        for(Entity monsterEntity: monsterSystemState.getMonsters()) {

            ThoughtComponent thoughtComponent = monsterEntity.getComponent(ThoughtComponent.class);

            boolean monsterIsAlive = thoughtComponent.isAlive();
            if (monsterIsAlive) {
                ModelComponent playerModel = monsterSystemState.getPlayer().getComponent(ModelComponent.class);
                LiveMonsterService.updateLiveMonster(delta, playerModel, monsterEntity, monsterSystemState);
            } else {
                DeadMonsterService.update(delta, monsterEntity, monsterSystemState);
            }
        }
    }
}
