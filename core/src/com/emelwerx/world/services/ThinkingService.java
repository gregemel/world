package com.emelwerx.world.services;

import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.ThoughtComponent;
import com.emelwerx.world.databags.AnimationComponent;
import com.emelwerx.world.databags.MonsterAnimations;

import static java.lang.String.format;

public class ThinkingService {
    public ThoughtComponent create(AnimationComponent animationComponent) {
        Gdx.app.log("ThinkingService", format("create %s", animationComponent.toString()));
        ThoughtComponent thoughtComponent = new ThoughtComponent();
        thoughtComponent.setAnimationComponent(animationComponent);
        thoughtComponent.setAlive(true);
        thoughtComponent.setRunning(true);
        return thoughtComponent;
    }

    public void update(ThoughtComponent thoughtComponent, float delta) {
        if (!thoughtComponent.isAlive()) {
            thoughtComponent.setAliveStateTime(thoughtComponent.getAliveStateTime() + delta);
        }
    }

    public void setAlive(ThoughtComponent thoughtComponent, AnimationComponent animationComponent, boolean alive) {
        thoughtComponent.setAlive(alive);
        AnimationService animationService = new AnimationService();
        animationService.animate(animationComponent,
                MonsterAnimations.getId(),
                MonsterAnimations.getOffsetDeath2(),
                MonsterAnimations.getDurationDeath2(),
                1, 3);
    }
}
