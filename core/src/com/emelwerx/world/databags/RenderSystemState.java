package com.emelwerx.world.databags;


import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.math.Vector3;
import com.emelwerx.world.services.AnimationComponentFactory;

public class RenderSystemState {

    private static final float FOV = 67F;
    private ImmutableArray<Entity> entities;
    private ModelBatch batch;
    private Environment environment;
    private DirectionalShadowLight shadowLight;
    private Entity gun;
    private Vector3 position;
    private AnimationComponentFactory animationComponentFactory = new AnimationComponentFactory();
    private PerspectiveCamera perspectiveCamera;
    private PerspectiveCamera gunCamera;
    private ParticleSystem particleSystem;

    public float getFOV() {
        return FOV;
    }

    public ImmutableArray<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ImmutableArray<Entity> entities) {
        this.entities = entities;
    }

    public ModelBatch getBatch() {
        return batch;
    }

    public void setBatch(ModelBatch batch) {
        this.batch = batch;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public DirectionalShadowLight getShadowLight() {
        return shadowLight;
    }

    public void setShadowLight(DirectionalShadowLight shadowLight) {
        this.shadowLight = shadowLight;
    }

    public Entity getGun() {
        return gun;
    }

    public void setPlayersVisibleItem(Entity gun) {
        this.gun = gun;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public AnimationComponentFactory getAnimationComponentFactory() {
        return animationComponentFactory;
    }

    public void setAnimationComponentFactory(AnimationComponentFactory animationComponentFactory) {
        this.animationComponentFactory = animationComponentFactory;
    }

    public PerspectiveCamera getPerspectiveCamera() {
        return perspectiveCamera;
    }

    public void setPerspectiveCamera(PerspectiveCamera perspectiveCamera) {
        this.perspectiveCamera = perspectiveCamera;
    }

    public PerspectiveCamera getGunCamera() {
        return gunCamera;
    }

    public void setGunCamera(PerspectiveCamera gunCamera) {
        this.gunCamera = gunCamera;
    }

    public ParticleSystem getParticleSystem() {
        return particleSystem;
    }

    public void setParticleSystem(ParticleSystem particleSystem) {
        this.particleSystem = particleSystem;
    }
}
