package com.emelwerx.world.systems;

import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.systemstates.UserInterfaceSystemState;

public class UserInterfaceSystem {

    private UserInterfaceSystemState userInterfaceSystemState;

    public UserInterfaceSystem(UserInterfaceSystemState userInterfaceSystemState) {
        this.userInterfaceSystemState = userInterfaceSystemState;
    }

    public UserInterfaceSystemState getUserInterfaceSystemState() {
        return userInterfaceSystemState;
    }

    public void update(float delta) {
        userInterfaceSystemState.getFpsLabel().setText("FPS: " + Gdx.graphics.getFramesPerSecond());
        userInterfaceSystemState.getStage().act(delta);
    }

    public void render() {
        userInterfaceSystemState.getStage().draw();
    }

    public void resize(int width, int height) {
        userInterfaceSystemState.getStage().getViewport().update(width, height);
    }

    public void dispose() {
        userInterfaceSystemState.getStage().dispose();
    }
}