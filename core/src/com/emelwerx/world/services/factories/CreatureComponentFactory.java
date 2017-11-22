package com.emelwerx.world.services.factories;

import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.components.AnimationComponent;
import com.emelwerx.world.databags.components.CreatureComponent;

import static java.lang.String.format;

public class CreatureComponentFactory {
    public static CreatureComponent create(AnimationComponent animationComponent) {
        Gdx.app.log("CreatureComponentFactory", format("init %s", animationComponent.toString()));
        CreatureComponent creatureComponent = new CreatureComponent(CreatureComponent.CREATURE_STATE.IDLE);
        creatureComponent.setAnimationComponent(animationComponent);
        creatureComponent.setTimeSinceDeath(0f);
        return creatureComponent;
    }
}
