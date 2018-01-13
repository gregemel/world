package com.emelwerx.world.services.loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;

import java.util.HashMap;

import static java.lang.String.format;

public class ModelCache {
     private static HashMap<String, Model> cache = new HashMap<String, Model>();

     public static Model get(String name, float scalar) {
         Model model = cache.get(name);
         if (model == null) {
             Gdx.app.log("ModelCache", format("cache fault %s", name));
             model = ModelLoader.load(name, scalar);
             cache.put(name, model);
         }
         return model;
     }
}
