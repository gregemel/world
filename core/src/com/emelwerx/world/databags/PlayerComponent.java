package com.emelwerx.world.databags;

import com.badlogic.ashley.core.Component;

public class PlayerComponent extends Component {
    private static int score;
    private float health;

    public PlayerComponent() {
        health = 100;
        score = 0;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int value) {
        score = value;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }
}