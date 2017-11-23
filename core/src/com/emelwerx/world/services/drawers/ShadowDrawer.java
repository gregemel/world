package com.emelwerx.world.services.drawers;


import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.math.Vector3;
import com.emelwerx.world.databags.components.AnimationComponent;
import com.emelwerx.world.databags.components.CreatureComponent;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.databags.components.PlayerComponent;
import com.emelwerx.world.databags.systemstates.RenderSystemState;
import com.emelwerx.world.services.Settings;

public class ShadowDrawer {

    public static void draw(RenderSystemState renderSystemState, float delta) {
        DirectionalShadowLight shadowLight = renderSystemState.getShadowLight();
        shadowLight.begin(Vector3.Zero, renderSystemState.getWorldPerspectiveCamera().direction);

        ModelBatch modelBatch = renderSystemState.getBatch();
        modelBatch.begin(shadowLight.getCamera());

        ImmutableArray<Entity> entities = renderSystemState.getEntities();

        for(Entity entity : entities) {
            render(renderSystemState, modelBatch, entity);
            animate(delta, entity);
        }

        modelBatch.end();
        shadowLight.end();
    }

    private static void render(RenderSystemState renderSystemState, ModelBatch modelBatch, Entity entity) {
        boolean isPlayerOrCreature = entity.getComponent(PlayerComponent.class) != null
                || entity.getComponent(CreatureComponent.class) != null;
        if (isPlayerOrCreature) {
            ModelComponent modelComponent = entity.getComponent(ModelComponent.class);
            if (isVisible(renderSystemState, modelComponent.getInstance())) {
                modelBatch.render(modelComponent.getInstance());
            }
        }
    }

    private static void animate(float delta, Entity entity) {
        AnimationComponent animationComponent = entity.getComponent(AnimationComponent.class);
        boolean hasAnimation = animationComponent != null & !Settings.isPaused();
        if (hasAnimation) {
            animationComponent.getAnimationController().update(delta);
        }
    }

    private static boolean isVisible(RenderSystemState renderSystemState, final ModelInstance instance) {
        PerspectiveCamera cam = renderSystemState.getWorldPerspectiveCamera();
        return cam.frustum.pointInFrustum(instance.transform.getTranslation(renderSystemState.getPosition()));
    }
}
