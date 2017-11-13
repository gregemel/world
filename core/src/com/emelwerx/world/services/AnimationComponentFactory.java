package com.emelwerx.world.services;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.emelwerx.world.databags.AnimationComponent;

import static java.lang.String.format;

public class AnimationComponentFactory {

    public static AnimationComponent create(ModelInstance modelInstance) {
        Gdx.app.log("AnimationComponentFactory", format("creating animation component %s", modelInstance.toString()));
        AnimationController animationController = new AnimationController(modelInstance);
        animationController.allowSameAnimation = true;
        AnimationComponent animationComponent = new AnimationComponent();
        animationComponent.setAnimationController(animationController);
        return animationComponent;
    }
}
