package com.emelwerx.world.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.ModelComponent;
import com.emelwerx.world.databags.MonsterComponent;
import com.emelwerx.world.databags.MonsterSystemState;

import static java.lang.String.format;

public class MonsterUpdater {
    private static final float deathDuration = 3.4f;

    public static void updateAll(float delta, MonsterSystemState monsterSystemState) {
        for(Entity monsterEntity: monsterSystemState.getMonsters()) {

            MonsterComponent monsterComponent = monsterEntity.getComponent(MonsterComponent.class);

            if (!monsterComponent.isAlive()) {
                float timeSinceDeath = monsterComponent.getTimeSinceDeath() + delta;
                if (timeSinceDeath >= deathDuration) {
                    Gdx.app.log("ThinkingSystem", format("times up for %s", monsterEntity.toString()));
                    monsterSystemState.getGameWorld().getEntityEngine().removeEntity(monsterEntity);
                    monsterSystemState.getGameWorld().getPhysicsSystem().removeBody(monsterEntity);
                } else {
                    monsterComponent.setTimeSinceDeath(timeSinceDeath);
                }
            }

            boolean monsterIsAlive = monsterComponent.isAlive();
            if (monsterIsAlive) {
                ModelComponent playerModel = monsterSystemState.getPlayer().getComponent(ModelComponent.class);
                LiveMonsterService.updateLiveMonster(delta, playerModel, monsterEntity, monsterSystemState);
            } else {
                DeadMonsterService.update(delta, monsterEntity, monsterSystemState);
            }
        }
    }
}
