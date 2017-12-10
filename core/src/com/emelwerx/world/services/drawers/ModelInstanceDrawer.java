package com.emelwerx.world.services.drawers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.services.updaters.AnimationUpdater;

public class ModelInstanceDrawer {

    public static void draw(Environment environment, float delta, ModelBatch modelBatch, Entity entity) {
        AnimationUpdater.update(entity, delta);
        ModelInstance instance = entity.getComponent(ModelComponent.class).getInstance();
        modelBatch.render(instance, environment);
    }
}