package com.emelwerx.world.services;

import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.CreatureComponent;
import com.emelwerx.world.databags.PlayerComponent;

public class CreaturePlayerHitDamager {
    public static void collide(PlayerComponent playerComponent, CreatureComponent creatureComponent) {
        Gdx.app.log("CreaturePlayerHitDamager", "OUCH!");
        playerComponent.setHealth(playerComponent.getHealth() - 10);
        creatureComponent.setCreatureState(CreatureComponent.CREATURE_STATE.DYING);
    }
}
