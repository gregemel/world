package com.emelwerx.world.databags;

import com.badlogic.ashley.core.Component;

public class StatusComponent extends Component {

    private boolean alive;
    private boolean running;
    private boolean attacking;
    private float aliveStateTime;
    private AnimationComponent animationComponent;

    private AnimationComponent getAnimationComponent() {
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