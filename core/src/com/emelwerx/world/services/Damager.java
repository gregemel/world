package com.emelwerx.world.services;

import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.components.CreatureComponent;
import com.emelwerx.world.databags.components.PlayerComponent;

import static java.lang.String.format;

public class Damager {
    public static void collideWithCreature(PlayerComponent playerComponent, CreatureComponent creatureComponent) {
        Gdx.app.log("Damager", "OUCH!");
        playerComponent.setHealth(playerComponent.getHealth() - 10);
        creatureComponent.setCreatureState(CreatureComponent.CREATURE_STATE.DYING);
    }

    public static void shootCreature(CreatureComponent creatureComponent) {
        if(creatureComponent.getCreatureState() != CreatureComponent.CREATURE_STATE.DYING) {
            Gdx.app.log("Damager", format("HIT creature %s", creatureComponent.toString()));
            creatureComponent.setCreatureState(CreatureComponent.CREATURE_STATE.DYING);
            PlayerComponent.setScore(PlayerComponent.getScore() + 100);
        } else {
            Gdx.app.log("Damager", format("you hit a dying creature %s", creatureComponent.toString()));
        }
    }
}
