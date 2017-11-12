package com.emelwerx.world.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.ModelComponent;
import com.emelwerx.world.databags.MonsterSystemState;
import com.emelwerx.world.databags.ThoughtComponent;

import static java.lang.String.format;

public class MonsterUpdater {
    private static final float deathDuration = 3.4f;

    public static void updateAll(float delta, MonsterSystemState monsterSystemState) {
        for(Entity monsterEntity: monsterSystemState.getMonsters()) {

            ThoughtComponent thoughtComponent = monsterEntity.getComponent(ThoughtComponent.class);

            if (!thoughtComponent.isAlive()) {
                float timeSinceDeath = thoughtComponent.getTimeSinceDeath() + delta;
                if (timeSinceDeath >= deathDuration) {
                    Gdx.app.log("ThinkingSystem", format("times up for %s", monsterEntity.toString()));
                    monsterSystemState.getGameWorld().getEntityEngine().removeEntity(monsterEntity);
                    monsterSystemState.getGameWorld().getPhysicsSystem().removeBody(monsterEntity);
                } else {
                    thoughtComponent.setTimeSinceDeath(timeSinceDeath);
                }
            }

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
