package com.emelwerx.world.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.ThoughtComponent;
import com.emelwerx.world.databags.StatusSystemState;

import static java.lang.String.format;


//the role of this class appears to be managing death of monster -ge [2017-11-06]
//TODO: rename ThinkingSystem
// works with StatusComponents, manages state/behavior of individual monsters
//
public class ThinkingSystem extends EntitySystem {

    private StatusSystemState statusSystemState;

    public StatusSystemState getStatusSystemState() {
        return statusSystemState;
    }

    public void setStatusSystemState(StatusSystemState statusSystemState) {
        this.statusSystemState = statusSystemState;
    }

    @Override
    public void addedToEngine(Engine engine) {
        Gdx.app.log("ThinkingSystem", "addedToEngine");
        statusSystemState.setEntities(engine.getEntitiesFor(Family.all(ThoughtComponent.class).get()));
    }

    @Override
    public void update(float delta) {
        for(Entity entity: statusSystemState.getEntities()) {
            ThoughtComponent thoughtComponent = entity.getComponent(ThoughtComponent.class);
            statusSystemState.getThinkingService().update(thoughtComponent, delta);

            if (thoughtComponent.getAliveStateTime() >= 3.4f) {
                Gdx.app.log("ThinkingSystem", format("times up for %s", entity.toString()));
                statusSystemState.getWorldService().remove(statusSystemState.getGameWorld(), entity);
            }
        }
    }
}
