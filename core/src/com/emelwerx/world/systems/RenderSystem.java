package com.emelwerx.world.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.databags.systemstates.RenderSystemState;
import com.emelwerx.world.services.drawers.EntitiesDrawer;
import com.emelwerx.world.services.drawers.ParticleDrawer;
import com.emelwerx.world.services.drawers.PlayerItemDrawer;
import com.emelwerx.world.services.drawers.ShadowDrawer;

import java.util.Locale;

import static java.lang.String.format;

public class RenderSystem extends EntitySystem {

    private RenderSystemState state;

    public RenderSystem(RenderSystemState state) {
        this.state = state;
    }

    public RenderSystemState getState() {
        return state;
    }

    public void addedToEngine(Engine e) {
        Gdx.app.log("RenderSystem", format("addedToEngine: %s", e.toString()));
        state.setEntities(e.getEntitiesFor(Family.all(ModelComponent.class).get()));
    }

    public void update(float delta) {
        ShadowDrawer.draw(state);
        EntitiesDrawer.draw(state, delta);
        ParticleDrawer.draw(state);
        PlayerItemDrawer.draw(state, delta);
    }

    public void resize(int width, int height) {
        Gdx.app.log("RenderSystem", String.format(Locale.US,"resizing (%d, %d)", width, height));
        state.getWorldPerspectiveCamera().viewportHeight = height;
        state.getWorldPerspectiveCamera().viewportWidth = width;
        state.getPlayerItemCamera().viewportHeight = height;
        state.getPlayerItemCamera().viewportWidth = width;
    }

    public void dispose() {
        Gdx.app.log("RenderSystem", "disposing");
        state.getBatch().dispose();
        state.setBatch(null);
    }
}