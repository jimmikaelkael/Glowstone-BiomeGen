package org.jimmikaelkael.glowstone.biomegen;

import java.util.HashMap;
import java.util.Map;

public class Biome {
    
    public static final Map<Integer,Biome> biomeMap = new HashMap<Integer,Biome>();
    
    public static final Biome[] biomes = new Biome[256];
    public static final Biome ocean = new Biome(0, makeColor(0, 0, 112));
    public static final Biome plains = new Biome(1, makeColor(141, 179, 96));
    
    public int index;
    public int color;
    
    public Biome(int index, int color) {
        biomes[index] = this;
        this.index = index;
        this.color = color;
        biomeMap.put(index, this);
    }

    public static int colorFromIndex(int id) {
        if (id > 255) {
            throw new IllegalArgumentException();
        }
        if (biomeMap.containsKey(id)) {
            return biomeMap.get(id).color;
        }
        return Biome.ocean.color;
    }

    public static int count() {
        return biomeMap.values().size();
    }

    public static Biome fromIndex(int id) {
        return biomeMap.get(id);
    }
   
    private static int makeColor(int r, int g, int b) {
        int color = 0xFF000000;
        color |= 0xFF0000 & (r << 16);
        color |= 0xFF00 & (g << 8);
        color |= 0xFF & b;
        return color;
    }
}