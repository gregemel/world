package com.emelwerx.world.databags;


import com.badlogic.ashley.core.Entity;

public class Scene {
    private String name;
    private Entity sky;
    private Entity ground;
    private int maxSpawnCount;

    //create spawn spots

    //max number of concurrent creatures


    public int getMaxSpawnCount() {
        return maxSpawnCount;
    }

    public void setMaxSpawnCount(int maxSpawnCount) {
        this.maxSpawnCount = maxSpawnCount;
    }

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
