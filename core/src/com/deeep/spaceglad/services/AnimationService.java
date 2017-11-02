package com.deeep.spaceglad.services;


import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.deeep.spaceglad.databags.AnimationComponent;

public class AnimationService {

    public AnimationComponent create(ModelInstance modelInstance) {
        AnimationController animationController = new AnimationController(modelInstance);
        animationController.allowSameAnimation = true;
        AnimationComponent animationComponent = new AnimationComponent();
        animationComponent.setAnimationController(animationController);
        return animationComponent;
    }

    public void animate(AnimationComponent animationComponent, final String id, final int loops, final int speed) {
        animationComponent.getAnimationController().animate(id, loops, speed, null, 0);
    }

    public void animate(AnimationComponent animationComponent, String id, float offset, float duration, int loopCount, int speed) {
        animationComponent.getAnimationController().animate(id, offset, duration, loopCount, speed, null, 0);
    }

    public void update(AnimationComponent animationComponent, float delta) {
        animationComponent.getAnimationController().update(delta);
    }

}
