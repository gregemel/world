package com.emelwerx.world.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.CharacterComponent;
import com.emelwerx.world.databags.MonsterComponent;
import com.emelwerx.world.databags.MonsterSystemState;
import com.emelwerx.world.databags.PlayerComponent;
import com.emelwerx.world.services.MonsterSpawner;
import com.emelwerx.world.services.MonsterUpdater;

import static java.lang.String.format;


public class MonsterSystem extends EntitySystem implements EntityListener {

    private MonsterSystemState monsterSystemState;

    public MonsterSystem(MonsterSystemState monsterSystemState) {
        this.monsterSystemState = monsterSystemState;
    }

    @Override
    public void addedToEngine(Engine engine) {
        Gdx.app.log("MonsterSystem", "adding to engine.");
        monsterSystemState.setEntityEngine(engine);

        Family monsterFamily = Family.all(MonsterComponent.class, CharacterComponent.class).get();
        ImmutableArray<Entity> monsters = engine.getEntitiesFor(monsterFamily);
        monsterSystemState.setMonsters(monsters);

        Family player = Family.one(PlayerComponent.class).get();
        engine.addEntityListener(player, this);
    }

    public void update(float delta) {
        MonsterSpawner.update(monsterSystemState);
        MonsterUpdater.updateAll(delta, monsterSystemState);
    }

    @Override
    public void entityAdded(Entity entity) {
        Gdx.app.log("MonsterSystem", format("entity added: %s", entity.toString()));
        monsterSystemState.setPlayer(entity);
    }

    @Override
    public void entityRemoved(Entity entity) {
        Gdx.app.log("MonsterSystem", format("entity removed: %s", entity.toString()));
    }
}
