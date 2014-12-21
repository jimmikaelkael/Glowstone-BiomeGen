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
        set(7, RIVER, makeColor(0, 0, 255));
        set(10, FROZEN_OCEAN, makeColor(144, 144, 160));
        set(11, FROZEN_RIVER, makeColor(160, 160, 255));
        set(12, ICE_PLAINS, makeColor(255, 255, 255));
        set(14, MUSHROOM_ISLAND, makeColor(255, 0, 255));
        set(15, MUSHROOM_SHORE, makeColor(160, 0, 255));
        set(16, BEACH, makeColor(250, 222, 85));
        set(21, JUNGLE, makeColor(83, 123, 9));
        set(24, DEEP_OCEAN, makeColor(0, 0, 48));
        set(25, STONE_BEACH, makeColor(162, 162, 132));
        set(26, COLD_BEACH, makeColor(250, 240, 192));
        set(27, BIRCH_FOREST, makeColor(48, 116, 68));
        set(29, ROOFED_FOREST, makeColor(64, 81, 26));
        set(30, COLD_TAIGA, makeColor(49, 85, 74));
        set(35, SAVANNA, makeColor(189, 178, 95));
        set(37, MESA, makeColor(217, 69, 21));
    }

    private static int makeColor(int r, int g, int b) {
        int color = 0xFF000000;
        color |= 0xFF0000 & (r << 16);
        color |= 0xFF00 & (g << 8);
        color |= 0xFF & b;
        return color;
    }
}
