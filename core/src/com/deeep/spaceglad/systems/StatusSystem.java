package com.deeep.spaceglad.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.deeep.spaceglad.databags.GameWorld;
import com.deeep.spaceglad.components.StatusComponent;
import com.deeep.spaceglad.services.WorldLoader;

public class StatusSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private GameWorld gameWorld;
    private WorldLoader worldService;

    public StatusSystem(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(StatusComponent.class).get());
    }

    @Override
    public void update(float delta) {
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            entity.getComponent(StatusComponent.class).update(delta);
            if (entity.getComponent(StatusComponent.class).aliveStateTime >= 3.4f) {
                if(worldService == null) {
                    worldService = new WorldLoader();
                }
                worldService.remove(gameWorld, entity);
            }
        }
    }
}
