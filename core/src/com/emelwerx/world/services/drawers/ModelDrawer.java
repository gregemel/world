package com.emelwerx.world.services.drawers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.emelwerx.world.databags.components.ItemComponent;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.databags.systemstates.RenderSystemState;

public class ModelDrawer {

    public static void drawEntities(RenderSystemState renderSystemState) {
        ModelBatch modelBatch = renderSystemState.getBatch();
        modelBatch.begin(renderSystemState.getPerspectiveCamera());

        for (Entity entity: renderSystemState.getEntities()) {
            boolean isNotThePlayerItem = entity.getComponent(ItemComponent.class) == null;
            if (isNotThePlayerItem) {
                ModelComponent mod = entity.getComponent(ModelComponent.class);
                modelBatch.render(mod.getInstance(), renderSystemState.getEnvironment());
            }
        }

        modelBatch.end();
    }
}
