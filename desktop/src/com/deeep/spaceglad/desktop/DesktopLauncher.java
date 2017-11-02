package com.deeep.spaceglad.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.deeep.spaceglad.WorldGDXAdapter;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = (int) WorldGDXAdapter.VIRTUAL_WIDTH;
        config.height = (int) WorldGDXAdapter.VIRTUAL_HEIGHT;
        new LwjglApplication(new WorldGDXAdapter(), config); /********/
    }
}