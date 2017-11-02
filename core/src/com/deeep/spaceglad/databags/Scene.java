package com.deeep.spaceglad.databags;


import com.badlogic.ashley.core.Entity;

public class Scene {
    private String name;
    private Entity sky;
    private Entity ground;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Entity getSky() {
        return sky;
    }

    public void setSky(Entity sky) {
        this.sky = sky;
    }

    public Entity getGround() {
        return ground;
    }

    public void setGround(Entity ground) {
        this.ground = ground;
    }
}
