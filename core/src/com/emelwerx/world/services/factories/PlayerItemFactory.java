package com.emelwerx.world.services.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.emelwerx.world.databags.components.ItemComponent;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.services.loaders.ModelLoader;

import java.util.Locale;

import static java.lang.String.format;

public class PlayerItemFactory {
    public static Entity create(String name, float x, float y, float z) {
        Gdx.app.log("PlayerItemFactory", format(Locale.US,"creating entity %s, %f, %f, %f", name, x, y, z));

        Entity itemEntity = new Entity();
        attachModel(name, x, y, z, itemEntity);
        return itemEntity;
    }

    private static void attachModel(String name, float x, float y, float z, Entity itemEntity) {
        Model itemModel = ModelLoader.load(name + ".g3dj");
        ModelComponent itemModelComponent = ModelComponentFactory.create(itemModel, x, y, z);
        itemModelComponent.getInstance().transform.rotate(0, 1, 0, 180);
        itemEntity.add(itemModelComponent);
        itemEntity.add(new ItemComponent());
        itemEntity.add(AnimationComponentFactory.create(itemModelComponent.getInstance()));
    }
}
