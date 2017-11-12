package com.emelwerx.world;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.emelwerx.world.screens.MainMenuScreen;
import com.emelwerx.world.services.Assets;
import com.emelwerx.world.services.Settings;

public class WorldCore extends ApplicationAdapter {
    public static final float VIRTUAL_WIDTH = 960;
    public static final float VIRTUAL_HEIGHT = 540;
    private Screen screen;

    @Override
    public void create() {
        Gdx.app.log("WorldCore", "create");
        new Assets();
        Settings.load();
        Gdx.input.setCatchBackKey(true);
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        screen.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("WorldCore", "resize");
        screen.resize(width, height);
    }

    public void setScreen(Screen screen) {
        if (this.screen != null) {
            this.screen.hide();
            this.screen.dispose();
        }
        this.screen = screen;
        if (this.screen != null) {
            this.screen.show();
            this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    @Override
    public void dispose() {
        Gdx.app.log("WorldCore", "dispose");
        Settings.save();
        Assets.dispose();
    }
}