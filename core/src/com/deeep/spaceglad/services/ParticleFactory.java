package com.deeep.spaceglad.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.deeep.spaceglad.Assets;
import com.deeep.spaceglad.databags.ParticleComponent;

public class ParticleFactory {

    public ParticleComponent create(String name, ParticleSystem particleSystem) {
        Gdx.app.log("ParticleFactory", "creating particle component");

        ParticleComponent particleComponent = new ParticleComponent();
        ParticleEffectLoader.ParticleEffectLoadParameter loadParam = new ParticleEffectLoader.ParticleEffectLoadParameter(particleSystem.getBatches());

        if (!Assets.assetManager.isLoaded("data/" + name + ".pfx")) {
            Assets.assetManager.load("data/" + name + ".pfx", ParticleEffect.class, loadParam);
            Assets.assetManager.finishLoading();
        }

        ParticleEffect originalEffect = Assets.assetManager.get("data/" + name + ".pfx");
        particleComponent.setOriginalEffect(originalEffect);

        return particleComponent;
    }

}
