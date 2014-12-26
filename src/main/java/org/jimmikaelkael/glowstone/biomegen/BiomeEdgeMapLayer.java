package org.jimmikaelkael.glowstone.biomegen;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.block.Biome;

public class BiomeEdgeMapLayer extends MapLayer {

    private static final Map<Integer, Integer> MESA_EDGES = new HashMap<Integer, Integer>();
    private static final Map<Integer, Integer> MEGA_TAIGA_EDGES = new HashMap<Integer, Integer>();
    private static final Map<Integer, Integer> DESERT_EDGES = new HashMap<Integer, Integer>();
    private static final Map<Integer, Integer> SWAMP1_EDGES = new HashMap<Integer, Integer>();
    private static final Map<Integer, Integer> SWAMP2_EDGES = new HashMap<Integer, Integer>();
    private static final Map<Map<Integer, Integer>, Integer[]> EDGES = new HashMap<Map<Integer, Integer>, Integer[]>();

    private final MapLayer belowLayer;

    public BiomeEdgeMapLayer(long seed, MapLayer belowLayer) {
        super(seed);
        this.belowLayer = belowLayer;
    }

    @Override
    public int[] generateValues(int x, int z, int sizeX, int sizeZ) {
        int gridX = x - 1;
        int gridZ = z - 1;
        int gridSizeX = sizeX + 2;
        int gridSizeZ = sizeZ + 2;
        int[] values = belowLayer.generateValues(gridX, gridZ, gridSizeX, gridSizeZ);

        int[] finalValues = new int[sizeX * sizeZ];
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                // This applies biome large edges using Von Neumann neighborhood
                int centerVal = values[j + 1 + (i + 1) * gridSizeX];
                int val = centerVal;
                for (Entry<Map<Integer, Integer>, Integer[]> entry : EDGES.entrySet()) {
                    final Map<Integer, Integer> map = entry.getKey();
                    if (map.containsKey(centerVal)) {
                        int upperVal = values[j + 1 + i * gridSizeX];
                        int lowerVal = values[j + 1 + (i + 2) * gridSizeX];
                        int leftVal = values[j + (i + 1) * gridSizeX];
                        int rightVal = values[j + 2 + (i + 1) * gridSizeX];
                        if (entry.getValue() == null && (!map.containsKey(upperVal) || !map.containsKey(lowerVal) ||
                                !map.containsKey(leftVal) || !map.containsKey(rightVal))) {
                            val = map.get(centerVal);
                            break;
                        } else if (entry.getValue() != null) {
                            for (int v : entry.getValue()) {
                                if (v == upperVal || v == lowerVal || v == leftVal || v == rightVal) {
                                    val = map.get(centerVal);
                                    break;
                                }
                            }
                            if (val != centerVal) {
                                break;
                            }
                        }
                    }
                }

                finalValues[j + i * sizeX] = val;
            }
        }
        return finalValues;
    }

    static {
        MESA_EDGES.put(GlowBiome.getId(Biome.MESA_PLATEAU_FOREST), GlowBiome.getId(Biome.MESA));
        MESA_EDGES.put(GlowBiome.getId(Biome.MESA_PLATEAU), GlowBiome.getId(Biome.MESA));

        MEGA_TAIGA_EDGES.put(GlowBiome.getId(Biome.MEGA_TAIGA), GlowBiome.getId(Biome.TAIGA));

        DESERT_EDGES.put(GlowBiome.getId(Biome.DESERT), GlowBiome.getId(Biome.EXTREME_HILLS_PLUS));

        SWAMP1_EDGES.put(GlowBiome.getId(Biome.SWAMPLAND), GlowBiome.getId(Biome.PLAINS));
        SWAMP2_EDGES.put(GlowBiome.getId(Biome.SWAMPLAND), GlowBiome.getId(Biome.JUNGLE_EDGE));

        EDGES.put(MESA_EDGES, null);
        EDGES.put(MEGA_TAIGA_EDGES, null);
        EDGES.put(DESERT_EDGES, new Integer[] {GlowBiome.getId(Biome.ICE_PLAINS)});
        EDGES.put(SWAMP1_EDGES, new Integer[] {GlowBiome.getId(Biome.DESERT), GlowBiome.getId(Biome.COLD_TAIGA), GlowBiome.getId(Biome.ICE_PLAINS)});
        EDGES.put(SWAMP2_EDGES, new Integer[] {GlowBiome.getId(Biome.JUNGLE)});
    }
}
