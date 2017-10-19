package com.deeep.spaceglad.components;

import com.badlogic.ashley.core.Component;

public class EnemyComponent extends Component {

    public enum STATE {
        IDLE,
        FLEEING,
        HUNTING
    }

    private  STATE state = STATE.IDLE;

    public EnemyComponent(STATE state){
        this.state = state;
    }
}
