package com.deeep.spaceglad.services;

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
import com.deeep.spaceglad.databags.ModelComponent;

public class ModelService {

    public Model loadModel(String name) {
        ModelLoader<?> modelLoader = new G3dModelLoader(new JsonReader());
        ModelData modelData = modelLoader.loadModelData(Gdx.files.internal("data/" + name + ".g3dj"));
        return new Model(modelData, new TextureProvider.FileTextureProvider());
    }

    public Model loadModel(String name, float scalar) {
        ModelLoader<?> modelLoader = new G3dModelLoader(new JsonReader());
        ModelData modelData = modelLoader.loadModelData(Gdx.files.internal("data/" + name + ".g3dj"));
        Model model = new Model(modelData, new TextureProvider.FileTextureProvider());

        for (Node node : model.nodes) {
            node.scale.scl(scalar);
        }

        model.calculateTransforms();

        return model;
    }


    public ModelComponent create(Model model, float x, float y, float z) {
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
