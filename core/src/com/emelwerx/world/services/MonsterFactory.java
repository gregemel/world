package com.emelwerx.world.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.emelwerx.world.databags.AnimationComponent;
import com.emelwerx.world.databags.CharacterComponent;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.databags.ModelComponent;
import com.emelwerx.world.databags.MonsterAnimations;
import com.emelwerx.world.databags.MonsterComponent;
import com.emelwerx.world.databags.ParticleComponent;
import com.emelwerx.world.databags.ThoughtComponent;
import com.emelwerx.world.systems.PhysicsSystem;

import java.util.Locale;

public class MonsterFactory {

    private static Model cachedMonsterModel;
    private static final float modelScalar = 0.0025f;

    public static Entity create(String name, World gameWorld, float x, float y, float z) {
        Gdx.app.log("MonsterFactory", String.format(Locale.US,
                "creating monster %s, %s, %.2f, %.2f, %.2f", name, gameWorld.toString(), x, y, z));

        Entity entity = new Entity();
        attachComponents(name, gameWorld, x, y, z, entity);
        return entity;
    }

    private static void attachComponents(String name, World gameWorld, float x, float y, float z, Entity entity) {
        ModelComponent modelComponent = getModelComponent(name, x, y, z);
        entity.add(modelComponent);

        CharacterComponent characterComponent = CharacterComponentFactory.create(entity, modelComponent);
        entity.add(characterComponent);

        PhysicsSystem physicsSystem = gameWorld.getPhysicsSystem();
        setPhysics(physicsSystem, entity);

        MonsterComponent monsterComponent = getMonsterComponent();
        entity.add(monsterComponent);

        AnimationComponent animationComponent = getAnimationComponent(modelComponent);
        entity.add(animationComponent);

        ThoughtComponent thoughtComponent = getStatusComponent(animationComponent);
        entity.add(thoughtComponent);

        ParticleComponent particleComponent = getParticleComponent(gameWorld);
        entity.add(particleComponent);
    }

    private static ModelComponent getModelComponent(String name, float x, float y, float z) {
        Model model = getCachedMonsterModel(name);
        ModelComponent enemyModelComponent = ModelComponentFactory.create(model, x, y, z);

        Material material = enemyModelComponent.getInstance().materials.get(0);
        BlendingAttribute blendingAttribute;
        material.set(blendingAttribute = new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
        enemyModelComponent.setBlendingAttribute(blendingAttribute);

        enemyModelComponent.getInstance().transform.set(enemyModelComponent.getMatrix4().setTranslation(x, y, z));
        return enemyModelComponent;
    }

    private static Model getCachedMonsterModel(String name) {
        if (cachedMonsterModel == null) {
            ModelLoader modelLoader = new ModelLoader();
            cachedMonsterModel = modelLoader.loadModel(name, modelScalar);
        }
        return cachedMonsterModel;
    }

    private static MonsterComponent getMonsterComponent() {
        return new MonsterComponent(MonsterComponent.STATE.HUNTING);
    }

    private static ParticleComponent getParticleComponent(World gameWorld) {
        return ParticleFactory.create("dieparticle", gameWorld.getRenderSystem().getRenderSystemState().getParticleSystem());
    }

    private static ThoughtComponent getStatusComponent(AnimationComponent animationComponent) {
        ThinkingService thinkingService = new ThinkingService();
        return thinkingService.create(animationComponent);
    }

    private static AnimationComponent getAnimationComponent(ModelComponent modelComponent) {
        AnimationService animationService = new AnimationService();
        AnimationComponent animationComponent = animationService.create(modelComponent.getInstance());
        animationService.animate(
                animationComponent,
                MonsterAnimations.getId(),
                MonsterAnimations.getOffsetRun1(),
                MonsterAnimations.getDurationRun1(),
                -1, 1);
        return animationComponent;
    }

    private static void setPhysics(PhysicsSystem physicsSystem, Entity entity) {
        btDiscreteDynamicsWorld collisionWorld = physicsSystem.getPhysicsSystemState().getCollisionWorld();
        CharacterComponent characterComponent = entity.getComponent(CharacterComponent.class);
        collisionWorld.addCollisionObject(
                characterComponent.getGhostObject(),
                (short) btBroadphaseProxy.CollisionFilterGroups.CharacterFilter,
                (short) (btBroadphaseProxy.CollisionFilterGroups.AllFilter));
        collisionWorld.addAction(
                characterComponent.getCharacterController());
    }
}