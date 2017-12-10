package com.emelwerx.world.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.emelwerx.world.WorldAdapter;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = (int) WorldAdapter.VIRTUAL_WIDTH;
        config.height = (int) WorldAdapter.VIRTUAL_HEIGHT;
        new LwjglApplication(new WorldAdapter(), config);
    }
}