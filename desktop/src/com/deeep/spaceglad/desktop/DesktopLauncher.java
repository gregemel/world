package com.deeep.spaceglad.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.deeep.spaceglad.WorldCore;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = (int) WorldCore.VIRTUAL_WIDTH;
        config.height = (int) WorldCore.VIRTUAL_HEIGHT;
        new LwjglApplication(new WorldCore(), config); /********/
    }
}