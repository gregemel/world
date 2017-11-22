package com.emelwerx.world.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.emelwerx.world.services.Settings;
import com.emelwerx.world.services.factories.WorldUiSystemFactory;
import com.emelwerx.world.systems.WorldUiSystem;
import com.emelwerx.world.WorldCore;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.services.PauseChecker;
import com.emelwerx.world.services.WorldDisposer;
import com.emelwerx.world.services.loaders.WorldLoader;
import com.emelwerx.world.services.drawers.WorldDrawer;

public class WorldScreen implements Screen {
    private WorldUiSystem worldUiSystem;
    private World world;

    public WorldScreen(WorldCore game) {
        worldUiSystem = WorldUiSystemFactory.create(game);
        world = WorldLoader.create("arena", worldUiSystem);

        Settings.setPaused(false);
        Gdx.input.setInputProcessor(worldUiSystem.getWorldUiSystemState().getStage());
        Gdx.input.setCursorCatched(true);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        worldUiSystem.update(delta);
        WorldDrawer.draw(world, delta);
        PauseChecker.checkPause(world);
        worldUiSystem.render();
    }

    @Override
    public void resize(int width, int height) {
        worldUiSystem.resize(width, height);
        world.getRenderSystem().resize(width, height);
    }

    @Override
    public void dispose() {
        WorldDisposer.dispose(world);
        worldUiSystem.dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

}