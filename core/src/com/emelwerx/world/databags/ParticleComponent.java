package com.emelwerx.world.databags;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;

public class ParticleComponent extends Component {
    private String name;
    private ParticleEffect originalEffect;
    private boolean used = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParticleEffect getOriginalEffect() {
        return originalEffect;
    }

    public void setOriginalEffect(ParticleEffect originalEffect) {
        this.originalEffect = originalEffect;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}