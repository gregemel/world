package com.deeep.spaceglad.databags;

import com.badlogic.ashley.core.Component;

public class PlayerComponent extends Component {
    public static int score;
    public float health;

    public PlayerComponent() {
        health = 100;
        score = 0;
    }
}