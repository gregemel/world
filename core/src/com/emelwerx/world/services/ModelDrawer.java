package com.emelwerx.world.services;

import com.badlogic.ashley.core.Entity;
import com.emelwerx.world.databags.ItemComponent;
import com.emelwerx.world.databags.ModelComponent;
import com.emelwerx.world.databags.RenderSystemState;

public class ModelDrawer {

    public static void draw(RenderSystemState renderSystemState) {
        renderSystemState.getBatch().begin(renderSystemState.getPerspectiveCamera());

        for (Entity entity: renderSystemState.getEntities()) {
            boolean isNotPlayerItem = entity.getComponent(ItemComponent.class) == null;
            if (isNotPlayerItem) {
                ModelComponent mod = entity.getComponent(ModelComponent.class);
                renderSystemState.getBatch().render(mod.getInstance(), renderSystemState.getEnvironment());
            }
        }

        renderSystemState.getBatch().end();
    }

}
