package com.emelwerx.world.databags;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.emelwerx.world.UI.GameUI;
import com.emelwerx.world.services.AnimationService;

public class PlayerSystemState {
    private Vector3 tmp = new Vector3();
    private Camera camera;
    private Entity itemEntity;
    private Entity skyEntity;
    private Entity playerEntity;
    private PlayerComponent playerComponent;
    private CharacterComponent characterComponent;
    private ModelComponent modelComponent;
    private GameUI gameUI;
    private World gameWorld;
    private Vector3 rayFrom = new Vector3();
    private Vector3 rayTo = new Vector3();
    private ClosestRayResultCallback rayTestCB;
    private Vector3 translation = new Vector3();
    private Matrix4 ghost = new Matrix4();
    private AnimationService animationService = new AnimationService();
    private float jumpForce = 25f;

    public float getJumpForce() {
        return jumpForce;
    }

    public void setJumpForce(float jumpForce) {
        this.jumpForce = jumpForce;
    }

    public Vector3 getTmp() {
        return tmp;
    }

    public void setTmp(Vector3 tmp) {
        this.tmp = tmp;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Entity getItem() {
        return itemEntity;
    }

    public void setItemEntity(Entity itemEntity) {
        this.itemEntity = itemEntity;
    }

    public Entity getSkyEntity() {
        return skyEntity;
    }

    public void setSkyEntity(Entity skyEntity) {
        this.skyEntity = skyEntity;
    }

    public Entity getPlayerEntity() {
        return playerEntity;
    }

    public void setPlayerEntity(Entity playerEntity) {
        this.playerEntity = playerEntity;
    }

    public PlayerComponent getPlayerComponent() {
        return playerComponent;
    }

    public void setPlayerComponent(PlayerComponent playerComponent) {
        this.playerComponent = playerComponent;
    }

    public CharacterComponent getCharacterComponent() {
        return characterComponent;
    }

    public void setCharacterComponent(CharacterComponent characterComponent) {
        this.characterComponent = characterComponent;
    }

    public ModelComponent getModelComponent() {
        return modelComponent;
    }

    public void setModelComponent(ModelComponent modelComponent) {
        this.modelComponent = modelComponent;
    }

    public GameUI getGameUI() {
        return gameUI;
    }

    public void setGameUI(GameUI gameUI) {
        this.gameUI = gameUI;
    }

    public World getGameWorld() {
        return gameWorld;
    }

    public void setGameWorld(World gameWorld) {
        this.gameWorld = gameWorld;
    }

    public Vector3 getRayFrom() {
        return rayFrom;
    }

    public void setRayFrom(Vector3 rayFrom) {
        this.rayFrom = rayFrom;
    }

    public Vector3 getRayTo() {
        return rayTo;
    }

    public void setRayTo(Vector3 rayTo) {
        this.rayTo = rayTo;
    }

    public ClosestRayResultCallback getRayTestCB() {
        return rayTestCB;
    }

    public void setRayTestCB(ClosestRayResultCallback rayTestCB) {
        this.rayTestCB = rayTestCB;
    }

    public Vector3 getTranslation() {
        return translation;
    }

    public void setTranslation(Vector3 translation) {
        this.translation = translation;
    }

    public Matrix4 getGhost() {
        return ghost;
    }

    public void setGhost(Matrix4 ghost) {
        this.ghost = ghost;
    }

    public AnimationService getAnimationService() {
        return animationService;
    }

    public void setAnimationService(AnimationService animationService) {
        this.animationService = animationService;
    }
}
