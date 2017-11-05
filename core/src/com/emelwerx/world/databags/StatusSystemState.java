package com.emelwerx.world.databags;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.emelwerx.world.services.StatusService;
import com.emelwerx.world.services.WorldLoader;

public class StatusSystemState {
    private ImmutableArray<Entity> entities;
    private World gameWorld;
    private WorldLoader worldService;
    private StatusService statusService;

    public ImmutableArray<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ImmutableArray<Entity> entities) {
        this.entities = entities;
    }

    public World getGameWorld() {
        return gameWorld;
    }

    public void setGameWorld(World gameWorld) {
        this.gameWorld = gameWorld;
    }

    public WorldLoader getWorldService() {
        return worldService;
    }

    public void setWorldService(WorldLoader worldService) {
        this.worldService = worldService;
    }

    public StatusService getStatusService() {
        return statusService;
    }

    public void setStatusService(StatusService statusService) {
        this.statusService = statusService;
    }
}
