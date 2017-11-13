package com.emelwerx.world.services;

import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.AnimationComponent;
import com.emelwerx.world.databags.MonsterComponent;

import static java.lang.String.format;

public class MonsterComponentFactory {
    public static MonsterComponent create(AnimationComponent animationComponent) {
        Gdx.app.log("MonsterComponentFactory", format("init %s", animationComponent.toString()));
        MonsterComponent monsterComponent = new MonsterComponent(MonsterComponent.MONSTER_STATE.IDLE);
        monsterComponent.setAnimationComponent(animationComponent);
        monsterComponent.setTimeSinceDeath(0f);
        return monsterComponent;
    }
}
