package com.emelwerx.world.services.updaters;

import com.badlogic.ashley.core.Entity;
import com.emelwerx.world.databags.components.AnimationComponent;
import com.emelwerx.world.services.Settings;

public class AnimationUpdater {
    public static void update(Entity entity, float delta) {
        AnimationComponent animationComponent = entity.getComponent(AnimationComponent.class);
        boolean hasAnimation = animationComponent != null & !Settings.isPaused();
        if (hasAnimation) {
            animationComponent.getAnimationController().update(delta);
        }
    }

}
