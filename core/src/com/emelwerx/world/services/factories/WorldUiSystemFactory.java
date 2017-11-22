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

        WorldUiSystemState worldUiSystemState = new WorldUiSystemState();

        worldUiSystemState.setGame(worldCore);
        Stage stage = new Stage(new FitViewport(WorldCore.VIRTUAL_WIDTH, WorldCore.VIRTUAL_HEIGHT));
        worldUiSystemState.setStage(stage);
        createWidgets(worldUiSystemState);
        configureWidgets(worldUiSystemState);

        return new WorldUiSystem(worldUiSystemState);
    }

    private static void createWidgets(WorldUiSystemState worldUiSystemState) {
        worldUiSystemState.setHealthWidget(new HealthWidget());
        worldUiSystemState.setScoreWidget(new ScoreWidget());
        worldUiSystemState.setPauseWidget(new PauseWidget(worldUiSystemState.getGame(), worldUiSystemState.getStage()));
        worldUiSystemState.setGameOverWidget(new GameOverWidget(worldUiSystemState.getGame(), worldUiSystemState.getStage()));
        worldUiSystemState.setCrosshairWidget(new CrosshairWidget());
        worldUiSystemState.setFpsLabel(new Label("", Assets.skin));
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            worldUiSystemState.setControllerWidget(new ControllerWidget());
        }
    }

    private static void configureWidgets(WorldUiSystemState worldUiSystemState) {
        worldUiSystemState.getHealthWidget().setSize(140, 25);
        worldUiSystemState.getHealthWidget().setPosition(WorldCore.VIRTUAL_WIDTH / 2 - worldUiSystemState.getHealthWidget().getWidth() / 2, 0);

        worldUiSystemState.getScoreWidget().setSize(140, 25);
        worldUiSystemState.getScoreWidget().setPosition(0, WorldCore.VIRTUAL_HEIGHT - worldUiSystemState.getScoreWidget().getHeight());

        worldUiSystemState.getPauseWidget().setSize(64, 64);
        worldUiSystemState.getPauseWidget().setPosition(WorldCore.VIRTUAL_WIDTH - worldUiSystemState.getPauseWidget().getWidth(), WorldCore.VIRTUAL_HEIGHT - worldUiSystemState.getPauseWidget().getHeight());

        worldUiSystemState.getGameOverWidget().setSize(280, 100);
        worldUiSystemState.getGameOverWidget().setPosition(WorldCore.VIRTUAL_WIDTH / 2 - 280 / 2, WorldCore.VIRTUAL_HEIGHT / 2);

        worldUiSystemState.getCrosshairWidget().setPosition(WorldCore.VIRTUAL_WIDTH / 2 - 16, WorldCore.VIRTUAL_HEIGHT / 2 - 16);
        worldUiSystemState.getCrosshairWidget().setSize(32, 32);

        worldUiSystemState.getFpsLabel().setPosition(0, 10);

        worldUiSystemState.getStage().addActor(worldUiSystemState.getHealthWidget());
        worldUiSystemState.getStage().addActor(worldUiSystemState.getScoreWidget());
        worldUiSystemState.getStage().addActor(worldUiSystemState.getCrosshairWidget());
        worldUiSystemState.getStage().setKeyboardFocus(worldUiSystemState.getPauseWidget());
        worldUiSystemState.getStage().addActor(worldUiSystemState.getFpsLabel());

        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            worldUiSystemState.getControllerWidget().addToStage(worldUiSystemState.getStage());
        }
    }

}
