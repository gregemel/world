package com.emelwerx.world.services.loaders;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonValue;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.services.factories.CreatureEntityFactory;
import com.emelwerx.world.services.factories.ItemEntityFactory;

import java.util.Locale;

import static java.lang.String.format;

public class ItemsLoader {

    public static void load(World world, JsonValue value) {
        for(JsonValue item: value.iterator()) {
            JsonValue tableJson = item.get("table");
            if(tableJson != null) {
                loadTable(world, tableJson);
            }
        }
    }

    private static void loadTable(World world, JsonValue json) {
        String modelFilename = json.getString("modelFile");
        JsonValue location = json.get("startLocation");
        float x = location.getFloat("x");
        float y = location.getFloat("y");
        float z = location.getFloat("z");

        Gdx.app.log("ItemsLoader", format(Locale.US, "load table %s, %.2f, %.2f, %.2f", modelFilename, x, y, z));
        Entity creatureEntity = ItemEntityFactory.create(world, modelFilename, x, y, z);
        world.getEntityEngine().addEntity(creatureEntity);
    }
}