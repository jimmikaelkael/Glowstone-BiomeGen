package org.jimmikaelkael.glowstone.biomegen;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.block.Biome;

public class MountainsMapLayer extends MapLayer {

    private static final int[] ISLANDS = new int[] {GlowBiome.getId(Biome.PLAINS), GlowBiome.getId(Biome.FOREST)};
    private static final Map<Integer, int[]> map = new HashMap<Integer, int[]>();

    private final MapLayer belowLayer;
    private final MapLayer variationLayer;

    public MountainsMapLayer(long seed, MapLayer belowLayer, MapLayer variationLayer) {
        super(seed);
        this.belowLayer = belowLayer;
        this.variationLayer = variationLayer;
    }

    @Override
    public int[] generateValues(int x, int z, int sizeX, int sizeZ) {
        int gridX = x - 1;
        int gridZ = z - 1;
        int gridSizeX = sizeX + 2;
        int gridSizeZ = sizeZ + 2;

        int[] values = belowLayer.generateValues(gridX, gridZ, gridSizeX, gridSizeZ);
        int[] eValues = variationLayer.generateValues(gridX, gridZ, gridSizeX, gridSizeZ);

        int[] finalValues = new int[sizeX * sizeZ];
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                setCoordsSeed(x + j, z + i);
                int centerValue = values[j + 1 + (i + 1) * gridSizeX];
                int variationValue = eValues[j + 1 + (i + 1) * gridSizeX];
                if (centerValue != 0 && variationValue == 3 && centerValue < 128) {
                    finalValues[j + i * sizeX] = GlowBiome.fromId(centerValue + 128) != null ? centerValue + 128 : centerValue;
                } else if (variationValue == 2 || nextInt(3) == 0) {
                    int val = centerValue;
                    if (map.containsKey(centerValue)) {
                        val = map.get(centerValue)[nextInt(map.get(centerValue).length)];
                    } else if (centerValue == GlowBiome.getId(Biome.DEEP_OCEAN) && nextInt(3) == 0) {
                        val = ISLANDS[nextInt(ISLANDS.length)];
                    }
                    if (variationValue == 2 && val != centerValue) {
                        val = GlowBiome.fromId(val + 128) != null ? val + 128 : centerValue;
                    }
                    if (val != centerValue) {
                        int count = 0;
                        if (values[j + 1 + i * gridSizeX] == centerValue) { // upper value
                            count++;
                        }
                        if (values[j + 1 + (i + 2) * gridSizeX] == centerValue) { // lower value
                            count++;
                        }
                        if (values[j + (i + 1) * gridSizeX] == centerValue) { // left value
                            count++;
                        }
                        if (values[j + 2 + (i + 1) * gridSizeX] == centerValue) { // right value
                            count++;
                        }
                        // spread mountains if not too close from an edge
                        finalValues[j + i * sizeX] = count < 3 ? centerValue : val;
                    } else {
                        finalValues[j + i * sizeX] = val;
                    }
                } else {
                    finalValues[j + i * sizeX] = centerValue;
                }
            }
        }
        return finalValues;
    }

    static {
        map.put(GlowBiome.getId(Biome.DESERT), new int[] {GlowBiome.getId(Biome.DESERT_HILLS)});
        map.put(GlowBiome.getId(Biome.FOREST), new int[] {GlowBiome.getId(Biome.FOREST_HILLS)});
        map.put(GlowBiome.getId(Biome.BIRCH_FOREST), new int[] {GlowBiome.getId(Biome.BIRCH_FOREST_HILLS)});
        map.put(GlowBiome.getId(Biome.ROOFED_FOREST), new int[] {GlowBiome.getId(Biome.PLAINS)});
        map.put(GlowBiome.getId(Biome.TAIGA), new int[] {GlowBiome.getId(Biome.TAIGA_HILLS)});
        map.put(GlowBiome.getId(Biome.MEGA_TAIGA), new int[] {GlowBiome.getId(Biome.MEGA_TAIGA_HILLS)});
        map.put(GlowBiome.getId(Biome.COLD_TAIGA), new int[] {GlowBiome.getId(Biome.COLD_TAIGA_HILLS)});
        map.put(GlowBiome.getId(Biome.PLAINS), new int[] {GlowBiome.getId(Biome.FOREST), GlowBiome.getId(Biome.FOREST), GlowBiome.getId(Biome.FOREST_HILLS)});
        map.put(GlowBiome.getId(Biome.ICE_PLAINS), new int[] {GlowBiome.getId(Biome.ICE_MOUNTAINS)});
        map.put(GlowBiome.getId(Biome.JUNGLE), new int[] {GlowBiome.getId(Biome.JUNGLE_HILLS)});
        map.put(GlowBiome.getId(Biome.OCEAN), new int[] {GlowBiome.getId(Biome.DEEP_OCEAN)});
        map.put(GlowBiome.getId(Biome.EXTREME_HILLS), new int[] {GlowBiome.getId(Biome.EXTREME_HILLS_PLUS)});
        map.put(GlowBiome.getId(Biome.SAVANNA), new int[] {GlowBiome.getId(Biome.SAVANNA_PLATEAU)});
        map.put(GlowBiome.getId(Biome.MESA_PLATEAU_FOREST), new int[] {GlowBiome.getId(Biome.MESA)});
        map.put(GlowBiome.getId(Biome.MESA_PLATEAU), new int[] {GlowBiome.getId(Biome.MESA)});
    }
}
