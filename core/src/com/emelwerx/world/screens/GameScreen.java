package com.emelwerx.world.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.emelwerx.world.services.Settings;
import com.emelwerx.world.UI.GameUI;
import com.emelwerx.world.WorldCore;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.services.PauseChecker;
import com.emelwerx.world.services.WorldDisposer;
import com.emelwerx.world.services.WorldLoader;
import com.emelwerx.world.services.WorldDrawer;

public class GameScreen implements Screen {
    private GameUI gameUI;
    private World world;

    public GameScreen(WorldCore game) {
        gameUI = new GameUI(game);
        WorldLoader worldLoader = new WorldLoader();
        world = worldLoader.create("arena", gameUI);

        Settings.setPaused(false);
        Gdx.input.setInputProcessor(gameUI.getStage());
        Gdx.input.setCursorCatched(true);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        gameUI.update(delta);
        WorldDrawer.draw(world, delta);
        PauseChecker.checkPause(world);
        gameUI.render();
    }

    @Override
    public void resize(int width, int height) {
        gameUI.resize(width, height);
        world.getRenderSystem().resize(width, height);
    }

    @Override
    public void dispose() {
        WorldDisposer.dispose(world);
        gameUI.dispose();
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