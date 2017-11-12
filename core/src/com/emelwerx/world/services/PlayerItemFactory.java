package com.emelwerx.world.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.emelwerx.world.databags.AnimationComponent;
import com.emelwerx.world.databags.ItemComponent;
import com.emelwerx.world.databags.ModelComponent;

import java.util.Locale;

import static java.lang.String.format;

public class PlayerItemFactory {
    public static Entity create(String name, float x, float y, float z) {
        Gdx.app.log("PlayerItemFactory", format(Locale.US,"creating entity %s, %f, %f, %f", name, x, y, z));

        Model itemModel = ModelLoader.loadModel(name);
        ModelComponent itemModelComponent = ModelComponentFactory.create(itemModel, x, y, z);

        itemModelComponent.getInstance().transform.rotate(0, 1, 0, 180);

        Entity itemEntity = new Entity();
        itemEntity.add(itemModelComponent);
        itemEntity.add(new ItemComponent());

        AnimationComponent itemAnimationComponent = AnimationComponentFactory.create(itemModelComponent.getInstance());
        itemEntity.add(itemAnimationComponent);

        return itemEntity;
    }
}
