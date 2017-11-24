package com.emelwerx.world.services.drawers;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.math.Vector3;
import com.emelwerx.world.databags.components.CreatureComponent;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.databags.components.PlayerComponent;
import com.emelwerx.world.databags.systemstates.RenderSystemState;

public class ShadowDrawer {

    public static void draw(RenderSystemState renderSystemState) {
        DirectionalShadowLight shadowLight = renderSystemState.getShadowLight();
        shadowLight.begin(Vector3.Zero, renderSystemState.getWorldPerspectiveCamera().direction);
        drawEntities(renderSystemState, shadowLight);
        shadowLight.end();
    }

    private static void drawEntities(RenderSystemState renderSystemState, DirectionalShadowLight shadowLight) {
        ModelBatch modelBatch = renderSystemState.getBatch();
        modelBatch.begin(shadowLight.getCamera());
        renderEachEntity(renderSystemState, modelBatch);
        modelBatch.end();
    }

    private static void renderEachEntity(RenderSystemState renderSystemState, ModelBatch modelBatch) {
        for(Entity entity : renderSystemState.getEntities()) {
            boolean isPlayerOrCreature = entity.getComponent(PlayerComponent.class) != null
                    || entity.getComponent(CreatureComponent.class) != null;
            if (isPlayerOrCreature) {
                ModelComponent modelComponent = entity.getComponent(ModelComponent.class);
                if (isVisible(renderSystemState, modelComponent.getInstance())) {
                    modelBatch.render(modelComponent.getInstance());
                }
            }
        }
    }

    private static boolean isVisible(RenderSystemState renderSystemState, final ModelInstance instance) {
        PerspectiveCamera cam = renderSystemState.getWorldPerspectiveCamera();
        return cam.frustum.pointInFrustum(instance.transform.getTranslation(renderSystemState.getPosition()));
    }
}
