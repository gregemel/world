package com.deeep.spaceglad.UI;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.deeep.spaceglad.Assets;
import com.deeep.spaceglad.WorldGDXAdapter;
import com.deeep.spaceglad.services.ControllerWidget;

public class GameUI {
    private WorldGDXAdapter game;
    public Stage stage;
    public HealthWidget healthWidget;
    private ScoreWidget scoreWidget;
    private PauseWidget pauseWidget;
    private CrosshairWidget crosshairWidget;
    public GameOverWidget gameOverWidget;
    private Label fpsLabel;
    private ControllerWidget controllerWidget;

    public GameUI(WorldGDXAdapter game) {
        this.game = game;
        stage = new Stage(new FitViewport(WorldGDXAdapter.VIRTUAL_WIDTH, WorldGDXAdapter.VIRTUAL_HEIGHT));
        setWidgets();
        configureWidgets();
    }

    private void setWidgets() {
        healthWidget = new HealthWidget();
        scoreWidget = new ScoreWidget();
        pauseWidget = new PauseWidget(game, stage);
        gameOverWidget = new GameOverWidget(game, stage);
        crosshairWidget = new CrosshairWidget();
        fpsLabel = new Label("", Assets.skin);
        if (Gdx.app.getType() == Application.ApplicationType.Android) controllerWidget = new ControllerWidget();
    }

    private void configureWidgets() {
        healthWidget.setSize(140, 25);
        healthWidget.setPosition(WorldGDXAdapter.VIRTUAL_WIDTH / 2 - healthWidget.getWidth() / 2, 0);
        scoreWidget.setSize(140, 25);
        scoreWidget.setPosition(0, WorldGDXAdapter.VIRTUAL_HEIGHT - scoreWidget.getHeight());
        pauseWidget.setSize(64, 64);
        pauseWidget.setPosition(WorldGDXAdapter.VIRTUAL_WIDTH - pauseWidget.getWidth(), WorldGDXAdapter.VIRTUAL_HEIGHT - pauseWidget.getHeight());
        gameOverWidget.setSize(280, 100);
        gameOverWidget.setPosition(WorldGDXAdapter.VIRTUAL_WIDTH / 2 - 280 / 2, WorldGDXAdapter.VIRTUAL_HEIGHT / 2);
        crosshairWidget.setPosition(WorldGDXAdapter.VIRTUAL_WIDTH / 2 - 16, WorldGDXAdapter.VIRTUAL_HEIGHT / 2 - 16);
        crosshairWidget.setSize(32, 32);

        fpsLabel.setPosition(0, 10);

        stage.addActor(healthWidget);
        stage.addActor(scoreWidget);
        stage.addActor(crosshairWidget);
        stage.setKeyboardFocus(pauseWidget);
        stage.addActor(fpsLabel);
        if (Gdx.app.getType() == Application.ApplicationType.Android) controllerWidget.addToStage(stage);
    }

    public void update(float delta) {
        fpsLabel.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
        stage.act(delta);
    }

    public void render() {
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    public void dispose() {
        stage.dispose();
    }
}