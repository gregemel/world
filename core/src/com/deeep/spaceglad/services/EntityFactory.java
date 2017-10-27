package com.deeep.spaceglad.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.utils.TextureProvider;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.physics.bullet.dynamics.btKinematicCharacterController;
import com.badlogic.gdx.utils.JsonReader;
import com.deeep.spaceglad.databags.AnimationComponent;
import com.deeep.spaceglad.databags.CharacterComponent;
import com.deeep.spaceglad.databags.ModelComponent;
import com.deeep.spaceglad.databags.ParticleComponent;
import com.deeep.spaceglad.databags.EnemyComponent;
import com.deeep.spaceglad.databags.GameWorld;
import com.deeep.spaceglad.databags.StatusComponent;
import com.deeep.spaceglad.systems.BulletSystem;

public class EntityFactory {

    private static Model enemyModel;
    private static ModelComponent enemyModelComponent;

    public static Entity create(String name, GameWorld gameWorld, float x, float y, float z) {

        BulletSystem bulletSystem = gameWorld.getBulletSystem();
        Entity entity = new Entity();
        ModelLoader<?> modelLoader = new G3dModelLoader(new JsonReader());

        if (enemyModel == null) {

            ModelData enemyModelData = modelLoader.loadModelData(Gdx.files.internal("data/" + name + ".g3dj"));
            enemyModel = new Model(enemyModelData, new TextureProvider.FileTextureProvider());

            for (Node node : enemyModel.nodes) {
                node.scale.scl(0.0025f);
            }

            enemyModel.calculateTransforms();

            ModelService modelService = new ModelService();
            enemyModelComponent = modelService.create(enemyModel, x, y, z);

            Material material = enemyModelComponent.getInstance().materials.get(0);
            BlendingAttribute blendingAttribute;
            material.set(blendingAttribute = new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
            enemyModelComponent.setBlendingAttribute(blendingAttribute);
        }

        ((BlendingAttribute) enemyModelComponent.getInstance().materials.get(0).get(BlendingAttribute.Type)).opacity = 1;
        enemyModelComponent.getInstance().transform.set(enemyModelComponent.getMatrix4().setTranslation(x, y, z));
        entity.add(enemyModelComponent);

        CharacterComponent characterComponent = new CharacterComponent();
        characterComponent.ghostObject = new btPairCachingGhostObject();
        characterComponent.ghostObject.setWorldTransform(enemyModelComponent.getInstance().transform);
        characterComponent.ghostShape = new btCapsuleShape(2f, 2f);
        characterComponent.ghostObject.setCollisionShape(characterComponent.ghostShape);
        characterComponent.ghostObject.setCollisionFlags(btCollisionObject.CollisionFlags.CF_CHARACTER_OBJECT);
        characterComponent.characterController = new btKinematicCharacterController(characterComponent.ghostObject, characterComponent.ghostShape, .35f);
        characterComponent.ghostObject.userData = entity;
        entity.add(characterComponent);

        bulletSystem.collisionWorld.addCollisionObject(entity.getComponent(CharacterComponent.class).ghostObject,
                (short) btBroadphaseProxy.CollisionFilterGroups.CharacterFilter,
                (short) (btBroadphaseProxy.CollisionFilterGroups.AllFilter));
        bulletSystem.collisionWorld.addAction(entity.getComponent(CharacterComponent.class).characterController);

        entity.add(new EnemyComponent(EnemyComponent.STATE.HUNTING));

        AnimationService animationService = new AnimationService();
        AnimationComponent animationComponent = animationService.create(enemyModelComponent.getInstance());
        animationService.animate(
                animationComponent,
                com.deeep.spaceglad.databags.EnemyAnimations.id,
                com.deeep.spaceglad.databags.EnemyAnimations.offsetRun1,
                com.deeep.spaceglad.databags.EnemyAnimations.durationRun1,
                -1, 1);

        entity.add(animationComponent);
        StatusService statusService = new StatusService();
        StatusComponent statusComponent = statusService.create(animationComponent);
        entity.add(statusComponent);

        ParticleFactory particleFactory = new ParticleFactory();
        ParticleComponent particleComponent = particleFactory.create(gameWorld.getRenderSystem().particleSystem);
        entity.add(particleComponent);

        return entity;
    }
}