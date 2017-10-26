package com.deeep.spaceglad.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.deeep.spaceglad.WorldGDXAdapter;
import com.deeep.spaceglad.databags.GameWorld;
import com.deeep.spaceglad.Settings;
import com.deeep.spaceglad.UI.GameUI;
import com.deeep.spaceglad.services.WorldLoader;

public class GameScreen implements Screen {
    private WorldGDXAdapter game;
    private GameUI gameUI;
    private GameWorld gameWorld;

    private WorldLoader worldService;

    public GameScreen(WorldGDXAdapter game) {
        this.game = game;
        gameUI = new GameUI(game);
        worldService = new WorldLoader();
        gameWorld = worldService.create(gameUI);

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
        worldService.render(delta);
        gameUI.render();
    }

    @Override
    public void resize(int width, int height) {
        gameUI.resize(width, height);
        worldService.resize(width, height);
    }

    @Override
    public void dispose() {
        worldService.dispose();
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