package com.deeep.spaceglad.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.utils.TextureProvider;
import com.badlogic.gdx.utils.JsonReader;
import com.deeep.spaceglad.databags.AnimationComponent;
import com.deeep.spaceglad.databags.ItemComponent;
import com.deeep.spaceglad.databags.ModelComponent;

public class PlayerItemFactory {
    public Entity create(String name, float x, float y, float z) {

        ModelService modelService = new ModelService();
        Model model = modelService.loadModel(name);
        ModelComponent modelComponent = modelService.create(model, x, y, z);

        modelComponent.getInstance().transform.rotate(0, 1, 0, 180);

        Entity gunEntity = new Entity();
        gunEntity.add(modelComponent);
        gunEntity.add(new ItemComponent());
        AnimationService animationService = new AnimationService();
        AnimationComponent animationComponent = animationService.create(modelComponent.getInstance());
        gunEntity.add(animationComponent);

        return gunEntity;
    }
}
