package com.deeep.spaceglad.services;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.utils.TextureProvider;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.UBJsonReader;
import com.deeep.spaceglad.databags.ModelComponent;

import java.util.Locale;

import static java.lang.String.format;

public class ModelService {

    public Model loadModel(String name) {
        Gdx.app.log("ModelService", format("loadModel %s", name));
        ModelLoader<?> modelLoader = new G3dModelLoader(new JsonReader());
        ModelData modelData = modelLoader.loadModelData(Gdx.files.internal("data/" + name + ".g3dj"));
        return new Model(modelData, new TextureProvider.FileTextureProvider());
    }

    public Model loadModel(String name, float scalar) {
        Gdx.app.log("ModelService", format(Locale.US,"loadModel %s, %f", name, scalar));

        Model model = loadModel(name);
        for (Node node : model.nodes) {
            node.scale.scl(scalar);
        }
        model.calculateTransforms();

        return model;
    }

    public Model getSkyModel(String name) {
        Gdx.app.log("ModelService", format("getSkyModel %s", name));
        G3dModelLoader modelLoader = new G3dModelLoader(new UBJsonReader());
        return modelLoader.loadModel(Gdx.files.getFileHandle("data/" + name + ".g3db", Files.FileType.Internal));
    }

    public ModelComponent create(Model model, float x, float y, float z) {
        Gdx.app.log("ModelService", format(Locale.US, "create model component %s, %f, %f, %f", model.toString(), x, y, z));
        ModelComponent modelComponent = new ModelComponent();
        Matrix4 matrix4 = new Matrix4();
        modelComponent.setMatrix4(matrix4);
        modelComponent.setModel(model);
        modelComponent.setInstance(new ModelInstance(model, matrix4.setToTranslation(x, y, z)));
        return modelComponent;
    }

    public void update(ModelComponent modelComponent, float delta) {
        BlendingAttribute blendingAttribute = modelComponent.getBlendingAttribute();
        if (blendingAttribute != null) {
            blendingAttribute.opacity = blendingAttribute.opacity - delta / 3;
        }
    }
}
