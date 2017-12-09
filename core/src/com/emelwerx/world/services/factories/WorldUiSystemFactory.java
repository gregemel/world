package com.emelwerx.world.services.factories;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.emelwerx.world.WorldAdapter;
import com.emelwerx.world.databags.systemstates.UserInterfaceSystemState;
import com.emelwerx.world.systems.UserInterfaceSystem;

public class WorldUiSystemFactory {
    public static UserInterfaceSystem create(WorldAdapter adapter) {
        UserInterfaceSystemState state = createState(adapter);
        UiWidgetsFactory.create(state);
        return new UserInterfaceSystem(state);
    }

    private static UserInterfaceSystemState createState(WorldAdapter adapter) {
        UserInterfaceSystemState state = new UserInterfaceSystemState();
        state.setWorldAdapter(adapter);
        attachStage(state);
        return state;
    }

    private static void attachStage(UserInterfaceSystemState state) {
        Stage stage = new Stage(new FitViewport(WorldAdapter.VIRTUAL_WIDTH, WorldAdapter.VIRTUAL_HEIGHT));
        state.setStage(stage);
    }
}