package com.deeep.spaceglad.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch;
import com.badlogic.gdx.math.Vector3;
import com.deeep.spaceglad.WorldGDXAdapter;
import com.deeep.spaceglad.Settings;
import com.deeep.spaceglad.databags.AnimationComponent;
import com.deeep.spaceglad.databags.EnemyComponent;
import com.deeep.spaceglad.databags.GunComponent;
import com.deeep.spaceglad.databags.ModelComponent;
import com.deeep.spaceglad.databags.PlayerComponent;
import com.deeep.spaceglad.services.AnimationService;


public class RenderSystem extends EntitySystem {

    private static final float FOV = 67F;
    private ImmutableArray<Entity> entities;
    private ModelBatch batch;
    private Environment environment;
    private DirectionalShadowLight shadowLight;
    public Entity gun;
    private Vector3 position;
    private AnimationService animationService = new AnimationService();


    public PerspectiveCamera perspectiveCamera;
    public PerspectiveCamera gunCamera;
    public static ParticleSystem particleSystem;

    public RenderSystem() {
        perspectiveCamera = new PerspectiveCamera(FOV, WorldGDXAdapter.VIRTUAL_WIDTH, WorldGDXAdapter.VIRTUAL_HEIGHT);
        perspectiveCamera.far = 10000f;

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1f));
        shadowLight = new DirectionalShadowLight(1024 * 5, 1024 * 5, 200f, 200f, 1f, 300f);
        shadowLight.set(0.8f, 0.8f, 0.8f, 0, -0.1f, 0.1f);
        environment.add(shadowLight);
        environment.shadowMap = shadowLight;

        batch = new ModelBatch();

        gunCamera = new PerspectiveCamera(FOV, WorldGDXAdapter.VIRTUAL_WIDTH, WorldGDXAdapter.VIRTUAL_HEIGHT);
        gunCamera.far = 100f;

        position = new Vector3();

        particleSystem = ParticleSystem.get();

        BillboardParticleBatch billboardParticleBatch = new BillboardParticleBatch();
        billboardParticleBatch.setCamera(perspectiveCamera);

        particleSystem.add(billboardParticleBatch);
    }

    public void addedToEngine(Engine e) {
        entities = e.getEntitiesFor(Family.all(ModelComponent.class).get());
    }

    public void update(float delta) {
        drawShadows(delta);
        drawModels();
    }

    private boolean isVisible(Camera cam, final ModelInstance instance) {
        return cam.frustum.pointInFrustum(instance.transform.getTranslation(position));
    }

    private void drawShadows(float delta) {

        shadowLight.begin(Vector3.Zero, perspectiveCamera.direction);

        batch.begin(shadowLight.getCamera());

        for (int x = 0; x < entities.size(); x++) {

            if (entities.get(x).getComponent(PlayerComponent.class) != null || entities.get(x).getComponent(EnemyComponent.class) != null) {

                ModelComponent mod = entities.get(x).getComponent(ModelComponent.class);

                if (isVisible(perspectiveCamera, mod.getInstance())) {
                    batch.render(mod.getInstance());
                }
            }

            if (entities.get(x).getComponent(AnimationComponent.class) != null & !Settings.Paused) {
                animationService.update(entities.get(x).getComponent(AnimationComponent.class), delta);
            }
        }

        batch.end();
        shadowLight.end();
    }

    private void drawModels() {

        batch.begin(perspectiveCamera);

        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getComponent(GunComponent.class) == null) {
                ModelComponent mod = entities.get(i).getComponent(ModelComponent.class);
                batch.render(mod.getInstance(), environment);
            }
        }
        batch.end();

        renderParticleEffects();

        drawGun();
    }

    private void renderParticleEffects() {

        batch.begin(perspectiveCamera);
        particleSystem.update(); // technically not necessary for rendering
        particleSystem.begin();
        particleSystem.draw();
        particleSystem.end();
        batch.render(particleSystem);
        batch.end();
    }

    private void drawGun() {
        Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);
        batch.begin(gunCamera);
        batch.render(gun.getComponent(ModelComponent.class).getInstance());
        batch.end();
    }

    public void resize(int width, int height) {
        perspectiveCamera.viewportHeight = height;
        perspectiveCamera.viewportWidth = width;
        gunCamera.viewportHeight = height;
        gunCamera.viewportWidth = width;
    }

    public void dispose() {
        batch.dispose();
        batch = null;
    }
}