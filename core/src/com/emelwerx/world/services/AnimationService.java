package com.emelwerx.world.services;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.emelwerx.world.databags.AnimationComponent;

import static java.lang.String.format;

public class AnimationService {

    public static AnimationComponent create(ModelInstance modelInstance) {
        Gdx.app.log("AnimationService", format("creating animation component %s", modelInstance.toString()));
        AnimationController animationController = new AnimationController(modelInstance);
        animationController.allowSameAnimation = true;
        AnimationComponent animationComponent = new AnimationComponent();
        animationComponent.setAnimationController(animationController);
        return animationComponent;
    }

    public static void animate(AnimationComponent animationComponent, final String id, final int loops, final int speed) {
        animationComponent.getAnimationController().animate(id, loops, speed, null, 0);
    }

    public static void animate(AnimationComponent animationComponent, String id, float offset, float duration, int loopCount, int speed) {
        animationComponent.getAnimationController().animate(id, offset, duration, loopCount, speed, null, 0);
    }

    public static void update(AnimationComponent animationComponent, float delta) {
        animationComponent.getAnimationController().update(delta);
    }

}
