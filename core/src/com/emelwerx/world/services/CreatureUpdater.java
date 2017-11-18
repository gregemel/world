package com.emelwerx.world.services;

import com.badlogic.ashley.core.Entity;
import com.emelwerx.world.databags.CreatureComponent;
import com.emelwerx.world.databags.ModelComponent;
import com.emelwerx.world.databags.CreatureSystemState;


public class CreatureUpdater {

    public static void updateAll(float delta, CreatureSystemState creatureSystemState) {
        for(Entity creatureEntity: creatureSystemState.getCreatures()) {
            CreatureComponent creatureComponent = creatureEntity.getComponent(CreatureComponent.class);
            CreatureComponent.CREATURE_STATE state = creatureComponent.getCreatureState();

            switch (state) {
                case IDLE:
                    creatureComponent.setCreatureState(CreatureComponent.CREATURE_STATE.HUNTING);
                    break;
                case HUNTING:
                    ModelComponent playerModel = creatureSystemState.getPlayer().getComponent(ModelComponent.class);
                    CreatureAttackUpdater.update(delta, playerModel, creatureEntity, creatureSystemState);
                    break;
                case DYING:
                    CreatureDyingUpdater.update(delta, creatureEntity, creatureSystemState);
                    break;
                default:
                    break;
            }
        }
    }
}
