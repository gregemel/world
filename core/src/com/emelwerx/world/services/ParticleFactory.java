package com.emelwerx.world.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.emitters.RegularEmitter;
import com.emelwerx.world.Assets;
import com.emelwerx.world.databags.ModelComponent;
import com.emelwerx.world.databags.ParticleComponent;

public class ParticleFactory {

    public static ParticleComponent create(String name, ParticleSystem particleSystem) {
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

    public static ParticleEffect createParticleEffect(ModelComponent modelComponent, ParticleComponent particleComponent) {
        ParticleEffect effect = particleComponent.getOriginalEffect().copy();
        ((RegularEmitter) effect.getControllers().first().emitter).setEmissionMode(RegularEmitter.EmissionMode.EnabledUntilCycleEnd);
        effect.setTransform(modelComponent.getInstance().transform);
        effect.scale(3.25f, 1, 1.5f);
        effect.init();
        effect.start();
        return effect;
    }
}
