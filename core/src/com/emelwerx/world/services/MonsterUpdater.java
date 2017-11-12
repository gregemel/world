package com.emelwerx.world.services;

import com.badlogic.ashley.core.Entity;
import com.emelwerx.world.databags.ModelComponent;
import com.emelwerx.world.databags.MonsterComponent;
import com.emelwerx.world.databags.MonsterSystemState;


public class MonsterUpdater {

    public static void updateAll(float delta, MonsterSystemState monsterSystemState) {
        for(Entity monsterEntity: monsterSystemState.getMonsters()) {
            MonsterComponent monsterComponent = monsterEntity.getComponent(MonsterComponent.class);

            MonsterComponent.MONSTER_STATE state = monsterComponent.getMonsterState();

            switch (state) {
                case IDLE:
                    monsterComponent.setMonsterState(MonsterComponent.MONSTER_STATE.HUNTING);
                    break;
                case HUNTING:
                    ModelComponent playerModel = monsterSystemState.getPlayer().getComponent(ModelComponent.class);
                    LiveMonsterService.updateLiveMonster(delta, playerModel, monsterEntity, monsterSystemState);
                    break;
                case DYING:
                    DeadMonsterService.update(delta, monsterEntity, monsterSystemState);
                    break;
                default:
                    break;
            }
        }
    }
}
