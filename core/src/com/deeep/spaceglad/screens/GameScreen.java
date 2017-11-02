package com.deeep.spaceglad.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.deeep.spaceglad.WorldGDXAdapter;
import com.deeep.spaceglad.databags.GameWorld;
import com.deeep.spaceglad.Settings;
import com.deeep.spaceglad.UI.GameUI;
import com.deeep.spaceglad.services.WorldDisposer;
import com.deeep.spaceglad.services.WorldLoader;
import com.deeep.spaceglad.services.WorldRenderer;

public class GameScreen implements Screen {
    private WorldGDXAdapter game;
    private GameUI gameUI;
    private GameWorld gameWorld;

    private WorldLoader worldLoader;
    private WorldRenderer worldRenderer;
    private WorldDisposer worldDisposer;

    public GameScreen(WorldGDXAdapter game) {
        this.game = game;
        gameUI = new GameUI(game);
        worldLoader = new WorldLoader();
        gameWorld = worldLoader.create(gameUI);
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
        worldRenderer.render(gameWorld, delta);
        gameUI.render();
    }

    @Override
    public void resize(int width, int height) {
        gameUI.resize(width, height);
        worldRenderer.resize(gameWorld, width, height);
    }

    @Override
    public void dispose() {
        getWorldDisposer().dispose(gameWorld);
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