package com.emelwerx.world.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.emelwerx.world.databags.ItemComponent;
import com.emelwerx.world.databags.ModelComponent;
import com.emelwerx.world.databags.RenderSystemState;

public class ModelDrawer {

    public static void draw(RenderSystemState renderSystemState) {
        renderSystemState.getBatch().begin(renderSystemState.getPerspectiveCamera());
        ImmutableArray<Entity> entities = renderSystemState.getEntities();

        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getComponent(ItemComponent.class) == null) {
                ModelComponent mod = entities.get(i).getComponent(ModelComponent.class);
                renderSystemState.getBatch().render(mod.getInstance(), renderSystemState.getEnvironment());
            }
        }

        renderSystemState.getBatch().end();
    }

}
