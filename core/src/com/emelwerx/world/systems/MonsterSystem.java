package com.emelwerx.world.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.emitters.RegularEmitter;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.emelwerx.world.databags.CharacterComponent;
import com.emelwerx.world.databags.MonsterComponent;
import com.emelwerx.world.databags.ModelComponent;
import com.emelwerx.world.databags.MonsterSystemState;
import com.emelwerx.world.databags.ParticleComponent;
import com.emelwerx.world.databags.PlayerComponent;
import com.emelwerx.world.databags.StatusComponent;
import com.emelwerx.world.services.MonsterFactory;

public class MonsterSystem extends EntitySystem implements EntityListener {

    private MonsterSystemState monsterSystemState;

    public void setMonsterSystemState(MonsterSystemState monsterSystemState) {
        this.monsterSystemState = monsterSystemState;
    }

    @Override
    public void addedToEngine(Engine e) {
        Gdx.app.log("MonsterSystem", "adding to engine.");
        monsterSystemState.setMonsters(e.getEntitiesFor(
                Family.all(MonsterComponent.class, CharacterComponent.class, StatusComponent.class).get()));
        e.addEntityListener(Family.one(PlayerComponent.class).get(), this);
        monsterSystemState.setEntityEngine(e);
    }

    public void update(float delta) {
        spawnAnyNewMonsters();
        updateAllMonsters(delta);
    }

    private void updateAllMonsters(float delta) {
        ModelComponent playerModel = monsterSystemState.getPlayer().getComponent(ModelComponent.class);

        for(Entity entity: monsterSystemState.getMonsters()) {

            ModelComponent modelComponent = entity.getComponent(ModelComponent.class);
            StatusComponent statusComponent = entity.getComponent(StatusComponent.class);

            boolean monsterIsDead = !statusComponent.isAlive();
            if (monsterIsDead) {
                updateDeadMonster(delta, entity, modelComponent);
            } else {
                updateLiveMonster(delta, playerModel, entity, modelComponent);
            }
        }
    }

    private void updateLiveMonster(float delta, ModelComponent playerModelComponent, Entity entity, ModelComponent monsterModelComponent) {
        Vector3 playerPosition = monsterSystemState.getPlayerPosition();
        Vector3 monsterPosition = monsterSystemState.getMonsterPosition();

        playerModelComponent.getInstance().transform.getTranslation(playerPosition);
        monsterModelComponent.getInstance().transform.getTranslation(monsterPosition);

        float dX = playerPosition.x - monsterPosition.x;
        float dZ = playerPosition.z - monsterPosition.z;
        float theta = (float) (Math.atan2(dX, dZ));

        Quaternion rotationToFacePlayer = getQuaternion(delta, entity, monsterModelComponent, theta);

        Vector3 translation = monsterSystemState.getTranslation();

        monsterModelComponent.getInstance().transform.set(
                translation.x, translation.y, translation.z,
                rotationToFacePlayer.x, rotationToFacePlayer.y, rotationToFacePlayer.z,
                rotationToFacePlayer.w);
    }

    private void updateDeadMonster(float delta, Entity entity, ModelComponent modelComponent) {
        monsterSystemState.getModelService().updateOpacity(modelComponent, delta);

        ParticleComponent particleComponent = entity.getComponent(ParticleComponent.class);

        boolean needToStartParticle = !particleComponent.isUsed();
        if (needToStartParticle) {
            particleComponent.setUsed(true);
            ParticleEffect particleEffect = getParticleEffect(modelComponent, particleComponent);
            ParticleSystem particleSystem = monsterSystemState.getGameWorld().getRenderSystem().getRenderSystemState().getParticleSystem();
            particleSystem.add(particleEffect);
        }
    }

    private ParticleEffect getParticleEffect(ModelComponent modelComponent, ParticleComponent particleComponent) {
        ParticleEffect effect = particleComponent.getOriginalEffect().copy();
        ((RegularEmitter) effect.getControllers().first().emitter).setEmissionMode(RegularEmitter.EmissionMode.EnabledUntilCycleEnd);
        effect.setTransform(modelComponent.getInstance().transform);
        effect.scale(3.25f, 1, 1.5f);
        effect.init();
        effect.start();
        return effect;
    }

    private void spawnAnyNewMonsters() {
        if (monsterSystemState.getMonsters().size() < 1) {
            Gdx.app.log("MonsterSystem", "spawning monster...");
            spawnMonster(getRandomSpawnIndex());
        }
    }

    private Quaternion getQuaternion(float delta, Entity entity, ModelComponent modelComponent, float theta) {
        Quaternion rot = monsterSystemState.getQuaternion().setFromAxis(0, 1, 0, (float) Math.toDegrees(theta) + 90);

        CharacterComponent characterComponent = monsterSystemState.getCm().get(entity);
        characterComponent.getCharacterDirection().set(-1, 0, 0).rot(modelComponent.getInstance().transform);

        Vector3 walkDirection = getWalkDirection(delta, characterComponent);
        characterComponent.getCharacterController().setWalkDirection(walkDirection);

        Matrix4 ghostMatrix = monsterSystemState.getGhost();
        ghostMatrix.set(0, 0, 0, 0);
        monsterSystemState.getTranslation().set(0, 0, 0);
        characterComponent.getGhostObject().getWorldTransform(ghostMatrix);
        ghostMatrix.getTranslation(monsterSystemState.getTranslation());

        return rot;
    }

    private Vector3 getWalkDirection(float delta, CharacterComponent characterComponent) {
        Vector3 walkDirection = characterComponent.getWalkDirection();
        walkDirection.set(0, 0, 0);
        walkDirection.add(characterComponent.getCharacterDirection());
        walkDirection.scl(10f * delta);   //TODO make this change on difficulty
        return walkDirection;
    }

    private void spawnMonster(int randomSpawnIndex) {
        float x = monsterSystemState.getxSpawns()[randomSpawnIndex];
        float y = 33;
        float z = monsterSystemState.getzSpawns()[randomSpawnIndex];
        Entity monster = MonsterFactory.create(
                "monster",
                monsterSystemState.getGameWorld(),
                x,y,z);

        monsterSystemState.getEntityEngine().addEntity(monster);
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
