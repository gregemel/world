package com.deeep.spaceglad.services;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.deeep.spaceglad.Assets;
import com.deeep.spaceglad.databags.ParticleComponent;

import static java.lang.String.format;

public class ParticleFactory {

    public ParticleComponent create(ParticleSystem particleSystem) {
        Gdx.app.log("ParticleFactory", "creating particle component");


        ParticleComponent particleComponent = new ParticleComponent();
        ParticleEffectLoader.ParticleEffectLoadParameter loadParam = new ParticleEffectLoader.ParticleEffectLoadParameter(particleSystem.getBatches());

        if (!Assets.assetManager.isLoaded("data/dieparticle.pfx")) {
            Assets.assetManager.load("data/dieparticle.pfx", ParticleEffect.class, loadParam);
            Assets.assetManager.finishLoading();
        }

        ParticleEffect originalEffect = Assets.assetManager.get("data/dieparticle.pfx");
        particleComponent.setOriginalEffect(originalEffect);

        return particleComponent;
    }

}
