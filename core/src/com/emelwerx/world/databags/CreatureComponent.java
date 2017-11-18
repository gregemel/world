package com.emelwerx.world.databags;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;

import java.util.Locale;

public class CreatureComponent extends Component {

    private CREATURE_STATE creatureState = CREATURE_STATE.IDLE;
    private float timeSinceDeath;
    private AnimationComponent animationComponent;
    public CreatureComponent(CREATURE_STATE creatureState){
        this.creatureState = creatureState;
    }

    public CREATURE_STATE getCreatureState() {
        return creatureState;
    }

    public void setCreatureState(CREATURE_STATE creatureState) {
        Gdx.app.log("CreatureComponent", String.format(Locale.US,"setCreatureState: %s, %s", this.toString(), creatureState.toString()));
        this.creatureState = creatureState;
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

    public enum CREATURE_STATE {
        IDLE,
        DYING,
        HUNTING
    }
}
