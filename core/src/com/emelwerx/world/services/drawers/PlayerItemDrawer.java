package com.emelwerx.world.services.drawers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.databags.systemstates.RenderSystemState;
import com.emelwerx.world.services.updaters.AnimationUpdater;

public class PlayerItemDrawer {

    public static void draw(RenderSystemState renderSystemState, float delta) {
        Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);
        renderSystemState.getBatch().begin(renderSystemState.getPlayerItemCamera());
        Entity playerItem = renderSystemState.getPlayerItem();
        AnimationUpdater.update(playerItem, delta);
        renderSystemState.getBatch().render(playerItem.getComponent(ModelComponent.class).getInstance());
        renderSystemState.getBatch().end();
    }
}
