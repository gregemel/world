package com.emelwerx.world.services.updaters;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.emelwerx.world.databags.components.CreatureComponent;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.databags.systemstates.CreatureSystemState;

import java.util.Locale;

import static java.lang.String.format;

public class CreaturesUpdater {

    private static float lastUpdated = 5.1f;

    public static void updateAll(float delta, CreatureSystemState state) {
        boolean isLogging = false;
        if(lastUpdated>5.0f) {
            isLogging = true;
            lastUpdated = 0f;
        } else {
            lastUpdated+=delta;
        }

        for(Entity creatureEntity: state.getCreatures()) {
            CreatureComponent creatureComponent = creatureEntity.getComponent(CreatureComponent.class);
            CreatureComponent.CREATURE_STATE creatureState = creatureComponent.getCreatureState();

            switch (creatureState) {
                case IDLE:
                    creatureComponent.setCreatureState(CreatureComponent.CREATURE_STATE.HUNTING);
                    break;
                case HUNTING:
                    CreatureChaseUpdater.update(delta, creatureEntity, state);
                    break;
                case DYING:
                    CreatureDyingUpdater.update(delta, creatureEntity, state.getWorld());
                    break;
                default:
                    break;
            }

            if(isLogging) {
                ModelComponent creatureModelComponent = creatureEntity.getComponent(ModelComponent.class);
                Matrix4 matrix4 = creatureModelComponent.getInstance().transform;

                Gdx.app.log("**creature location**", format(Locale.US,"(%f, %f, %f)",
                        matrix4.getValues()[12], matrix4.getValues()[13], matrix4.getValues()[14]));

            }
        }
    }
}
