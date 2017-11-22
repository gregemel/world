package com.emelwerx.world.services.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.emitters.RegularEmitter;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.databags.components.ParticleComponent;
import com.emelwerx.world.services.Assets;

import static java.lang.String.format;

public class ParticleComponentFactory {

    public static ParticleComponent create(String name, ParticleSystem particleSystem) {
        Gdx.app.log("ParticleComponentFactory", format("creating particle component: %s", name));

        ParticleComponent particleComponent = new ParticleComponent();
        ParticleEffect originalEffect = getParticleEffect(name, particleSystem);
        particleComponent.setOriginalEffect(originalEffect);

        return particleComponent;
    }

    private static ParticleEffect getParticleEffect(String name, ParticleSystem particleSystem) {
        ParticleEffectLoader.ParticleEffectLoadParameter loadParam
                = new ParticleEffectLoader.ParticleEffectLoadParameter(particleSystem.getBatches());

        if (!Assets.assetManager.isLoaded("data/" + name + ".pfx")) {
            Assets.assetManager.load("data/" + name + ".pfx", ParticleEffect.class, loadParam);
            Assets.assetManager.finishLoading();
        }

        return Assets.assetManager.get("data/" + name + ".pfx");
    }

    public static ParticleEffect createParticleEffect(ModelComponent creatureModelComponent, ParticleComponent particleComponent) {
        ParticleEffect effect = particleComponent.getOriginalEffect().copy();
        ((RegularEmitter) effect.getControllers().first().emitter).setEmissionMode(RegularEmitter.EmissionMode.EnabledUntilCycleEnd);
        effect.setTransform(creatureModelComponent.getInstance().transform);
        effect.scale(3.25f, 1, 1.5f);
        effect.init();
        effect.start();
        return effect;
    }
}
