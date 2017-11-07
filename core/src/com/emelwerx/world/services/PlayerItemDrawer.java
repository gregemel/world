package com.emelwerx.world.services;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.emelwerx.world.databags.ModelComponent;
import com.emelwerx.world.databags.RenderSystemState;

public class PlayerItemDrawer {

    public static void draw(RenderSystemState renderSystemState) {
        Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);
        renderSystemState.getBatch().begin(renderSystemState.getGunCamera());
        renderSystemState.getBatch().render(renderSystemState.getGun().getComponent(ModelComponent.class).getInstance());
        renderSystemState.getBatch().end();
    }

}
