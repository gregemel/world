package com.emelwerx.world.databags.systemstates;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.databags.components.CharacterComponent;
import com.emelwerx.world.databags.components.CreatureComponent;

public class CreatureSystemState {
    private ImmutableArray<Entity> creatures;
    private Entity player;
    private World world;
    private Vector3 playerPosition;
    private Vector3 currentCreaturePosition;
    private Matrix4 ghost;
    private Vector3 translation;
    private Quaternion quaternion;
    private ComponentMapper<CharacterComponent> characterComponentMapper = ComponentMapper.getFor(CharacterComponent.class);
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

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
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


    public ComponentMapper<CharacterComponent> getCharacterComponentMapper() {
        return characterComponentMapper;
    }

    public void setCharacterComponentMapper(ComponentMapper<CharacterComponent> characterComponentMapper) {
        this.characterComponentMapper = characterComponentMapper;
    }

    public ComponentMapper<CreatureComponent> getSm() {
        return sm;
    }

    public void setSm(ComponentMapper<CreatureComponent> sm) {
        this.sm = sm;
    }
}
