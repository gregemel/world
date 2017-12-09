package com.emelwerx.world.services.factories;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.emelwerx.world.WorldAdapter;
import com.emelwerx.world.databags.systemstates.UserInterfaceSystemState;
import com.emelwerx.world.services.Assets;
import com.emelwerx.world.services.ui.widgets.ControllerWidget;
import com.emelwerx.world.services.ui.widgets.CrosshairWidget;
import com.emelwerx.world.services.ui.widgets.GameOverWidget;
import com.emelwerx.world.services.ui.widgets.HealthWidget;
import com.emelwerx.world.services.ui.widgets.PauseWidget;
import com.emelwerx.world.services.ui.widgets.ScoreWidget;

public class UiWidgetsFactory {
    public static void create(UserInterfaceSystemState userInterfaceSystemState) {
        attachHealthWidget(userInterfaceSystemState);
        attachScoreWidget(userInterfaceSystemState);
        attachPauseWidget(userInterfaceSystemState);
        attachGameOverWidget(userInterfaceSystemState);
        attachCrosshairWidget(userInterfaceSystemState);
        attachFpsWidget(userInterfaceSystemState);
        attachControllerWidget(userInterfaceSystemState);
    }

    private static void attachControllerWidget(UserInterfaceSystemState userInterfaceSystemState) {
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            ControllerWidget controllerWidget = new ControllerWidget();
            userInterfaceSystemState.setControllerWidget(controllerWidget);
            controllerWidget.addToStage(userInterfaceSystemState.getStage());
        }
    }

    private static void attachFpsWidget(UserInterfaceSystemState userInterfaceSystemState) {
        Label fpsLabel = new Label("", Assets.skin);
        userInterfaceSystemState.setFpsLabel(fpsLabel);
        fpsLabel.setPosition(0, 10);
        userInterfaceSystemState.getStage().addActor(fpsLabel);
    }

    private static void attachCrosshairWidget(UserInterfaceSystemState userInterfaceSystemState) {
        CrosshairWidget crosshairWidget = new CrosshairWidget();
        userInterfaceSystemState.setCrosshairWidget(crosshairWidget);
        crosshairWidget.setPosition(WorldAdapter.VIRTUAL_WIDTH / 2 - 16, WorldAdapter.VIRTUAL_HEIGHT / 2 - 16);
        crosshairWidget.setSize(32, 32);
        userInterfaceSystemState.getStage().addActor(crosshairWidget);
    }

    private static void attachGameOverWidget(UserInterfaceSystemState userInterfaceSystemState) {
        GameOverWidget gameOverWidget = new GameOverWidget(userInterfaceSystemState.getWorldAdapter(), userInterfaceSystemState.getStage());
        userInterfaceSystemState.setGameOverWidget(gameOverWidget);
        gameOverWidget.setSize(280, 100);
        gameOverWidget.setPosition(WorldAdapter.VIRTUAL_WIDTH / 2 - 280 / 2, WorldAdapter.VIRTUAL_HEIGHT / 2);
    }

    private static void attachPauseWidget(UserInterfaceSystemState userInterfaceSystemState) {
        PauseWidget pauseWidget = new PauseWidget(userInterfaceSystemState.getWorldAdapter(), userInterfaceSystemState.getStage());
        userInterfaceSystemState.setPauseWidget(pauseWidget);
        pauseWidget.setSize(64, 64);
        pauseWidget.setPosition(WorldAdapter.VIRTUAL_WIDTH - pauseWidget.getWidth(),
                WorldAdapter.VIRTUAL_HEIGHT - pauseWidget.getHeight());
        userInterfaceSystemState.getStage().setKeyboardFocus(pauseWidget);
    }

    private static void attachScoreWidget(UserInterfaceSystemState userInterfaceSystemState) {
        ScoreWidget scoreWidget = new ScoreWidget();
        userInterfaceSystemState.setScoreWidget(scoreWidget);
        scoreWidget.setSize(140, 25);
        scoreWidget.setPosition(0, WorldAdapter.VIRTUAL_HEIGHT - scoreWidget.getHeight());
        userInterfaceSystemState.getStage().addActor(scoreWidget);
    }

    private static void attachHealthWidget(UserInterfaceSystemState userInterfaceSystemState) {
        HealthWidget healthWidget = new HealthWidget();
        userInterfaceSystemState.setHealthWidget(healthWidget);
        healthWidget.setSize(140, 25);
        healthWidget.setPosition(WorldAdapter.VIRTUAL_WIDTH / 2 - healthWidget.getWidth() / 2, 0);
        userInterfaceSystemState.getStage().addActor(healthWidget);
    }
}