package com.emelwerx.world.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.emelwerx.world.databags.AnimationComponent;
import com.emelwerx.world.databags.ItemComponent;
import com.emelwerx.world.databags.ModelComponent;

import static java.lang.String.format;

public class PlayerItemFactory {
    public static Entity create(String name, float x, float y, float z) {
        Gdx.app.log("PlayerItemFactory", format("creating entity %s, %f, %f, %f", name, x, y, z));

        ModelService modelService = new ModelService();
        ModelLoader modelLoader = new ModelLoader();
        Model model = modelLoader.loadModel(name);
        ModelComponent modelComponent = modelService.create(model, x, y, z);

        modelComponent.getInstance().transform.rotate(0, 1, 0, 180);

        Entity itemEntity = new Entity();
        itemEntity.add(modelComponent);
        itemEntity.add(new ItemComponent());
        AnimationService animationService = new AnimationService();
        AnimationComponent animationComponent = animationService.create(modelComponent.getInstance());
        itemEntity.add(animationComponent);

        return itemEntity;
    }
}
