package com.emelwerx.world.systems;

import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.systemstates.WorldUiSystemState;

public class WorldUiSystem {

    private WorldUiSystemState worldUiSystemState;

    public WorldUiSystem(WorldUiSystemState worldUiSystemState) {
        this.worldUiSystemState = worldUiSystemState;
    }

    public WorldUiSystemState getWorldUiSystemState() {
        return worldUiSystemState;
    }

    public void update(float delta) {
        worldUiSystemState.getFpsLabel().setText("FPS: " + Gdx.graphics.getFramesPerSecond());
        worldUiSystemState.getStage().act(delta);
    }

    public void render() {
        worldUiSystemState.getStage().draw();
    }

    public void resize(int width, int height) {
        worldUiSystemState.getStage().getViewport().update(width, height);
    }

    public void dispose() {
        worldUiSystemState.getStage().dispose();
    }
}