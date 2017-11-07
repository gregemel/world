package com.emelwerx.world.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.emelwerx.world.databags.CharacterComponent;
import com.emelwerx.world.databags.MonsterComponent;
import com.emelwerx.world.databags.ModelComponent;
import com.emelwerx.world.databags.MonsterSystemState;
import com.emelwerx.world.databags.ParticleComponent;
import com.emelwerx.world.databags.PlayerComponent;
import com.emelwerx.world.databags.ThoughtComponent;
import com.emelwerx.world.services.MonsterFactory;
import com.emelwerx.world.services.ParticleFactory;

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

        for(Entity entity: monsterSystemState.getMonsters()) {

            ModelComponent modelComponent = entity.getComponent(ModelComponent.class);
            ThoughtComponent thoughtComponent = entity.getComponent(ThoughtComponent.class);

            boolean monsterIsAlive = thoughtComponent.isAlive();
            if (monsterIsAlive) {
                ModelComponent playerModel = monsterSystemState.getPlayer().getComponent(ModelComponent.class);
                updateLiveMonster(delta, playerModel, entity, modelComponent);
            } else {
                updateDeadMonster(delta, entity, modelComponent);
            }
        }
    }

    private void updateLiveMonster(float delta, ModelComponent playerModelComponent, Entity entity, ModelComponent monsterModelComponent) {
        rotateMonsterTowardsPlayer(delta, playerModelComponent, entity, monsterModelComponent);
    }

    private void rotateMonsterTowardsPlayer(float delta, ModelComponent playerModelComponent, Entity entity, ModelComponent monsterModelComponent) {
        Quaternion rotationToFacePlayer = getRotationToFaceTarget(delta, playerModelComponent, entity, monsterModelComponent);

        Vector3 translation = monsterSystemState.getTranslation();

        monsterModelComponent.getInstance().transform.set(
                translation.x, translation.y, translation.z,
                rotationToFacePlayer.x, rotationToFacePlayer.y, rotationToFacePlayer.z,
                rotationToFacePlayer.w);
    }

    private Quaternion getRotationToFaceTarget(float delta,
                                               ModelComponent target,
                                               Entity entity,
                                               ModelComponent monsterModelComponent) {

        Vector3 targetPosition = monsterSystemState.getPlayerPosition();
        Vector3 monsterPosition = monsterSystemState.getMonsterPosition();

        target.getInstance().transform.getTranslation(targetPosition);
        monsterModelComponent.getInstance().transform.getTranslation(monsterPosition);

        float dX = targetPosition.x - monsterPosition.x;
        float dZ = targetPosition.z - monsterPosition.z;
        float theta = (float) (Math.atan2(dX, dZ));

        Quaternion rot = monsterSystemState.getQuaternion().setFromAxis(0, 1, 0, (float) Math.toDegrees(theta) + 90);

        CharacterComponent characterComponent = monsterSystemState.getCm().get(entity);
        characterComponent.getCharacterDirection().set(-1, 0, 0).rot(monsterModelComponent.getInstance().transform);

        Vector3 walkDirection = getWalkDirection(delta, characterComponent);
        characterComponent.getCharacterController().setWalkDirection(walkDirection);

        Matrix4 ghostMatrix = monsterSystemState.getGhostMatrix();
        ghostMatrix.set(0, 0, 0, 0);
        Vector3 translation = monsterSystemState.getTranslation();
        translation.set(0, 0, 0);
        characterComponent.getGhostObject().getWorldTransform(ghostMatrix);
        ghostMatrix.getTranslation(translation);

        return rot;
    }

    private Vector3 getWalkDirection(float delta, CharacterComponent characterComponent) {
        Vector3 walkDirection = characterComponent.getWalkDirection();
        walkDirection.set(0, 0, 0);
        walkDirection.add(characterComponent.getCharacterDirection());
        walkDirection.scl(10f * delta);   //TODO make this change on difficulty
        return walkDirection;
    }

    private void updateDeadMonster(float delta, Entity entity, ModelComponent modelComponent) {
        updateOpacity(delta, modelComponent);

        updateParticles(entity, modelComponent);
    }

    private void updateParticles(Entity entity, ModelComponent modelComponent) {
        ParticleComponent particleComponent = entity.getComponent(ParticleComponent.class);

        boolean needToStartParticle = !particleComponent.isUsed();
        if (needToStartParticle) {
            particleComponent.setUsed(true);
            ParticleEffect particleEffect = ParticleFactory.createParticleEffect(modelComponent, particleComponent);
            ParticleSystem particleSystem = monsterSystemState.getGameWorld().getRenderSystem().getRenderSystemState().getParticleSystem();
            particleSystem.add(particleEffect);
        }
    }

    private void updateOpacity(float delta, ModelComponent modelComponent) {
        BlendingAttribute blendingAttribute = modelComponent.getBlendingAttribute();
        if (blendingAttribute != null) {
            blendingAttribute.opacity = blendingAttribute.opacity - delta / 3;
        }

    }

    private void spawnAnyNewMonsters() {
        if (monsterSystemState.getMonsters().size() < 1) {
            Gdx.app.log("MonsterSystem", "spawning monster...");
            spawnMonster(getRandomSpawnIndex());
        }
    }



    private void spawnMonster(int randomSpawnIndex) {
        float x = monsterSystemState.getxSpawns()[randomSpawnIndex];
        float y = 33;
        float z = monsterSystemState.getzSpawns()[randomSpawnIndex];
        Entity monster = MonsterFactory.create(
                "monster",
                monsterSystemState.getGameWorld(),
                x,y,z);

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

    private int getRandomSpawnIndex() {
        return monsterSystemState.getRandom().nextInt(monsterSystemState.getxSpawns().length);
    }
}
