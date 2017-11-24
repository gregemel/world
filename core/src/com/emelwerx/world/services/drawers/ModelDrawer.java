package com.emelwerx.world.services.drawers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.emelwerx.world.databags.components.AnimationComponent;
import com.emelwerx.world.databags.components.ItemComponent;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.databags.systemstates.RenderSystemState;
import com.emelwerx.world.services.Settings;

public class ModelDrawer {

    public static void drawEntities(RenderSystemState renderSystemState, float delta) {
        ModelBatch modelBatch = renderSystemState.getBatch();
        modelBatch.begin(renderSystemState.getWorldPerspectiveCamera());

        for (Entity entity: renderSystemState.getEntities()) {
            boolean isNotThePlayerItem = entity.getComponent(ItemComponent.class) == null;
            if (isNotThePlayerItem) {
                advanceAnimation(entity, delta);
                ModelComponent mod = entity.getComponent(ModelComponent.class);
                modelBatch.render(mod.getInstance(), renderSystemState.getEnvironment());
            }
        }

        modelBatch.end();
    }

    private static void advanceAnimation(Entity entity, float delta) {
        AnimationComponent animationComponent = entity.getComponent(AnimationComponent.class);
        boolean hasAnimation = animationComponent != null & !Settings.isPaused();
        if (hasAnimation) {
            animationComponent.getAnimationController().update(delta);
        }
    }
}
