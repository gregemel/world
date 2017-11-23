package com.emelwerx.world.databags.systemstates;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.emelwerx.world.WorldCore;
import com.emelwerx.world.ui.widgets.ControllerWidget;
import com.emelwerx.world.ui.widgets.CrosshairWidget;
import com.emelwerx.world.ui.widgets.GameOverWidget;
import com.emelwerx.world.ui.widgets.HealthWidget;
import com.emelwerx.world.ui.widgets.PauseWidget;
import com.emelwerx.world.ui.widgets.ScoreWidget;


public class WorldUiSystemState {
    private WorldCore worldCore;
    private Stage stage;
    private HealthWidget healthWidget;
    private ScoreWidget scoreWidget;
    private PauseWidget pauseWidget;
    private CrosshairWidget crosshairWidget;
    private GameOverWidget gameOverWidget;
    private Label fpsLabel;
    private ControllerWidget controllerWidget;

    public WorldCore getWorldCore() {
        return worldCore;
    }

    public void setWorldCore(WorldCore worldCore) {
        this.worldCore = worldCore;
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

}
