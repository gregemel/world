package com.deeep.spaceglad.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.emitters.RegularEmitter;
import com.badlogic.gdx.math.Quaternion;
import com.deeep.spaceglad.databags.CharacterComponent;
import com.deeep.spaceglad.databags.MonsterComponent;
import com.deeep.spaceglad.databags.ModelComponent;
import com.deeep.spaceglad.databags.MonsterSystemState;
import com.deeep.spaceglad.databags.ParticleComponent;
import com.deeep.spaceglad.databags.PlayerComponent;
import com.deeep.spaceglad.databags.StatusComponent;
import com.deeep.spaceglad.services.MonsterFactory;

public class MonsterSystem extends EntitySystem implements EntityListener {

    private MonsterSystemState monsterSystemState;

    public void setMonsterSystemState(MonsterSystemState monsterSystemState) {
        this.monsterSystemState = monsterSystemState;
    }

    @Override
    public void addedToEngine(Engine e) {
        monsterSystemState.setMonsters(e.getEntitiesFor(
                Family.all(MonsterComponent.class, CharacterComponent.class, StatusComponent.class).get()));
        e.addEntityListener(Family.one(PlayerComponent.class).get(), this);
        monsterSystemState.setEntityEngine(e);
    }

    public void update(float delta) {
        if (monsterSystemState.getMonsters().size() < 1) {
            spawnMonster(getRandomSpawnIndex());
        }

        for (int i = 0; i < monsterSystemState.getMonsters().size(); i++) {

            Entity entity = monsterSystemState.getMonsters().get(i);

            ModelComponent modelComponent = entity.getComponent(ModelComponent.class);
            ModelComponent playerModel = monsterSystemState.getPlayer().getComponent(ModelComponent.class);

            StatusComponent statusComponent = entity.getComponent(StatusComponent.class);

            if (!statusComponent.isAlive()) {
                monsterSystemState.getModelService().update(modelComponent, delta);
            }

            ParticleComponent particleComponent = entity.getComponent(ParticleComponent.class);

            if (!statusComponent.isAlive() && !particleComponent.isUsed()) {

                particleComponent.setUsed(true);

                ParticleEffect effect = particleComponent.getOriginalEffect().copy();
                ((RegularEmitter) effect.getControllers().first().emitter).setEmissionMode(RegularEmitter.EmissionMode.EnabledUntilCycleEnd);
                effect.setTransform(modelComponent.getInstance().transform);
                effect.scale(3.25f, 1, 1.5f);
                effect.init();
                effect.start();
                monsterSystemState.getGameWorld().getRenderSystem().getRenderSystemState().getParticleSystem().add(effect);
            }

            if (!monsterSystemState.getSm().get(entity).alive) {
                return;
            }

            playerModel.getInstance().transform.getTranslation(monsterSystemState.getPlayerPosition());
            modelComponent.getInstance().transform.getTranslation(monsterSystemState.getEnemyPosition());

            float dX = monsterSystemState.getPlayerPosition().x - monsterSystemState.getEnemyPosition().x;
            float dZ = monsterSystemState.getPlayerPosition().z - monsterSystemState.getEnemyPosition().z;

            float theta = (float) (Math.atan2(dX, dZ));

            //Calculate the transforms
            Quaternion rot = monsterSystemState.getQuaternion().setFromAxis(0, 1, 0, (float) Math.toDegrees(theta) + 90);

            monsterSystemState.getCm().get(entity).getCharacterDirection().set(-1, 0, 0).rot(modelComponent.getInstance().transform);
            monsterSystemState.getCm().get(entity).getWalkDirection().set(0, 0, 0);
            monsterSystemState.getCm().get(entity).getWalkDirection().add(monsterSystemState.getCm().get(entity).getCharacterDirection());
            monsterSystemState.getCm().get(entity).getWalkDirection().scl(10f * delta);   //TODO make this change on difficulty
            monsterSystemState.getCm().get(entity).getCharacterController().setWalkDirection(monsterSystemState.getCm().get(entity).getWalkDirection());

            monsterSystemState.getGhost().set(0, 0, 0, 0);
            monsterSystemState.getTranslation().set(0, 0, 0);
            monsterSystemState.getCm().get(entity).getGhostObject().getWorldTransform(monsterSystemState.getGhost());
            monsterSystemState.getGhost().getTranslation(monsterSystemState.getTranslation());

            modelComponent.getInstance().transform.set(
                    monsterSystemState.getTranslation().x,
                    monsterSystemState.getTranslation().y,
                    monsterSystemState.getTranslation().z,
                    rot.x, rot.y, rot.z, rot.w);
        }
    }

    private void spawnMonster(int randomSpawnIndex) {

        monsterSystemState.getEntityEngine().addEntity(MonsterFactory.create(
                "monster",
                monsterSystemState.getGameWorld(),
                monsterSystemState.getxSpawns()[randomSpawnIndex],
                33,
                monsterSystemState.getzSpawns()[randomSpawnIndex]));
    }

    @Override
    public void entityAdded(Entity entity) {
        monsterSystemState.setPlayer(entity);
    }

    @Override
    public void entityRemoved(Entity entity) {
    }

    private int getRandomSpawnIndex() {
        return monsterSystemState.getRandom().nextInt(monsterSystemState.getxSpawns().length);
    }
}
