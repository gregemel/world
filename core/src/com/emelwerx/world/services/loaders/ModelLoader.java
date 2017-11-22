package com.emelwerx.world.services.loaders;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.utils.TextureProvider;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.UBJsonReader;

import java.util.Locale;

import static java.lang.String.format;

public class ModelLoader {
    public static Model load(String name) {
        Gdx.app.log("ModelLoader", format("load %s", name));
        com.badlogic.gdx.assets.loaders.ModelLoader<?> modelLoader = new G3dModelLoader(new JsonReader());
        ModelData modelData = modelLoader.loadModelData(Gdx.files.internal("data/" + name));
        return new Model(modelData, new TextureProvider.FileTextureProvider());
    }

    public static Model load(String name, float scalar) {
        Gdx.app.log("ModelLoader", format(Locale.US,"load %s, %f", name, scalar));

        Model model = load(name);
        for (Node node : model.nodes) {
            node.scale.scl(scalar);
        }
        model.calculateTransforms();

        return model;
    }

    public static Model loadSky(String name) {
        Gdx.app.log("ModelLoader", format("loadSky %s", name));
        G3dModelLoader modelLoader = new G3dModelLoader(new UBJsonReader());
        return modelLoader.loadModel(Gdx.files.getFileHandle("data/" + name, Files.FileType.Internal));
    }

}
