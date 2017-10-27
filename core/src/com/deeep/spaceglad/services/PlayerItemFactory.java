package com.deeep.spaceglad.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.utils.TextureProvider;
import com.badlogic.gdx.utils.JsonReader;
import com.deeep.spaceglad.components.AnimationComponent;
import com.deeep.spaceglad.components.GunComponent;
import com.deeep.spaceglad.components.ModelComponent;

public class PlayerItemFactory {
    public static Entity create(String name, float x, float y, float z) {

        ModelLoader<?> modelLoader = new G3dModelLoader(new JsonReader());
        ModelData modelData = modelLoader.loadModelData(Gdx.files.internal("data/" + name + ".g3dj"));
        Model model = new Model(modelData, new TextureProvider.FileTextureProvider());

        ModelComponent modelComponent = new ModelComponent(model, x, y, z);
        modelComponent.instance.transform.rotate(0, 1, 0, 180);

        Entity gunEntity = new Entity();
        gunEntity.add(modelComponent);
        gunEntity.add(new GunComponent());
        gunEntity.add(new AnimationComponent(modelComponent.instance));

        return gunEntity;
    }
}
