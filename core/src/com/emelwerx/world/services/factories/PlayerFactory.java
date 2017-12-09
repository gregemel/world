package com.emelwerx.world.services.factories;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector3;
import com.emelwerx.world.databags.Scene;
import com.emelwerx.world.databags.World;

public class PlayerFactory {

    public static Entity create(World world, Vector3 start) {
        Entity player = PlayerEntityFactory.create(world.getPhysicsSystem(), start.x, start.y, start.z);
        world.setPlayer(player);
        world.getEntityEngine().addEntity(player);
        Entity itemEntity = PlayerItemFactory.create("GUNMODEL", 2.5f, -1.9f, -4);
        world.setEntityPlayerItem(itemEntity);
        world.getEntityEngine().addEntity(itemEntity);
        world.getPlayerSystem().getPlayerSystemState().setVisibleItem(itemEntity);
        world.getRenderSystem().getRenderSystemState().setPlayersVisibleItem(itemEntity);
        return player;
    }

}
