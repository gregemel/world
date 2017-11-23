package com.emelwerx.world.services.drawers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.databags.systemstates.RenderSystemState;

public class PlayerItemDrawer {

    public static void draw(RenderSystemState renderSystemState) {
        Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);
        renderSystemState.getBatch().begin(renderSystemState.getPlayerItemCamera());
        renderSystemState.getBatch().render(renderSystemState.getPlayerItem().getComponent(ModelComponent.class).getInstance());
        renderSystemState.getBatch().end();
    }

}
