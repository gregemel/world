package com.emelwerx.world.services.factories;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.systems.CreatureSystem;
import com.emelwerx.world.systems.PhysicsSystem;
import com.emelwerx.world.systems.PlayerSystem;
import com.emelwerx.world.systems.RenderSystem;
import com.emelwerx.world.systems.UserInterfaceSystem;

public class EntityEngineFactory {

    public static Engine create(World world, UserInterfaceSystem userInterfaceSystem) {
        Engine entityEngine = new Engine();
        RenderSystem renderSystem = createRenderSystem(world, entityEngine);
        createPhysicsSystem(world, entityEngine);
        PerspectiveCamera worldPerspectiveCamera = renderSystem.getRenderSystemState().getWorldPerspectiveCamera();
        createPlayerSystem(world, entityEngine, worldPerspectiveCamera, userInterfaceSystem);
        createCreatureSystem(world, entityEngine);
        world.setEntityEngine(entityEngine);
        return entityEngine;
    }

    private static RenderSystem createRenderSystem(World world, Engine engine) {
        RenderSystem renderSystem = RenderSystemFactory.create(world);
        world.setRenderSystem(renderSystem);
        engine.addSystem(renderSystem);
        return renderSystem;
    }

    private static void createPhysicsSystem(World world, Engine engine) {
        PhysicsSystem physicsSystem = PhysicsSystemFactory.create();
        world.setPhysicsSystem(physicsSystem);
        engine.addSystem(physicsSystem);
        if (world.isDebug()) {
            btDiscreteDynamicsWorld collisionWorld = physicsSystem.getPhysicsSystemState().getCollisionWorld();
            collisionWorld.setDebugDrawer(world.getDebugDrawer());
        }
    }

    private static void createPlayerSystem(World world,
                                           Engine engine,
                                           PerspectiveCamera worldPerspectiveCamera,
                                           UserInterfaceSystem userInterfaceSystem) {
        PlayerSystem playerSystem = PlayerSystemFactory.create(
                world, userInterfaceSystem, worldPerspectiveCamera);
        world.setPlayerSystem(playerSystem);
        engine.addSystem(playerSystem);
    }

    private static void createCreatureSystem(World world, Engine engine) {
        CreatureSystem creatureSystem = CreatureSystemFactory.create(world);
        engine.addSystem(creatureSystem);
    }

}
