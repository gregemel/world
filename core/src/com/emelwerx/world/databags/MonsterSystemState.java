package com.emelwerx.world.databags;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

public class MonsterSystemState {
    private ImmutableArray<Entity> monsters;
    private Entity player;
    private World gameWorld;

    private Engine entityEngine;

    //this should come from the character component
    private Vector3 playerPosition = new Vector3();

    //this should come from the character component
    private Vector3 currentMonsterPosition = new Vector3();

    private Matrix4 ghost = new Matrix4();
    private Vector3 translation = new Vector3();
    private Quaternion quaternion = new Quaternion();
    private Random random = new Random();

    private ComponentMapper<CharacterComponent> cm = ComponentMapper.getFor(CharacterComponent.class);
    private ComponentMapper<MonsterComponent> sm = ComponentMapper.getFor(MonsterComponent.class);

    public ImmutableArray<Entity> getMonsters() {
        return monsters;
    }

    public void setMonsters(ImmutableArray<Entity> monsters) {
        this.monsters = monsters;
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

    public Vector3 getCurrentMonsterPosition() {
        return currentMonsterPosition;
    }

    public void setCurrentMonsterPosition(Vector3 currentMonsterPosition) {
        this.currentMonsterPosition = currentMonsterPosition;
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

    public ComponentMapper<MonsterComponent> getSm() {
        return sm;
    }

    public void setSm(ComponentMapper<MonsterComponent> sm) {
        this.sm = sm;
    }
}
