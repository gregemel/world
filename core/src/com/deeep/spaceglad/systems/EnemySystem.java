package com.deeep.spaceglad.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.emitters.RegularEmitter;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.deeep.spaceglad.databags.CharacterComponent;
import com.deeep.spaceglad.databags.ModelComponent;
import com.deeep.spaceglad.databags.ParticleComponent;
import com.deeep.spaceglad.databags.EnemyComponent;
import com.deeep.spaceglad.databags.GameWorld;
import com.deeep.spaceglad.databags.PlayerComponent;
import com.deeep.spaceglad.databags.StatusComponent;
import com.deeep.spaceglad.services.EntityFactory;
import com.deeep.spaceglad.services.ModelService;

import java.util.Random;

public class EnemySystem extends EntitySystem implements EntityListener {
    private ImmutableArray<Entity> entities;
    private Entity player;
    private Quaternion quat = new Quaternion();
    private Engine engine;
    private GameWorld gameWorld;
    private Vector3 playerPosition = new Vector3();
    private Vector3 enemyPosition = new Vector3();
    private Matrix4 ghost = new Matrix4();
    private Vector3 translation = new Vector3();
    private Random random = new Random();
    private ModelService modelService = new ModelService();

    private float[] xSpawns = {12, -12, 112, -112};
    private float[] zSpawns = {-112, 112, -12, 12};

    private ComponentMapper<CharacterComponent> cm = ComponentMapper.getFor(CharacterComponent.class);
    private ComponentMapper<StatusComponent> sm = ComponentMapper.getFor(StatusComponent.class);

    public EnemySystem(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    @Override
    public void addedToEngine(Engine e) {
        entities = e.getEntitiesFor(Family.all(EnemyComponent.class, CharacterComponent.class, StatusComponent.class).get());
        e.addEntityListener(Family.one(PlayerComponent.class).get(), this);
        this.engine = e;
    }

    public void update(float delta) {
        if (entities.size() < 1) spawnEnemy(getRandomSpawnIndex());

        for (int i = 0; i < entities.size(); i++) {

            Entity entity = entities.get(i);

            ModelComponent modelComponent = entity.getComponent(ModelComponent.class);
            ModelComponent playerModel = player.getComponent(ModelComponent.class);

            StatusComponent statusComponent = entity.getComponent(StatusComponent.class);

            if (!statusComponent.isAlive()) {
                modelService.update(modelComponent, delta);
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
                RenderSystem.particleSystem.add(effect);
            }

            if (!sm.get(entity).alive) {
                return;
            }

            playerModel.getInstance().transform.getTranslation(playerPosition);
            modelComponent.getInstance().transform.getTranslation(enemyPosition);

            float dX = playerPosition.x - enemyPosition.x;
            float dZ = playerPosition.z - enemyPosition.z;

            float theta = (float) (Math.atan2(dX, dZ));

            //Calculate the transforms
            Quaternion rot = quat.setFromAxis(0, 1, 0, (float) Math.toDegrees(theta) + 90);

            cm.get(entity).getCharacterDirection().set(-1, 0, 0).rot(modelComponent.getInstance().transform);
            cm.get(entity).getWalkDirection().set(0, 0, 0);
            cm.get(entity).getWalkDirection().add(cm.get(entity).getCharacterDirection());
            cm.get(entity).getWalkDirection().scl(10f * delta);   //TODO make this change on difficulty
            cm.get(entity).getCharacterController().setWalkDirection(cm.get(entity).getWalkDirection());

            ghost.set(0, 0, 0, 0);
            translation.set(0, 0, 0);
            cm.get(entity).getGhostObject().getWorldTransform(ghost);
            ghost.getTranslation(translation);

            modelComponent.getInstance().transform.set(translation.x, translation.y, translation.z, rot.x, rot.y, rot.z, rot.w);
        }
    }

    private void spawnEnemy(int randomSpawnIndex) {
        engine.addEntity(EntityFactory.create("monster", gameWorld, xSpawns[randomSpawnIndex], 33, zSpawns[randomSpawnIndex]));
    }

    @Override
    public void entityAdded(Entity entity) {
        player = entity;
    }

    @Override
    public void entityRemoved(Entity entity) {
    }

    private int getRandomSpawnIndex() {
        return random.nextInt(xSpawns.length);
    }
}
