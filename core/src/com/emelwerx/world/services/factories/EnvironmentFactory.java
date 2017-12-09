package com.emelwerx.world.services.factories;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.emelwerx.world.databags.systemstates.RenderSystemState;

public class EnvironmentFactory {

    public static void create(RenderSystemState renderSystemState) {
        Environment environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1f));
        DirectionalShadowLight shadowLight = new DirectionalShadowLight(
                1024 * 5,
                1024 * 5,
                200f,
                200f,
                1f,
                300f);
        shadowLight.set(0.8f, 0.8f, 0.8f, 0, -0.1f, 0.1f);
        environment.add(shadowLight);
        environment.shadowMap = shadowLight;

        renderSystemState.setShadowLight(shadowLight);
        renderSystemState.setEnvironment(environment);
    }
}