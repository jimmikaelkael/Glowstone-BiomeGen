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
        colors[id] = id > 128 ? lightenColor(color, 40) : color;
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
        set(10, FROZEN_OCEAN, makeColor(144, 144, 160)); // no longer generated
        set(11, FROZEN_RIVER, makeColor(160, 160, 255));
        set(12, ICE_PLAINS, makeColor(255, 255, 255));
        set(13, ICE_MOUNTAINS, makeColor(160, 160, 160));
        set(14, MUSHROOM_ISLAND, makeColor(255, 0, 255));
        set(15, MUSHROOM_SHORE, makeColor(160, 0, 255));
        set(16, BEACH, makeColor(250, 222, 85));
        set(17, DESERT_HILLS, makeColor(210, 95, 18));
        set(18, FOREST_HILLS, makeColor(34, 85, 28));
        set(19, TAIGA_HILLS, makeColor(22, 57, 51));
        set(20, SMALL_MOUNTAINS, makeColor(114, 120, 154)); // no longer generated
        set(21, JUNGLE, makeColor(83, 123, 9));
        set(22, JUNGLE_HILLS, makeColor(44, 66, 5));
        set(23, JUNGLE_EDGE, makeColor(98, 139, 23));
        set(24, DEEP_OCEAN, makeColor(0, 0, 48));
        set(25, STONE_BEACH, makeColor(162, 162, 132));
        set(26, COLD_BEACH, makeColor(250, 240, 192));
        set(27, BIRCH_FOREST, makeColor(48, 116, 68));
        set(28, BIRCH_FOREST_HILLS, makeColor(31, 95, 50));
        set(29, ROOFED_FOREST, makeColor(64, 81, 26));
        set(30, COLD_TAIGA, makeColor(49, 85, 74));
        set(31, COLD_TAIGA_HILLS, makeColor(36, 63, 54));
        set(32, MEGA_TAIGA, makeColor(89, 102, 81));
        set(33, MEGA_TAIGA_HILLS, makeColor(69, 79, 62));
        set(34, EXTREME_HILLS_PLUS, makeColor(80, 112, 80));
        set(35, SAVANNA, makeColor(189, 178, 95));
        set(36, SAVANNA_PLATEAU, makeColor(167, 157, 100));
        set(37, MESA, makeColor(217, 69, 21));
        set(38, MESA_PLATEAU_FOREST, makeColor(176, 151, 101));
        set(39, MESA_PLATEAU, makeColor(202, 140, 101));

        set(129, SUNFLOWER_PLAINS, makeColor(141, 179, 96));
        set(130, DESERT_MOUNTAINS, makeColor(250, 148, 24));
        set(131, EXTREME_HILLS_MOUNTAINS, makeColor(96, 96, 96));
        set(132, FLOWER_FOREST, makeColor(5, 102, 33));
        set(133, TAIGA_MOUNTAINS, makeColor(11, 102, 89));
        set(134, SWAMPLAND_MOUNTAINS, makeColor(7, 249, 178));
        set(140, ICE_PLAINS_SPIKES, makeColor(140, 180, 180));
        set(149, JUNGLE_MOUNTAINS, makeColor(83, 123, 9));
        set(151, JUNGLE_EDGE_MOUNTAINS, makeColor(98, 139, 23));
        set(155, BIRCH_FOREST_MOUNTAINS, makeColor(48, 116, 68));
        set(156, BIRCH_FOREST_HILLS_MOUNTAINS, makeColor(31, 95, 50));
        set(157, ROOFED_FOREST_MOUNTAINS, makeColor(64, 81, 26));
        set(158, COLD_TAIGA_MOUNTAINS, makeColor(49, 85, 74));
        set(160, MEGA_SPRUCE_TAIGA, makeColor(89, 102, 81));
        set(161, MEGA_SPRUCE_TAIGA_HILLS, makeColor(69, 79, 62));
        set(162, EXTREME_HILLS_PLUS_MOUNTAINS, makeColor(80, 112, 80));
        set(163, SAVANNA_MOUNTAINS, makeColor(189, 178, 95));
        set(164, SAVANNA_PLATEAU_MOUNTAINS, makeColor(167, 157, 100));
        set(165, MESA_BRYCE, makeColor(217, 69, 21));
        set(166, MESA_PLATEAU_FOREST_MOUNTAINS, makeColor(176, 151, 101));
        set(167, MESA_PLATEAU_MOUNTAINS, makeColor(202, 140, 101));
    }

    private static int makeColor(int r, int g, int b) {
        int color = 0xFF000000;
        color |= 0xFF0000 & (r << 16);
        color |= 0xFF00 & (g << 8);
        color |= 0xFF & b;
        return color;
    }

    private static int lightenColor(int color, int brightness) {
        int r = (color & 0x00FF0000) >> 16;
        int g = (color & 0x0000FF00) >> 8;
        int b = (color & 0x000000FF);
        r += brightness;
        g += brightness;
        b += brightness;
        if (r > 0xFF) r = 0xFF;
        if (g > 0xFF) g = 0xFF;
        if (b > 0xFF) b = 0xFF;
        return makeColor(r, g, b);
    }
}
