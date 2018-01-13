package com.emelwerx.world.services.loaders;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonValue;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.services.factories.CreatureEntityFactory;

import java.util.Locale;

import static java.lang.String.format;

public class CreaturesLoader {

    public static void load(World world, JsonValue value) {
        for(JsonValue item: value.iterator()) {
            JsonValue goblin = item.get("goblin");
            if(goblin != null) {
                loadGoblin(world, goblin);
            }
        }
    }

    private static void loadGoblin(World world, JsonValue goblin) {
        String modelFilename = goblin.getString("modelFile");
        JsonValue location = goblin.get("startLocation");
        float x = location.getFloat("x");
        float y = location.getFloat("y");
        float z = location.getFloat("z");

        Gdx.app.log("CreaturesLoader", format(Locale.US, "load goblin %s, %.2f, %.2f, %.2f", modelFilename, x, y, z));
        Entity creatureEntity = CreatureEntityFactory.create(world, modelFilename, x, y, z);
        world.getEntityEngine().addEntity(creatureEntity);
    }
}