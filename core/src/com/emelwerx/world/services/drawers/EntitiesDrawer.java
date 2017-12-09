package com.emelwerx.world.services.drawers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.emelwerx.world.databags.components.ItemComponent;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.databags.systemstates.RenderSystemState;
import com.emelwerx.world.services.updaters.AnimationUpdater;

public class EntitiesDrawer {

    public static void draw(RenderSystemState state, float delta) {
        ModelBatch modelBatch = state.getBatch();
        modelBatch.begin(state.getWorldPerspectiveCamera());
        drawEachEntityModel(state, delta, modelBatch);
        modelBatch.end();
    }

    private static void drawEachEntityModel(RenderSystemState state, float delta, ModelBatch modelBatch) {
        for (Entity entity: state.getEntities()) {
            boolean isNotThePlayerItem = entity.getComponent(ItemComponent.class) == null;
            if (isNotThePlayerItem) {
                AnimationUpdater.update(entity, delta);
                ModelInstance instance = entity.getComponent(ModelComponent.class).getInstance();
                modelBatch.render(instance, state.getEnvironment());
            }
        }
    }
}