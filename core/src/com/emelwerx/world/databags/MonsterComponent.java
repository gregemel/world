package com.emelwerx.world.databags;

import com.badlogic.ashley.core.Component;

public class MonsterComponent extends Component {

    private  STATE state = STATE.IDLE;
    private boolean alive;
    private boolean running;
    private boolean attacking;
    private float timeSinceDeath;
    private AnimationComponent animationComponent;
    public MonsterComponent(STATE state){
        this.state = state;
    }

    public STATE getState() {
        return state;
    }

    public void setState(STATE state) {
        this.state = state;
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

    public float getTimeSinceDeath() {
        return timeSinceDeath;
    }

    public void setTimeSinceDeath(float timeSinceDeath) {
        this.timeSinceDeath = timeSinceDeath;
    }

    public AnimationComponent getAnimationComponent() {
        return animationComponent;
    }

    public void setAnimationComponent(AnimationComponent animationComponent) {
        this.animationComponent = animationComponent;
    }

    public enum STATE {
        IDLE,
        FLEEING,
        HUNTING
    }
}
