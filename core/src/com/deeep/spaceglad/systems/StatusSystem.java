package com.deeep.spaceglad.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.deeep.spaceglad.databags.StatusComponent;
import com.deeep.spaceglad.databags.StatusSystemState;

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
        ImmutableArray<Entity> entities = statusSystemState.getEntities();
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            statusSystemState.getStatusService().update(entity.getComponent(StatusComponent.class), delta);
            if (entity.getComponent(StatusComponent.class).aliveStateTime >= 3.4f) {
                statusSystemState.getWorldService().remove(statusSystemState.getGameWorld(), entity);
            }
        }
    }
}
