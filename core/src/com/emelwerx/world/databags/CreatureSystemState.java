package com.emelwerx.world.databags;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

public class CreatureSystemState {
    private ImmutableArray<Entity> creatures;
    private Entity player;
    private World gameWorld;

    private Engine entityEngine;

    //this should come from the character component
    private Vector3 playerPosition = new Vector3();

    //this should come from the character component
    private Vector3 currentCreaturePosition = new Vector3();

    private Matrix4 ghost = new Matrix4();
    private Vector3 translation = new Vector3();
    private Quaternion quaternion = new Quaternion();
    private Random random = new Random();

    private ComponentMapper<CharacterComponent> cm = ComponentMapper.getFor(CharacterComponent.class);
    private ComponentMapper<CreatureComponent> sm = ComponentMapper.getFor(CreatureComponent.class);

    public ImmutableArray<Entity> getCreatures() {
        return creatures;
    }

    public void setCreatures(ImmutableArray<Entity> creatures) {
        this.creatures = creatures;
    }

    public Entity getPlayer() {
        return player;
    }

    public void setPlayer(Entity player) {
        this.player = player;
    }

    public Quaternion getQuaternion() {
        return quaternion;
    }

    public void setQuaternion(Quaternion quaternion) {
        this.quaternion = quaternion;
    }

    public Engine getEntityEngine() {
        return entityEngine;
    }

    public void setEntityEngine(Engine entityEngine) {
        this.entityEngine = entityEngine;
    }

    public World getGameWorld() {
        return gameWorld;
    }

    public void setGameWorld(World gameWorld) {
        this.gameWorld = gameWorld;
    }

    public Vector3 getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(Vector3 playerPosition) {
        this.playerPosition = playerPosition;
    }

    public Vector3 getCurrentCreaturePosition() {
        return currentCreaturePosition;
    }

    public void setCurrentCreaturePosition(Vector3 currentCreaturePosition) {
        this.currentCreaturePosition = currentCreaturePosition;
    }

    public Matrix4 getGhostMatrix() {
        return ghost;
    }

    public void setGhost(Matrix4 ghost) {
        this.ghost = ghost;
    }

    public Vector3 getTranslation() {
        return translation;
    }

    public void setTranslation(Vector3 translation) {
        this.translation = translation;
    }


    public ComponentMapper<CharacterComponent> getCm() {
        return cm;
    }

    public void setCm(ComponentMapper<CharacterComponent> cm) {
        this.cm = cm;
    }

    public ComponentMapper<CreatureComponent> getSm() {
        return sm;
    }

    public void setSm(ComponentMapper<CreatureComponent> sm) {
        this.sm = sm;
    }
}
