package com.emelwerx.world.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.CharacterComponent;
import com.emelwerx.world.databags.ModelComponent;
import com.emelwerx.world.databags.MonsterComponent;
import com.emelwerx.world.databags.MonsterSystemState;
import com.emelwerx.world.databags.PlayerComponent;
import com.emelwerx.world.databags.ThoughtComponent;
import com.emelwerx.world.services.DeadMonsterService;
import com.emelwerx.world.services.LiveMonsterService;
import com.emelwerx.world.services.MonsterFactory;

import static java.lang.String.format;


//monster system manages the worlds monsters as a whole... -ge[2017-11-06]
public class MonsterSystem extends EntitySystem implements EntityListener {

    private MonsterSystemState monsterSystemState;

    public MonsterSystem(MonsterSystemState monsterSystemState) {
        this.monsterSystemState = monsterSystemState;
    }

    @Override
    public void addedToEngine(Engine engine) {
        Gdx.app.log("MonsterSystem", "adding to engine.");
        monsterSystemState.setEntityEngine(engine);
        Family monsterFamily = Family.all(MonsterComponent.class, CharacterComponent.class, ThoughtComponent.class).get();
        ImmutableArray<Entity> monsters = engine.getEntitiesFor(monsterFamily);
        monsterSystemState.setMonsters(monsters);
        Family player = Family.one(PlayerComponent.class).get();
        engine.addEntityListener(player, this);
    }

    public void update(float delta) {
        spawnAnyNewMonsters();
        updateAllMonsters(delta);
    }

    private void updateAllMonsters(float delta) {

        for(Entity monsterEntity: monsterSystemState.getMonsters()) {

            ThoughtComponent thoughtComponent = monsterEntity.getComponent(ThoughtComponent.class);

            boolean monsterIsAlive = thoughtComponent.isAlive();
            if (monsterIsAlive) {
                ModelComponent playerModel = monsterSystemState.getPlayer().getComponent(ModelComponent.class);
                LiveMonsterService.updateLiveMonster(delta, playerModel, monsterEntity, monsterSystemState);
            } else {
                DeadMonsterService.update(delta, monsterEntity, monsterSystemState);
            }
        }
    }

    private void spawnAnyNewMonsters() {
        int numberOfMonsters = monsterSystemState.getMonsters().size();
        if (numberOfMonsters < 1) {
            Gdx.app.log("MonsterSystem", "spawning monster...");
            spawnMonster();
        }
    }

    private void spawnMonster() {
        Entity monster = MonsterFactory.create(
                "monster",
                monsterSystemState.getGameWorld());

        Gdx.app.log("MonsterSystem", format("monster spawned: %s", monster.toString()));

        monsterSystemState.getEntityEngine().addEntity(monster);
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
