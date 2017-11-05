package com.emelwerx.world.services;

import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.StatusComponent;
import com.emelwerx.world.databags.AnimationComponent;
import com.emelwerx.world.databags.MonsterAnimations;

import static java.lang.String.format;

public class StatusService {
    public StatusComponent create(AnimationComponent animationComponent) {
        Gdx.app.log("StatusService", format("create %s", animationComponent.toString()));
        StatusComponent statusComponent = new StatusComponent();
        statusComponent.setAnimationComponent(animationComponent);
        statusComponent.setAlive(true);
        statusComponent.setRunning(true);
        return statusComponent;
    }

    public void update(StatusComponent statusComponent, float delta) {
        if (!statusComponent.isAlive()) {
            statusComponent.setAliveStateTime(statusComponent.getAliveStateTime() + delta);
        }
    }

    public void setAlive(StatusComponent statusComponent, AnimationComponent animationComponent, boolean alive) {
        statusComponent.setAlive(alive);
        AnimationService animationService = new AnimationService();
        animationService.animate(animationComponent,
                MonsterAnimations.getId(),
                MonsterAnimations.getOffsetDeath2(),
                MonsterAnimations.getDurationDeath2(),
                1, 3);
    }
}
