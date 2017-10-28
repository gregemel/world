package com.deeep.spaceglad.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.deeep.spaceglad.databags.AnimationComponent;
import com.deeep.spaceglad.databags.CharacterComponent;
import com.deeep.spaceglad.databags.EnemyComponent;
import com.deeep.spaceglad.databags.GameWorld;
import com.deeep.spaceglad.Settings;
import com.deeep.spaceglad.UI.GameUI;
import com.deeep.spaceglad.UI.ControllerWidget;
import com.deeep.spaceglad.databags.ModelComponent;
import com.deeep.spaceglad.databags.PlayerComponent;
import com.deeep.spaceglad.databags.StatusComponent;
import com.deeep.spaceglad.services.AnimationService;

public class PlayerSystem extends EntitySystem implements EntityListener, InputProcessor {
    public Entity gun, dome;
    private Entity player;
    private PlayerComponent playerComponent;
    private CharacterComponent characterComponent;
    private ModelComponent modelComponent;
    private GameUI gameUI;
    private final Vector3 tmp = new Vector3();
    private final Camera camera;
    private GameWorld gameWorld;
    private Vector3 rayFrom = new Vector3();
    private Vector3 rayTo = new Vector3();
    private ClosestRayResultCallback rayTestCB;
    private Vector3 translation = new Vector3();
    private Matrix4 ghost = new Matrix4();
    private AnimationService animationService = new AnimationService();

    public PlayerSystem(GameWorld gameWorld, GameUI gameUI, Camera camera) {
        this.camera = camera;
        this.gameWorld = gameWorld;
        this.gameUI = gameUI;
        rayTestCB = new ClosestRayResultCallback(Vector3.Zero, Vector3.Z);
    }

    @Override
    public void addedToEngine(Engine engine) {
        engine.addEntityListener(Family.all(PlayerComponent.class).get(), this);
    }

    @Override
    public void update(float delta) {
        if (player == null) return;
        updateMovement(delta);
        updateStatus();
        checkGameOver();
    }

    private void updateMovement(float delta) {
        float deltaX;
        float deltaY;

        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            deltaX = -ControllerWidget.getWatchVector().x * 1.5f;
            deltaY = ControllerWidget.getWatchVector().y * 1.5f;
        } else {
            deltaX = -Gdx.input.getDeltaX() * 0.5f;
            deltaY = -Gdx.input.getDeltaY() * 0.5f;
        }

        tmp.set(0, 0, 0);
        camera.rotate(camera.up, deltaX);
        tmp.set(camera.direction).crs(camera.up).nor();
        camera.direction.rotate(tmp, deltaY);
        tmp.set(0, 0, 0);

        characterComponent.getCharacterDirection().set(-1, 0, 0).rot(modelComponent.getInstance().transform).nor();
        characterComponent.getWalkDirection().set(0, 0, 0);

        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            if (ControllerWidget.getMovementVector().y > 0) characterComponent.getWalkDirection().add(camera.direction);
            if (ControllerWidget.getMovementVector().y < 0) characterComponent.getWalkDirection().sub(camera.direction);
            if (ControllerWidget.getMovementVector().x < 0) tmp.set(camera.direction).crs(camera.up).scl(-1);
            if (ControllerWidget.getMovementVector().x > 0) tmp.set(camera.direction).crs(camera.up);
            characterComponent.getWalkDirection().add(tmp);
            characterComponent.getWalkDirection().scl(10f * delta);
            characterComponent.getCharacterController().setWalkDirection(characterComponent.getWalkDirection());
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) characterComponent.getWalkDirection().add(camera.direction);
            if (Gdx.input.isKeyPressed(Input.Keys.S)) characterComponent.getWalkDirection().sub(camera.direction);
            if (Gdx.input.isKeyPressed(Input.Keys.A)) tmp.set(camera.direction).crs(camera.up).scl(-1);
            if (Gdx.input.isKeyPressed(Input.Keys.D)) tmp.set(camera.direction).crs(camera.up);
            characterComponent.getWalkDirection().add(tmp);
            characterComponent.getWalkDirection().scl(10f * delta);
            characterComponent.getCharacterController().setWalkDirection(characterComponent.getWalkDirection());
        }

        ghost.set(0, 0, 0, 0);
        translation.set(0, 0, 0);
        translation = new Vector3();
        characterComponent.getGhostObject().getWorldTransform(ghost);   //TODO export this
        ghost.getTranslation(translation);
        modelComponent.getInstance().transform.set(translation.x, translation.y, translation.z, camera.direction.x, camera.direction.y, camera.direction.z, 0);
        camera.position.set(translation.x, translation.y, translation.z);
        camera.update(true);

        dome.getComponent(ModelComponent.class).getInstance().transform.setToTranslation(translation.x, translation.y, translation.z);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            // TODO change this back to 25
            characterComponent.getCharacterController().setJumpSpeed(25);
            characterComponent.getCharacterController().jump();
        }

        if (Gdx.input.justTouched()) {
            fire();
        }
    }

    private void updateStatus() {
        gameUI.healthWidget.setValue(playerComponent.health);
    }


    private void fire() {
        Ray ray = camera.getPickRay(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        rayFrom.set(ray.origin);
        rayTo.set(ray.direction).scl(50f).add(rayFrom);
        rayTestCB.setCollisionObject(null);
        rayTestCB.setClosestHitFraction(1f);
        rayTestCB.setRayFromWorld(rayFrom);
        rayTestCB.setRayToWorld(rayTo);

        gameWorld.getBulletSystem().collisionWorld.rayTest(rayFrom, rayTo, rayTestCB);

        if (rayTestCB.hasHit()) {
            final btCollisionObject obj = rayTestCB.getCollisionObject();
            if (((Entity) obj.userData).getComponent(EnemyComponent.class) != null) {
                if(((Entity) obj.userData).getComponent(StatusComponent.class).alive) {
                    ((Entity) obj.userData).getComponent(StatusComponent.class).setAlive(false);
                    PlayerComponent.score += 100;
                }
            }
        }

        animationService.animate(
            gun.getComponent(AnimationComponent.class), "Armature|shoot", 1, 3);

    }

    private void checkGameOver() {
        if (playerComponent.health <= 0 && !Settings.Paused) {
            Settings.Paused = true;
            gameUI.gameOverWidget.gameOver();
        }
    }

    @Override
    public void entityAdded(Entity entity) {
        player = entity;
        playerComponent = entity.getComponent(PlayerComponent.class);
        characterComponent = entity.getComponent(CharacterComponent.class);
        modelComponent = entity.getComponent(ModelComponent.class);
        //
    }

    @Override
    public void entityRemoved(Entity entity) {
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}