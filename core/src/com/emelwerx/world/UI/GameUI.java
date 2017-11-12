package com.emelwerx.world.UI;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.emelwerx.world.services.Assets;
import com.emelwerx.world.WorldCore;

public class GameUI {
    private WorldCore game;
    private Stage stage;
    private HealthWidget healthWidget;
    private ScoreWidget scoreWidget;
    private PauseWidget pauseWidget;
    private CrosshairWidget crosshairWidget;
    private GameOverWidget gameOverWidget;
    private Label fpsLabel;
    private ControllerWidget controllerWidget;

    public GameUI(WorldCore game) {
        this.game = game;
        stage = new Stage(new FitViewport(WorldCore.VIRTUAL_WIDTH, WorldCore.VIRTUAL_HEIGHT));
        setWidgets();
        configureWidgets();
    }

    public WorldCore getGame() {
        return game;
    }

    public void setGame(WorldCore game) {
        this.game = game;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public HealthWidget getHealthWidget() {
        return healthWidget;
    }

    public void setHealthWidget(HealthWidget healthWidget) {
        this.healthWidget = healthWidget;
    }

    public ScoreWidget getScoreWidget() {
        return scoreWidget;
    }

    public void setScoreWidget(ScoreWidget scoreWidget) {
        this.scoreWidget = scoreWidget;
    }

    public PauseWidget getPauseWidget() {
        return pauseWidget;
    }

    public void setPauseWidget(PauseWidget pauseWidget) {
        this.pauseWidget = pauseWidget;
    }

    public CrosshairWidget getCrosshairWidget() {
        return crosshairWidget;
    }

    public void setCrosshairWidget(CrosshairWidget crosshairWidget) {
        this.crosshairWidget = crosshairWidget;
    }

    public GameOverWidget getGameOverWidget() {
        return gameOverWidget;
    }

    public void setGameOverWidget(GameOverWidget gameOverWidget) {
        this.gameOverWidget = gameOverWidget;
    }

    public Label getFpsLabel() {
        return fpsLabel;
    }

    public void setFpsLabel(Label fpsLabel) {
        this.fpsLabel = fpsLabel;
    }

    public ControllerWidget getControllerWidget() {
        return controllerWidget;
    }

    public void setControllerWidget(ControllerWidget controllerWidget) {
        this.controllerWidget = controllerWidget;
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
        healthWidget.setPosition(WorldCore.VIRTUAL_WIDTH / 2 - healthWidget.getWidth() / 2, 0);

        scoreWidget.setSize(140, 25);
        scoreWidget.setPosition(0, WorldCore.VIRTUAL_HEIGHT - scoreWidget.getHeight());

        pauseWidget.setSize(64, 64);
        pauseWidget.setPosition(WorldCore.VIRTUAL_WIDTH - pauseWidget.getWidth(), WorldCore.VIRTUAL_HEIGHT - pauseWidget.getHeight());

        gameOverWidget.setSize(280, 100);
        gameOverWidget.setPosition(WorldCore.VIRTUAL_WIDTH / 2 - 280 / 2, WorldCore.VIRTUAL_HEIGHT / 2);

        crosshairWidget.setPosition(WorldCore.VIRTUAL_WIDTH / 2 - 16, WorldCore.VIRTUAL_HEIGHT / 2 - 16);
        crosshairWidget.setSize(32, 32);

        fpsLabel.setPosition(0, 10);

        stage.addActor(healthWidget);
        stage.addActor(scoreWidget);
        stage.addActor(crosshairWidget);
        stage.setKeyboardFocus(pauseWidget);
        stage.addActor(fpsLabel);

        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            controllerWidget.addToStage(stage);
        }
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