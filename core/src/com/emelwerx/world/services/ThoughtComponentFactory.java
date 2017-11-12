package com.emelwerx.world.services;

import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.AnimationComponent;
import com.emelwerx.world.databags.ThoughtComponent;

import static java.lang.String.format;

public class ThoughtComponentFactory {
    public static ThoughtComponent create(AnimationComponent animationComponent) {
        Gdx.app.log("ThoughtComponentFactory", format("create %s", animationComponent.toString()));
        ThoughtComponent thoughtComponent = new ThoughtComponent();
        thoughtComponent.setAnimationComponent(animationComponent);
        thoughtComponent.setAlive(true);
        thoughtComponent.setRunning(true);
        return thoughtComponent;
    }
}
