package com.deeep.spaceglad.databags;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

public class AnimationComponent extends Component {
    private AnimationController animationController;

    public AnimationController getAnimationController() {
        return animationController;
    }

    public void setAnimationController(AnimationController animationController) {
        this.animationController = animationController;
    }
}