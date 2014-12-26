package org.jimmikaelkael.glowstone.biomegen;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.block.Biome;

public class ShoreMapLayer extends MapLayer {

    private static final Set<Integer> OCEANS = new HashSet<Integer>();
    private static final Map<Integer, Integer> SPECIAL_SHORES = new HashMap<Integer, Integer>();
    private final MapLayer belowLayer;

    public ShoreMapLayer(long seed, MapLayer belowLayer) {
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
                // This applies shores using Von Neumann neighborhood
                // it takes a 3x3 grid with a cross shape and analyzes values as follow
                // 0X0
                // XxX
                // 0X0
                // the grid center value decides how we are proceeding:
                // - if it's not ocean and it's surrounded by at least 1 ocean cell
                // it turns the center value into beach.
                int upperVal = values[j + 1 + i * gridSizeX];
                int lowerVal = values[j + 1 + (i + 2) * gridSizeX];
                int leftVal = values[j + (i + 1) * gridSizeX];
                int rightVal = values[j + 2 + (i + 1) * gridSizeX];
                int centerVal = values[j + 1 + (i + 1) * gridSizeX];
                //setCoordsSeed(x + j, z + i);
                if (!OCEANS.contains(centerVal) && (OCEANS.contains(upperVal) || OCEANS.contains(lowerVal) ||
                        OCEANS.contains(leftVal) || OCEANS.contains(rightVal))) {
                    finalValues[j + i * sizeX] =
                            SPECIAL_SHORES.containsKey(centerVal) ? SPECIAL_SHORES.get(centerVal) : GlowBiome.getId(Biome.BEACH);
                } else {
                    finalValues[j + i * sizeX] = centerVal;
                }
            }
        }
        return finalValues;
    }

    static {
        OCEANS.add(GlowBiome.getId(Biome.OCEAN));
        OCEANS.add(GlowBiome.getId(Biome.DEEP_OCEAN));

        SPECIAL_SHORES.put(GlowBiome.getId(Biome.EXTREME_HILLS), GlowBiome.getId(Biome.STONE_BEACH));
        SPECIAL_SHORES.put(GlowBiome.getId(Biome.EXTREME_HILLS_PLUS), GlowBiome.getId(Biome.STONE_BEACH));
        SPECIAL_SHORES.put(GlowBiome.getId(Biome.EXTREME_HILLS_MOUNTAINS), GlowBiome.getId(Biome.STONE_BEACH));
        SPECIAL_SHORES.put(GlowBiome.getId(Biome.EXTREME_HILLS_PLUS_MOUNTAINS), GlowBiome.getId(Biome.STONE_BEACH));
        SPECIAL_SHORES.put(GlowBiome.getId(Biome.ICE_PLAINS), GlowBiome.getId(Biome.COLD_BEACH));
        SPECIAL_SHORES.put(GlowBiome.getId(Biome.ICE_MOUNTAINS), GlowBiome.getId(Biome.COLD_BEACH));
        SPECIAL_SHORES.put(GlowBiome.getId(Biome.ICE_PLAINS_SPIKES), GlowBiome.getId(Biome.COLD_BEACH));
        SPECIAL_SHORES.put(GlowBiome.getId(Biome.COLD_TAIGA), GlowBiome.getId(Biome.COLD_BEACH));
        SPECIAL_SHORES.put(GlowBiome.getId(Biome.COLD_TAIGA_HILLS), GlowBiome.getId(Biome.COLD_BEACH));
        SPECIAL_SHORES.put(GlowBiome.getId(Biome.COLD_TAIGA_MOUNTAINS), GlowBiome.getId(Biome.COLD_BEACH));
        SPECIAL_SHORES.put(GlowBiome.getId(Biome.ICE_MOUNTAINS), GlowBiome.getId(Biome.COLD_BEACH));
        SPECIAL_SHORES.put(GlowBiome.getId(Biome.MUSHROOM_ISLAND), GlowBiome.getId(Biome.MUSHROOM_SHORE));
        SPECIAL_SHORES.put(GlowBiome.getId(Biome.SWAMPLAND), GlowBiome.getId(Biome.SWAMPLAND));
        SPECIAL_SHORES.put(GlowBiome.getId(Biome.MESA), GlowBiome.getId(Biome.MESA));
        SPECIAL_SHORES.put(GlowBiome.getId(Biome.MESA_PLATEAU_FOREST), GlowBiome.getId(Biome.MESA_PLATEAU_FOREST));
        SPECIAL_SHORES.put(GlowBiome.getId(Biome.MESA_PLATEAU_FOREST_MOUNTAINS), GlowBiome.getId(Biome.MESA_PLATEAU_FOREST_MOUNTAINS));
        SPECIAL_SHORES.put(GlowBiome.getId(Biome.MESA_PLATEAU), GlowBiome.getId(Biome.MESA_PLATEAU));
        SPECIAL_SHORES.put(GlowBiome.getId(Biome.MESA_PLATEAU_MOUNTAINS), GlowBiome.getId(Biome.MESA_PLATEAU_MOUNTAINS));
        SPECIAL_SHORES.put(GlowBiome.getId(Biome.MESA_BRYCE), GlowBiome.getId(Biome.MESA_BRYCE));
    }
}
