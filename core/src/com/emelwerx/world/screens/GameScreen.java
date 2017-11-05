package com.emelwerx.world.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.emelwerx.world.WorldCore;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.Settings;
import com.emelwerx.world.UI.GameUI;
import com.emelwerx.world.services.WorldDisposer;
import com.emelwerx.world.services.WorldLoader;
import com.emelwerx.world.services.WorldRenderer;

public class GameScreen implements Screen {
    private WorldCore game;
    private GameUI gameUI;
    private World world;

    private WorldLoader worldLoader;
    private WorldRenderer worldRenderer;
    private WorldDisposer worldDisposer;

    public GameScreen(WorldCore game) {
        this.game = game;
        gameUI = new GameUI(game);
        worldLoader = new WorldLoader();
        world = worldLoader.create("arena", gameUI);
        worldRenderer = new WorldRenderer();

        Settings.Paused = false;
        Gdx.input.setInputProcessor(gameUI.stage);
        Gdx.input.setCursorCatched(true);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        gameUI.update(delta);
        worldRenderer.render(world, delta);
        gameUI.render();
    }

    @Override
    public void resize(int width, int height) {
        gameUI.resize(width, height);
        worldRenderer.resize(world, width, height);
    }

    @Override
    public void dispose() {
        getWorldDisposer().dispose(world);
        gameUI.dispose();
    }

    private WorldDisposer getWorldDisposer() {
        if(worldDisposer == null) {
            worldDisposer = new WorldDisposer();
        }
        return worldDisposer;
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