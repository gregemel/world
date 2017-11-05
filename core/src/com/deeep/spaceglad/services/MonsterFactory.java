package com.deeep.spaceglad.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy;
import com.badlogic.gdx.physics.bullet.collision.btCapsuleShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btPairCachingGhostObject;
import com.badlogic.gdx.physics.bullet.dynamics.btKinematicCharacterController;
import com.deeep.spaceglad.databags.AnimationComponent;
import com.deeep.spaceglad.databags.CharacterComponent;
import com.deeep.spaceglad.databags.World;
import com.deeep.spaceglad.databags.ModelComponent;
import com.deeep.spaceglad.databags.MonsterAnimations;
import com.deeep.spaceglad.databags.MonsterComponent;
import com.deeep.spaceglad.databags.ParticleComponent;
import com.deeep.spaceglad.databags.StatusComponent;
import com.deeep.spaceglad.systems.PhysicsSystem;

import java.util.Locale;

public class MonsterFactory {

    private static Model enemyModel;
    private static ModelComponent enemyModelComponent;
    private static final float modelScalar = 0.0025f;

    public static Entity create(String name, World gameWorld, float x, float y, float z) {
        Gdx.app.log("MonsterFactory", String.format(Locale.US,
                "creating monster %s, %s, %.2f, %.2f, %.2f", name, gameWorld.toString(), x, y, z));

        Entity entity = new Entity();

        ModelService modelService = new ModelService();

        enemyModel = getMonsterModel(name, modelService);

        enemyModelComponent = getMonsterModelComponent(x, y, z, modelService);
        entity.add(enemyModelComponent);

        CharacterComponent characterComponent = getCharacterComponent(entity);
        entity.add(characterComponent);

        PhysicsSystem physicsSystem = gameWorld.getPhysicsSystem();
        setPhysics(physicsSystem, entity);

        MonsterComponent monsterComponent = getMonsterComponent();
        entity.add(monsterComponent);

        AnimationComponent animationComponent = getAnimationComponent();
        entity.add(animationComponent);

        StatusComponent statusComponent = getStatusComponent(animationComponent);
        entity.add(statusComponent);

        ParticleComponent particleComponent = getParticleComponent(gameWorld);
        entity.add(particleComponent);

        return entity;
    }

    private static Model getMonsterModel(String name, ModelService modelService) {
        Gdx.app.log("MonsterFactory", String.format("getMonsterModel %s", name));
        if (enemyModel == null) {
            enemyModel = modelService.loadModel(name, modelScalar);
        }

        return enemyModel;
    }

    private static ModelComponent getMonsterModelComponent(float x, float y, float z, ModelService modelService) {
        Gdx.app.log("MonsterFactory", "getMonsterModelComponent");
        ModelComponent enemyModelComponent = modelService.create(enemyModel, x, y, z);

        Material material = enemyModelComponent.getInstance().materials.get(0);
        BlendingAttribute blendingAttribute;
        material.set(blendingAttribute = new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
        enemyModelComponent.setBlendingAttribute(blendingAttribute);

        enemyModelComponent.getInstance().transform.set(enemyModelComponent.getMatrix4().setTranslation(x, y, z));
        return enemyModelComponent;
    }

    private static MonsterComponent getMonsterComponent() {
        Gdx.app.log("MonsterFactory", "getMonsterModelComponent");
        return new MonsterComponent(MonsterComponent.STATE.HUNTING);
    }

    private static ParticleComponent getParticleComponent(World gameWorld) {
        Gdx.app.log("MonsterFactory", "getParticleComponent");
        ParticleFactory particleFactory = new ParticleFactory();
        return particleFactory.create("dieparticle", gameWorld.getRenderSystem().getRenderSystemState().getParticleSystem());
    }

    private static StatusComponent getStatusComponent(AnimationComponent animationComponent) {
        Gdx.app.log("MonsterFactory", "getStatusComponent");
        StatusService statusService = new StatusService();
        return statusService.create(animationComponent);
    }

    private static AnimationComponent getAnimationComponent() {
        Gdx.app.log("MonsterFactory", "getAnimationComponent");
        AnimationService animationService = new AnimationService();
        AnimationComponent animationComponent = animationService.create(enemyModelComponent.getInstance());
        animationService.animate(
                animationComponent,
                MonsterAnimations.getId(),
                MonsterAnimations.getOffsetRun1(),
                MonsterAnimations.getDurationRun1(),
                -1, 1);
        return animationComponent;
    }

    private static CharacterComponent getCharacterComponent(Entity entity) {
        Gdx.app.log("MonsterFactory", "getCharacterComponent");
        CharacterComponent characterComponent = new CharacterComponent();
        characterComponent.setGhostObject(
                new btPairCachingGhostObject());

        characterComponent.getGhostObject().setWorldTransform(enemyModelComponent.getInstance().transform);
        characterComponent.setGhostShape(
                new btCapsuleShape(2f, 2f));

        characterComponent.getGhostObject().setCollisionShape(characterComponent.getGhostShape());
        characterComponent.getGhostObject().setCollisionFlags(btCollisionObject.CollisionFlags.CF_CHARACTER_OBJECT);

        characterComponent.setCharacterController(
                new btKinematicCharacterController(
                    characterComponent.getGhostObject(),
                        characterComponent.getGhostShape(),
                        .35f));

        characterComponent.getGhostObject().userData = entity;
        return characterComponent;
    }

    private static void setPhysics(PhysicsSystem physicsSystem, Entity entity) {
        Gdx.app.log("MonsterFactory", "setPhysics");
        physicsSystem.getPhysicsSystemState().getCollisionWorld().addCollisionObject(entity.getComponent(CharacterComponent.class).getGhostObject(),
                (short) btBroadphaseProxy.CollisionFilterGroups.CharacterFilter,
                (short) (btBroadphaseProxy.CollisionFilterGroups.AllFilter));
        physicsSystem.getPhysicsSystemState().getCollisionWorld().addAction(entity.getComponent(CharacterComponent.class).getCharacterController());
    }
}