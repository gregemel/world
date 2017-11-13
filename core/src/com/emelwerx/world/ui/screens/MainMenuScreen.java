package com.emelwerx.world.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.emelwerx.world.services.Assets;
import com.emelwerx.world.WorldCore;

public class MainMenuScreen implements Screen {
    private WorldCore game;
    private Stage stage;
    private Image backgroundImage;
    private Image titleImage;
    private TextButton playButton;
    private TextButton leaderboardsButton;
    private TextButton quitButton;

    public MainMenuScreen(WorldCore game) {
        this.game = game;
        stage = new Stage(new FitViewport(WorldCore.VIRTUAL_WIDTH, WorldCore.VIRTUAL_HEIGHT));
        setWidgets();
        configureWidgers();
        setListeners();

        Gdx.input.setInputProcessor(stage);
    }

    private void setWidgets() {
        backgroundImage = new Image(new Texture(Gdx.files.internal("data/backgroundMN.png")));
        titleImage = new Image(new Texture(Gdx.files.internal("data/title.png")));
        playButton = new TextButton("Play", Assets.skin);
        leaderboardsButton = new TextButton("Leaderboards", Assets.skin);
        quitButton = new TextButton("Quit", Assets.skin);
    }

    private void configureWidgers() {
        backgroundImage.setSize(WorldCore.VIRTUAL_WIDTH, WorldCore.VIRTUAL_HEIGHT);
        backgroundImage.setColor(1, 1, 1, 0);
        backgroundImage.addAction(Actions.fadeIn(0.65f));
        titleImage.setSize(620, 200);
        titleImage.setPosition(WorldCore.VIRTUAL_WIDTH / 2 - titleImage.getWidth() / 2, WorldCore.VIRTUAL_HEIGHT / 2);
        titleImage.setColor(1, 1, 1, 0);
        titleImage.addAction(new SequenceAction(Actions.delay(0.65f), Actions.fadeIn(0.75f)));
        playButton.setSize(128, 64);
        playButton.setPosition(WorldCore.VIRTUAL_WIDTH / 2 - playButton.getWidth() / 2, WorldCore.VIRTUAL_HEIGHT / 2 - 100);
        playButton.setColor(1, 1, 1, 0);
        playButton.addAction(new SequenceAction(Actions.delay(0.65f), Actions.fadeIn(0.75f)));
        leaderboardsButton.setSize(128, 64);
        leaderboardsButton.setPosition(WorldCore.VIRTUAL_WIDTH / 2 - playButton.getWidth() / 2, WorldCore.VIRTUAL_HEIGHT / 2 - 170);
        leaderboardsButton.setColor(1, 1, 1, 0);
        leaderboardsButton.addAction(new SequenceAction(Actions.delay(0.65f), Actions.fadeIn(0.75f)));
        quitButton.setSize(128, 64);
        quitButton.setPosition(WorldCore.VIRTUAL_WIDTH / 2 - playButton.getWidth() / 2, WorldCore.VIRTUAL_HEIGHT / 2 - 240);
        quitButton.setColor(1, 1, 1, 0);
        quitButton.addAction(new SequenceAction(Actions.delay(0.65f), Actions.fadeIn(0.75f)));

        stage.addActor(backgroundImage);
        stage.addActor(titleImage);
        stage.addActor(playButton);
        stage.addActor(leaderboardsButton);
        stage.addActor(quitButton);
    }

    private void setListeners() {
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen nextGameScreen = new GameScreen(game);
                game.setScreen(nextGameScreen);
            }
        });
        leaderboardsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LeaderboardsScreen(game));
            }
        });
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void render(float delta) {
        /** Updates */
        stage.act(delta);
        /** Draw */
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void show() {
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