package org.jimmikaelkael.glowstone.biomegen;

import java.util.Arrays;

import org.bukkit.block.Biome;
import static org.bukkit.block.Biome.*;

public class GlowBiome {

    private static final int[] ids = new int[Biome.values().length];
    private static final int[] colors = new int[256];
    private static final Biome[] biomes = new Biome[256];

    public static int getId(org.bukkit.block.Biome biome) {
        return ids[biome.ordinal()];
    }

    public static int getColor(int id) {
        return colors[id];
    }

    private static void set(int id, org.bukkit.block.Biome biome, int color) {
        ids[biome.ordinal()] = id;
        colors[id] = color;
        biomes[id] = biome;
    }

    static {
        Arrays.fill(ids, -1);
        Arrays.fill(colors, 0);
        set(0, OCEAN, makeColor(0, 0, 112));
        set(1, PLAINS, makeColor(141, 179, 96));
        set(2, DESERT, makeColor(250, 148, 24));
        set(3, EXTREME_HILLS, makeColor(96, 96, 96));
        set(4, FOREST, makeColor(5, 102, 33));
        set(5, TAIGA, makeColor(11, 102, 89));
        set(6, SWAMPLAND, makeColor(7, 249, 178));
        set(16, BEACH, makeColor(250, 222, 85));
        set(24, DEEP_OCEAN, makeColor(0, 0, 48));
    }

    private static int makeColor(int r, int g, int b) {
        int color = 0xFF000000;
        color |= 0xFF0000 & (r << 16);
        color |= 0xFF00 & (g << 8);
        color |= 0xFF & b;
        return color;
    }
}
