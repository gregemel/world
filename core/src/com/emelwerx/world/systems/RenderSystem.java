package com.emelwerx.world.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.math.Vector3;
import com.emelwerx.world.Settings;
import com.emelwerx.world.databags.AnimationComponent;
import com.emelwerx.world.databags.MonsterComponent;
import com.emelwerx.world.databags.ItemComponent;
import com.emelwerx.world.databags.ModelComponent;
import com.emelwerx.world.databags.PlayerComponent;
import com.emelwerx.world.databags.RenderSystemState;

import static java.lang.String.*;


public class RenderSystem extends EntitySystem {

    private RenderSystemState renderSystemState;

    public RenderSystemState getRenderSystemState() {
        return renderSystemState;
    }

    public void setRenderSystemState(RenderSystemState renderSystemState) {
        this.renderSystemState = renderSystemState;
    }

    public void addedToEngine(Engine e) {
        Gdx.app.log("RenderSystem", format("addedToEngine: %s", e.toString()));
        renderSystemState.setEntities(e.getEntitiesFor(Family.all(ModelComponent.class).get()));
    }

    public void update(float delta) {
        drawShadows(delta);
        drawModels();
    }

    private boolean isVisible(Camera cam, final ModelInstance instance) {
        return cam.frustum.pointInFrustum(instance.transform.getTranslation(getRenderSystemState().getPosition()));
    }

    private void drawShadows(float delta) {
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
                if (isVisible(renderSystemState.getPerspectiveCamera(), mod.getInstance())) {
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

    private void drawModels() {
        renderSystemState.getBatch().begin(renderSystemState.getPerspectiveCamera());
        ImmutableArray<Entity> entities = renderSystemState.getEntities();

        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getComponent(ItemComponent.class) == null) {
                ModelComponent mod = entities.get(i).getComponent(ModelComponent.class);
                renderSystemState.getBatch().render(mod.getInstance(), renderSystemState.getEnvironment());
            }
        }

        renderSystemState.getBatch().end();
        renderParticleEffects();
        drawGun();
    }

    private void renderParticleEffects() {
        renderSystemState.getBatch().begin(renderSystemState.getPerspectiveCamera());
        ParticleSystem particleSystem = renderSystemState.getParticleSystem();
        particleSystem.update(); // not necessary
        particleSystem.begin();
        particleSystem.draw();
        particleSystem.end();
        renderSystemState.getBatch().render(particleSystem);
        renderSystemState.getBatch().end();
    }

    private void drawGun() {
        Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);
        renderSystemState.getBatch().begin(renderSystemState.getGunCamera());
        renderSystemState.getBatch().render(renderSystemState.getGun().getComponent(ModelComponent.class).getInstance());
        renderSystemState.getBatch().end();
    }

    public void resize(int width, int height) {
        Gdx.app.log("RenderSystem", String.format("resizing (%d, %d)", width, height));
        renderSystemState.getPerspectiveCamera().viewportHeight = height;
        renderSystemState.getPerspectiveCamera().viewportWidth = width;
        renderSystemState.getGunCamera().viewportHeight = height;
        renderSystemState.getGunCamera().viewportWidth = width;
    }

    public void dispose() {
        Gdx.app.log("RenderSystem", "disposing");
        renderSystemState.getBatch().dispose();
        renderSystemState.setBatch(null);
    }
}