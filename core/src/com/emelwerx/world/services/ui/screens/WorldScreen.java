package com.emelwerx.world.services.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.emelwerx.world.WorldAdapter;
import com.emelwerx.world.services.Settings;
import com.emelwerx.world.services.factories.WorldUiSystemFactory;
import com.emelwerx.world.systems.UserInterfaceSystem;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.services.Pauser;
import com.emelwerx.world.services.Disposer;
import com.emelwerx.world.services.loaders.WorldLoader;
import com.emelwerx.world.services.drawers.DebugDrawer;

public class WorldScreen implements Screen {
    private UserInterfaceSystem userInterfaceSystem;
    private World world;

    public WorldScreen(WorldAdapter game) {
        userInterfaceSystem = WorldUiSystemFactory.create(game);
        world = WorldLoader.load("arena", userInterfaceSystem);

        Settings.setPaused(false);
        Gdx.input.setInputProcessor(userInterfaceSystem.getUserInterfaceSystemState().getStage());
        Gdx.input.setCursorCatched(true);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        userInterfaceSystem.update(delta);
        world.getEntityEngine().update(delta);
        DebugDrawer.draw(world, delta);
        Pauser.check(world);
        userInterfaceSystem.render();
    }

    @Override
    public void resize(int width, int height) {
        userInterfaceSystem.resize(width, height);
        world.getRenderSystem().resize(width, height);
    }

    @Override
    public void dispose() {
        Disposer.dispose(world);
        userInterfaceSystem.dispose();
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