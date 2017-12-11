package com.emelwerx.world.systems;

import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.systemstates.UserInterfaceSystemState;

public class UserInterfaceSystem {

    private UserInterfaceSystemState state;

    public UserInterfaceSystem(UserInterfaceSystemState state) {
        this.state = state;
    }

    public UserInterfaceSystemState getState() {
        return state;
    }

    public void update(float delta) {
        state.getFpsLabel().setText("FPS: " + Gdx.graphics.getFramesPerSecond());
        state.getStage().act(delta);
    }

    public void render() {
        state.getStage().draw();
    }

    public void resize(int width, int height) {
        state.getStage().getViewport().update(width, height);
    }

    public void dispose() {
        state.getStage().dispose();
    }
}