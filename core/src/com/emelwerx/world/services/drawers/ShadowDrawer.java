package com.emelwerx.world.services.drawers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.math.Vector3;
import com.emelwerx.world.databags.components.CreatureComponent;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.databags.systemstates.RenderSystemState;

public class ShadowDrawer {

    public static void draw(RenderSystemState state) {
        DirectionalShadowLight shadowLight = state.getShadowLight();
        shadowLight.begin(Vector3.Zero, state.getWorldPerspectiveCamera().direction);
        drawEntityShadows(state, shadowLight);
        shadowLight.end();
    }

    private static void drawEntityShadows(RenderSystemState state, DirectionalShadowLight shadowLight) {
        ModelBatch modelBatch = state.getBatch();
        modelBatch.begin(shadowLight.getCamera());
        drawEachEntityShadow(state, modelBatch);
        modelBatch.end();
    }

    private static void drawEachEntityShadow(RenderSystemState state, ModelBatch modelBatch) {
        for(Entity entity : state.getEntities()) {

            if(entity.getComponent(CreatureComponent.class) == null)
                return;

            ModelInstance modelInstance = entity.getComponent(ModelComponent.class).getInstance();
            if (isVisible(state, modelInstance)) {
                modelBatch.render(modelInstance);
            }
        }
    }

    private static boolean isVisible(RenderSystemState state, final ModelInstance instance) {
        PerspectiveCamera cam = state.getWorldPerspectiveCamera();
        return cam.frustum.pointInFrustum(instance.transform.getTranslation(state.getPosition()));
    }
}