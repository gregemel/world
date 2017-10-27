package com.deeep.spaceglad.services;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.math.Matrix4;
import com.deeep.spaceglad.databags.ModelComponent;

public class ModelService {

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
