package com.emelwerx.world.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.databags.systemstates.RenderSystemState;
import com.emelwerx.world.services.drawers.ModelDrawer;
import com.emelwerx.world.services.drawers.ParticleDrawer;
import com.emelwerx.world.services.drawers.PlayerItemDrawer;
import com.emelwerx.world.services.drawers.ShadowDrawer;

import java.util.Locale;

import static java.lang.String.format;


public class RenderSystem extends EntitySystem {

    private RenderSystemState renderSystemState;

    public RenderSystem(RenderSystemState renderSystemState) {
        this.renderSystemState = renderSystemState;
    }

    public RenderSystemState getRenderSystemState() {
        return renderSystemState;
    }

    public void addedToEngine(Engine e) {
        Gdx.app.log("RenderSystem", format("addedToEngine: %s", e.toString()));
        renderSystemState.setEntities(e.getEntitiesFor(Family.all(ModelComponent.class).get()));
    }

    public void update(float delta) {
        ShadowDrawer.draw(renderSystemState, delta);
        ModelDrawer.drawEntities(renderSystemState, delta);
        ParticleDrawer.draw(renderSystemState);
        PlayerItemDrawer.draw(renderSystemState);
    }

    public void resize(int width, int height) {
        Gdx.app.log("RenderSystem", String.format(Locale.US,"resizing (%d, %d)", width, height));
        renderSystemState.getWorldPerspectiveCamera().viewportHeight = height;
        renderSystemState.getWorldPerspectiveCamera().viewportWidth = width;
        renderSystemState.getPlayerItemCamera().viewportHeight = height;
        renderSystemState.getPlayerItemCamera().viewportWidth = width;
    }

    public void dispose() {
        Gdx.app.log("RenderSystem", "disposing");
        renderSystemState.getBatch().dispose();
        renderSystemState.setBatch(null);
    }
}