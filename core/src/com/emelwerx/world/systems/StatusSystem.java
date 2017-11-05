package com.emelwerx.world.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.StatusComponent;
import com.emelwerx.world.databags.StatusSystemState;

import static java.lang.String.format;

public class StatusSystem extends EntitySystem {

    private StatusSystemState statusSystemState;

    public StatusSystemState getStatusSystemState() {
        return statusSystemState;
    }

    public void setStatusSystemState(StatusSystemState statusSystemState) {
        this.statusSystemState = statusSystemState;
    }

    @Override
    public void addedToEngine(Engine engine) {
        Gdx.app.log("StatusSystem", "addedToEngine");
        statusSystemState.setEntities(engine.getEntitiesFor(Family.all(StatusComponent.class).get()));
    }

    @Override
    public void update(float delta) {
        for(Entity entity: statusSystemState.getEntities()) {
            StatusComponent statusComponent = entity.getComponent(StatusComponent.class);
            statusSystemState.getStatusService().update(statusComponent, delta);

            if (statusComponent.getAliveStateTime() >= 3.4f) {
                Gdx.app.log("StatusSystem", format("times up for %s", entity.toString()));
                statusSystemState.getWorldService().remove(statusSystemState.getGameWorld(), entity);
            }
        }
    }
}
