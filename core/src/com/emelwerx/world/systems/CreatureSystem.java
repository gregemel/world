package com.emelwerx.world.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.components.CharacterComponent;
import com.emelwerx.world.databags.components.CreatureComponent;
import com.emelwerx.world.databags.systemstates.CreatureSystemState;
import com.emelwerx.world.databags.components.PlayerComponent;
import com.emelwerx.world.services.CreatureSpawner;
import com.emelwerx.world.services.updaters.CreatureUpdater;

import static java.lang.String.format;

public class CreatureSystem extends EntitySystem implements EntityListener {

    private CreatureSystemState creatureSystemState;

    public CreatureSystem(CreatureSystemState creatureSystemState) {
        this.creatureSystemState = creatureSystemState;
    }

    @Override   //EntitySystem
    public void addedToEngine(Engine engine) {
        Gdx.app.log("CreatureSystem", "adding to engine.");

        Family creatureFamily = Family.all(CreatureComponent.class, CharacterComponent.class).get();
        ImmutableArray<Entity> creatureEntities = engine.getEntitiesFor(creatureFamily);
        creatureSystemState.setCreatures(creatureEntities);

        Family player = Family.one(PlayerComponent.class).get();
        engine.addEntityListener(player, this);
    }

    //EntitySystem
    public void update(float delta) {
        CreatureSpawner.update(creatureSystemState);
        CreatureUpdater.updateAll(delta, creatureSystemState);
    }

    @Override   //EntityListener
    public void entityAdded(Entity entity) {
        Gdx.app.log("CreatureSystem", format("entity added: %s", entity.toString()));
        creatureSystemState.setPlayer(entity);
    }

    @Override   //EntityListener
    public void entityRemoved(Entity entity) {
        Gdx.app.log("CreatureSystem", format("entity removed: %s", entity.toString()));
    }
}
