package com.emelwerx.world.databags;

import com.badlogic.ashley.core.Component;

public class MonsterComponent extends Component {

    public enum STATE {
        IDLE,
        FLEEING,
        HUNTING
    }

    private  STATE state = STATE.IDLE;

    public MonsterComponent(STATE state){
        this.state = state;
    }
}
