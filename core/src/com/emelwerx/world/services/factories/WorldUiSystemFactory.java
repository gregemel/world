package com.emelwerx.world.services.factories;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.emelwerx.world.WorldCore;
import com.emelwerx.world.databags.systemstates.WorldUiSystemState;
import com.emelwerx.world.services.Assets;
import com.emelwerx.world.systems.WorldUiSystem;
import com.emelwerx.world.ui.widgets.ControllerWidget;
import com.emelwerx.world.ui.widgets.CrosshairWidget;
import com.emelwerx.world.ui.widgets.GameOverWidget;
import com.emelwerx.world.ui.widgets.HealthWidget;
import com.emelwerx.world.ui.widgets.PauseWidget;
import com.emelwerx.world.ui.widgets.ScoreWidget;

public class WorldUiSystemFactory {
    public static WorldUiSystem create(WorldCore worldCore) {
        WorldUiSystemState worldUiSystemState = createWorldUiSystemState(worldCore);
        attachStage(worldUiSystemState);
        createWidgets(worldUiSystemState);
        return new WorldUiSystem(worldUiSystemState);
    }

    private static void attachStage(WorldUiSystemState worldUiSystemState) {
        Stage stage = new Stage(new FitViewport(WorldCore.VIRTUAL_WIDTH, WorldCore.VIRTUAL_HEIGHT));
        worldUiSystemState.setStage(stage);
    }

    private static WorldUiSystemState createWorldUiSystemState(WorldCore worldCore) {
        WorldUiSystemState worldUiSystemState = new WorldUiSystemState();
        worldUiSystemState.setWorldCore(worldCore);
        return worldUiSystemState;
    }

    private static void createWidgets(WorldUiSystemState worldUiSystemState) {
        attachHealthWidget(worldUiSystemState);
        attachScoreWidget(worldUiSystemState);
        attachPauseWidget(worldUiSystemState);
        attachGameOverWidget(worldUiSystemState);
        attachCrosshairWidget(worldUiSystemState);
        attachFpsWidget(worldUiSystemState);
        attachControllerWidget(worldUiSystemState);
    }

    private static void attachControllerWidget(WorldUiSystemState worldUiSystemState) {
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            ControllerWidget controllerWidget = new ControllerWidget();
            worldUiSystemState.setControllerWidget(controllerWidget);
            controllerWidget.addToStage(worldUiSystemState.getStage());
        }
    }

    private static void attachFpsWidget(WorldUiSystemState worldUiSystemState) {
        Label fpsLabel = new Label("", Assets.skin);
        worldUiSystemState.setFpsLabel(fpsLabel);
        fpsLabel.setPosition(0, 10);
        worldUiSystemState.getStage().addActor(fpsLabel);
    }

    private static void attachCrosshairWidget(WorldUiSystemState worldUiSystemState) {
        CrosshairWidget crosshairWidget = new CrosshairWidget();
        worldUiSystemState.setCrosshairWidget(crosshairWidget);
        crosshairWidget.setPosition(WorldCore.VIRTUAL_WIDTH / 2 - 16, WorldCore.VIRTUAL_HEIGHT / 2 - 16);
        crosshairWidget.setSize(32, 32);
        worldUiSystemState.getStage().addActor(crosshairWidget);
    }

    private static void attachGameOverWidget(WorldUiSystemState worldUiSystemState) {
        GameOverWidget gameOverWidget = new GameOverWidget(worldUiSystemState.getWorldCore(), worldUiSystemState.getStage());
        worldUiSystemState.setGameOverWidget(gameOverWidget);
        gameOverWidget.setSize(280, 100);
        gameOverWidget.setPosition(WorldCore.VIRTUAL_WIDTH / 2 - 280 / 2, WorldCore.VIRTUAL_HEIGHT / 2);
    }

    private static void attachPauseWidget(WorldUiSystemState worldUiSystemState) {
        PauseWidget pauseWidget = new PauseWidget(worldUiSystemState.getWorldCore(), worldUiSystemState.getStage());
        worldUiSystemState.setPauseWidget(pauseWidget);
        pauseWidget.setSize(64, 64);
        pauseWidget.setPosition(WorldCore.VIRTUAL_WIDTH - pauseWidget.getWidth(),
                WorldCore.VIRTUAL_HEIGHT - pauseWidget.getHeight());
        worldUiSystemState.getStage().setKeyboardFocus(pauseWidget);
    }

    private static void attachScoreWidget(WorldUiSystemState worldUiSystemState) {
        ScoreWidget scoreWidget = new ScoreWidget();
        worldUiSystemState.setScoreWidget(scoreWidget);
        scoreWidget.setSize(140, 25);
        scoreWidget.setPosition(0, WorldCore.VIRTUAL_HEIGHT - scoreWidget.getHeight());
        worldUiSystemState.getStage().addActor(scoreWidget);
    }

    private static void attachHealthWidget(WorldUiSystemState worldUiSystemState) {
        HealthWidget healthWidget = new HealthWidget();
        worldUiSystemState.setHealthWidget(healthWidget);
        healthWidget.setSize(140, 25);
        healthWidget.setPosition(WorldCore.VIRTUAL_WIDTH / 2 - healthWidget.getWidth() / 2, 0);
        worldUiSystemState.getStage().addActor(healthWidget);
    }
}
