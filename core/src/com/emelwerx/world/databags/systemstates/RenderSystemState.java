package com.emelwerx.world.databags.systemstates;


import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.math.Vector3;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.services.factories.AnimationComponentFactory;

public class RenderSystemState {

    //fov should be a configuration setting
    private static final float FOV = 67F;
    private ImmutableArray<Entity> entities;
    private ModelBatch batch;
    private Environment environment;
    private DirectionalShadowLight shadowLight;
    private Entity playerItem;
    private Vector3 position;
    private PerspectiveCamera worldPerspectiveCamera;
    private PerspectiveCamera playerItemCamera;
    private ParticleSystem particleSystem;
    private World world;

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

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

    public Entity getPlayerItem() {
        return playerItem;
    }

    public void setPlayersVisibleItem(Entity item) {
        this.playerItem = item;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public PerspectiveCamera getWorldPerspectiveCamera() {
        return worldPerspectiveCamera;
    }

    public void setWorldPerspectiveCamera(PerspectiveCamera worldPerspectiveCamera) {
        this.worldPerspectiveCamera = worldPerspectiveCamera;
    }

    public PerspectiveCamera getPlayerItemCamera() {
        return playerItemCamera;
    }

    public void setPlayerItemCamera(PerspectiveCamera playerItemCamera) {
        this.playerItemCamera = playerItemCamera;
    }

    public ParticleSystem getParticleSystem() {
        return particleSystem;
    }

    public void setParticleSystem(ParticleSystem particleSystem) {
        this.particleSystem = particleSystem;
    }
}
