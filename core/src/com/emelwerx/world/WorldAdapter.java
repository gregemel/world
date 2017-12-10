package com.emelwerx.world;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.emelwerx.world.services.ui.screens.MainMenuScreen;
import com.emelwerx.world.services.Assets;
import com.emelwerx.world.services.Settings;

import static java.lang.String.format;

public class WorldAdapter extends ApplicationAdapter {
    public static final float VIRTUAL_WIDTH = 960;
    public static final float VIRTUAL_HEIGHT = 540;
    private Screen screen;

    @Override
    public void create() {
        Gdx.app.log("WorldAdapter", "load");
        Assets.init();
        Settings.load();
        Gdx.input.setCatchBackKey(true);
        MainMenuScreen firstScreen = new MainMenuScreen(this);
        setScreen(firstScreen);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        screen.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("WorldAdapter", "resize");
        screen.resize(width, height);
    }

    public void setScreen(Screen screen) {
        Gdx.app.log("WorldAdapter", format("setScreen(%s)", screen.toString()));
        if (this.screen != null) {
            this.screen.hide();
            this.screen.dispose();
        }
        this.screen = screen;
        this.screen.show();
        this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void dispose() {
        Gdx.app.log("WorldAdapter", "dispose");
        Settings.save();
        Assets.dispose();
    }
}