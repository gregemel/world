package com.emelwerx.world.services;


import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.math.Vector3;
import com.emelwerx.world.Settings;
import com.emelwerx.world.databags.AnimationComponent;
import com.emelwerx.world.databags.ModelComponent;
import com.emelwerx.world.databags.MonsterComponent;
import com.emelwerx.world.databags.PlayerComponent;
import com.emelwerx.world.databags.RenderSystemState;

public class ShadowDrawer {

    public static void draw(RenderSystemState renderSystemState, float delta) {
        DirectionalShadowLight shadowLight = renderSystemState.getShadowLight();
        shadowLight.begin(Vector3.Zero, renderSystemState.getPerspectiveCamera().direction);

        ModelBatch modelBatch = renderSystemState.getBatch();
        modelBatch.begin(shadowLight.getCamera());

        ImmutableArray<Entity> entities = renderSystemState.getEntities();

        for(Entity entity : entities) {
            boolean playerOrMonster = entity.getComponent(PlayerComponent.class) != null
                    || entity.getComponent(MonsterComponent.class) != null;
            if (playerOrMonster) {
                ModelComponent mod = entity.getComponent(ModelComponent.class);
                if (isVisible(renderSystemState, mod.getInstance())) {
                    modelBatch.render(mod.getInstance());
                }
            }

            AnimationComponent animationComponent = entity.getComponent(AnimationComponent.class);
            boolean hasAnimation = animationComponent != null & !Settings.Paused;
            if (hasAnimation) {
                renderSystemState.getAnimationService().update(animationComponent, delta);
            }
        }

        modelBatch.end();
        shadowLight.end();
    }

    private static boolean isVisible(RenderSystemState renderSystemState, final ModelInstance instance) {
        PerspectiveCamera cam = renderSystemState.getPerspectiveCamera();
        return cam.frustum.pointInFrustum(instance.transform.getTranslation(renderSystemState.getPosition()));
    }

}
