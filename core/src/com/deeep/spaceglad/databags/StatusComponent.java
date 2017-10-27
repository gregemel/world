package com.deeep.spaceglad.databags;

import com.badlogic.ashley.core.Component;

public class StatusComponent extends Component {

    public boolean alive, running, attacking;
    public float aliveStateTime;
    private AnimationComponent animationComponent;

    public AnimationComponent getAnimationComponent() {
        return animationComponent;
    }

    public void setAnimationComponent(AnimationComponent animationComponent) {
        this.animationComponent = animationComponent;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public float getAliveStateTime() {
        return aliveStateTime;
    }

    public void setAliveStateTime(float aliveStateTime) {
        this.aliveStateTime = aliveStateTime;
    }

}