package com.emelwerx.world.databags;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;

import java.util.Locale;

public class MonsterComponent extends Component {

    private MONSTER_STATE monsterState = MONSTER_STATE.IDLE;
    private boolean alive;
    private boolean running;
    private boolean attacking;
    private float timeSinceDeath;
    private AnimationComponent animationComponent;
    public MonsterComponent(MONSTER_STATE monsterState){
        this.monsterState = monsterState;
    }

    public MONSTER_STATE getMonsterState() {
        return monsterState;
    }

    public void setMonsterState(MONSTER_STATE monsterState) {
        Gdx.app.log("MonsterComponent", String.format(Locale.US,"setMonsterState (%s, %s)", this.toString(), monsterState.toString()));
        this.monsterState = monsterState;
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

    public enum MONSTER_STATE {
        IDLE,
        DYING,
        HUNTING
    }
}
